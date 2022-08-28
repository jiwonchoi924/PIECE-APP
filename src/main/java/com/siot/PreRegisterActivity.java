package com.siot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.siot.R;

public class PreRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_register);

        TextView registerType = (TextView) findViewById(R.id.registerType);
        Button hostButton = (Button) findViewById(R.id.hostButton);
        Button guestButton = (Button) findViewById(R.id.guestButton);

        hostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent preregisterIntent = new Intent(PreRegisterActivity.this, HostRegisterActivity.class);
                PreRegisterActivity.this.startActivity(preregisterIntent);
            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent preregisterIntent = new Intent(PreRegisterActivity.this, GuestRegisterActivity.class);
                PreRegisterActivity.this.startActivity(preregisterIntent);
            }
        });
    }
}
