package com.savita.nasa_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.savita.nasa_app.adapters.ApodAdapter;
import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.configs.NasaApiConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.models.Neo;
import com.savita.nasa_app.net.ApiController;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.net.Loader;
import com.savita.nasa_app.net.NeoController;
import com.savita.nasa_app.sample_data.ApodSample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private Date startDate;
    private Date endDate;
    private DateFormat formatter;
    private GridLayoutManager manager;
    private Calendar calendar;
    private boolean loading = false;
    private boolean loadingLastImage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_list);

        lastImage = findViewById(R.id.last_image_preview);
        spinner = findViewById(R.id.progressBar);

        formatter = new SimpleDateFormat(ApodConfig.DATE_FORMAT);
        calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, -1);
        startDate = calendar.getTime();

        //TODO change this
        controller = new ApodController(new ApodSample(getFilesDir()), NasaApiConfig.BASE_URL + NasaApiConfig.APOD_PATH);
//        controller = new ApodController(new ApiController(), NasaApiConfig.BASE_URL + "/planetary/apod");

        items = new ArrayList<>();
        apodAdapter = new ApodAdapter(this, items);

        previewRecyclerView = findViewById(R.id.preview_recycler_view);
        manager = new GridLayoutManager(this, 2);
        previewRecyclerView.setLayoutManager(manager);
        previewRecyclerView.setAdapter(apodAdapter);


        previewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScroll();
            }
        });

        lastImage.setOnClickListener((view) -> {
            Intent intent = new Intent(this, ApodActivity.class);
            intent.putExtra(AppConfig.APOD_EXTRA_KEY, 0);
            startActivity(intent);
        });

        spinner.setVisibility(View.VISIBLE);
        previewRecyclerView.setVisibility(View.GONE);
        lastImage.setVisibility(View.GONE);

        loadData();
    }

    private void onScroll() {
        int lastVisiblePosition = manager.findLastVisibleItemPosition();
        Log.d(TAG, "onScroll: " + manager.getItemCount() + " " + lastVisiblePosition + " " + loading);
        if(manager.getItemCount() - lastVisiblePosition <= 2 && !loading) {
            Log.d(TAG, "onScrolled: need load");
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            endDate = calendar.getTime();
            calendar.add(Calendar.MONTH, -1);
            startDate = calendar.getTime();
            loadData();
        }
    }

    private void loadData() {
        loading = true;
        new Thread(() -> {
            HashMap<String, String> params = new HashMap<>();

            if(startDate != null) {
                params.put("start_date", formatter.format(startDate));
                Log.d(TAG, "loadData: start date: " + formatter.format(startDate));
            }

            if(endDate != null) {
                params.put("end_date", formatter.format(endDate));
                Log.d(TAG, "loadData: end date: " + formatter.format(endDate));
            }

            List<Apod> newApods = controller.get(params);
            items.addAll(newApods);

            previewRecyclerView.post(() -> apodAdapter.notifyDataSetChanged());
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && loadingLastImage && items.size() > 0) {
                new Thread(() -> Loader.loadImageInto(items.get(0).getUrl(), lastImage)).start();
                lastImage.post(() -> lastImage.setVisibility(View.VISIBLE));
                loadingLastImage = false;
            }
            previewRecyclerView.post(() -> previewRecyclerView.setVisibility(View.VISIBLE));
            spinner.post(() -> spinner.setVisibility(View.GONE));
            loading = false;
        }).start();
    }
}