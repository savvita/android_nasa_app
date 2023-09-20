package com.savita.nasa_app.adapters;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.nasa_app.ApodActivity;
import com.savita.nasa_app.NeoActivity;
import com.savita.nasa_app.R;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.models.Neo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NeoAdapter extends RecyclerView.Adapter<NeoAdapter.NeoViewHolder> {
    private static final String TAG = "NeoAdapter";
    private Context context;
    private List<Neo> items;
    private LayoutInflater inflater;
    private DateFormat formatter;

    public NeoAdapter(Context context, List<Neo> items) {
        this.context = context;
        this.items = items;
        this.formatter = new SimpleDateFormat(AppConfig.DATE_TIME_FORMAT);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NeoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.neo_item_view, parent, false);
        return new NeoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NeoViewHolder holder, int position) {
        Neo item = items.get(position);
        if(item != null) {
            holder.name.setText(item.getName());
            if(item.getCloseApproachData() != null && item.getCloseApproachData().getCloseApproachDateFull() != null) {
                holder.date.setText(context.getString(R.string.close_approach_date) + formatter.format(item.getCloseApproachData().getCloseApproachDateFull()));
            } else {
                holder.date.setVisibility(View.GONE);
            }

            if(item.isPotentiallyHazardous()) {
                holder.alert.setVisibility(View.VISIBLE);
            } else {
                holder.alert.setVisibility(View.GONE);
            }
            holder.container.setOnClickListener((view) -> {
                Intent intent = new Intent(context, NeoActivity.class);
                intent.putExtra(AppConfig.NEO_EXTRA_KEY, position);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class NeoViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView name;
        private TextView date;
        private ImageView alert;

        public NeoViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.neo_item_container);
            name = itemView.findViewById(R.id.neo_item_name);
            date = itemView.findViewById(R.id.neo_item_date);
            alert = itemView.findViewById(R.id.note_item_alert);
        }
    }
}
