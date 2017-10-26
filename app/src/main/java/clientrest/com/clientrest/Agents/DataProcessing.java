package clientrest.com.clientrest.Agents;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import clientrest.com.clientrest.DataBase.DAO.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Address;
import clientrest.com.clientrest.DataBase.Entity.City;
import clientrest.com.clientrest.DataBase.Entity.Point;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.DataBase.Entity.State;
import clientrest.com.clientrest.DataBase.Manager.AddressManager;
import clientrest.com.clientrest.Service.MQTTService;
import clientrest.com.clientrest.Uteis.StringSimilarity;

/**
 * Created by Fagner Roger on 11/10/2017.
 */

public class DataProcessing {
    private static int PUBLISH = 4;
    private Context context;
    private Request request;
    private int cont;


    public int getCont() {
        return cont;
    }

    public void setRequest(Request request, int i) {
        this.cont = i;
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public DataProcessing(Context context, Request request) {
        this.context = context;


        if (request.getCheckUser() == 1) { // USER
            for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                if (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getState() == 3) { //NEGOTIATOR
                    setRequest(request, i);
                    new AnonymizeAttribute().execute();
                }
            }
        } else { //mecanismo
            for (int i = 0; i < request.getInferredDecisionId().getInferredDecisionAttributesList().size(); i++) {
                if (request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getState() == 3) {
                    setRequest(request, i);
                    new AnonymizeAttribute().execute();
                }
            }
        }

    }


    private class AnonymizeAttribute extends AsyncTask<Void, Void, String> {

        DBHelper database;
        Request request;
        int cont;
        String attribute;
        String value;

        @Override
        protected void onPreExecute() {
            this.database = new DBHelper(context);
            this.cont = getCont();
            this.request = getRequest();

            if (request.getCheckUser() == 1) {
                attribute = request.getUserDecisionId().getUserDecisionAttributesList().get(cont).getDataAtttributeId().getAttribute();
                value = request.getUserDecisionId().getUserDecisionAttributesList().get(cont).getInformation();
            } else {
                attribute = request.getInferredDecisionId().getInferredDecisionAttributesList().get(cont).getDataAttributes().getAttribute();
                value = database.getThingsAttribute(request.getInferredDecisionId().getInferredDecisionAttributesList().get(cont).getDataAttributes().getAttribute());
            }
        }


        @Override
        protected String doInBackground(Void... param) {
            attribute = attribute.toLowerCase();
            if (attribute.equals("idade")) {
                return AnonymizeAge(value);
            } else if (attribute.equals("rua")) {
                return AnonymizeStreet(value);
            } else if (attribute.equals("bairro")) {
                return AnonymizeNeighborhood(value);
            } else if (attribute.equals("cidade")) {
                return AnonymizeCity(value);
            } else if (attribute.equals("estado")) {
                return AnonymizeState(value);
            } else if (attribute.equals("gps")) {
                return AnonymizeLocation(value);
            } else if (attribute.equals("sexo")) {
                return "indefinido";
            } else {
                return Mask(value, new Random().nextInt());
            }
        }

        @Override
        protected void onPostExecute(String information) {
            if (request.getCheckUser() == 1) {
                request.getUserDecisionId().getUserDecisionAttributesList().get(cont).setAnonymised_information(information);
                database.updateUserDecisionAttributes(request.getUserDecisionId().getUserDecisionAttributesList().get(cont));
            } else {
                request.getInferredDecisionId().getInferredDecisionAttributesList().get(cont).setAnonymised_information(information);
                database.updateInferredDecision(request.getInferredDecisionId().getInferredDecisionAttributesList().get(cont));

            }
            sendReplyConsumer(request, context);
            super.onPostExecute(information);
        }

    }


    private void sendReplyConsumer(Request request, Context context) {
        DBHelper database = new DBHelper(context);
        Intent intent = new Intent(context, MQTTService.class);
        Bundle mBundle2 = new Bundle();
        mBundle2.putInt("CODE", PUBLISH);
        mBundle2.putString("reply", database.getConsumerResponseNegotiated(request));
        mBundle2.putString("topic", request.getUuid());
        intent.putExtras(mBundle2);
        context.startService(intent);
    }

    static char randomChar(Random r, String cs, boolean uppercase) {
        char c = cs.charAt(r.nextInt(cs.length()));
        return uppercase ? Character.toUpperCase(c) : c;
    }

