package com.savita.nasa_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.savita.nasa_app.adapters.ApodAdapter;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.configs.NasaApiConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.net.Loader;
import com.savita.nasa_app.sample_data.ApodSample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ApodListActivity extends AppCompatActivity {
    private static final String TAG = "ApodListActivity";
    private ApodController controller;
    private RecyclerView previewRecyclerView;
    private ProgressBar spinner;
    private ApodAdapter apodAdapter;
    private List<Apod> items;
    private ImageView lastImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_list);

        lastImage = findViewById(R.id.last_image_preview);
        spinner = findViewById(R.id.progressBar);

        //TODO change this
        controller = new ApodController(new ApodSample(getFilesDir()), NasaApiConfig.BASE_URL + "/planetary/apod");

        items = new ArrayList<>();
        apodAdapter = new ApodAdapter(this, items);

        previewRecyclerView = findViewById(R.id.preview_recycler_view);
        previewRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        previewRecyclerView.setAdapter(apodAdapter);

        lastImage.setOnClickListener((view) -> {
            Intent intent = new Intent(this, ApodActivity.class);
            intent.putExtra(AppConfig.APOD_EXTRA_KEY, 0);
            startActivity(intent);
        });
        loadData();
    }

    private void loadData() {
        spinner.setVisibility(View.VISIBLE);
        previewRecyclerView.setVisibility(View.GONE);
        lastImage.setVisibility(View.GONE);
        new Thread(() -> {
            HashMap<String, String> params = new HashMap<>();

            //TODO change this
            params.put("start_date", "2023-08-01");
            params.put("end_date", "2023-08-01");

            controller.get(params);
            items.clear();
            items.addAll(ApodController.items);

            previewRecyclerView.post(() -> apodAdapter.notifyDataSetChanged());
            if(items.size() > 0) {
                new Thread(() -> Loader.loadImageInto(items.get(0).getUrl(), lastImage)).start();
                lastImage.post(() -> lastImage.setVisibility(View.VISIBLE));
            }
            previewRecyclerView.post(() -> previewRecyclerView.setVisibility(View.VISIBLE));
            spinner.post(() -> spinner.setVisibility(View.GONE));
        }).start();
    }
}