package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.databse.entry.Movie;
import com.example.popularmovies.models.DetailsMovie;

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
        int id = integers[0];
        Movie movie = DetailsMovie.getDetailsMovie(id);
        if (movie.getGenres() != null && movie.getGenres().size() > 0) {
            for (int i = 0; i < movie.getGenres().size(); i++) {
                movie.getGenres().get(i).setFavoriteId(id);
            }
        }
        return movie;
    }


    @Override
    protected void onPostExecute(Movie movie) {
        this.delegate.onPostExecute(movie);
    }

}
