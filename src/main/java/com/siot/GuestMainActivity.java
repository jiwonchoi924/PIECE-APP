package com.siot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GuestMainActivity extends AppCompatActivity {

    TextView txtView;
    phpDown task;
    ArrayList<ListItem> listItem = new ArrayList<ListItem>();
    private List<String> storenamelist;
    private List<String> storeaddresslist;
    int index;
    String GUEST_NAME;
    String GUEST_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        storenamelist = new ArrayList<String>();
        storeaddresslist = new ArrayList<String>();
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        task = new phpDown();
        txtView = (TextView)findViewById(R.id.txtView);
        task.execute("http://pognni.cafe24.com/StoreObject.php");
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, storenamelist));

        ImageButton searchButton = (ImageButton) this.findViewById(R.id.searchButton);

        Intent intent = getIntent(); //인텐트에서 건너온 값
        GUEST_NAME = intent.getStringExtra("GUEST_NAME"); //사용할 값 = 건너온 값
        GUEST_ID = intent.getStringExtra("GUEST_ID");

        searchButton.setOnClickListener(new View.OnClickListener(){ //검색버튼 클릭했을 때

            @Override
            public void onClick(View view){
                final String STORE_NAME= autoCompleteTextView.getText().toString();
                if(storenamelist.contains(STORE_NAME)){
                    index = storenamelist.indexOf(STORE_NAME);
                    String STORE_ADDRESS = storeaddresslist.get(index);
                    Intent intent = new Intent(GuestMainActivity.this, StoreActivity.class);
                    intent.putExtra("STORE_NAME", STORE_NAME);
                    intent.putExtra("STORE_ADDRESS", STORE_ADDRESS);
                    intent.putExtra("GUEST_NAME", GUEST_NAME);
                    intent.putExtra("GUEST_ID", GUEST_ID);
                    GuestMainActivity.this.startActivity(intent);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuestMainActivity.this);
                    builder.setMessage("검색 결과가 없습니다.")
                            .setNegativeButton("다시 시도", null) //다시시도 negative버튼
                            .create()
                            .show(); //다이얼로그 띄움
                }

            }

        });

    }

    private class phpDown extends AsyncTask<String, Integer,String> {

        @Override
        protected String doInBackground(String... urls){

            StringBuilder jsonHtml = new StringBuilder();

            try{
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                        for(;;){
                            String line = br.readLine();
                            if(line == null) break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str){

            String STORE_NAME;
            String STORE_ADDRESS;

            try{
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("StoreObject");
                for(int i=0; i<ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    STORE_NAME = jo.getString("STORE_NAME");
                    STORE_ADDRESS = jo.getString("STORE_ADDRESS");
                    listItem.add(new ListItem(STORE_NAME, STORE_ADDRESS));
                    //txtView.append("STORE_NAME : " + listItem.get(i).getData(0) + "\nSTORE_ADDRESS : " + listItem.get(i).getData(1) + "\n");
                    storenamelist.add(listItem.get(i).getData(0));
                    storeaddresslist.add(listItem.get(i).getData(1));
                }
            } catch(JSONException e){
                e.printStackTrace();
            }

        }
    }

}
