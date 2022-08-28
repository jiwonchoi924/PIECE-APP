package com.siot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class GuestRegisterRequest extends StringRequest {//등록 클래스

    final static private String URL = "http://pognni.cafe24.com/GuestRegister.php";
    private Map<String, String> parameters;

    public GuestRegisterRequest(String GUEST_EMAIL, String GUEST_PW, int GUEST_ID, String GUEST_NAME, String GUEST_BDATE, Response.Listener<String> listener){ //생성자 //변수 모르겠다
        super(Method.POST, URL, listener, null);//호스트방식으로 전송
        parameters = new HashMap<>(); //해시맵을 이용하여 초기화
        parameters.put("GUEST_EMAIL", GUEST_EMAIL);
        parameters.put("GUEST_PW", GUEST_PW);
        parameters.put("GUEST_ID", GUEST_ID+" "); //int string으로?
        parameters.put("GUEST_NAME", GUEST_NAME);
        parameters.put("GUEST_BDATE", GUEST_BDATE);
    }

    @Override
    public Map<String, String> getParams() { // 현재 가지고있는 파라미터 반환
        return parameters;
    }

}
