package com.savita.nasa_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.savita.nasa_app.adapters.ApodPageAdapter;
import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.net.Loader;

import java.io.File;

public class ApodActivity extends AppCompatActivity {
    private static final String TAG = "ApodActivity";
    private ViewPager2 pager;
    private int currentIndex = 0;

    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        pager = findViewById(R.id.pager);
        FragmentStateAdapter adapter = new ApodPageAdapter(this, (idx) -> {
            if (checkPermission()) {
                saveToGallery();
            } else {
                requestPermission();
            }
        });
        pager.setAdapter(adapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentIndex = position;

            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(AppConfig.APOD_EXTRA_KEY)) {
            currentIndex = intent.getIntExtra(AppConfig.APOD_EXTRA_KEY, 0);
        }

        pager.setCurrentItem(currentIndex);
    }

    private void saveToGallery() {
        Log.d(TAG, "saveToGallery: " + currentIndex);
        Apod item = ApodController.items.get(currentIndex);
        if(item == null) {
            Toast.makeText(this, getString(R.string.saving_failed), Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                String.valueOf(System.currentTimeMillis()) + ".jpg");

        Log.d(TAG, "saveToGallery: " + file.toString());
        String url = item.getHdurl() != null ? item.getHdurl() : item.getUrl();

        new Thread(() -> Loader.downloadImage(
                url,
                file,
                this::showSuccess,
                this::showFail
        )).start();

    }
    private void showSuccess(Void arg) {
        runOnUiThread(() -> Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show());
    }

    private void showFail(Void arg) {
        runOnUiThread(() -> Toast.makeText(this, getString(R.string.saving_failed), Toast.LENGTH_SHORT).show());
    }


    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveToGallery();
        } else {
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }
}