package com.siot;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {//등록 클래스

    final static private String URL = "http://pognni.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String EMAIL, Response.Listener<String> listener){ //생성자 //변수 모르겠다
        super(Method.POST, URL, listener, null);//호스트방식으로 전송
        parameters = new HashMap<>(); //해시맵을 이용하여 초기화
        parameters.put("EMAIL", EMAIL);
    }

    @Override
    public Map<String, String> getParams() { // 현재 가지고있는 파라미터 반환
        return parameters;
    }

}
