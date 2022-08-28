package com.siot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.lang.String;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.siot.inicissample.MainActivity;


public class PaychatActivity extends AppCompatActivity {

    private static String port = "8123"; // 수정코드
    private static String ip = "3.14.148.217";  //수정코드

    String streammsg = "";
    TextView showText;
    //Button connectBtn;
    Button payBtn;
    Button Button_send;
    EditText editText_massage;
    Handler msghandler;

    //TextView currentAmountTv; ////1205
    TextView balanceAmountTv; ////1218
    TextView totalAmountTv; ////1205

    int currentAmount = 0; ////1205
    private static int totalAmount = 25000; ////인텐트로 수정 필요!!
    private static int balanceAmount = 0; ///oncreate안에선언??


    String mac=""; //임시로 mac

    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    Socket socket;

    PipedInputStream sendstream = null;
    PipedOutputStream receivestream = null;

    LinkedList<SocketClient> threadList;

    String MYGUESTNAME;
    String MYGUESTID;

    String GUESTNAME;
    String GUESTID;

    String STORE_NAME;
    String NUMBER;


    /*public String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces;
            interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;

                }
                byte[] mac = intf.getHardwareAddress();

                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paychat);

        Intent intent = getIntent(); //인텐트에서 건너온 값
        MYGUESTNAME = intent.getStringExtra("GUEST_NAME"); //사용할 값 = 건너온 값
        MYGUESTID = intent.getStringExtra("GUEST_ID");
        STORE_NAME = intent.getStringExtra("STORE_NAME");
        NUMBER = intent.getStringExtra("NUMBER");

        showText = (TextView) findViewById(R.id.showText_TextView);
        editText_massage = (EditText) findViewById(R.id.editText_massage);
        Button_send = (Button) findViewById(R.id.Button_send);
        threadList = new LinkedList<PaychatActivity.SocketClient>();

        //currentAmountTv = (TextView) findViewById(R.id.currentAmount_TextView);
        balanceAmountTv = (TextView) findViewById(R.id.balanceAmount_TextView);
        totalAmountTv = (TextView) findViewById(R.id.totalAmount_TextView);


        payBtn = (Button)findViewById(R.id.pay_Button);
        payBtn.setEnabled(false);

        if(balanceAmount ==0) payBtn.setEnabled(true);

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

        totalAmountTv.setText("총액 " + totalAmount +"원"); ////1205
        balanceAmountTv.setText("남은 금액 " + totalAmount + "원"); ////1218


        // ReceiveThread를통해서 받은 메세지를 Handler로 MainThread에서 처리(외부Thread에서는 UI변경이불가)
        msghandler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                if (hdmsg.what == 1111) {

                    balanceAmountTv.setText("남은 금액 " + balanceAmount + "원"); ////1217
                    showText.append(hdmsg.obj.toString() + "\n");
                }
            }
        };

        client = new SocketClient(ip, port);
        threadList.add(client);
        client.start();


        //전송 버튼 클릭 이벤트
        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //SendThread 시작
                if (editText_massage.getText().toString() != null) {
                    send = new SendThread(socket);
                    send.start();

                    //시작후 edittext 초기화
                    editText_massage.setText("");
                }
            }
        });



    }

    class SocketClient extends Thread {
        boolean threadAlive;
        String ip;
        String port;

        //InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader br = null;

        private DataOutputStream output = null;

        public SocketClient(String ip, String port) {
            threadAlive = true;
            this.ip = ip;
            this.port = port;
        }



        @Override
        public void run() {

            try {
                // 연결후 바로 ReceiveThread 시작
                socket = new Socket(ip, Integer.parseInt(port));
                //inputStream = socket.getInputStream();
                output = new DataOutputStream(socket.getOutputStream());
                receive = new ReceiveThread(socket);
                receive.start();

                output.writeUTF(MYGUESTNAME);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ReceiveThread extends Thread { //수신//////////////////////////////////////////////////////
        private Socket socket = null;
        DataInputStream input;

        public ReceiveThread(Socket socket) {
            this.socket = socket;
            try{
                input = new DataInputStream(socket.getInputStream());
            }catch(Exception e){
            }
        }
        // 메세지 수신후 Handler로 전달
        public void run() {
            try {
                while (input != null) {

                    String msg = input.readUTF(); //input의 문자열 읽음->msg

                    int idx = msg.indexOf("+");

                    String key = msg.substring(0,idx);
                    String price = msg.substring(idx+1); //msg문자열 자름

                    String tempString = key+ " " +price;

                    int priceInt=0;
                    priceInt = Integer.parseInt(price);
                    currentAmount += priceInt; //현재입력한 금액 총합ㅋㅋ


                    int tmptotal = totalAmount;

                    balanceAmount = tmptotal - currentAmount;


                    if (msg != null) {
                        Log.d(ACTIVITY_SERVICE, "test");

                        Message hdmsg = msghandler.obtainMessage();
                        hdmsg.what = 1111;
                        hdmsg.obj = tempString;
                        msghandler.sendMessage(hdmsg);
                        Log.d(ACTIVITY_SERVICE,hdmsg.obj.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SendThread extends Thread { //////송신//////////////////////////////////////
        private Socket socket;
        String sendmsg = editText_massage.getText().toString();
        DataOutputStream output;

        public SendThread(Socket socket) {
            this.socket = socket;
            try {
                output = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
            }
        }

        public void run() {

            try {

                // 메세지 전송부 (누군지 식별하기위한 방법으로 mac를 사용) -->나의 guestname
                Log.d(ACTIVITY_SERVICE, "11111");
                /*String mac = null;

                mac = getMACAddress("wlan0");*/

                if (output != null) {
                    if (sendmsg != null) {
                        output.writeUTF(sendmsg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException npe) {
                npe.printStackTrace();

            }
        }
    }

}