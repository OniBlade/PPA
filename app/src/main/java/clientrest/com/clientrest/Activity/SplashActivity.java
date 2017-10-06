package clientrest.com.clientrest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import clientrest.com.clientrest.R;
import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.Service.MQTTService;


public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private DBHelper mydb;
    private static int TRAIN_MLP = 1;
    private static int SAVE_NEW_REQUEST = 2;
    private static int PROCESSING_REQUESTS = 3;


    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.background_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mydb = new DBHelper(getApplicationContext());
                mydb.getWritableDatabase();

                Intent it2 = new Intent(getApplicationContext(), MQTTService.class);
                Bundle mBundle2 = new Bundle();
                mBundle2.putInt("CODE",TRAIN_MLP);
                it2.putExtras(mBundle2);
                startService(it2);


                Intent it = new Intent(getApplicationContext(), MQTTService.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("CODE",PROCESSING_REQUESTS);
                it.putExtras(mBundle);
                startService(it);

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}