package com.siot;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.siot.R;

import org.json.JSONException;
import org.json.JSONObject;

import static java.sql.Types.NULL;

public class HostRegisterActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private boolean validate = false;
    EditText storeaddressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_register);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFF39C11));

        TextView textView4 = (TextView) findViewById(R.id.textView4);
        final EditText hostemailText = (EditText) findViewById(R.id.hostemailText);
        final EditText hostpwText = (EditText)findViewById(R.id.hostpwText);
        final EditText hostpwcheckText = (EditText)findViewById(R.id.hostpwcheckText);
        final EditText ownernameText = (EditText)findViewById(R.id.ownernameText);
        final EditText ownerphoneText = (EditText)findViewById(R.id.ownerphoneText);
        final EditText storenameText = (EditText)findViewById(R.id.storenameText);
        storeaddressText = (EditText)findViewById(R.id.storeaddressText);
        final EditText storephoneText = (EditText)findViewById(R.id.storephoneText);

        Button addressButton = (Button) findViewById(R.id.addressButton);
        addressButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent (getApplicationContext(),DaumWebViewActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
        );

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String HOST_EMAIL = hostemailText.getText().toString();
                if (validate) {
                    return;
                }
                if (HOST_EMAIL.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                    dialog = builder.setMessage("???????????? ??? ?????? ??? ????????????.")
                            .setPositiveButton("??????", null)
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                                dialog = builder.setMessage("????????? ??? ?????? ??????????????????.")
                                        .setPositiveButton("??????", null)
                                        .create();
                                dialog.show();
                                //guestemailText.setEnabled(false);
                                validate = true;
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                                dialog = builder.setMessage("????????? ??? ?????? ??????????????????.")
                                        .setNegativeButton("??????", null)
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
                ValidateRequest validateRequest = new ValidateRequest(HOST_EMAIL, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HostRegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){ //???????????? ????????? ??????

            @Override
            public void onClick(View view){
                String HOST_EMAIL = hostemailText.getText().toString();
                String HOST_PW = hostpwText.getText().toString();
                String HOST_PW2 = hostpwcheckText.getText().toString();
                String OWNER_NAME = ownernameText.getText().toString();
                String OWNER_PHONE = ownerphoneText.getText().toString();
                String STORE_NAME = storenameText.getText().toString();
                String STORE_ADDRESS = storeaddressText.getText().toString();
                String STORE_PHONE = storephoneText.getText().toString();
                int HOST_ID_ = NULL;

                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                    dialog = builder.setMessage("?????? ?????? ????????? ????????????.")
                            .setNegativeButton("??????", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(HOST_EMAIL.equals("") || HOST_PW.equals("") || OWNER_NAME.equals("") || OWNER_PHONE.equals("") || STORE_NAME.equals("") || STORE_ADDRESS.equals("") || STORE_PHONE.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                    dialog = builder.setMessage("??? ??? ?????? ??????????????????.")
                            .setNegativeButton("??????", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(!HOST_PW.equals(HOST_PW2)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                    dialog = builder.setMessage("??????????????? ?????? ????????????.")
                            .setNegativeButton("??????", null)
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                                dialog = builder.setMessage("?????? ????????? ??????????????????.")
                                        .setPositiveButton("??????", null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HostRegisterActivity.this);
                                dialog = builder.setMessage("?????? ????????? ??????????????????.")
                                        .setNegativeButton("??????", null)
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
                HostRegisterRequest registerRequest = new HostRegisterRequest(HOST_ID_, HOST_EMAIL, HOST_PW, OWNER_NAME, OWNER_PHONE, STORE_NAME, STORE_ADDRESS, STORE_PHONE, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HostRegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK) {
                String address = data.getStringExtra("address");
                storeaddressText.setText(address);
            }
            if(resultCode == Activity.RESULT_CANCELED){
            }
        }
    }

}

