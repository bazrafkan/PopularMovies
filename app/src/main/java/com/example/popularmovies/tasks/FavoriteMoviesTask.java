package com.example.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Genre;
import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.databse.entry.Video;

import java.util.ArrayList;

public class FavoriteMoviesTask extends AsyncTask<FavoriteMovies, Void, FavoriteMovies> {
    public final static String INSERT_ACTION = "insert_to_favorites_genre";
    public final static String DELETE_ACTION = "delete_from_favorites_genre";
    public final static String GET_ACTION = "get_from_favorites_genre";
    private String action;
    private Context context;

    public interface AsyncFavoritesAndGenreTaskResult {
        void onPreExecute();

        void onPostExecute(FavoriteMovies result);
    }

    public AsyncFavoritesAndGenreTaskResult delegate;

    public FavoriteMoviesTask(AsyncFavoritesAndGenreTaskResult delegate, Context context, String action) {
        this.delegate = delegate;
        this.context = context;
        this.action = action;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.delegate.onPreExecute();
    }

    @Override
    protected FavoriteMovies doInBackground(FavoriteMovies... favoriteMovies) {
        AppDatabase mAppDatabase = AppDatabase.getInstance(context);
        FavoriteMovies item = favoriteMovies[0];
        int id = item.movie.getId();
        if (action == INSERT_ACTION) {
            mAppDatabase.favoriteMoviesDao().insertFavoriteMovies(
                    item.movie,
                    item.genres != null ? item.genres : new ArrayList<Genre>(),
                    item.reviews != null ? item.reviews : new ArrayList<Review>(),
                    item.videos != null ? item.videos : new ArrayList<Video>());
            return mAppDatabase.favoriteMoviesDao().getFavoriteMovies(id);
        }
        if (action == DELETE_ACTION) {
            mAppDatabase.favoriteMoviesDao().deleteFavoriteMovies(id);
            return mAppDatabase.favoriteMoviesDao().getFavoriteMovies(id);
        }
        if (action == GET_ACTION) {
            return mAppDatabase.favoriteMoviesDao().getFavoriteMovies(id);
        }
        return null;
    }

    @Override
    protected void onPostExecute(FavoriteMovies result) {
        delegate.onPostExecute(result);
    }
}
