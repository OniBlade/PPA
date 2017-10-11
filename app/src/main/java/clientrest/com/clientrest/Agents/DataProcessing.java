package clientrest.com.clientrest.Agents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.Service.MQTTService;

/**
 * Created by Fagner Roger on 11/10/2017.
 */

public class DataProcessing {
    private static int PUBLISH = 4;

    public DataProcessing(Context context, Request request) {
        DBHelper database = new DBHelper(context);
        if (request.getCheckUser() == 1) { // USER
            for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                if (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getState() == 3) { //NEGOTIATOR
                    request.getUserDecisionId().getUserDecisionAttributesList().get(i).setAnonymised_information(AnonymizeInformation(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getInformation()));
                    database.updateUserDecisionAttributes(request.getUserDecisionId().getUserDecisionAttributesList().get(i));
                }
            }
        } else { //mecanismo
            for (int i = 0; i < request.getInferredDecisionId().getInferredDecisionAttributesList().size(); i++) {
                if(request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getState() ==3){
                    String information  = AnonymizeInformation(database.getThingsAttribute(request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getDataAttributes().getAttribute()));
                    request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).setAnonymised_information(information);
                    database.updateInferredDecision(request.getInferredDecisionId().getInferredDecisionAttributesList().get(i));
                }
            }
        }
        sendReplyConsumer(request, context);

    }

    private String AnonymizeInformation(String data) {
        return data+" anonimizado";
    }

    private void sendReplyConsumer(Request request, Context context){
        DBHelper database = new DBHelper(context);
        Intent intent = new Intent(context, MQTTService.class);
        Bundle mBundle2 = new Bundle();
        mBundle2.putInt("CODE", PUBLISH);
        mBundle2.putString("reply", database.getConsumerResponseNegotiated(request));
        mBundle2.putString("topic",request.getUuid());
        intent.putExtras(mBundle2);
        context.startService(intent);
    }
}
