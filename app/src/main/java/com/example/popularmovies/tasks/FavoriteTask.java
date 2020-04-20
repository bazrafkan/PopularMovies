package com.example.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.entry.Favorite;

public class FavoriteTask extends AsyncTask<Favorite, Void, Favorite> {
    private Context context;

    public interface AsyncListMoviesResult {
        void onPreExecute();

        void onPostExecute(Favorite result);
    }

    public AsyncListMoviesResult delegate;

    public FavoriteTask(AsyncListMoviesResult delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.delegate.onPreExecute();
    }

    @Override
    protected Favorite doInBackground(Favorite... favorites) {
        AppDatabase mAppDatabase = AppDatabase.getInstance(context);
        return mAppDatabase.favoriteDao().getFavorite(favorites[0].getId());
    }

    @Override
    protected void onPostExecute(Favorite result) {
        delegate.onPostExecute(result);
    }
}
