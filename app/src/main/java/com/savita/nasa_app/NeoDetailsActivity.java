package com.savita.nasa_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.savita.nasa_app.adapters.NeoDetailsAdapter;
import com.savita.nasa_app.configs.NasaApiConfig;
import com.savita.nasa_app.configs.NeoConfig;
import com.savita.nasa_app.models.CloseApproachData;
import com.savita.nasa_app.net.NeoController;
import com.savita.nasa_app.sample_data.NeoDetailsSample;
import com.savita.nasa_app.sample_data.NeoSample;

import java.util.ArrayList;
import java.util.List;

public class NeoDetailsActivity extends AppCompatActivity {
    private TextView name;
    private RecyclerView recycler;
    private List<CloseApproachData> items;
    private NeoController controller;
    private ProgressBar spinner;
    private NeoDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neo_details);
        name = findViewById(R.id.neo_details_name);
        recycler = findViewById(R.id.neo_details_recycler);
        spinner = findViewById(R.id.neo_details_spinner);
        items = new ArrayList<>();
        adapter = new NeoDetailsAdapter(this, items);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        //TODO change this
        controller = new NeoController(new NeoDetailsSample(getFilesDir()), NasaApiConfig.BASE_URL + NasaApiConfig.NEO_PATH);

        Intent intent = getIntent();
        if(intent.hasExtra(NeoConfig.NAME_KEY)) {
            name.setText(intent.getStringExtra(NeoConfig.NAME_KEY));
        }

        if(intent.hasExtra(NeoConfig.ID_KEY)) {
            loadData(intent.getStringExtra(NeoConfig.ID_KEY));
        }
    }

    private void loadData(String id) {
        spinner.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);

        new Thread(() -> {
            List<CloseApproachData> data = controller.getDetails(id);
            items.clear();
            items.addAll(data);
            recycler.post(() -> adapter.notifyDataSetChanged());
            spinner.post(() -> spinner.setVisibility(View.GONE));
            recycler.post(() -> recycler.setVisibility(View.VISIBLE));
        }).start();
    }
}