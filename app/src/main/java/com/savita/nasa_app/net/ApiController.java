package com.savita.nasa_app.net;

import android.util.Log;

import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.configs.NasaApiConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.net.ssl.HttpsURLConnection;

public class ApiController implements IApiController {
    private static final String TAG = "ApiController";

    @Override
    public String get(String baseUrl, HashMap<String, String> params) {
        HttpsURLConnection connection = null;
        String result = null;
        try {
            connection = getConnection(baseUrl, params);
            connection.setDoInput(true);

            int code = connection.getResponseCode();
            Log.d(TAG, "get: " + code + "; message: " + connection.getResponseMessage());

            if(code == HttpsURLConnection.HTTP_OK) {
                try (InputStream stream = connection.getInputStream()) {
                    result = getStringFromStream(stream);
                }
            }
        } catch (Exception ex) {
            Log.d(TAG, "get: ", ex);
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }
    private HttpsURLConnection getConnection(String baseUrl, HashMap<String, String> params) throws IOException {
        java.net.URL url = new URL(getUrl(baseUrl, params));
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");

        return connection;
    }

    private String getUrl(String baseUrl, HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder(baseUrl);
        sb.append("?api_key=" + NasaApiConfig.API_KEY);

        if(params != null && params.size() > 0) {
            sb.append("&");
            params.keySet().forEach(key -> {
                sb.append(key + "=" + params.get(key) + "&");
            });

            sb.deleteCharAt(sb.length() - 1);
        }

        Log.d(TAG, "getUrl: " + sb.toString());

        return sb.toString();
    }
//
//    public static <T> List<T> getList(String baseUrl, HashMap<String, String> params, Function<JSONObject, T> deserializer) {
//        HttpsURLConnection connection = null;
//        List<T> results = new ArrayList<>();
//        try {
//            connection = getConnection(baseUrl, params);
//            connection.setDoInput(true);
//
//            int code = connection.getResponseCode();
//            Log.d(TAG, "get: " + code + "; message: " + connection.getResponseMessage());
//
//            if(code == HttpsURLConnection.HTTP_OK) {
//                try (InputStream stream = connection.getInputStream()) {
//                    String json = getStringFromStream(stream);
//                    Log.d(TAG, "getList: " + json);
//                    JSONArray jsonArr = new JSONArray(json);
//                    for(int i = 0; i < jsonArr.length(); i++) {
//                        JSONObject objJson = jsonArr.getJSONObject(i);
//                        results.add(deserializer.apply(objJson));
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Log.d(TAG, "get: ", ex);
//        } finally {
//            if(connection != null) {
//                connection.disconnect();
//            }
//        }
//
//        return results;
//    }
//
//    public static <T> T getSingle(String baseUrl, HashMap<String, String> params, Function<JSONObject, T> deserializer) {
//        HttpsURLConnection connection = null;
//        T result = null;
//        try {
//            connection = getConnection(baseUrl, params);
//            connection.setDoInput(true);
//
//            int code = connection.getResponseCode();
//            Log.d(TAG, "get: " + code + "; message: " + connection.getResponseMessage());
//
//            if(code == HttpsURLConnection.HTTP_OK) {
//                try (InputStream stream = connection.getInputStream()) {
//                    String json = getStringFromStream(stream);
//                    result = deserializer.apply(new JSONObject(json));
//                }
//            }
//        } catch (Exception ex) {
//            Log.d(TAG, "get: ", ex);
//        } finally {
//            if(connection != null) {
//                connection.disconnect();
//            }
//        }
//
//        return result;
//    }

    private String getStringFromStream(InputStream stream) throws IOException {
        char[] buffer = new char[AppConfig.BUFFER_SIZE];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, numRead);
        }

        return out.toString();
    }
}
