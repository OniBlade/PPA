package clientrest.com.clientrest.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import clientrest.com.clientrest.*;
import clientrest.com.clientrest.Activity.MainActivity;
import clientrest.com.clientrest.DataBase.Controller.Request_Controller;
import clientrest.com.clientrest.DataBase.DBHelper;

public class MQTTService extends Service {


    private static String broker = "tcp://m13.cloudmqtt.com:19314";
    private static String password = "consumer";
    private static String userName = "consumer";
    private static String topic = "Solicitar";
    private static String content = "Solicitação Teste";
    private static String clientId = "";
    private static Context context;
    private Request_Controller request_controller;
    private static int TRAIN_MLP = 1;
    private static int SAVE_NEW_REQUEST = 2;
    private static int PROCESSING_REQUESTS = 3;
    MemoryPersistence persistence;
    MqttClient mqttClient;

    public MQTTService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        subscribe();
        request_controller = new Request_Controller(context);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            int codeId = intent.getExtras().getInt("CODE");
            if (codeId == TRAIN_MLP) {
                new Training_MultilayerPerceptron().execute();
            } else {
                if (codeId == SAVE_NEW_REQUEST) {
                    new SaveRequest().execute(intent.getExtras().getString("request"));
                } else {
                    if (codeId == PROCESSING_REQUESTS) {
                        new ProcessRequest(context);
                    }
                }
            }

        }
        return (super.onStartCommand(intent, flags, startId));
    }

    private void subscribe() {
        persistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(broker, clientId, persistence);
            mqttClient.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage msg) throws Exception {
                    Log.i("MQTTService", "messageArrived");
                    Intent it = new Intent(context, MQTTService.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("CODE", SAVE_NEW_REQUEST);
                    mBundle.putString("request", msg.toString());
                    it.putExtras(mBundle);
                    startService(it);
                }

                public void deliveryComplete(IMqttDeliveryToken arg0) {
                    Log.i("MQTTService", "deliveryComplete");
                }

                public void connectionLost(Throwable arg0) {
                    Log.i("MQTTService", "connectionLost");
                    subscribe();
                }
            });

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(1);
            mqttClient.subscribe(topic, 1);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void GenerateReturnPort(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject requestJson = null;
            requestJson = new JSONObject();
            requestJson.put("username", "consumer");
            requestJson.put("topic", jsonObject.getString("uuid"));
            requestJson.put("read", true);
            requestJson.put("write", false);

            CreateACLByUser(requestJson.toString());
        } catch (JSONException e) {
            Log.i("MQTTService", "GenerateReturnPort" + e.toString());
        }

    }

    private void CreateACLByUser(String str) {
        try {
            String stringUrl = "http://api.cloudmqtt.com/acl";

            URL myurl = new URL(stringUrl);
            HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            con.setRequestProperty("Authorization", "Basic " + context.getResources().getString(R.string.authentication));
            OutputStream os = con.getOutputStream();
            os.write(str.getBytes());
            os.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == 204) {
                Log.i("MQTTService", "Porta criado com sucesso!");
            } else {
                Log.i("MQTTService", "HttpURLConnectionCode:" + con.getResponseCode());
                Log.i("MQTTService", "HttpURLConnectionMessage:" + con.getResponseMessage());
            }
        } catch (IOException ex) {
            Log.i("MQTTService", "HttpURLConnection ERROR:" + ex.toString());
        }
    }

    private void showNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_18dp) // notification icon
                .setContentTitle("Solicitação!") // title for notification
                .setContentText("Fagner Lindão") // message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.getNotification());
    }

    private class SaveRequest extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... param) {
            boolean ret = false;
            if (!CheckRequest(param[0])) {
                GenerateReturnPort(param[0]);
                request_controller.saveRequest(param[0]);
                Intent it = new Intent(context, MQTTService.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("CODE", PROCESSING_REQUESTS);
                it.putExtras(mBundle);
                startService(it);
            } else {
                Log.i("MQTTService", "request Existe");
            }
            showNotification();
            return ret;
        }
    }

    private class Training_MultilayerPerceptron extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... param) {
            new MLP(context);
            return null;
        }
    }

    private boolean CheckRequest(String obj) {
        try {
            JSONObject jsonObject = new JSONObject(obj);
            String request_id = jsonObject.getString("request_id");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}