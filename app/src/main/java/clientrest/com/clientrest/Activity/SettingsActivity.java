package clientrest.com.clientrest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import clientrest.com.clientrest.Activity.Settings.HelpSettings;
import clientrest.com.clientrest.Activity.Settings.MQTTSettings;
import clientrest.com.clientrest.Activity.Settings.NotificationSettings;
import clientrest.com.clientrest.Activity.Settings.PrivacySetting;
import clientrest.com.clientrest.R;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout lnPrivacy, lnDatabase, lnHelp, lnMQTT, lnNotification;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        InitializeVariables();

        lnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsActivity.this, PrivacySetting.class);
                SettingsActivity.this.startActivity(mainIntent);

            }
        });

        lnDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsActivity.this, PrivacySetting.class);
                SettingsActivity.this.startActivity(mainIntent);

            }
        });

        lnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsActivity.this, HelpSettings.class);
                SettingsActivity.this.startActivity(mainIntent);

            }
        });

        lnMQTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsActivity.this, MQTTSettings.class);
                SettingsActivity.this.startActivity(mainIntent);

            }
        });

        lnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsActivity.this, NotificationSettings.class);
                SettingsActivity.this.startActivity(mainIntent);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(mainIntent);
                SettingsActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void InitializeVariables() {
        lnPrivacy = (LinearLayout) findViewById(R.id.lnPrivacy);
        lnDatabase = (LinearLayout) findViewById(R.id.lnDatabase);
        lnHelp = (LinearLayout) findViewById(R.id.lnHelp);
        lnMQTT = (LinearLayout) findViewById(R.id.lnMQTT);
        lnNotification = (LinearLayout) findViewById(R.id.lnNotification);
        btnBack = (ImageButton) findViewById(R.id.btnBack);

    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        this.startActivity(mainIntent);
        this.finish();
        return;
    }

}
