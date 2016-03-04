package com.owen.gcm_modle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.owen.gcm_modle.gcm.GcmController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GcmController gcmController = new GcmController(this);
        gcmController.init();

        String notificateinInfo = this.getIntent().getStringExtra(getString(R.string.notification_info));

        TextView textView = (TextView)findViewById(R.id.notification_text);
        textView.setText(notificateinInfo);
    }
}
