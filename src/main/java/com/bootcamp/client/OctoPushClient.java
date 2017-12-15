package com.bootcamp.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class OctoPushClient {
    RestTemplate restTemplate;

    public OctoPushClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        restTemplate = new RestTemplate(factory);
    }

    public void sendSms(String sms) throws IOException {

        String recipients = "0022996344200,0022966168535";
        String uri= "https://www.octopush-dm.com/api/sms?user_login=unis.gnacadja@gmail.com&api_key=1kED1kgHphEztpX7iSoXnCf2V3YvRrL9&sms_text="+sms+"&sms_recipients="+recipients+"&sms_type=FR&sms_sender=Bignon";

        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Accept", "*");
        headers.add("Content-Type", "*");
        HttpEntity request = new HttpEntity(null, headers);

        String apiResponse = restTemplate.postForObject(uri,
                request, String.class);

        System.out.println(apiResponse);

//        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
//        String jsonData = response.getBody();

    }
}
