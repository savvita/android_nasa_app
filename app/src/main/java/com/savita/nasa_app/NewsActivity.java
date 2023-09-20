package com.savita.nasa_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.savita.nasa_app.adapters.NewsAdapter;
import com.savita.nasa_app.models.News;
import com.savita.nasa_app.net.NewsController;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ProgressBar spinner;
    private NewsAdapter adapter;
    private List<News> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        items = new ArrayList<>();
        recycler = findViewById(R.id.news_recycler);
        spinner = findViewById(R.id.news_spinner);
        adapter = new NewsAdapter(this, items);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void loadData() {
        spinner.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        new Thread(() -> {
            List<News> news = NewsController.get();
            items.clear();
            items.addAll(news);
            recycler.post(() -> adapter.notifyDataSetChanged());
            spinner.post(() -> spinner.setVisibility(View.GONE));
            recycler.post(() -> recycler.setVisibility(View.VISIBLE));
        }).start();
    }
}