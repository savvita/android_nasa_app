package com.savita.nasa_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.net.ApodController;
import com.savita.nasa_app.net.Loader;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

public class ApodFragment extends Fragment {
    private int idx;
    public static final String APOD_IDX = "apod_idx";
    public static final int IDM_A = 101;
    private Consumer<Integer> onSaveClicked;

    public ApodFragment() {
    }

    public Consumer<Integer> getOnSaveClicked() {
        return onSaveClicked;
    }

    public void setOnSaveClicked(Consumer<Integer> onSaveClicked) {
        this.onSaveClicked = onSaveClicked;
    }

    public static ApodFragment newInstance(int idx) {
        ApodFragment fragment = new ApodFragment();
        Bundle args = new Bundle();
        args.putInt(APOD_IDX, idx);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idx = getArguments().getInt(APOD_IDX);
        } else {
            idx = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apod, container, false);
        Apod apod = ApodController.items.get(idx);
        if(apod != null) {
            ImageView image = view.findViewById(R.id.apod_image);

            new Thread(() -> Loader.loadImageInto(apod.getUrl(), image)).start();

            TextView title = view.findViewById(R.id.apod_title);
            TextView date = view.findViewById(R.id.apod_date);
            TextView explanation = view.findViewById(R.id.apod_explanation);
            TextView copyright = view.findViewById(R.id.apod_copyright);
            title.setText(apod.getTitle());
            date.setText(new SimpleDateFormat(ApodConfig.DATE_FORMAT).format(apod.getDate()));
            explanation.setText(apod.getExplanation());
            if(apod.getCopyright() != null) {
                copyright.setText("Copyright: " + apod.getCopyright());
            } else {
                copyright.setVisibility(View.GONE);
            }

            registerForContextMenu(image);
        }
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_A, Menu.NONE, getString(R.string.save_to_gallery));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == IDM_A) {
            if(onSaveClicked != null) {
                onSaveClicked.accept(idx);
            }
        }
        return true;
    }
}