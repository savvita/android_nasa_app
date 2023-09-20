package com.savita.nasa_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.savita.nasa_app.adapters.MainItemAdapter;
import com.savita.nasa_app.configs.NasaApiConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.models.MainItem;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.sample_data.ApodSample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recycler;
    private MainItemAdapter adapter;
    private List<MainItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeItems();
        adapter = new MainItemAdapter(this, items);
        recycler = findViewById(R.id.main_recycler);
        recycler.setAdapter(adapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycler.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void initializeItems() {
        items = new ArrayList<>();
        items.add(new MainItem(getString(R.string.news), R.drawable.news, NewsActivity.class));
        items.add(new MainItem(getString(R.string.astronomy_picture_of_the_day), R.drawable.apod, ApodListActivity.class));
        items.add(new MainItem(getString(R.string.near_earth_object), R.drawable.neo, NeoListActivity.class));
    }
}