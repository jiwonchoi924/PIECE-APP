package com.siot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class StoreActivity extends AppCompatActivity {
    String STORE_NAME;
    String STORE_ADDRESS;
    String GUEST_NAME;
    String GUEST_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView txtView = (TextView) findViewById(R.id.txtView);
        TextView txtView2 = (TextView) findViewById(R.id.txtView2);
        Button noButton = (Button) findViewById(R.id.noButton);
        Button yesButton = (Button) findViewById(R.id.yesButton);

        Intent intent = getIntent(); //인텐트에서 건너온 값
        STORE_NAME = intent.getStringExtra("STORE_NAME"); //사용할 값 = 건너온 값
        STORE_ADDRESS = intent.getStringExtra("STORE_ADDRESS");
        GUEST_NAME = intent.getStringExtra("GUEST_NAME"); //사용할 값 = 건너온 값
        GUEST_ID = intent.getStringExtra("GUEST_ID");

        txtView.setText(STORE_NAME + " 으로 선택하시겠어요?\n");
        txtView2.setText(STORE_NAME + "\n" + STORE_ADDRESS);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, OrderNumberActivity.class);
                intent.putExtra("GUEST_NAME", GUEST_NAME);
                intent.putExtra("GUEST_ID", GUEST_ID);
                intent.putExtra("STORE_NAME", STORE_NAME);
                StoreActivity.this.startActivity(intent);
            }
        });

    }

}
