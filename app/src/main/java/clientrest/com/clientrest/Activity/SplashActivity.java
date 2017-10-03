package clientrest.com.clientrest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import clientrest.com.clientrest.R;
import clientrest.com.clientrest.DataBase.DBHelper;


public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private DBHelper mydb;


    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.background_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                mydb = new DBHelper(getApplicationContext());
                mydb.getWritableDatabase();

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}