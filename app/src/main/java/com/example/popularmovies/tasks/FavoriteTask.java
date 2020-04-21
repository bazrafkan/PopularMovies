package com.example.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.entry.Movie;

public class FavoriteTask extends AsyncTask<Movie, Void, Movie> {
    private Context context;

    public interface AsyncListMoviesResult {
        void onPreExecute();

        void onPostExecute(Movie result);
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
    protected Movie doInBackground(Movie... movies) {
        AppDatabase mAppDatabase = AppDatabase.getInstance(context);
        return mAppDatabase.favoriteDao().getMovie(movies[0].getId());
    }

    @Override
    protected void onPostExecute(Movie result) {
        delegate.onPostExecute(result);
    }
}
