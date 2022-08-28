package com.siot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {//로그인 클래스

    final static private String URL = "http://pognni.cafe24.com/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String EMAIL, String PW, Response.Listener<String> listener){ //생성자
        super(Method.POST, URL, listener, null);//호스트방식으로 전송
        parameters = new HashMap<>(); //해시맵을 이용하여 초기화
        parameters.put("EMAIL", EMAIL);
        parameters.put("PW", PW);
    }

    @Override
    public Map<String, String> getParams() { // 현재 가지고있는 파라미터 반환
        return parameters;
    }
    //Email과 pw를 url로 보낸 뒤에 결과값을 받아오는 역할
}
