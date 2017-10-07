package clientrest.com.clientrest.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import clientrest.com.clientrest.Agents.AnalyzeData;
import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.DataBase.Entity.TrainingSet;

/**
 * Created by Fagner Roger on 03/10/2017.
 */

public class ProcessRequest {

    private final static String TAG = "ProcessRequest";
    private Context context;
    private String trainingFileFormattedArff;

    public ProcessRequest(Context context) {
        this.context = context;
        new AnalyzeAndProcessRequest().execute();
    }

    public Context getContext() {
        return context;
    }

    public String getTrainingFileFormattedArff() {
        return trainingFileFormattedArff;
    }

    public void setTrainingFileFormattedArff(String trainingFileFormattedArff) {
        this.trainingFileFormattedArff = trainingFileFormattedArff;
    }

    private class AnalyzeAndProcessRequest extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... param) {
            DBHelper database = new DBHelper(getContext());
            boolean flag = false;
            MLP mlp = null;
            List<Request> requestList = database.getListRequestNotProcessed();
            List<TrainingSet> trainingSetList = database.getListTrainingSeT();
            for (int i = 0; i < requestList.size(); i++) {
                AnalyzeData analyzesData = new AnalyzeData();
                for (int j = 0; j < requestList.get(i).getDataId().getDataAttributesList().size(); j++) { //todos atributos
                    if (analyzesData.thisDataExists(requestList.get(i).getDataId().getDataAttributesList().get(j).getAttribute())) { //verifica se existe no banco de dados
                        if (database.isExistInTrainingSet("data_type", requestList.get(i).getDataId().getDataAttributesList().get(j).getAttribute())) { //verifica se existe na base de treinamento
                            mlp = new MLP(context, getTrain_testArff(requestList.get(i), j));
                            if (database.saveInferred_Decision(requestList.get(i), j, mlp.getPrediction(), true)) {
                                flag = true;
                            }
                            Log.i(TAG, "Existe na base de treinamento");
                        } else {
                            flag = database.saveInferred_Decision(requestList.get(i), j, mlp.getPrediction(), false);
                            Log.i(TAG, "não contem na base de treinamento");
                        }
                    } else {
                        flag = database.saveInferred_Decision(requestList.get(i), j, mlp.getPrediction(), false);
                        Log.i(TAG, "Dado não contem no banco de dados externo");
                    }
                }
                if (flag) {
                    Log.i(TAG, "updateCheckUserRequest");
                    database.updateCheckUserRequest(requestList.get(i));
                }else{
                    Log.i(TAG, "não precisa prever");
                    Log.i(TAG, "getCheckUser"+requestList.get(i).getCheckUser());
                    database.updateRequestStatus(requestList.get(i),true);
                }
                flag = false;
            }
            return null;
        }
    }

    /**
     * @Param request
     * @ j is the position of the array that contains the attribute that will be inferred
     **/
    private String getTrain_testArff(Request request, int j) {
        StringBuilder arff = new StringBuilder();
        String delimiter = ",";
        arff.append(request.getConsumerId().getConsumerId() + delimiter);
        arff.append(request.getDataId().getDataAttributesList().get(j).getAttribute() + delimiter);
        arff.append("user" + delimiter);//request.getDataId().getDataAttributesList().get(j).getUser_benefit()
        arff.append(request.getDataId().getDataAttributesList().get(j).getRetention() + delimiter);
        arff.append(request.getLocation() + delimiter);
        arff.append(getStringParam(request.getDataId().getDataAttributesList().get(j).getShared()) + delimiter);
        arff.append(getStringParam(request.getDataId().getDataAttributesList().get(j).getInferred()) + delimiter);
        arff.append("deny");
        return arff.toString();
    }

    private String getStringParam(int shared) {
        return  (shared == 0)?"no":"yes";
    }


}
