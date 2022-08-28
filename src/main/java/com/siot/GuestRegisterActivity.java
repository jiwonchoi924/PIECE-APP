package com.siot;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static java.sql.Types.NULL;

public class GuestRegisterActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_register);

        TextView textView = (TextView) findViewById(R.id.forTest);

        final EditText guestemailText = (EditText)findViewById(R.id.guestemailText);
        final EditText guestpwText = (EditText)findViewById(R.id.guestpwText);
        final EditText guestpwcheckText = (EditText)findViewById(R.id.guestpwcheckText);
        final EditText guestnameText = (EditText)findViewById(R.id.guestnameText);
        final EditText guestbdateText = (EditText)findViewById(R.id.guestbdateText);

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String GUEST_EMAIL = guestemailText.getText().toString();
                if (validate) {
                    return;
                }
                if (GUEST_EMAIL.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                //guestemailText.setEnabled(false);
                                validate = true;
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(GUEST_EMAIL, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GuestRegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){ //등록버튼 이벤트 처리

            @Override
            public void onClick(View view){
                String GUEST_EMAIL = guestemailText.getText().toString();
                String GUEST_PW = guestpwText.getText().toString();
                String GUEST_PW2 = guestpwcheckText.getText().toString();
                String GUEST_NAME = guestnameText.getText().toString();
                String GUEST_BDATE = guestbdateText.getText().toString();
                int GUEST_ID = NULL;

                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(GUEST_EMAIL.equals("") || GUEST_PW.equals("") || GUEST_NAME.equals("") || GUEST_BDATE.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(!GUEST_PW.equals(GUEST_PW2)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 같지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GuestRegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                GuestRegisterRequest registerRequest = new GuestRegisterRequest(GUEST_EMAIL, GUEST_PW, GUEST_ID, GUEST_NAME, GUEST_BDATE, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GuestRegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
