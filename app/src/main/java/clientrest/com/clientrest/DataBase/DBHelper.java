package clientrest.com.clientrest.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.compat.BuildConfig;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import clientrest.com.clientrest.DataBase.Entity.Consumer;
import clientrest.com.clientrest.DataBase.Entity.ConsumerAttributes;
import clientrest.com.clientrest.DataBase.Entity.Data;
import clientrest.com.clientrest.DataBase.Entity.DataAttributes;
import clientrest.com.clientrest.DataBase.Entity.Request;
import clientrest.com.clientrest.R;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    /* private static final String MSG_TABLE_NAME = "mensagens";
     private static final String MSG_COLUMN_ID = "id";
     private static final String MSG_COLUMN_NAME = "name";
     private static final String MSG_COLUMN_CATEGORY = "categoria";
     private static final String MSG_COLUMN_AUTHOR = "autor";
     private static final String MSG_COLUMN_FAV = "favoritos";
     private static final String MSG_COLUMN_NENVIOS = "numeroenvios";
     private static final String FAV_TABLE_NAME = "favoritos";
     private static final String FAV_COLUMN_MSGID = "msgid";*/
    private final static String TAG = "DatabaseHelper";
    private static final float DATABASE_VERSION = 1.2F;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        /*db.execSQL("DROP TABLE IF EXISTS " + MSG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FAV_TABLE_NAME);
        onCreate(db);*/
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

   /* private void copyDataBase(ArrayList<FraseDummyContent.DummyItem> mensagensList) throws IOException {
        OutputStream os = new FileOutputStream(pathToSaveDBFile);
        InputStream is = myContext.getAssets().open(DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.flush();
        os.close();
        if (mensagensList != null) {
            for (FraseDummyContent.DummyItem mensagem : mensagensList) {
                updateMensagem(mensagem.id, mensagem.name, mensagem.categoria, mensagem.favoritos, mensagem.nEnvios);
                if (mensagem.favoritos > 0) {
                    insertFavoritos(mensagem.id);
                }
            }
        }
    }*/

    public void deleteDb() {
        File file = new File(pathToSaveDBFile);
        if (file.exists()) {
            file.delete();
        }
    }



   /* public boolean insertFrase(Integer id, String name, String categoria, String autor, Integer fav, Integer nEnvios) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MSG_COLUMN_ID, id);
        contentValues.put(MSG_COLUMN_NAME, name);
        contentValues.put(MSG_COLUMN_CATEGORY, categoria);
        contentValues.put(MSG_COLUMN_AUTHOR, autor == null ? "" : autor);
        contentValues.put(MSG_COLUMN_FAV, fav);
        contentValues.put(MSG_COLUMN_NENVIOS, nEnvios);
        db.insert(MSG_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertFavoritos(Integer msgid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FAV_COLUMN_MSGID, msgid);
        db.insert(FAV_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }*/

   /* public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + MSG_TABLE_NAME + " where id=" + id + "", null);
    }

    public int getnumberOfRowsInMensagens() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, MSG_TABLE_NAME);
    }

    public int getnumberOfRowsInFavoritos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, FAV_TABLE_NAME);
    }*/

   /* public boolean updateMensagem(Integer id, String name, String categoria, Integer favoritos, Integer nEnvios) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (name != null)
            contentValues.put(MSG_COLUMN_NAME, name);
        if (categoria != null)
            contentValues.put(MSG_COLUMN_CATEGORY, categoria);
        if (favoritos != null)
            contentValues.put(MSG_COLUMN_FAV, favoritos);
        if (nEnvios != null) {
            Cursor c = getData(id);
            c.moveToFirst();
            contentValues.put(MSG_COLUMN_NENVIOS, c.getInt(5) + nEnvios);
            c.close();
        }
        db.update(MSG_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }*/

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    /*public Integer deleteMensagem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MSG_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deleteFavorito(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FAV_TABLE_NAME,
                "msgid = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<FraseDummyContent.DummyItem> getAllMensagens() {
        ArrayList<FraseDummyContent.DummyItem> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MSG_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            FraseDummyContent.DummyItem dItem = new FraseDummyContent.DummyItem(res.getInt(0), res.getString(1), res.getString(2), res.getString(3),
                    Integer.parseInt(res.getString(4)), Integer.parseInt(res.getString(5)));
            array_list.add(dItem);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<FraseDummyContent.DummyItem> getAllFavoritos() {
        ArrayList<FraseDummyContent.DummyItem> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FAV_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Cursor c = getData(res.getInt(0));
            c.moveToFirst();
            FraseDummyContent.DummyItem dItem = new FraseDummyContent.DummyItem(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                    c.getInt(4), c.getInt(5));
            array_list.add(dItem);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<FraseDummyContent.DummyItem> getAllMaisEnviadas() {
        ArrayList<FraseDummyContent.DummyItem> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MSG_TABLE_NAME + " where " + MSG_COLUMN_NENVIOS + "> 0 ORDER BY " + MSG_COLUMN_NENVIOS + " DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            FraseDummyContent.DummyItem dItem = new FraseDummyContent.DummyItem(res.getInt(0), res.getString(1), res.getString(2), res.getString(3),
                    Integer.parseInt(res.getString(4)), Integer.parseInt(res.getString(5)));
            array_list.add(dItem);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<FraseDummyContent.DummyItem> getAllHistorico() {
        ArrayList<FraseDummyContent.DummyItem> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MSG_TABLE_NAME + " where " + MSG_COLUMN_NENVIOS + ">0 ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            FraseDummyContent.DummyItem dItem = new FraseDummyContent.DummyItem(res.getInt(0), res.getString(1), res.getString(2), res.getString(3),
                    Integer.parseInt(res.getString(4)), Integer.parseInt(res.getString(5)));
            array_list.add(dItem);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public ArrayList<FraseDummyContent.DummyItem> getAllMensagensFromCategory(String categoria) {
        ArrayList<FraseDummyContent.DummyItem> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + MSG_TABLE_NAME + " where " + MSG_COLUMN_CATEGORY + "=\"" + categoria + "\" ORDER BY Random()", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            FraseDummyContent.DummyItem dItem = new FraseDummyContent.DummyItem(res.getInt(0), res.getString(1), res.getString(2), res.getString(3),
                    Integer.parseInt(res.getString(4)), Integer.parseInt(res.getString(5)));
            array_list.add(dItem);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }*/


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
        contentValues.put("data_id", dataAttributes.getDataId().getDataId());
        db.insert("data_attributes", null, contentValues);
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
        contentValues.put("consumer_id", consumerAttributes.getConsumerId().getConsumerId());
        db.insert("consumer_attributes", null, contentValues);
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


}