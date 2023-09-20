package com.savita.nasa_app.adapters;

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

import com.savita.nasa_app.R;
import com.savita.nasa_app.models.MainItem;

import java.util.List;

public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.MainItemViewHolder> {
    private List<MainItem> items;
    private Context context;
    public MainItemAdapter(Context context, List<MainItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MainItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item_view, parent, false);
        return new MainItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainItemViewHolder holder, int position) {
        MainItem item = items.get(position);
        if(item != null) {
            holder.title.setText(item.getTitle());
            holder.image.setImageResource(item.getImageResourceId());
            holder.container.setOnClickListener(v -> {
                Intent intent = new Intent(context, item.getLinkTo());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MainItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private ConstraintLayout container;

        public MainItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.main_item_image);
            title = itemView.findViewById(R.id.main_item_title);
            container = itemView.findViewById(R.id.main_item_container);
        }
    }
}
