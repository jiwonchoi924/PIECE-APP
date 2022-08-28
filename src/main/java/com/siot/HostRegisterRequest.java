package com.siot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class HostRegisterRequest extends StringRequest {//등록 클래스

    final static private String URL = "http://pognni.cafe24.com/HostRegister.php";
    private Map<String, String> parameters;

    public HostRegisterRequest(int HOST_ID_, String HOST_EMAIL, String HOST_PW, String OWNER_NAME, String OWNER_PHONE, String STORE_NAME, String STORE_ADDRESS, String STORE_PHONE, Response.Listener<String> listener){ //생성자 //변수 모르겠다
        super(Method.POST, URL, listener, null);//호스트방식으로 전송
        parameters = new HashMap<>(); //해시맵을 이용하여 초기화
        parameters.put("HOST_ID_",HOST_ID_+" "); //int string으로?
        parameters.put("HOST_EMAIL", HOST_EMAIL);
        parameters.put("HOST_PW", HOST_PW);
        parameters.put("OWNER_NAME", OWNER_NAME);
        parameters.put("OWNER_PHONE", OWNER_PHONE);
        parameters.put("STORE_NAME", STORE_NAME);
        parameters.put("STORE_ADDRESS", STORE_ADDRESS);
        parameters.put("STORE_PHONE", STORE_PHONE);
    }

    @Override
    public Map<String, String> getParams() { // 현재 가지고있는 파라미터 반환
        return parameters;
    }

}
