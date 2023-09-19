package com.savita.nasa_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.nasa_app.ApodActivity;
import com.savita.nasa_app.R;
import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.models.Apod;
import com.savita.nasa_app.net.Loader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ApodAdapter extends RecyclerView.Adapter<ApodAdapter.ApodViewHolder> {
    private static final String TAG = "ApodAdapter";
    private Context context;
    private List<Apod> items;
    private LayoutInflater inflater;
    private DateFormat formatter;

    public ApodAdapter(Context context, List<Apod> items) {
        this.context = context;
        this.items = items;
        this.formatter = new SimpleDateFormat(ApodConfig.DATE_FORMAT);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ApodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.apod_item_view, parent, false);
        return new ApodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApodViewHolder holder, int position) {
        Apod item = items.get(position);
        if(item != null) {
            holder.dateTxt.setText(formatter.format(item.getDate()));
            new Thread(() -> Loader.loadImageInto(item.getUrl(), holder.preview)).start();
            holder.container.setOnClickListener((view) -> {
                Intent intent = new Intent(context, ApodActivity.class);
                intent.putExtra(AppConfig.APOD_EXTRA_KEY, position);
                context.startActivity(intent);
            });
            Log.d(TAG, "onBindViewHolder: " + item.toString());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ApodViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTxt;
        private ConstraintLayout container;
        private ImageView preview;

        public ApodViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.apod_date_preview);
            container = itemView.findViewById(R.id.apod_item_container);
            preview = itemView.findViewById(R.id.apod_image_preview);
        }
    }
}
