package com.siot;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class HostMainActivity extends AppCompatActivity {

    String STORE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);

//        setContentView(R.layout.activity_host_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView welcomeMessageText = (TextView) findViewById(R.id.welcomemessageText);

        Intent intent = getIntent(); //인텐트에서 건너온 값
        STORE_NAME = intent.getStringExtra("STORE_NAME"); //사용할 값 = 건너온 값

        welcomeMessageText.setText(STORE_NAME);

        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener(){ //검색버튼 클릭했을 때

            @Override
            public void onClick(View view){
                Intent intent = new Intent(HostMainActivity.this, OrderNumber2Activity.class);
                intent.putExtra("STORE_NAME", STORE_NAME);
                HostMainActivity.this.startActivity(intent);
            }

        });
    }
}
