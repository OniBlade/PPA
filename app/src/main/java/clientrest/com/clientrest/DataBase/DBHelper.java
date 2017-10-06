package clientrest.com.clientrest.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.compat.BuildConfig;
import android.util.Log;

import com.mongodb.client.model.ReturnDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import clientrest.com.clientrest.DataBase.Entity.Consumer;
import clientrest.com.clientrest.DataBase.Entity.ConsumerAttributes;
import clientrest.com.clientrest.DataBase.Entity.Data;
import clientrest.com.clientrest.DataBase.Entity.DataAttributes;
import clientrest.com.clientrest.DataBase.Entity.InferredDecision;
import clientrest.com.clientrest.DataBase.Entity.InferredDecisionAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.DataBase.Entity.TrainingSet;
import clientrest.com.clientrest.DataBase.Entity.UserDecision;
import clientrest.com.clientrest.DataBase.Entity.UserDecisionAttributes;
import clientrest.com.clientrest.R;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private final static String TAG = "DatabaseHelper";
    private static final float DATABASE_VERSION = 1.2F;
    private static final String ALLOW = "allow";
    private static final String DENY = "deny";
    private static final String NEGOTIATE = "negotiate";
    private final Context myContext;
    private String pathToSaveDBFile;
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
        pathToSaveDBFile = new StringBuffer("/data/data/" + BuildConfig.APPLICATION_ID + "/databases/").append(DATABASE_NAME).toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onCreate");
        db.execSQL(myContext.getResources().getString(R.string.user_decision_attributes));
        db.execSQL(myContext.getResources().getString(R.string.user_decision));
        db.execSQL(myContext.getResources().getString(R.string.request));
        db.execSQL(myContext.getResources().getString(R.string.inferred_decision_attributes));
        db.execSQL(myContext.getResources().getString(R.string.inferred_decision));
        db.execSQL(myContext.getResources().getString(R.string.data_attributes));
        db.execSQL(myContext.getResources().getString(R.string.data));
        db.execSQL(myContext.getResources().getString(R.string.consumer_attributes));
        db.execSQL(myContext.getResources().getString(R.string.consumer));
        db.execSQL(myContext.getResources().getString(R.string.training_set));
        db.execSQL(myContext.getResources().getString(R.string.model_models));

        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste1));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste2));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste3));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste4));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste5));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste6));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste7));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste8));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste9));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste10));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste11));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste12));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste13));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste14));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste15));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste16));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste17));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste18));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste19));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste20));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste21));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste22));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste23));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste24));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste25));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste26));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste27));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste28));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste29));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste30));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste31));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste32));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste33));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste34));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste35));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste36));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste37));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste38));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste39));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste40));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste41));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste42));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste43));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste44));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste45));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste46));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste47));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste48));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste49));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste50));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste51));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste52));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste53));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste54));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste55));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste56));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste57));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste58));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste59));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste60));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste61));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste62));
        db.execSQL(myContext.getResources().getString(R.string.conjunto_teste63));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            File file = new File(pathToSaveDBFile);
            checkDB = file.exists();
        } catch (SQLiteException e) {
            Log.d(TAG, e.getMessage());
        }
        return checkDB;
    }

    public void deleteDb() {
        File file = new File(pathToSaveDBFile);
        if (file.exists()) {
            file.delete();
        }
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    private boolean checkifTableExists(String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "';";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getCount() == 0 ? false : true;
    }

    private float getVersionId() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT version FROM dbversion";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        float v = cursor.getFloat(0);
        db.close();
        return v;
    }

    public void saveMLP(Object obj, StringBuilder header) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            ContentValues contentValues = new ContentValues();
            contentValues.put("model", out.toByteArray());
            contentValues.put("template_header", header.toString());
            db.insert("mlp_models", null, contentValues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getMLP() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from mlp_models ORDER BY mlp_models_id DESC LIMIT 1", null);
        res.moveToFirst();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(res.getBlob(1));
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getHeaderMLP() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from mlp_models ORDER BY mlp_models_id DESC LIMIT 1", null);
        res.moveToFirst();
        String c = res.getString(2);
        return c;
    }

    public int saveData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data_id", (byte[]) null);
        return (int) db.insert("data", null, contentValues);
    }

    public void saveDataAttributes(DataAttributes dataAttributes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("attribute", dataAttributes.getAttribute());
        contentValues.put("retention", dataAttributes.getRetention());
        contentValues.put("shared", dataAttributes.getShared());
        contentValues.put("data_id", dataAttributes.getDataId());
        db.insert("data_attributes", null, contentValues);
    }

    public Data getData(int id) {
        List<DataAttributes> dataAttributesList = new ArrayList<>();
        Data data = new Data(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from data_attributes where data_id=" + id, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            DataAttributes dataAttributes = new DataAttributes();
            dataAttributes.setDataId(data.getDataId());
            dataAttributes.setAttribute(res.getString(1));
            dataAttributes.setInferred(res.getInt(2));
            dataAttributes.setShared(res.getInt(3));
            dataAttributes.setDataAttributesId(res.getInt(4));
            dataAttributes.setRetention(res.getString(5));
            dataAttributesList.add(dataAttributes);
            res.moveToNext();
        }
        data.setDataAttributesList(dataAttributesList);
        res.close();
        return data;
    }

    public int saveConsumer(Consumer consumer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("consumer_id", (byte[]) null);
        return (int) db.insert("consumer", null, contentValues);
    }

    public void saveConsumerAttributes(ConsumerAttributes consumerAttributes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("attribute", consumerAttributes.getAttribute());
        contentValues.put("value", consumerAttributes.getValue());
        contentValues.put("consumer_id", consumerAttributes.getConsumerId());
        db.insert("consumer_attributes", null, contentValues);
    }

    public void saveUserDecision(Request request, DataAttributes dataAttributes, int decision, String information) {
        SQLiteDatabase db = this.getWritableDatabase();
        request = getRequest(request.getRequestId());
        ContentValues contentValues = new ContentValues();
        UserDecision userDecision = new UserDecision();

        if (request.getUserDecisionId().getUserDecisionId() == 0) {
            userDecision = new UserDecision();
            contentValues.clear();
            contentValues.put("user_decision_id", (byte[]) null);
            userDecision.setUserDecisionId((int) db.insert("user_decision", null, contentValues));

            contentValues.clear();
            if ((!information.isEmpty()) || (decision==2)) {
                contentValues.put("information", information);
            }
            contentValues.put("data_attribute_id", dataAttributes.getDataAttributesId());
            contentValues.put("state", decision);
            contentValues.put("user_decision_id", userDecision.getUserDecisionId());
            db.insert("user_decision_attributes", null, contentValues);

            contentValues.clear();
            contentValues.put("user_decision_id", userDecision.getUserDecisionId());
            db.update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});

        } else {
            userDecision.setUserDecisionId(request.getUserDecisionId().getUserDecisionId());
            boolean flag = true;
            for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                if (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getDataAttributesId() == dataAttributes.getDataAttributesId()) {
                    contentValues.clear();
                    if ((!information.isEmpty()) || (decision==2)) {
                        contentValues.put("information", information);
                    }
                    flag=false;
                    contentValues.put("data_attribute_id", dataAttributes.getDataAttributesId());
                    contentValues.put("state", decision);
                    contentValues.put("user_decision_id", userDecision.getUserDecisionId());
                    db.update("user_decision_attributes", contentValues, "user_decision_attributes_id = ? ", new String[]{Integer.toString(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getUserDecisionAttributesId())});
                }
            }
            if(flag){
                contentValues.clear();
                if ((!information.isEmpty()) || (decision==2)) {
                    contentValues.put("information", information);
                }
                contentValues.put("data_attribute_id", dataAttributes.getDataAttributesId());
                contentValues.put("state", decision);
                contentValues.put("user_decision_id", userDecision.getUserDecisionId());
                db.insert("user_decision_attributes", null, contentValues);
            }
        }
    }

    public boolean saveInferred_Decision(Request request, int j, String[] prediction, boolean isPrediction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        InferredDecision inferredDecision = new InferredDecision();
        if (request.getInferredDecisionId().getInferredDecisionId() == null) {
            inferredDecision = new InferredDecision();
            contentValues.clear();
            contentValues.put("inferred_decision_id", (byte[]) null);
            inferredDecision.setInferredDecisionId((int) db.insert("inferred_decision", null, contentValues));
        } else {
            inferredDecision.setInferredDecisionId(request.getInferredDecisionId().getInferredDecisionId());
        }
        contentValues.clear();
        if (isPrediction) {
            contentValues.put("trust_level", getPercentage(prediction[1]));//trust_level
        }
        contentValues.put("data_attributes_id", request.getDataId().getDataAttributesList().get(j).getDataAttributesId());
        contentValues.put("inferred_decision_id", inferredDecision.getInferredDecisionId());
        db.insert("inferred_decision_attributes", null, contentValues);

        if (isPrediction) {
            contentValues.clear();
            contentValues.put("inferred", StringToIntDecision(prediction[0]));
            db.update("data_attributes", contentValues, "data_attributes_id = ?", new String[]{Integer.toString(request.getDataId().getDataAttributesList().get(j).getDataId())});
        }
        contentValues.clear();
        request.setInferredDecisionId(inferredDecision);
        contentValues.put("inferred_decision_id", inferredDecision.getInferredDecisionId());
        db.update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});
        if (isPrediction) {
            return (getPercentage(prediction[1]) < getUserConfidenceLevel()) ? true : false;
        } else {
            return true;
        }
    }

    public void updateCheckUserRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        request.setCheckUser(true);
        contentValues.put("check_user", request.getCheckUser());
        db.update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});

    }

    private int StringToIntDecision(String s) {
        if (s.equals(ALLOW)) {
            return 1;
        } else {
            if (s.equals(DENY)) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    private double getUserConfidenceLevel() {
        return 75.8;
    }

    private double getPercentage(String v) {
        Double value = Double.valueOf(v);
        return value * 100;
    }

    public Consumer getConsumer(int id) {
        List<ConsumerAttributes> consumerAttributesList = new ArrayList<>();
        Consumer consumer = new Consumer(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from consumer_attributes where consumer_id=" + id, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            ConsumerAttributes consumerAttributes = new ConsumerAttributes();
            consumerAttributes.setAttribute(res.getString(0));
            consumerAttributes.setValue(res.getString(1));
            consumerAttributes.setConsumerAttributesId(res.getInt(2));
            consumerAttributes.setConsumerId(consumer.getConsumerId());
            consumerAttributesList.add(consumerAttributes);
            res.moveToNext();
        }
        consumer.setConsumerAttributesList(consumerAttributesList);
        res.close();
        return consumer;
    }

    public void saveRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data_id", request.getDataId().getDataId());
        contentValues.put("consumer_id", request.getConsumerId().getConsumerId());
        contentValues.put("state", request.getState());
        contentValues.put("location", request.getLocation());
        contentValues.put("reason", request.getReason());
        contentValues.put("uuid", request.getUuid());
        db.insert("request", null, contentValues);
    }

    public Request getRequest(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Request request = new Request(id);
        Cursor res = db.rawQuery("select * from request where request_id=" + id, null);
        res.moveToFirst();

        request.setInferredDecisionId(getInferredDecision(res.getInt(0)));
        request.setUuid(res.getString(1));
        request.setUserDecisionId(getUserDecision(res.getInt(2)));
        request.setDataId(getData(res.getInt(3)));
        request.setReason(res.getString(4));
        request.setConsumerId(getConsumer(res.getInt(5)));
        request.setState(res.getInt(6));
        request.setLocation(res.getString(7));
        request.setRequestId(res.getInt(8));
        request.setCheckUser(res.getInt(9));
        request.setUserBenefit(res.getString(10));
        return request;
    }

    private InferredDecision getInferredDecision(int id) {
        InferredDecision inferredDecision = new InferredDecision(id);
        List<InferredDecisionAttributes> inferredDecisionAttributesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from inferred_decision_attributes where inferred_decision_id=" + id, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            InferredDecisionAttributes inferredDecisionAttributes = new InferredDecisionAttributes();
            inferredDecisionAttributes.setInferredDecisionId(inferredDecision);
            inferredDecisionAttributes.setDataAttributes(getDataAttributesForId(res.getInt(0)));

            inferredDecisionAttributes.setTrustLevel(res.getDouble(2));
            inferredDecisionAttributesList.add(inferredDecisionAttributes);
            res.moveToNext();
        }
        inferredDecision.setInferredDecisionAttributesList(inferredDecisionAttributesList);
        return inferredDecision;
    }

    private DataAttributes getDataAttributesForId(int id) {
        DataAttributes dataAttributes = new DataAttributes(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from data_attributes where data_attributes_id ==" + id, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            dataAttributes.setDataId(res.getInt(0));
            dataAttributes.setAttribute(res.getString(1));
            dataAttributes.setInferred(res.getInt(2));
            dataAttributes.setShared(res.getInt(3));
            dataAttributes.setDataAttributesId(res.getInt(4));
            dataAttributes.setRetention(res.getString(5));
            res.moveToNext();
        }
        return dataAttributes;
    }

    private UserDecision getUserDecision(int id) {
        UserDecision userDecision = new UserDecision(id);
        List<UserDecisionAttributes> userDecisionAttributesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user_decision_attributes where user_decision_id=" + id, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            UserDecisionAttributes userDecisionAttributes = new UserDecisionAttributes();
            userDecisionAttributes.setInformation(res.getString(0));
            userDecisionAttributes.setState(res.getInt(1));
            userDecisionAttributes.setUserDecisionAttributesId(res.getInt(2));
            userDecisionAttributes.setUserDecisionId(userDecision);
            userDecisionAttributes.setDataAtttributeId(getDataAttributesForId(res.getInt(4)));
            userDecisionAttributesList.add(userDecisionAttributes);
            res.moveToNext();
        }
        userDecision.setUserDecisionAttributesList(userDecisionAttributesList);
        res.close();

        return userDecision;
    }

    public List<Request> getListRequestNotProcessed() {
        List<Request> requestList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from request where (state=0 and inferred_decision_id IS NULL)", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Request request = new Request();
            request.setInferredDecisionId(getInferredDecision(res.getInt(0)));
            request.setUuid(res.getString(1));
            request.setUserDecisionId(getUserDecision(res.getInt(2)));
            request.setDataId(getData(res.getInt(3)));
            request.setReason(res.getString(4));
            request.setConsumerId(getConsumer(res.getInt(5)));
            request.setState(res.getInt(6));
            request.setLocation(res.getString(7));
            request.setRequestId(res.getInt(8));
            request.setCheckUser(res.getInt(9));
            request.setUserBenefit(res.getString(10));
            requestList.add(request);
            res.moveToNext();
        }
        return requestList;
    }

    public List<TrainingSet> getListTrainingSeT() {
        List<TrainingSet> trainingSetList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from training_set", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            TrainingSet trainingSet = new TrainingSet();
            trainingSet.setTrainingSetId(res.getInt(0));
            trainingSet.setDeviceType(res.getInt(1));
            trainingSet.setDataType(res.getString(2));
            trainingSet.setUserBenefit(res.getString(3));
            trainingSet.setRetention(res.getString(4));
            trainingSet.setLocation(res.getString(5));
            trainingSet.setShared(res.getString(6));
            trainingSet.setInferred(res.getString(7));
            trainingSet.setResult(res.getString(8));
            trainingSetList.add(trainingSet);
            res.moveToNext();
        }
        return trainingSetList;
    }

    public boolean isExistInTrainingSet(String column, String attribute) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from training_set where " + column + "=\"" + attribute + "\"", null);
        if (res.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Request> getListNotification() {
        List<Request> requestList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from request where (state=0 and check_user = 1) ", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Request request = new Request();
            request.setInferredDecisionId(getInferredDecision(res.getInt(0)));
            request.setUuid(res.getString(1));
            request.setUserDecisionId(getUserDecision(res.getInt(2)));
            request.setDataId(getData(res.getInt(3)));
            request.setReason(res.getString(4));
            request.setConsumerId(getConsumer(res.getInt(5)));
            request.setState(res.getInt(6));
            request.setLocation(res.getString(7));
            request.setRequestId(res.getInt(8));
            request.setCheckUser(res.getInt(9));
            request.setUserBenefit(res.getString(10));
            requestList.add(request);
            res.moveToNext();
        }
        return requestList;
    }

    public void updateRequestStatus(Request request, boolean state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        request.setState(state);
        contentValues.put("state", request.getState());
        db.update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});

    }
}