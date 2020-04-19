package com.example.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.entry.FavoritesAndGenre;

public class FavoritesAndGenreTask extends AsyncTask<FavoritesAndGenre, Void, FavoritesAndGenre> {
    public final static String INSERT_ACTION = "insert_to_favorites_genre";
    public final static String DELETE_ACTION = "delete_from_favorites_genre";
    public final static String GET_ACTION = "get_from_favorites_genre";
    private String action;
    private Context context;

    public interface AsyncFavoritesAndGenreTaskResult {
        void onPreExecute();

        void onPostExecute(FavoritesAndGenre result);
    }

    public AsyncFavoritesAndGenreTaskResult delegate;

    public FavoritesAndGenreTask(AsyncFavoritesAndGenreTaskResult delegate, Context context, String action) {
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
    protected FavoritesAndGenre doInBackground(FavoritesAndGenre... favoritesAndGenres) {
        AppDatabase mAppDatabase = AppDatabase.getInstance(context);
        FavoritesAndGenre item = favoritesAndGenres[0];
        int id = favoritesAndGenres[0].favorites.getId();
        if (action == INSERT_ACTION) {
            mAppDatabase.favoritesAndGenreDao().insertFavoritesWithGenre(item.favorites, item.genres);
            return mAppDatabase.favoritesAndGenreDao().getFavoritesAndGenre(id);
        }
        if (action == DELETE_ACTION) {
            mAppDatabase.favoritesAndGenreDao().deleteFavoritesWithGenre(id);
            return mAppDatabase.favoritesAndGenreDao().getFavoritesAndGenre(id);
        }
        if (action == GET_ACTION) {
            return mAppDatabase.favoritesAndGenreDao().getFavoritesAndGenre(id);
        }
        return null;
    }

    @Override
    protected void onPostExecute(FavoritesAndGenre result) {
        delegate.onPostExecute(result);
    }
}
