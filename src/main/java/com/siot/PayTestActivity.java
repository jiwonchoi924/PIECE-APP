package com.siot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.siot.inicissample.MainActivity;
import com.siot.R;

public class PayTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_test);

        Button payBtn = (Button) findViewById(R.id.pay_Button);
        payBtn.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // 메인을 가는 인텐트 생성
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        //액티비티 시작
                        startActivity(intent);

                    }
                }
        );

    }
}
