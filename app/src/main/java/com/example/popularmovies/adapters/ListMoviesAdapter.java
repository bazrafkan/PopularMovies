package com.example.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.ListMovies;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ImageViewHolder> {
    private static final String TAG = ListMoviesAdapter.class.getSimpleName();
    private List<ListMovies> listMovies;
    final private ListItemClickListener listener;

    public interface ListItemClickListener {
        void onListItemClick(int id);
    }

    public ListMoviesAdapter(List<ListMovies> listMovies, ListItemClickListener listener) {
        this.listMovies = listMovies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.image_list_item, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        imageViewHolder.context = context;
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        holder.bind(listMovies.get(position).getPosterPath());
    }

    @Override
    public int getItemCount() {
        if (listMovies != null) {
            return listMovies.size();
        } else {
            return 0;

        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mItemImage;
        Context context;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.iv_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }

        void bind(String path) {
            Log.d(TAG, "path: "
                    + path);
            Picasso.with(context)
                    .load(path)
                    .into(mItemImage);
        }

    }
}