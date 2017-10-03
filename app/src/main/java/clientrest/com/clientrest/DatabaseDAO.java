package clientrest.com.clientrest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Fagner Roger on 16/09/2017.
 */

public class DatabaseDAO {


    private String Host;
    private String Port;
    private Context context;

    public DatabaseDAO(Context context){
        this.context = context;
        setParametrosConexao();
    }

    private void setParametrosConexao() {
        final SharedPreferences sharedPreferences = context.getSharedPreferences("Conexao", Context.MODE_PRIVATE);
        Host = sharedPreferences.getString("Host", "");
        Port = sharedPreferences.getString("Port", "");
    }

    public boolean checkConnection(String host, String port) {
        try {
            return new TaskcheckConnection().execute(host, port).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void FinalizarNotificao(boolean status, JSONObject jsonObject) {
        try {
            new TaskFinalizarNotificao().execute(context.getResources().getString(R.string.collection), jsonObject.toString(),String.valueOf(status)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class TaskFinalizarNotificao extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            try {
                if (params[0] != "") {
                    MongoClientURI connectionString = new MongoClientURI("mongodb://" + Host + ":"+Port);
                    MongoClient mongoClient = new MongoClient(connectionString);

                    MongoDatabase db = mongoClient.getDatabase(context.getResources().getString(R.string.nome_banco));
                    MongoCollection<Document> collection = db.getCollection(params[0]);

                    JSONObject obj = null;
                    Document doc = null;
                    try {
                        obj = new JSONObject(params[1]);

                        obj.remove(context.getResources().getString(R.string.objeto_verificar_usuario));
                        obj.put(context.getResources().getString(R.string.objeto_verificar_usuario), Boolean.valueOf(params[2]));

                        doc = doc.parse(obj.toString());

                        BasicDBObject ant = new BasicDBObject();
                        ant.put("_id", new ObjectId(getNumeroPedido(obj)));

                        collection.findOneAndReplace(ant, doc);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return "";
            } catch (Exception e) {
                return null;
            }
        }
    }

    private class TaskSalvarDecisaoAtributo extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            try {
                MongoClientURI connectionString = new MongoClientURI("mongodb://" + Host + ":"+Port);
                MongoClient mongoClient = new MongoClient(connectionString);

                MongoDatabase db = mongoClient.getDatabase(context.getResources().getString(R.string.nome_banco));
                MongoCollection<Document> collection = db.getCollection(params[0]);

                JSONObject jsonOriginal = null;
                JSONObject objAtributoOriginal = null;
                Document doc = null;
                try {
                    jsonOriginal = new JSONObject(params[1]);
                    objAtributoOriginal = new JSONObject(params[2]);

                    /*-----------Atributo que chegou--------------------*/
                    JSONObject objAtributo = new JSONObject();
                    objAtributo.put(context.getResources().getString(R.string.objeto_field) + params[3], objAtributoOriginal.get(context.getResources().getString(R.string.objeto_field) + params[3]));
                    objAtributo.put(context.getResources().getString(R.string.objeto_decisao_usuario_atributo_codigo), params[4]);
                    objAtributo.put(context.getResources().getString(R.string.objeto_decisao_usuario_atributo_descricao), ToStringCodDecision(params[4]));

                    if(!getContemDado(objAtributoOriginal)){
                        objAtributo.put(context.getResources().getString(R.string.objeto_decisao_usuario_atributo_valor), params[5]);
                    }
                    /*------------------------------------------------*/

                    if (jsonOriginal.isNull(context.getResources().getString(R.string.objeto_decisao_usuario))){
                        JSONArray array = new JSONArray();
                        array.put(objAtributo);

                        JSONObject objDecision = new JSONObject();
                        objDecision.put(context.getResources().getString(R.string.objeto_decisao_usuario_atributo), array);

                        JSONObject ibjTeste = new JSONObject();
                        ibjTeste.put(context.getResources().getString(R.string.objeto_decisao_usuario), objDecision);
                        jsonOriginal.put(context.getResources().getString(R.string.objeto_decisao_usuario), objDecision);


                    } else {
                        JSONObject obj1 = jsonOriginal.getJSONObject(context.getResources().getString(R.string.objeto_decisao_usuario));
                        JSONArray aux = obj1.getJSONArray(context.getResources().getString(R.string.objeto_decisao_usuario_atributo));

                        JSONArray array = new JSONArray();
                        boolean isExist = false;

                        for (int i = 0; i < aux.length(); i++) {
                            try {
                                if (aux.getJSONObject(i).getString(context.getResources().getString(R.string.objeto_field) + params[3]) != null) {
                                    array.put(objAtributo);
                                    isExist = true;
                                }
                            } catch (JSONException e) {
                                array.put(aux.getJSONObject(i));
                            }
                        }

                        if (!isExist) {
                            array.put(objAtributo);
                        }

                        JSONObject objDecision = new JSONObject();
                        objDecision.put(context.getResources().getString(R.string.objeto_decisao_usuario_atributo), array);

                        JSONObject ibjTeste = new JSONObject();
                        ibjTeste.put(context.getResources().getString(R.string.objeto_decisao_usuario), objDecision);

                        jsonOriginal.remove(context.getResources().getString(R.string.objeto_decisao_usuario));
                        jsonOriginal.put(context.getResources().getString(R.string.objeto_decisao_usuario), objDecision);


                    }
                    doc = doc.parse(jsonOriginal.toString());

                    BasicDBObject ant = new BasicDBObject();
                    ant.put("_id", new ObjectId(getNumeroPedido(jsonOriginal)));

                    collection.findOneAndReplace(ant, doc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return "";
            } catch (MongoSocketOpenException e) {
                e.printStackTrace();
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    private String getNumeroPedido(JSONObject jsonObject) {
        try {
            JSONObject idObj = jsonObject.getJSONObject("_id");
            return (String) idObj.get("$oid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean getContemDado(JSONObject obj) {
        try {
            return obj.getBoolean(context.getResources().getString(R.string.objeto_decisao_inferida_atributo_contem));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class TaskcheckConnection extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... params) {
            try {
                MongoClientURI connectionString = new MongoClientURI("mongodb://" + params[0] + ":"+params[1]);
                MongoClient mongoClient = new MongoClient(connectionString);
                return true;
            } catch (MongoSocketOpenException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private class TaskListNotification extends AsyncTask<String, Void, List<Document>> {

        protected List<Document> doInBackground(String... params) {
            List<Document> listRecord = new ArrayList<>();
            try {
                if (!Host.isEmpty()) {
                    MongoClientURI connectionString = new MongoClientURI("mongodb://" + Host + ":"+Port);
                    MongoClient mongoClient = new MongoClient(connectionString);

                    MongoDatabase db = mongoClient.getDatabase(context.getResources().getString(R.string.nome_banco));
                    MongoCollection<Document> collection = db.getCollection(context.getResources().getString(R.string.collection));

                    DBObject query = QueryBuilder.start(context.getResources().getString(R.string.objeto_verificar_usuario)).is(true).get();

                    listRecord = (List<Document>) collection.find((Bson) query).into(new ArrayList<Document>());
                }
                return listRecord;
            } catch (MongoSocketOpenException e) {
                e.fillInStackTrace();
            }
            return listRecord;
        }
    }

    private class getIdTask extends AsyncTask<String, Void, Document> {

        protected Document doInBackground(String... params) {
            Document document = new Document();
            try {
                if (params[0] != "") {
                    MongoClientURI connectionString = new MongoClientURI("mongodb://" + Host + ":"+Port);
                    MongoClient mongoClient = new MongoClient(connectionString);

                    MongoDatabase db = mongoClient.getDatabase(context.getResources().getString(R.string.nome_banco));
                    MongoCollection<Document> collection = db.getCollection(params[0]);
                    BasicDBObject whereQuery = new BasicDBObject();
                    whereQuery.put("_id", new ObjectId(params[1]));
                    document = collection.find(whereQuery).first();

                    return document;
                }
            } catch (Exception e) {
                return null;
            }
            return document;
        }
    }

    public void SalvarDecisaoAtributo(JSONObject jsonOriginal, JSONObject obj, int posicao, int decisao, String atributo) {
        try {
            Document doc2 = findID(context.getResources().getString(R.string.collection), getNumeroPedido(jsonOriginal));

            String x = new TaskSalvarDecisaoAtributo().execute( context.getResources().getString(R.string.collection), doc2.toJson(), obj.toString(), String.valueOf(posicao), String.valueOf(decisao), atributo).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String ToStringCodDecision(String dc) {
        int decision = Integer.valueOf(dc);
        if (context.getResources().getInteger(R.integer.ACCEPT) == decision) {
            return context.getResources().getString(R.string.ACCEPT);
        } else {
            if (context.getResources().getInteger(R.integer.DENY) == decision) {
                return context.getResources().getString(R.string.DENY);
            } else {
                if (context.getResources().getInteger(R.integer.NEGOTIATE) == decision) {
                    return context.getResources().getString(R.string.NEGOTIATE);
                } else {
                    return null;
                }
            }
        }
    }

    public Document findID(String collect, String id) {
        try {
            return new getIdTask().execute( collect, id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Document> getListNotification() {
        try {
            return new TaskListNotification().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
