package clientrest.com.clientrest.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.compat.BuildConfig;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import clientrest.com.clientrest.DataBase.Entity.Consumer;
import clientrest.com.clientrest.DataBase.Entity.ConsumerAttributes;
import clientrest.com.clientrest.DataBase.Entity.Data;
import clientrest.com.clientrest.DataBase.Entity.DataAttributes;
import clientrest.com.clientrest.DataBase.Entity.HistoryObject;
import clientrest.com.clientrest.DataBase.Entity.InferredDecision;
import clientrest.com.clientrest.DataBase.Entity.InferredDecisionAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.DataBase.Entity.Settings;
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
        db.execSQL(myContext.getResources().getString(R.string.things));
        db.execSQL(myContext.getResources().getString(R.string.configuration));
        db.execSQL(myContext.getResources().getString(R.string.privacy_setting_pattern));
/*

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
*/
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
        db.close();
        return cursor.getCount() == 0 ? false : true;
    }

    private float getVersionId() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        String query = "SELECT version FROM dbversion";
        Cursor cursor = null;
        float v;
        try {
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            v = cursor.getFloat(0);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return v;
    }

    public void saveMLP(Object obj, StringBuilder header) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            ContentValues contentValues = new ContentValues();
            contentValues.put("model", out.toByteArray());
            contentValues.put("template_header", header.toString());
            DataBase_insert("mlp_models", null, contentValues);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return null;
    }

    public String getHeaderMLP() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        String head = "";
        try {
            res = db.rawQuery("select * from mlp_models ORDER BY mlp_models_id DESC LIMIT 1", null);
            res.moveToFirst();
            head = res.getString(2);
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return head;
    }

    public int saveData(Data data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("data_id", (byte[]) null);
        return DataBase_insert("data", null, contentValues);
    }

    public void saveDataAttributes(DataAttributes dataAttributes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("attribute", dataAttributes.getAttribute());
        contentValues.put("retention", dataAttributes.getRetention());
        contentValues.put("shared", dataAttributes.getShared());
        contentValues.put("data_id", dataAttributes.getDataId());

        DataBase_insert("data_attributes", null, contentValues);
    }

    public Data getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DataAttributes> dataAttributesList = new ArrayList<>();
        Data data = new Data(id);
        Cursor res = null;
        try {
            res = db.rawQuery("select * from data_attributes where data_id=" + id, null);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return data;
    }

    public int saveConsumer(Consumer consumer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("consumer_id", (byte[]) null);
        return DataBase_insert("consumer", null, contentValues);
    }

    public void saveConsumerAttributes(ConsumerAttributes consumerAttributes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("attribute", consumerAttributes.getAttribute());
        contentValues.put("value", consumerAttributes.getValue());
        contentValues.put("consumer_id", consumerAttributes.getConsumerId());
        DataBase_insert("consumer_attributes", null, contentValues);
    }

    private int DataBase_insert(String table, String nullColumnHack, ContentValues contentValues) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            return (int) db.insert(table, nullColumnHack, contentValues);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    private void DataBase_update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.update(table, contentValues, whereClause, whereArgs);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public void saveUserDecision(Request request, DataAttributes dataAttributes, int decision, String information) {

        request = getRequest(request.getRequestId());
        ContentValues contentValues = new ContentValues();
        UserDecision userDecision = new UserDecision();

        if (request.getUserDecisionId().getUserDecisionId() == 0) {
            userDecision = new UserDecision();
            contentValues.clear();
            contentValues.put("user_decision_id", (byte[]) null);
            userDecision.setUserDecisionId(DataBase_insert("user_decision", null, contentValues));

            contentValues.clear();
            if ((!information.isEmpty()) || (decision == 2)) {
                saveThingsInformation(dataAttributes.getAttribute(), information);
                contentValues.put("information", information);
            }
            contentValues.put("data_attribute_id", dataAttributes.getDataAttributesId());
            contentValues.put("state", decision);
            contentValues.put("user_decision_id", userDecision.getUserDecisionId());
            DataBase_insert("user_decision_attributes", null, contentValues);

            contentValues.clear();
            contentValues.put("user_decision_id", userDecision.getUserDecisionId());
            DataBase_update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});

        } else {
            userDecision.setUserDecisionId(request.getUserDecisionId().getUserDecisionId());
            boolean flag = true;
            for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                if (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getDataAttributesId() == dataAttributes.getDataAttributesId()) {
                    contentValues.clear();
                    if ((!information.isEmpty()) || (decision == 2)) {
                        saveThingsInformation(dataAttributes.getAttribute(), information);
                        contentValues.put("information", information);
                    }
                    flag = false;
                    contentValues.put("data_attribute_id", dataAttributes.getDataAttributesId());
                    contentValues.put("state", decision);
                    contentValues.put("user_decision_id", userDecision.getUserDecisionId());
                    DataBase_update("user_decision_attributes", contentValues, "user_decision_attributes_id = ? ", new String[]{Integer.toString(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getUserDecisionAttributesId())});
                }
            }
            if (flag) {
                contentValues.clear();
                if ((!information.isEmpty()) || (decision == 2)) {
                    saveThingsInformation(dataAttributes.getAttribute(), information);
                    contentValues.put("information", information);
                }
                contentValues.put("data_attribute_id", dataAttributes.getDataAttributesId());
                contentValues.put("state", decision);
                contentValues.put("user_decision_id", userDecision.getUserDecisionId());
                DataBase_insert("user_decision_attributes", null, contentValues);
            }
        }

    }

    private void saveThingsInformation(String attribute, String information) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("attribute", attribute);
        contentValues.put("value", information);
        DataBase_insert("things", null, contentValues);
    }

    public boolean saveInferred_Decision(Request request, int j, String[] prediction, boolean isPrediction) {
        ContentValues contentValues = new ContentValues();
        try {
            InferredDecision inferredDecision = new InferredDecision();
            if (request.getInferredDecisionId().getInferredDecisionId() == 0) {
                inferredDecision = new InferredDecision();
                contentValues.clear();
                contentValues.put("inferred_decision_id", (byte[]) null);
                inferredDecision.setInferredDecisionId(DataBase_insert("inferred_decision", null, contentValues));
            } else {
                inferredDecision.setInferredDecisionId(request.getInferredDecisionId().getInferredDecisionId());
            }
            contentValues.clear();
            if (isPrediction) {
                contentValues.put("state", StringToIntDecision(prediction[0]));//decision
                contentValues.put("trust_level", getPercentage(prediction[1]));//trust_level
            }
            contentValues.put("data_attributes_id", request.getDataId().getDataAttributesList().get(j).getDataAttributesId());
            contentValues.put("inferred_decision_id", inferredDecision.getInferredDecisionId());
            DataBase_insert("inferred_decision_attributes", null, contentValues);

            contentValues.clear();
            request.setInferredDecisionId(inferredDecision);
            contentValues.put("inferred_decision_id", inferredDecision.getInferredDecisionId());
            DataBase_update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});
        } finally {
            if (isPrediction) {
                return (getPercentage(prediction[1]) < getUserConfidenceLevel()) ? true : false;
            } else {
                return true;
            }
        }
    }

    public void updateCheckUserRequest(Request request, boolean status) {
        ContentValues contentValues = new ContentValues();
        request.setCheckUser(status);
        contentValues.put("check_user", request.getCheckUser());
        DataBase_update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});
    }

    private int StringToIntDecision(String s) {
        if (s.equals(ALLOW)) {
            return 1;
        } else {
            if (s.equals(DENY)) {
                return 2;
            } else {
                if (s.equals(NEGOTIATE)) {
                    return 3;
                } else {
                    return Integer.parseInt(null);
                }
            }
        }
    }

    private double getUserConfidenceLevel() {
        Settings settings = getLastPrivacySettings();
        return Double.valueOf(settings.getConfidenceLevel());
    }

    private double getPercentage(String v) {
        Double value = Double.valueOf(v);
        return value * 100;
    }

    public Consumer getConsumer(int id) {
        List<ConsumerAttributes> consumerAttributesList = new ArrayList<>();
        Consumer consumer = new Consumer(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from consumer_attributes where consumer_id=" + id, null);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return consumer;
    }

    public void saveRequest(Request request) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("data_id", request.getDataId().getDataId());
        contentValues.put("consumer_id", request.getConsumerId().getConsumerId());
        contentValues.put("state", request.getState());
        contentValues.put("location", request.getLocation());
        contentValues.put("reason", request.getReason());
        contentValues.put("uuid", request.getUuid());
        contentValues.put("user_benefit", "user_consumer");
        DataBase_insert("request", null, contentValues);
    }

    public Request getRequest(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Request request = new Request(id);
        Cursor res = null;
        try {
            res = db.rawQuery("select * from request where request_id=" + id, null);
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

        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return request;
    }

    public void getAllRequest() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from request", null);
            res.moveToFirst();
            if (res.getCount() > 0) {
                //f
            }

        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }

    }

    private InferredDecision getInferredDecision(int id) {
        InferredDecision inferredDecision = new InferredDecision(id);
        List<InferredDecisionAttributes> inferredDecisionAttributesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from inferred_decision_attributes where inferred_decision_id=" + id, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                InferredDecisionAttributes inferredDecisionAttributes = new InferredDecisionAttributes();
                inferredDecisionAttributes.setInferredDecisionId(inferredDecision);
                inferredDecisionAttributes.setDataAttributes(getDataAttributesForId(res.getInt(0)));
                inferredDecisionAttributes.setInferredDecisionAttributesId(res.getInt(1));
                inferredDecisionAttributes.setTrustLevel(res.getDouble(2));
                inferredDecisionAttributes.setState(res.getInt(4));
                inferredDecisionAttributes.setAnonymised_information(res.getString(5));
                inferredDecisionAttributesList.add(inferredDecisionAttributes);
                res.moveToNext();
            }
            inferredDecision.setInferredDecisionAttributesList(inferredDecisionAttributesList);
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return inferredDecision;
    }

    private DataAttributes getDataAttributesForId(int id) {
        DataAttributes dataAttributes = new DataAttributes(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from data_attributes where data_attributes_id ==" + id, null);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return dataAttributes;
    }

    private UserDecision getUserDecision(int id) {
        UserDecision userDecision = new UserDecision(id);
        List<UserDecisionAttributes> userDecisionAttributesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from user_decision_attributes where user_decision_id=" + id, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                UserDecisionAttributes userDecisionAttributes = new UserDecisionAttributes();
                userDecisionAttributes.setInformation(res.getString(0));
                userDecisionAttributes.setState(res.getInt(1));
                userDecisionAttributes.setUserDecisionAttributesId(res.getInt(2));
                userDecisionAttributes.setUserDecisionId(userDecision);
                userDecisionAttributes.setDataAtttributeId(getDataAttributesForId(res.getInt(4)));
                userDecisionAttributes.setAnonymised_information(res.getString(5));
                userDecisionAttributesList.add(userDecisionAttributes);
                res.moveToNext();
            }
            userDecision.setUserDecisionAttributesList(userDecisionAttributesList);
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return userDecision;
    }

    public List<Request> getListRequestNotProcessed() {
        List<Request> requestList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from request where (state=0 and inferred_decision_id IS NULL)", null);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return requestList;
    }

    public List<TrainingSet> getListTrainingSeT() {
        List<TrainingSet> trainingSetList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from training_set", null);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return trainingSetList;
    }

    public boolean isExistInTrainingSet(String column, String attribute) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from training_set where " + column + "=\"" + attribute + "\"", null);
            if (res.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public List<HistoryObject> getHistoryUser() {
        List<HistoryObject> historyObjectArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select \n" +
                "data_attributes.data_attributes_id , \n" +
                "consumer_attributes.attribute, \n" +
                "consumer_attributes.value, \n" +
                "request.reason, \n" +
                "data_attributes.attribute,\n" +
                "user_decision_attributes.state\n" +
                "from data_attributes \n" +
                "JOIN data ON data.data_id = data_attributes.data_id\n" +
                "JOIN request ON request.data_id =  data.data_id\n" +
                "join consumer on consumer.consumer_id = request.consumer_id\n" +
                "join consumer_attributes on consumer.consumer_id = consumer_attributes.consumer_id\n" +
                "JOIN user_decision on user_decision.user_decision_id = request.user_decision_id\n" +
                "JOIN user_decision_attributes on user_decision_attributes.data_attribute_id = data_attributes.data_attributes_id\n" +
                "where request.state=1 and request.check_user=1";
        Cursor res = null;
        try {
            res = db.rawQuery(sql, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                HistoryObject historyObject = new HistoryObject();
                historyObject.setData_attributes_id(res.getInt(0));
                historyObject.setConsumer_attribute(res.getString(1));
                historyObject.setConsumer_value(res.getString(2));
                historyObject.setRequest_reason(res.getString(3));
                historyObject.setData_attributes_attribute(res.getString(4));
                historyObject.setInferred_decision_attributes_state(res.getInt(5));
                historyObjectArrayList.add(historyObject);
                res.moveToNext();
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return historyObjectArrayList;
    }

    public List<HistoryObject> getHistoryMechanism() {
        List<HistoryObject> historyObjectArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select \n" +
                "data_attributes.data_attributes_id , \n" +
                "consumer_attributes.attribute, \n" +
                "consumer_attributes.value, \n" +
                "request.reason, \n" +
                "data_attributes.attribute,\n" +
                "inferred_decision_attributes.state, \n" +
                "inferred_decision_attributes.trust_level\n" +
                "from data_attributes \n" +
                "JOIN data ON data.data_id = data_attributes.data_id\n" +
                "JOIN request ON request.data_id =  data.data_id\n" +
                "join consumer on consumer.consumer_id = request.consumer_id\n" +
                "join consumer_attributes on consumer.consumer_id = consumer_attributes.consumer_id\n" +
                "JOIN inferred_decision on inferred_decision.inferred_decision_id = request.inferred_decision_id\n" +
                "JOIN inferred_decision_attributes on inferred_decision_attributes.inferred_decision_id =  inferred_decision.inferred_decision_id and \n" +
                "inferred_decision_attributes.data_attributes_id=data_attributes.data_attributes_id\n" +
                "where request.state=1 and request.check_user=0";

        Cursor res = null;
        try {
            res = db.rawQuery(sql, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                HistoryObject historyObject = new HistoryObject();
                historyObject.setData_attributes_id(res.getInt(0));
                historyObject.setConsumer_attribute(res.getString(1));
                historyObject.setConsumer_value(res.getString(2));
                historyObject.setRequest_reason(res.getString(3));
                historyObject.setData_attributes_attribute(res.getString(4));
                historyObject.setInferred_decision_attributes_state(res.getInt(5));
                historyObject.setInferred_decision_attributes_trust_level(res.getDouble(6));
                historyObjectArrayList.add(historyObject);
                res.moveToNext();
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return historyObjectArrayList;
    }

    public List<Request> getListNotification() {
        List<Request> requestList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from request where (state=0 and check_user = 1) ", null);
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
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
        return requestList;
    }

    public void updateRequestStatus(Request request, boolean state) {
        ContentValues contentValues = new ContentValues();
        request.setState(state);
        contentValues.put("state", request.getState());
        DataBase_update("request", contentValues, "request_id = ? ", new String[]{Integer.toString(request.getRequestId())});
    }


    public boolean thingsExists(String data) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from things where attribute= \"" + data + "\"", null);
            if (res.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public void saveTraining_set(Request request) {
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
            contentValues.put("device_type", request.getConsumerId().getConsumerId());
            contentValues.put("data_type", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getAttribute());
            contentValues.put("user_benefit", request.getUserBenefit());
            contentValues.put("retention", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getRetention());
            contentValues.put("location", request.getLocation());
            contentValues.put("shared", getStringParam(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getShared()));
            contentValues.put("inferred", getStringParam(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getInferred()));
            contentValues.put("result", getResultStringParam(request.getUserDecisionId().getUserDecisionAttributesList().get(i).getState()));
            DataBase_insert("training_set", null, contentValues);
            contentValues.clear();
        }

    }

    private String getStringParam(int shared) {
        return (shared == 0) ? "no" : "yes";
    }

    private String getResultStringParam(int result) {
        if (result == 1) {
            return ALLOW;
        } else {
            if (result == 2) {
                return DENY;
            } else {
                if (result == 3) {
                    return NEGOTIATE;
                } else {
                    return null;
                }
            }
        }
    }


    public boolean getExistUUID(String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from request where uuid= \"" + uuid + "\"", null);
            res.moveToFirst();
            if (res.getCount() > 0) {
                Log.e("getExistUUID", "TRUE");
                return true;
            } else {
                Log.e("getExistUUID", "FALSE");
                return false;
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public int getConsumerForUUID(String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from request where uuid= \"" + uuid + "\"", null);
            res.moveToFirst();
            if (res.getCount() > 0) {
                return res.getInt(5);
            } else {
                return Integer.parseInt(null);
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public Set<Integer> getAllConsumer() {
        Set<Integer> deviceType = new HashSet<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from consumer ", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                deviceType.add(res.getInt(0));
                res.moveToNext();
            }
            return deviceType;
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public boolean isExistHeaderMLP(Consumer consumerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from mlp_models ORDER BY mlp_models_id DESC LIMIT 1", null);
            res.moveToFirst();
            if (res.getCount() > 0) {
                String headerMLP = res.getString(2);
                int in = headerMLP.indexOf("@attribute device_type {");
                int end = headerMLP.length();
                String aux = headerMLP.substring(in, end);//@attribute device_type
                aux = aux.substring(0, aux.indexOf("}"));
                if (aux.contains(String.valueOf(consumerId.getConsumerId()))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public String getConsumerResponse(Request request) {
        request = getRequest(request.getRequestId());
        JSONObject jsonObject = null;
        boolean flag;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("request_code", request.getRequestId());

            JSONArray dataArray = new JSONArray();
            if (request.getCheckUser() == 1) {  // usuario que respondeu
                for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                    JSONObject itemObject = new JSONObject();
                    itemObject.put("attribute", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getAttribute());
                    flag = (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getState() == 3) ? true : false;
                    if (!flag) {
                        itemObject.put("value", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getInformation());
                    }
                    itemObject.put("state", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getState());
                    dataArray.put(itemObject);
                }
            } else {  // mecanismo que respondeu
                for (int i = 0; i < request.getInferredDecisionId().getInferredDecisionAttributesList().size(); i++) {
                    JSONObject itemObject = new JSONObject();
                    itemObject.put("attribute", request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getDataAttributes().getAttribute());
                    flag = (request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getState() == 3) ? true : false;
                    if (!flag) {
                        itemObject.put("value", getThingsAttribute(request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getDataAttributes().getAttribute()));
                    }
                    itemObject.put("state", request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getState());
                    dataArray.put(itemObject);
                }
            }
            jsonObject.put("data", dataArray);


        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return jsonObject.toString();
    }


    public void saveRequestJson(String obj) {
        try {
            Request request = new Request();
            JSONObject jsonObject = new JSONObject(obj);
            request.setLocation(jsonObject.getString("location"));
            request.setReason(jsonObject.getString("reason"));
            request.setUuid(jsonObject.getString("uuid"));
            request.setDataId(saveDataJSON(obj));
            request.setConsumerId(saveConsumerJSON(obj));
            saveRequest(request);
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    private Data saveDataJSON(String obj) {
        try {
            JSONObject jsonObject = new JSONObject(obj);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Data data = new Data();
            data.setDataId(saveData(data));
            List<DataAttributes> dataAttributesList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                DataAttributes dataAttributes = new DataAttributes();
                dataAttributes.setDataId(data.getDataId());
                dataAttributes.setAttribute(jsonArray.getJSONObject(i).getString("attribute"));
                dataAttributes.setRetention(jsonArray.getJSONObject(i).getString("retention"));
                dataAttributes.setShared((jsonArray.getJSONObject(i).getBoolean("shared")) ? 1 : 0);
                saveDataAttributes(dataAttributes);
                dataAttributesList.add(dataAttributes);
            }
            data.setDataAttributesList(dataAttributesList);
            return data;
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    private Consumer saveConsumerJSON(String obj) {
        try {
            JSONObject jsonObject = new JSONObject(obj);
            JSONArray jsonArray = jsonObject.getJSONArray("consumer");

            Consumer consumer = new Consumer();
            if (getExistUUID(jsonObject.getString("uuid"))) {
                consumer = getConsumer(getConsumerForUUID(jsonObject.getString("uuid")));
            } else {

                consumer.setConsumerId(saveConsumer(consumer));
                List<ConsumerAttributes> consumerAttributesList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ConsumerAttributes consumerAttributes = new ConsumerAttributes();
                    consumerAttributes.setConsumerId(consumer.getConsumerId());
                    consumerAttributes.setAttribute(jsonArray.getJSONObject(i).getString("attribute"));
                    consumerAttributes.setValue(jsonArray.getJSONObject(i).getString("value"));
                    saveConsumerAttributes(consumerAttributes);
                    consumerAttributesList.add(consumerAttributes);
                }
                consumer.setConsumerAttributesList(consumerAttributesList);
            }
            return consumer;
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    public void updateUserDecisionAttributes(UserDecisionAttributes userDecisionAttributes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", userDecisionAttributes.getState());
        contentValues.put("information", userDecisionAttributes.getInformation());
        contentValues.put("anonymised_information", userDecisionAttributes.getAnonymised_information());
        DataBase_update("user_decision_attributes", contentValues, "user_decision_attributes_id = ? ", new String[]{Integer.toString(userDecisionAttributes.getUserDecisionAttributesId())});

    }

    public String getThingsAttribute(String attribute) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from things where attribute = \"" + attribute + "\"", null);
            res.moveToFirst();
            return res.getString(2);
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public String getConsumerResponseNegotiated(Request request) {
        request = getRequest(request.getRequestId());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("request_code", request.getRequestId());

            JSONArray dataArray = new JSONArray();
            if (request.getCheckUser() == 1) {  // usuario que respondeu
                for (int i = 0; i < request.getUserDecisionId().getUserDecisionAttributesList().size(); i++) {
                    if (request.getUserDecisionId().getUserDecisionAttributesList().get(i).getState() == 3) {
                        JSONObject itemObject = new JSONObject();
                        itemObject.put("attribute", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getDataAtttributeId().getAttribute());
                        itemObject.put("value", request.getUserDecisionId().getUserDecisionAttributesList().get(i).getAnonymised_information());
                        dataArray.put(itemObject);
                    }
                }
            } else {  // mecanismo que respondeu
                for (int i = 0; i < request.getInferredDecisionId().getInferredDecisionAttributesList().size(); i++) {
                    if (request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getState() == 3) {
                        JSONObject itemObject = new JSONObject();
                        itemObject.put("attribute", request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getDataAttributes().getAttribute());
                        itemObject.put("value", request.getInferredDecisionId().getInferredDecisionAttributesList().get(i).getAnonymised_information());
                        dataArray.put(itemObject);
                    }
                }
            }
            jsonObject.put("data", dataArray);


        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return jsonObject.toString();
    }

    public void updateInferredDecision(InferredDecisionAttributes inferredDecisionAttributes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", inferredDecisionAttributes.getState());
        contentValues.put("anonymised_information", inferredDecisionAttributes.getAnonymised_information());
        DataBase_update("inferred_decision_attributes", contentValues, "inferred_decision_attributes_id = ? ", new String[]{Integer.toString(inferredDecisionAttributes.getInferredDecisionAttributesId())});
    }

    public Settings getLastPrivacySettings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        Settings settings;
        try {
            res = db.rawQuery("select * from settings ORDER BY configuration_id DESC LIMIT 1", null);
            res.moveToFirst();
            settings = new Settings();
            settings.setConfigurationId(res.getInt(0));
            settings.setConfidenceLevel(res.getInt(1));
            settings.setAlwaysNotify(res.getInt(2));
            settings.setNotifyNewConsumer(res.getInt(3));
            settings.setSoundNotification(res.getInt(4));
            return settings;
        } finally {
            if (res != null) {
                res.close();
            }
            db.close();
        }
    }

    public void insertPrivacySetting(Settings settings) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("always_notify", settings.getAlwaysNotify());
        contentValues.put("confidence_level", settings.getConfidenceLevel());
        contentValues.put("notify_new_consumer", settings.getNotifyNewConsumer());
        contentValues.put("sound_notification", settings.getSoundNotification());
        DataBase_insert("settings", null, contentValues);
    }
}