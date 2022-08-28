package com.siot;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OrderNumberActivity extends AppCompatActivity {

    String GUEST_NAME;
    String GUEST_ID;
    String STORE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_number);

        TextView textView = (TextView)findViewById(R.id.textView);
        EditText inputNumberText = (EditText) findViewById(R.id.inputNumberText);
        Button cancleButton = (Button)findViewById(R.id.cancleButton);
        Button okButton = (Button)findViewById(R.id.okButton);

        Intent intent = getIntent();
        GUEST_NAME = intent.getStringExtra("GUEST_NAME"); //사용할 값 = 건너온 값
        GUEST_ID = intent.getStringExtra("GUEST_ID");
        STORE_NAME = intent.getStringExtra("STORE_NAME");


        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderNumberActivity.this, PaychatActivity.class);
                intent.putExtra("GUEST_NAME", GUEST_NAME);
                intent.putExtra("GUEST_ID", GUEST_ID);
                intent.putExtra("STORE_NAME", STORE_NAME);
                OrderNumberActivity.this.startActivity(intent);
            }
        });
    }
}
