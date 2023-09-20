package com.savita.nasa_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.nasa_app.R;
import com.savita.nasa_app.configs.AppConfig;
import com.savita.nasa_app.models.CloseApproachData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NeoDetailsAdapter extends RecyclerView.Adapter<NeoDetailsAdapter.NeoDetailsViewHolder> {
    private static final String TAG = "NeoDetailsAdapter";
    private Context context;
    private List<CloseApproachData> items;
    private LayoutInflater inflater;
    private DateFormat formatter;
    public NeoDetailsAdapter(Context context, List<CloseApproachData> items) {
        this.context = context;
        this.items = items;
        this.formatter = new SimpleDateFormat(AppConfig.DATE_TIME_FORMAT);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NeoDetailsAdapter.NeoDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.neo_details_item_view, parent, false);
        return new NeoDetailsAdapter.NeoDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NeoDetailsAdapter.NeoDetailsViewHolder holder, int position) {
        CloseApproachData item = items.get(position);
        if(item != null) {
            holder.closeApproachDate.setText(formatter.format(item.getCloseApproachDateFull()));
            holder.velocity.setText(String.valueOf(item.getRelativeVelocity()));
            holder.missDistance.setText(String.valueOf(item.getMissDistance()));
            holder.orbitingBody.setText(item.getOrbitingBody());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class NeoDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView closeApproachDate;
        private TextView velocity;
        private TextView missDistance;
        private TextView orbitingBody;

        public NeoDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            closeApproachDate = itemView.findViewById(R.id.neo_details_close_approach_date);
            velocity = itemView.findViewById(R.id.neo_details_velocity);
            missDistance = itemView.findViewById(R.id.neo_details_miss_distance);
            orbitingBody = itemView.findViewById(R.id.neo_details_orbiting_body);
        }
    }
}
