package com.example.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.FavoritesMovies;

public class FavoritesMoviesTask extends AsyncTask<FavoritesMovies, Void, FavoritesMovies> {
    public final static String INSERT_ACTION = "insert_to_favorites_genre";
    public final static String DELETE_ACTION = "delete_from_favorites_genre";
    public final static String GET_ACTION = "get_from_favorites_genre";
    private String action;
    private Context context;

    public interface AsyncFavoritesAndGenreTaskResult {
        void onPreExecute();

        void onPostExecute(FavoritesMovies result);
    }

    public AsyncFavoritesAndGenreTaskResult delegate;

    public FavoritesMoviesTask(AsyncFavoritesAndGenreTaskResult delegate, Context context, String action) {
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
    protected FavoritesMovies doInBackground(FavoritesMovies... favoritesMovies) {
        AppDatabase mAppDatabase = AppDatabase.getInstance(context);
        FavoritesMovies item = favoritesMovies[0];
        int id = item.favorites.getId();
        if (action == INSERT_ACTION) {
            mAppDatabase.favoritesMoviesDao().insertFavoritesMovies(item.favorites, item.genres);
            return mAppDatabase.favoritesMoviesDao().getFavoritesMovies(id);
        }
        if (action == DELETE_ACTION) {
            mAppDatabase.favoritesMoviesDao().deleteFavoritesMovies(id);
            return mAppDatabase.favoritesMoviesDao().getFavoritesMovies(id);
        }
        if (action == GET_ACTION) {
            return mAppDatabase.favoritesMoviesDao().getFavoritesMovies(id);
        }
        return null;
    }

    @Override
    protected void onPostExecute(FavoritesMovies result) {
        delegate.onPostExecute(result);
    }
}
