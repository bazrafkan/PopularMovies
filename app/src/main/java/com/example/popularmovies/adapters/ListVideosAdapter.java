package com.example.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.databse.entry.Video;

import java.util.List;

public class ListVideosAdapter extends RecyclerView.Adapter<ListVideosAdapter.ItemViewHolder> {
    private static final String TAG = ListVideosAdapter.class.getSimpleName();
    private List<Video> videos;
    final private ListItemClickListener listener;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailers_list_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(videos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (videos != null) {
            return videos.size();
        } else {
            return 0;

        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int id);
    }

    public ListVideosAdapter(List<Video> videos, ListItemClickListener listener) {
        this.videos = videos;
        this.listener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mItemText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemText = itemView.findViewById(R.id.tv_item_video_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }

        void bind(String name) {
            Log.d(TAG, "name: " + name);
            mItemText.setText(name);
        }
    }
}
