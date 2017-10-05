package clientrest.com.clientrest.DataBase.Controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Consumer;
import clientrest.com.clientrest.DataBase.Entity.ConsumerAttributes;
import clientrest.com.clientrest.DataBase.Entity.Data;
import clientrest.com.clientrest.DataBase.Entity.DataAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.R;

/**
 * Created by Fagner Roger on 02/10/2017.
 */

public class Request_Controller {

    private final static String TAG = "Request_Controller";
    private DBHelper database;
    private Context context;

    public Request_Controller(Context context) {
        this.database = new DBHelper(context);
        this.context = context;
    }

    public void saveRequest(String obj) {
        try {
            Request request = new Request();
            JSONObject jsonObject = new JSONObject(obj);
            request.setLocation(jsonObject.getString("location"));
            request.setReason(jsonObject.getString("reason"));
            request.setUuid(jsonObject.getString("uuid"));
            request.setDataId(saveData(obj));
            request.setConsumerId(saveConsumer(obj));
            database.saveRequest(request);
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    private Data saveData(String obj) {
        try {
            JSONObject jsonObject = new JSONObject(obj);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Data data = new Data();
            data.setDataId(database.saveData(data));
            List<DataAttributes> dataAttributesList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                DataAttributes dataAttributes = new DataAttributes();
                dataAttributes.setDataId(data.getDataId());
                dataAttributes.setAttribute(jsonArray.getJSONObject(i).getString("attribute"));
                dataAttributes.setRetention(jsonArray.getJSONObject(i).getString("retention"));
                dataAttributes.setShared((jsonArray.getJSONObject(i).getBoolean("shared")) ? 1 : 0);
                database.saveDataAttributes(dataAttributes);
                dataAttributesList.add(dataAttributes);
            }
            data.setDataAttributesList(dataAttributesList);
            return data;
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    private Consumer saveConsumer(String obj) {
        try {
            JSONObject jsonObject = new JSONObject(obj);
            JSONArray jsonArray = jsonObject.getJSONArray("consumer");

            Consumer consumer = new Consumer();
            consumer.setConsumerId(database.saveConsumer(consumer));
            List<ConsumerAttributes> consumerAttributesList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                ConsumerAttributes consumerAttributes = new ConsumerAttributes();
                consumerAttributes.setConsumerId(consumer.getConsumerId());
                consumerAttributes.setAttribute(jsonArray.getJSONObject(i).getString("attribute"));
                consumerAttributes.setValue(jsonArray.getJSONObject(i).getString("value"));
                database.saveConsumerAttributes(consumerAttributes);
                consumerAttributesList.add(consumerAttributes);
            }
            consumer.setConsumerAttributesList(consumerAttributesList);
            return consumer;
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return null;
    }
}
