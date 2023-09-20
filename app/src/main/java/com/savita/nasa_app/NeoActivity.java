package com.savita.nasa_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.savita.nasa_app.adapters.ApodPageAdapter;
import com.savita.nasa_app.adapters.NeoPageAdapter;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.configs.NeoConfig;
import com.savita.nasa_app.net.NeoController;

public class NeoActivity extends AppCompatActivity {
    private static final String TAG = "NeoActivity";
    private ViewPager2 pager;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neo);

        pager = findViewById(R.id.neo_pager);
        FragmentStateAdapter adapter = new NeoPageAdapter(this, (idx) -> showDetails());
        pager.setAdapter(adapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentIndex = position;
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(AppConfig.NEO_EXTRA_KEY)) {
            currentIndex = intent.getIntExtra(AppConfig.NEO_EXTRA_KEY, 0);
        }

        pager.setCurrentItem(currentIndex);
    }

    private void showDetails() {
        Intent intent = new Intent(this, NeoDetailsActivity.class);
        intent.putExtra(NeoConfig.ID_KEY, NeoController.items.get(currentIndex).getId());
        intent.putExtra(NeoConfig.NAME_KEY, NeoController.items.get(currentIndex).getName());
        startActivity(intent);
    }
}