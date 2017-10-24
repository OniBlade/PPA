package clientrest.com.clientrest.Agents;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Request;

/**
 * Created by Fagner Roger on 11/10/2017.
 */

public class Negotiator {
    private Context context;

    public Negotiator(Context context, String obj) {
        try {
            boolean error = false;
            JSONObject jsonObject = new JSONObject(obj);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            DBHelper database = new DBHelper(context);
            Request request = database.getRequest(jsonObject.getInt("request_code"));

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject itemObject = dataArray.getJSONObject(i);
                if (!checksAttributeExistsRequest(request, itemObject.getString("attribute"))) {
                    error = true;// o atributo não existe na solicitação
                }
            }

            if (!error){
                new DataProcessing(context, request);
            }else{
                //publica o erro
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checksAttributeExistsRequest(Request request, String item) {
        for (int i = 0; i < request.getDataId().getDataAttributesList().size(); i++) {
            if (request.getDataId().getDataAttributesList().get(i).getAttribute().equals(item)) {
                return true;
            }
        }
        return false;
    }
}
