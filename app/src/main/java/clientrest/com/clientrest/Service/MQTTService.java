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
import clientrest.com.clientrest.Agents.AnalyzeData;
import clientrest.com.clientrest.DataBase.Controller.Request_Controller;

public class MQTTService extends Service {


    private static String broker = "tcp://m13.cloudmqtt.com:19314";
    private static Context context;
    private static int TRAIN_MLP = 1;
    private static int SAVE_NEW_REQUEST = 2;
    private static int PROCESSING_REQUESTS = 3;
    private static int PUBLISH = 4;
    MemoryPersistence persistence;
    MqttClient mqttClient;

    public MQTTService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Subscribe();
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
            } else if (codeId == SAVE_NEW_REQUEST) {
                new SaveRequest().execute(intent.getExtras().getString("request"));
            } else if (codeId == PROCESSING_REQUESTS) {
                new AnalyzeData(context);
            } else if (codeId == PUBLISH) {
                new sendInformationConsumer().execute(intent.getExtras().getString("topic"), intent.getExtras().getString("reply"));

            }
        }
        return (super.onStartCommand(intent, flags, startId));
    }


    private void Publish(String topic, String content) {
        String clientId = MqttClient.generateClientId();
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            mqttClient.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage msg)
                        throws Exception {
                }

                public void deliveryComplete(IMqttDeliveryToken arg0) {
                }

                public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                }
            });

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("user");
            connOpts.setPassword("user".toCharArray());
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(1);
            mqttClient.publish(topic, message);
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    private void Subscribe() {
        persistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(broker, "134679", persistence);
            mqttClient.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage msg) throws Exception {

                    Intent intent = new Intent(context, MQTTService.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("CODE", SAVE_NEW_REQUEST);
                    mBundle.putString("request", msg.toString());
                    intent.putExtras(mBundle);
                    startService(intent);
                }

                public void deliveryComplete(IMqttDeliveryToken arg0) {
                }

                public void connectionLost(Throwable arg0) {
                    Subscribe();
                }
            });

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("user");
            connOpts.setPassword("user".toCharArray());
            mqttClient.connect(connOpts);
            MqttMessage message = new MqttMessage("".getBytes());
            message.setQos(1);
            mqttClient.subscribe("request", 1);

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
        HttpURLConnection con = null;
        try {
            String stringUrl = "http://api.cloudmqtt.com/acl";
            URL myurl = new URL(stringUrl);
            con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            con.setRequestProperty("Authorization", "Basic " + context.getResources().getString(R.string.authentication));
            OutputStream os = con.getOutputStream();
            os.write(str.getBytes());
            os.close();

            int HttpResult = con.getResponseCode();
            if (HttpResult == 204) {
                Log.i("MQTTService", "Porta criado com sucesso!");
            } else {
                Log.i("MQTTService", "HttpURLConnectionCode:" + con.getResponseCode());
                Log.i("MQTTService", "HttpURLConnectionMessage:" + con.getResponseMessage());
            }
        } catch (IOException ex) {
            Log.i("MQTTService", "HttpURLConnection ERROR:" + ex.toString());
        } finally {
            con.disconnect();
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

                Request_Controller request_controller = new Request_Controller(context);
                request_controller.saveRequest(param[0]);
                Intent intent = new Intent(context, MQTTService.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("CODE", PROCESSING_REQUESTS);
                intent.putExtras(mBundle);

                startService(intent);
            } else {
                Log.i("MQTTService", "request Existe");//fazer quando ele ja existe
            }
            //showNotification();
            return ret;
        }
    }

    private class Training_MultilayerPerceptron extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... param) {
            MLP mlp = new MLP(context);
            mlp.RetrainMLP();
            return null;
        }

    }

    private class sendInformationConsumer extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... param) {
            Publish(param[0], param[1]);
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
