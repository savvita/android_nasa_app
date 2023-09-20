package com.savita.nasa_app.sample_data;

import android.util.Log;

import com.savita.nasa_app.net.IApiController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class NeoSample implements IApiController {
    private static final String TAG = "NeoSample";
    private final File directory;
    public NeoSample(File appDirectory) {
        directory = appDirectory;
    }
    public String get(String baseUrl, HashMap<String, String> params) {
        File file = new File(directory, "sample/neo_sample.txt");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception ex) {
            Log.d(TAG, "getNeoSample: ", ex);
        }

        return sb.toString();
    }
}
