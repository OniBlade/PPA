package clientrest.com.clientrest.Activity.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import clientrest.com.clientrest.R;

public class HelpSettings extends AppCompatActivity {

    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_settings);
        InitializeVariables();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void InitializeVariables() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
    }


    @Override
    public void onBackPressed() {
        finish();
        return;
    }
}
