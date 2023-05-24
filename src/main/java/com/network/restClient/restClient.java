package com.network.restClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class restClient {

    public static Map<String, Object> pathVariableGet(String address, String... values) {
        Map<String, Object> resultMap = new HashMap<>();
        if (values.length != 0) {
            for (String value : values) {
                address = address.concat("/");
                address = address.concat(value);
            }
        }
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(address);

        getRequest.addHeader("accept", "application/json");
        try {
            HttpResponse httpResponse = httpClient.execute(getRequest);

            if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                if (httpResponse.getEntity() != null) {
                    String content = EntityUtils.toString(httpResponse.getEntity());
                    resultMap = new ObjectMapper().readValue(content, new TypeReference<Map<String, Object>>() {
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
