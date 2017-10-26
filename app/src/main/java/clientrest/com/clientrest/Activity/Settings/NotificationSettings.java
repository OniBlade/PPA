package clientrest.com.clientrest.Activity.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import clientrest.com.clientrest.DataBase.DAO.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Settings;
import clientrest.com.clientrest.R;

public class NotificationSettings extends AppCompatActivity {

    private CheckBox cbAlwaysNotifyNewConsumer, cbNotifyAlways, cbSong;
    private DBHelper database;
    private Settings settings;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_settings);

        database = new DBHelper(getBaseContext());
        settings = database.getLastPrivacySettings();
        initializeVariables();

        cbAlwaysNotifyNewConsumer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                  settings.setNotifyNewConsumer(1);
                    cbNotifyAlways.setEnabled(false);
                }else{
                    settings.setNotifyNewConsumer(0);
                    cbNotifyAlways.setEnabled(true);
                }
                database.insertPrivacySetting(settings);
            }
        });

        cbSong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   settings.setSoundNotification(1);
                }else{
                    settings.setSoundNotification(0);;
                }
                database.insertPrivacySetting(settings);
            }
        });

        cbNotifyAlways.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settings.setAlwaysNotify(1);
                    cbAlwaysNotifyNewConsumer.setEnabled(false);
                }else{
                    settings.setAlwaysNotify(0);;
                    cbAlwaysNotifyNewConsumer.setEnabled(true);
                }
                database.insertPrivacySetting(settings);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    private void initializeVariables() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        cbAlwaysNotifyNewConsumer = (CheckBox) findViewById(R.id.cbAlwaysNotifyNewConsumer);
        cbNotifyAlways = (CheckBox) findViewById(R.id.cbNotifyAlways);
        cbSong = (CheckBox) findViewById(R.id.cbSong);

        cbAlwaysNotifyNewConsumer.setChecked((settings.getNotifyNewConsumer() == 1) ? true : false);
        cbNotifyAlways.setChecked((settings.getAlwaysNotify() == 1) ? true : false);
        cbSong.setChecked((settings.getSoundNotification() == 1) ? true : false);

        if(cbNotifyAlways.isChecked()){
            cbAlwaysNotifyNewConsumer.setEnabled(false);
        }
        if(cbAlwaysNotifyNewConsumer.isChecked()){
            cbNotifyAlways.setEnabled(false);
        }
    }


}
