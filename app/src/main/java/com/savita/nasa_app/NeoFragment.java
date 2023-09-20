package com.savita.nasa_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.models.Neo;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.net.Loader;
import com.savita.nasa_app.net.NeoController;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

public class NeoFragment extends Fragment {
    private int idx;
    public static final String NEO_IDX = "neo_idx";


    private Consumer<Integer> onSeeAllClicked;

    public NeoFragment() {
    }
    public static NeoFragment newInstance(int idx) {
        NeoFragment fragment = new NeoFragment();
        Bundle args = new Bundle();
        args.putInt(NEO_IDX, idx);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnSeeAllClicked(Consumer<Integer> onSeeAllClicked) {
        this.onSeeAllClicked = onSeeAllClicked;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idx = getArguments().getInt(NEO_IDX);
        } else {
            idx = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neo, container, false);
        Neo item = NeoController.items.get(idx);
        if(item != null) {
            TextView name = view.findViewById(R.id.neo_name);
            TextView maxDiameter = view.findViewById(R.id.neo_max_diameter);
            TextView minDiameter = view.findViewById(R.id.neo_min_diameter);
            TextView closeApproachDate = view.findViewById(R.id.neo_close_approach_date);
            TextView velocity = view.findViewById(R.id.neo_velocity);
            TextView missDistance = view.findViewById(R.id.neo_miss_distance);
            TextView orbitingBody = view.findViewById(R.id.neo_orbiting_body);
            TextView isPotentiallyHazardous = view.findViewById(R.id.neo_hazardous);
            Button seeAllBtn = view.findViewById(R.id.see_all_btn);

            name.setText(item.getName());
            maxDiameter.setText(item.getEstimatedDiameterMeters().getMax() + " " + getString(R.string.meters));
            minDiameter.setText(item.getEstimatedDiameterMeters().getMin() + " " + getString(R.string.meters));
            closeApproachDate.setText(new SimpleDateFormat(AppConfig.DATE_TIME_FORMAT).format(item.getCloseApproachData().getCloseApproachDateFull()));
            velocity.setText(item.getCloseApproachData().getRelativeVelocity() + " " + getString(R.string.km_s));
            missDistance.setText(item.getCloseApproachData().getMissDistance() + " " + getString(R.string.a_u));
            orbitingBody.setText(item.getCloseApproachData().getOrbitingBody());

            if(item.isPotentiallyHazardous()) {
                isPotentiallyHazardous.setVisibility(View.VISIBLE);
            } else {
                isPotentiallyHazardous.setVisibility(View.GONE);
            }

            seeAllBtn.setOnClickListener(v -> {
                if(onSeeAllClicked != null) {
                    onSeeAllClicked.accept(idx);
                }
            });
        }
        return view;
    }
}