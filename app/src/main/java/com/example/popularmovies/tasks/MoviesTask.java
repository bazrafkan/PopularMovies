package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.models.DetailsMovie;
import com.example.popularmovies.models.Movie;

public class MoviesTask extends AsyncTask<Integer, Void, Movie> {
    public interface AsyncMoviesTaskResult {
        void onPreExecute();

        void onPostExecute(Movie result);
    }

    public AsyncMoviesTaskResult delegate;

    public MoviesTask(AsyncMoviesTaskResult delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.delegate.onPreExecute();
    }

    @Override
    protected Movie doInBackground(Integer... integers) {
        return DetailsMovie.getDetailsMovie(integers[0]);
    }


    @Override
    protected void onPostExecute(Movie movie) {
        this.delegate.onPostExecute(movie);
    }

}
