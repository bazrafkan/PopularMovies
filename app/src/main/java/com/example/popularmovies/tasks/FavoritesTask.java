package com.example.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.entry.Favorites;

public class FavoritesTask extends AsyncTask<Favorites, Void, Favorites> {
    public final static String INSERT_ACTION = "insert_to_favorites";
    public final static String DELETE_ACTION = "delete_from_favorites";
    public final static String GET_ACTION = "get_from_favorites";
    private String action;
    private Context context;

    public interface AsyncListMoviesResult {
        void onPreExecute();

        void onPostExecute(Favorites result);
    }

    public AsyncListMoviesResult delegate;

    public FavoritesTask(AsyncListMoviesResult delegate, Context context, String action) {
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
    protected Favorites doInBackground(Favorites... favorites) {
        AppDatabase mAppDatabase = AppDatabase.getInstance(context);
        if (action == INSERT_ACTION) {
            mAppDatabase.favoritesDao().insertFavorites(favorites[0]);
            return mAppDatabase.favoritesDao().getFavorites(favorites[0].getId());
        }
        if (action == DELETE_ACTION) {
            mAppDatabase.favoritesDao().deleteFavorites(favorites[0]);
            return mAppDatabase.favoritesDao().getFavorites(favorites[0].getId());
        }
        if (action == GET_ACTION) {
            return mAppDatabase.favoritesDao().getFavorites(favorites[0].getId());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Favorites result) {
        delegate.onPostExecute(result);
    }
}
