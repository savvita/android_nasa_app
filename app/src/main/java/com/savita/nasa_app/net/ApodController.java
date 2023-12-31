package com.savita.nasa_app.net;

import android.util.Log;

import com.savita.nasa_app.models.Apod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ApodController {
    private static final String TAG = "ApodController";
    public final String baseUrl;
    private IApiController controller;
    public static final List<Apod> items = new ArrayList<>();

    public ApodController(IApiController controller, String baseUrl) {
        this.controller = controller;
        this.baseUrl = baseUrl;
    }

    public List<Apod> get(HashMap<String, String> params) {
        List<Apod> apods = new ArrayList<>();
        String response = controller.get(baseUrl, params);

        if(response != null) {
            if(params == null || params.size() == 0) {
                try {
                    apods.add(Apod.deserialize(new JSONObject(response)));
                } catch (Exception ex) {
                    Log.d(TAG, "get: ", ex);
                }
            } else {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        apods.add(Apod.deserialize(jsonArr.getJSONObject(i)));
                    }

                    Collections.reverse(apods);
                    this.items.addAll(apods);
                } catch (Exception ex) {
                    Log.d(TAG, "get: ", ex);
                }
            }
        }

        return apods;
    }
}
