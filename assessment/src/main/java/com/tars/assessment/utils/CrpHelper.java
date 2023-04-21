package com.tars.assessment.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CrpHelper {
    public static String getXm(String dxbh) {
        String url = "http://localhost:9007/ic/crp/xm/" + dxbh;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.getForEntity(url,
                String.class);
        return res.getBody();
    }
}
