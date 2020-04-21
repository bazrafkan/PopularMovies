package com.example.popularmovies.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ImageViewHolder> {
    private static final String TAG = ListMoviesAdapter.class.getSimpleName();
    private List<FavoriteMovies> listMovies;
    final private ListItemClickListener listener;

    public interface ListItemClickListener {
        void onListItemClick(int id);
    }

    public ListMoviesAdapter(List<FavoriteMovies> listMovies, ListItemClickListener listener) {
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
        holder.bind(listMovies.get(position).movie);
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

        void bind(Movie movie) {
            if (movie.getImage() != null && movie.getImage().length > 0) {
                Bitmap bmp = BitmapFactory.decodeByteArray(movie.getImage(),
                        0,
                        movie.getImage().length);
                mItemImage.setImageBitmap(bmp);
            } else {
                Log.d(TAG, "path: "
                        + movie.getPosterPathCompleted());
                Picasso.with(context)
                        .load(movie.getPosterPathCompleted())
                        .into(mItemImage);
            }

        }

    }
}