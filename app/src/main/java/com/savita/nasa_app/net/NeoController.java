package com.savita.nasa_app.net;

import android.util.Log;

import com.savita.nasa_app.configs.NeoConfig;
import com.savita.nasa_app.models.Neo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NeoController {
    private static final String TAG = "NeoController";
    public final String baseUrl;
    private IApiController controller;
    public static final List<Neo> items = new ArrayList<>();

    public NeoController(IApiController controller, String baseUrl) {
        this.controller = controller;
        this.baseUrl = baseUrl;
    }


    public List<Neo> get(HashMap<String, String> params) {
        List<Neo> neos = new ArrayList<>();
        String response = controller.get(baseUrl, params);

        Log.d(TAG, "get: " + response);

        if(response != null) {
            try {
                JSONObject responseObj = new JSONObject(response);
                JSONObject neoObj = responseObj.getJSONObject(NeoConfig.NEAR_EARTH_OBJECTS_KEY);
                for (Iterator<String> it = neoObj.keys(); it.hasNext(); ) {
                    String key = it.next();
                    try {
                        JSONArray neoArr = neoObj.getJSONArray(key);
                        for(int i = 0; i < neoArr.length(); i++) {
                            Neo neo = Neo.deserialize(neoArr.getJSONObject(i));
                            neos.add(neo);
                        }
                    } catch(Exception ex) {
                        Log.d(TAG, "get: ", ex);
                    }
                }

                items.clear();
                items.addAll(neos);
            } catch (Exception ex) {
                Log.d(TAG, "get: ", ex);
            }
        }

        return neos;
    }
}
