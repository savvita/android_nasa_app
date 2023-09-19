package com.savita.nasa_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.savita.nasa_app.configs.NasaApiConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.sample_data.ApodSample;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ApodController apodController;

    //TODO delete permissions for reading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apodController = new ApodController(new ApodSample(getFilesDir()), NasaApiConfig.BASE_URL + "/planetary/apod");
//
//        //TODO delete this
//        ApodController controller = new ApodController();
//        HashMap<String, String> params = new HashMap<>();
//        params.put("start_date", "2023-08-01");
//
//        new Thread(() -> {
//
//            List<APOD> apods = controller.getList(params);
//            Log.d(TAG, "onCreate: " + apods.size());
//        }).start();

        if (checkPermission()) {
            loadSample();
        } else {
            requestPermission();
        }

    }

    //TODO delete this

    private void loadSample() {
        new Thread(() -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("start_date", "2023-08-01");
            List<Apod> apods = apodController.get(params);
            Log.d(TAG, "loadSample: " + apods.size());
        }).start();
    }

    private static final int PERMISSION_REQUEST_CODE = 100;
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSample();
        } else {
        }
    }
    // end deleting
}