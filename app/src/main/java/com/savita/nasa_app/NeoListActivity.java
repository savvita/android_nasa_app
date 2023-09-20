package com.savita.nasa_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.savita.nasa_app.adapters.NeoAdapter;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.configs.NasaApiConfig;
import com.savita.nasa_app.models.Neo;
import com.savita.nasa_app.net.ApiController;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.net.NeoController;
import com.savita.nasa_app.net.NewsController;
import com.savita.nasa_app.sample_data.ApodSample;
import com.savita.nasa_app.sample_data.NeoSample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class NeoListActivity extends AppCompatActivity {
    private TextView dateView;
    private RecyclerView neoRecyclerView;
    private DateFormat formatter;
    private ProgressBar spinner;
    private NeoAdapter adapter;
    private List<Neo> items;
    private NeoController controller;
    private Date date;
    private SwitchCompat isHazardousOnly;

    private DatePickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neo_list);

        dateView = findViewById(R.id.neo_list_date);
        spinner = findViewById(R.id.neo_list_progress_bar);
        neoRecyclerView = findViewById(R.id.neo_recycler);

        date = new Date();

        items = new ArrayList<>();

        adapter = new NeoAdapter(this, items);
        neoRecyclerView.setAdapter(adapter);
        neoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        formatter = new SimpleDateFormat(AppConfig.DATE_FORMAT);
        dateView.setText(formatter.format(new Date(System.currentTimeMillis())));
        dateView.setOnClickListener(view -> dialog.show());

        //TODO change this
        controller = new NeoController(new NeoSample(getFilesDir()), NasaApiConfig.BASE_URL + NasaApiConfig.NEO_PATH);
//        controller = new NeoController(new ApiController(), NasaApiConfig.BASE_URL + NasaApiConfig.NEO_PATH);

        isHazardousOnly = findViewById(R.id.hazardous_only_switch);
        isHazardousOnly.setOnCheckedChangeListener((btn, checked) -> filter(checked));

        dialog = new DatePickerDialog(this);
        dialog.setOnDateSetListener((view, year, month, day) -> setDate(year, month, day));

        loadData();
    }

    private void setDate(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        date = calendar.getTime();
        dateView.setText(formatter.format(date));
        loadData();
        isHazardousOnly.setChecked(false);
    }
    private void filter(boolean checked) {
        items.clear();
        if(checked) {
            items.addAll(NeoController.items.stream().filter(x -> x.isPotentiallyHazardous()).collect(Collectors.toList()));
        } else {
            items.addAll(NeoController.items);
        }
        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        spinner.setVisibility(View.VISIBLE);
        neoRecyclerView.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put("start_date", formatter.format(date));
        params.put("end_date", formatter.format(date));
        new Thread(() -> {
            List<Neo> neos = controller.get(params);
            items.clear();
            items.addAll(neos);
            neoRecyclerView.post(() -> adapter.notifyDataSetChanged());
            spinner.post(() -> spinner.setVisibility(View.GONE));
            neoRecyclerView.post(() -> neoRecyclerView.setVisibility(View.VISIBLE));
        }).start();
    }
}