    static String Mask(String str, int seed) {

        final String cons = "bcdfghjklmnpqrstvwxz";
        final String vowel = "aeiouy";
        final String digit = "0123456789";

        Random r = new Random(seed);
        char data[] = str.toCharArray();

        for (int n = 0; n < data.length; ++n) {
            char ln = Character.toLowerCase(data[n]);
            if (cons.indexOf(ln) >= 0) {
                data[n] = randomChar(r, cons, ln != data[n]);
            } else if (vowel.indexOf(ln) >= 0) {
                data[n] = randomChar(r, vowel, ln != data[n]);
            } else if (digit.indexOf(ln) >= 0) {
                data[n] = randomChar(r, digit, ln != data[n]);
            }
        }

        return new String(data);
    }


    private String AnonymizeState(String value) {
        StringSimilarity distance = new StringSimilarity();
        Double acceptedLevel = 0.85;
        AddressManager addressManager = new AddressManager(context);
        List<State> estadosList = addressManager.getStateList();
        for (State estado : estadosList) {
            if (distance.similarity(estado.getName().toLowerCase(), (value.toLowerCase())) > acceptedLevel) {
                return estado.getCountry();
            }
        }

        return "não encontrado";
    }

    private String AnonymizeCity(String value) {
        StringSimilarity distance = new StringSimilarity();
        Double acceptedLevel = 0.85;
        AddressManager addressManager = new AddressManager(context);
        List<City> cidadesList = addressManager.getCityList();

        for (City cidade : cidadesList) {
            if (distance.similarity(cidade.getName().toLowerCase(), (value.toLowerCase())) > acceptedLevel) {
                return cidade.getState().getName();
            }
        }

        return "não encontrado";
    }

    private String AnonymizeNeighborhood(String value) {
        Double acceptedLevel = 0.85;
        StringSimilarity distance = new StringSimilarity();
        AddressManager addressManager = new AddressManager(context);
        List<Address> enderecosList = addressManager.getAddressList();

        for (Address endereco : enderecosList) {
            if (distance.similarity(endereco.getNeighborhood().toLowerCase(), (value.toLowerCase())) > acceptedLevel) {
                return endereco.getCity();
            }
        }
        return "não encontrado";
    }

    private String AnonymizeStreet(String value) {
        Double acceptedLevel = 0.85;
        StringSimilarity distance = new StringSimilarity();
        AddressManager addressManager = new AddressManager(context);
        List<Address> adressList = addressManager.getAddressList();

        for (Address adress : adressList) {
            if (distance.similarity(adress.getAdress().toLowerCase(), (value.toLowerCase())) > acceptedLevel) {
                return adress.getNeighborhood();
            }
        }
        return "não encontrado";
    }

    private static String AnonymizeAge(String value) {
        Random r = new Random();
        int Result = r.nextInt(6 - 2) + 2;
        int age = Integer.valueOf(value);
        return (age - Result) + "-" + (age + Result);
    }

    private static String AnonymizeLocation(String value) {
        String lat = "";
        String lon = "";
        Point point;
        boolean flag = true;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == ',') {
                flag = false;
            } else {
                if (flag) {
                    lat = lat + value.charAt(i);
                } else {
                    lon = lon + value.charAt(i);
                }
            }
        }
        point = accuracyAdjustment(Double.valueOf(lat), Double.valueOf(lon), 250);
        return point.getLat() + "," + point.getLon();
    }

    private static Point accuracyAdjustment(Double lat, double lon, int distance) {

        double earthRadius = 6371;

        int degrees = (int) (Math.random() * 180);
        int minutes = (int) (Math.random() * 60);
        int seconds = (int) (Math.random() * 60);

        double direction = degrees + ((double) minutes) / 60 + ((double) seconds) / 3600;

        double numAleatorio = Math.random();
        if (numAleatorio > 0.5) {
            direction = direction + (-1);
        }

        // int dAleatorio = (int) (Math.random() * distance);
        double d = distance * 0.001;  //round((dAleatorio * 0.001),3);

        double lat1 = Math.toRadians(lat);
        double lon1 = Math.toRadians(lon);
        double brng = Math.toRadians(direction);
        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / earthRadius)
                + Math.cos(lat1) * Math.sin(d / earthRadius) * Math.cos(brng)
        );
        double lon2 = Math.atan2(Math.sin(brng) * Math.sin(d / earthRadius) * Math.cos(lat1),
                Math.cos(d / earthRadius) - Math.sin(lat1) * Math.sin(lat2));
        lon2 = (lon1 - lon2 + Math.PI) % (2 * Math.PI) - Math.PI;

        return new Point(Math.toDegrees(lat2), Math.toDegrees(lon2));
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
