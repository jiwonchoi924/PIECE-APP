package com.siot;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.siot.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.pwText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerButton = (TextView) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, PreRegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){ //로그인버튼 클릭했을 때

            @Override
            public void onClick(View view){
                final String EMAIL = idText.getText().toString();
                final String PW = pwText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success"); //값을 찾았다면 success
                            if(success){
                                boolean isguest = jsonResponse.getBoolean("isguest");
                                if(isguest){
                                    String GUEST_EMAIL = jsonResponse.getString("GUEST_EMAIL");
                                    String GUEST_PW = jsonResponse.getString("GUEST_PW");
                                    String GUEST_NAME = jsonResponse.getString("GUEST_NAME");
                                    String GUEST_ID = jsonResponse.getString("GUEST_ID");

                                    Intent intent = new Intent(LoginActivity.this, GuestMainActivity.class);//메인액티비티?
                                    intent.putExtra("GUEST_EMAIL", GUEST_EMAIL);
                                    intent.putExtra("GUEST_PW", GUEST_PW); //인텐트에 값 저장
                                    intent.putExtra("GUEST_NAME", GUEST_NAME);
                                    intent.putExtra("GUEST_ID", GUEST_ID);
                                    LoginActivity.this.startActivity(intent); //액티비티 전환
                                }
                                else{
                                    String HOST_EMAIL = jsonResponse.getString("HOST_EMAIL");
                                    String HOST_PW = jsonResponse.getString("HOST_PW");
                                    String STORE_NAME = jsonResponse.getString("STORE_NAME");

                                    Intent intent = new Intent(LoginActivity.this, HostMainActivity.class);//메인액티비티?
                                    intent.putExtra("HOST_EMAIL", HOST_EMAIL);
                                    intent.putExtra("HOST_PW", HOST_PW); //인텐트에 값 저장
                                    intent.putExtra("STORE_NAME", STORE_NAME);
                                    LoginActivity.this.startActivity(intent); //액티비티 전환
                                }
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); //로그인 액티비티에 띄우는 다이얼로그
                                builder.setMessage("로그인 실패")
                                        .setNegativeButton("다시 시도", null) //다시시도 negative버튼
                                        .create()
                                        .show(); //다이얼로그 띄움

                            }
                        } catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(EMAIL, PW, responseListener); //만들었던 loginrequest이용하여 response받아옴
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
