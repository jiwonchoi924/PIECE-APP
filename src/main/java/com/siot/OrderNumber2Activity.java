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

//import java.net.Socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class OrderNumber2Activity extends AppCompatActivity {

    Socket socket; //
    String STORE_NAME;
    EditText inputNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_number2);

        final TextView textView = (TextView)findViewById(R.id.textView); //
        inputNumberText = (EditText) findViewById(R.id.inputNumberText);
        Button cancleButton = (Button)findViewById(R.id.cancleButton);
        Button okButton = (Button)findViewById(R.id.okButton);

        //수정부분
        try{
            socket = IO.socket("http://3.14.148.217:8123");
        }catch (Exception e){
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("message_from_client", "I am Android");
            }
        }).on("message_from_server", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(args[0].toString());
                    }
                });
            }
        });
        socket.connect();

        //여기까지

        Intent intent = getIntent();
        STORE_NAME = intent.getStringExtra("STORE_NAME"); //사용할 값 = 건너온 값

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NUMBER = inputNumberText.getText().toString();

                socket.emit("message_from_client",NUMBER); //

                /*Intent intent = new Intent(OrderNumber2Activity.this, HostPaychatActivity.class);
                intent.putExtra("STORE_NAME", STORE_NAME);
                intent.putExtra("NUMBER", NUMBER);
                OrderNumber2Activity.this.startActivity(intent);*/
            }
        });
    }
}
