package clientrest.com.clientrest.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import clientrest.com.clientrest.R;

public class PrivacyActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Configuração Privacidade");

        initializeVariables();

        tvDisplay.setText("Níve de Confiança: " + seekBar.getProgress() + " %");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                tvDisplay.setText("Níve de Confiança: " + progress + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvDisplay.setText("Níve de Confiança: " + progress + " %");
            }

        });

    }

    @Override
    public void onBackPressed(){
        Intent mainIntent = new Intent(PrivacyActivity.this, MainActivity.class);
        PrivacyActivity.this.startActivity(mainIntent);
        PrivacyActivity.this.finish();
        return;
    }

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
    }


}
