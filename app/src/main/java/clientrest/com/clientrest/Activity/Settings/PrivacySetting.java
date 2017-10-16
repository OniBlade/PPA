package clientrest.com.clientrest.Activity.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import clientrest.com.clientrest.DataBase.DBHelper;
import clientrest.com.clientrest.DataBase.Entity.Settings;
import clientrest.com.clientrest.R;

public class PrivacySetting extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView tvDisplay;
    private DBHelper database;
    private Settings settings;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_settings);

        database = new DBHelper(getBaseContext());
        settings = database.getLastPrivacySettings();
        initializeVariables();

        tvDisplay.setText("Nível de Confiança: " + seekBar.getProgress() + " %");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                tvDisplay.setText("Nível de Confiança: " + progress + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvDisplay.setText("Nível de Confiança: " + progress + " %");
                settings.setConfidenceLevel(progress);
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
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);

        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        seekBar.setProgress(settings.getConfidenceLevel());

    }


}
