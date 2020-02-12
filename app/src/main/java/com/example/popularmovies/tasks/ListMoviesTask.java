package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.models.ListMovies;
import com.example.popularmovies.models.PopularMovies;
import com.example.popularmovies.models.SortedMovies;
import com.example.popularmovies.models.TopRatedMovies;

import java.util.List;

public class ListMoviesTask extends AsyncTask<SortedMovies, Void, List<ListMovies>> {
    public interface AsyncListMoviesResult {
        void onPreExecute();

        void onPostExecute(List<ListMovies> result);
    }

    public AsyncListMoviesResult delegate;

    public ListMoviesTask(AsyncListMoviesResult delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.delegate.onPreExecute();
    }

    @Override
    protected List<ListMovies> doInBackground(SortedMovies... sortedMovies) {
        switch (sortedMovies[0]) {
            case TopRated:
                return TopRatedMovies.getTopRatedMovies();
            case Popular:
                return PopularMovies.getPopularMovies();
            default:
                return null;
        }
    }

    @Override
    protected void onPostExecute(List<ListMovies> listMovies) {
        delegate.onPostExecute(listMovies);
    }

}

