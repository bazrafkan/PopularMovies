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
import com.example.popularmovies.models.Videos;

import java.util.List;

public class ListVideosAdapter extends RecyclerView.Adapter<ListVideosAdapter.ItemViewHolder> {
    private static final String TAG = ListVideosAdapter.class.getSimpleName();
    private List<Videos> listVideos;
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
        holder.bind(listVideos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (listVideos != null) {
            return listVideos.size();
        } else {
            return 0;

        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int id);
    }

    public ListVideosAdapter(List<Videos> listVideos, ListItemClickListener listener) {
        this.listVideos = listVideos;
        this.listener = listener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
