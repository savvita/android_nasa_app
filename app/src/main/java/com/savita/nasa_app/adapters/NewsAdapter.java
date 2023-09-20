package com.savita.nasa_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savita.nasa_app.R;
import com.savita.nasa_app.models.News;
import com.savita.nasa_app.net.Loader;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private static final String TAG = "NewsAdapter";
    private Context context;
    private List<News> items;
    private LayoutInflater inflater;
    public NewsAdapter(Context context, List<News> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item_view, parent, false);
        return new NewsAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        News item = items.get(position);
        if(item != null) {
            if(item.getImageUrl() != null) {
                new Thread(() -> Loader.loadImageInto(item.getImageUrl(), holder.image)).start();
            }

            holder.title.setText(item.getHeader());
            holder.text.setText(item.getText());
            holder.category.setText(item.getCategory());
            holder.time.setText(item.getTime());
            if(item.getLink() != null) {
                holder.link.setVisibility(View.VISIBLE);
                holder.link.setOnClickListener(v -> openLink(item.getLink()));
            } else {
                holder.link.setVisibility(View.GONE);
            }

            Log.d(TAG, "onBindViewHolder: " + item.toString());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView text;
        private TextView category;
        private TextView time;
        private TextView link;
        private ImageView image;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            text = itemView.findViewById(R.id.news_text);
            category = itemView.findViewById(R.id.news_category);
            time = itemView.findViewById(R.id.news_time);
            link = itemView.findViewById(R.id.news_link);
            image = itemView.findViewById(R.id.news_image);
        }
    }
}
