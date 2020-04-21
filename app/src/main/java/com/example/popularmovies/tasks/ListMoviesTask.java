package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Movie;
import com.example.popularmovies.models.ListMovies;
import com.example.popularmovies.models.PopularMovies;
import com.example.popularmovies.models.SortedMovies;
import com.example.popularmovies.models.TopRatedMovies;

import java.util.ArrayList;
import java.util.List;

public class ListMoviesTask extends AsyncTask<SortedMovies, Void, List<FavoriteMovies>> {
    public interface AsyncListMoviesResult {
        void onPreExecute();

        void onPostExecute(List<FavoriteMovies> result);
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
    protected List<FavoriteMovies> doInBackground(SortedMovies... sortedMovies) {
        List<FavoriteMovies> result = null;
        if (sortedMovies[0] == SortedMovies.TopRated) {
            List<ListMovies> listMovies = TopRatedMovies.getTopRatedMovies();
            if (listMovies != null) {
                result = new ArrayList<>();
                for (ListMovies item : listMovies) {
                    FavoriteMovies favoriteMovies = new FavoriteMovies();
                    favoriteMovies.movie = new Movie();
                    favoriteMovies.movie.setId(item.getId());
                    favoriteMovies.movie.setPosterPath(item.getOriginalPosterPath());
                    result.add(favoriteMovies);
                }
            }
        }
        if (sortedMovies[0] == SortedMovies.Popular) {
            List<ListMovies> listMovies = PopularMovies.getPopularMovies();
            if (listMovies != null) {
                result = new ArrayList<>();
                for (ListMovies item : listMovies) {
                    FavoriteMovies favoriteMovies = new FavoriteMovies();
                    favoriteMovies.movie = new Movie();
                    favoriteMovies.movie.setId(item.getId());
                    favoriteMovies.movie.setPosterPath(item.getOriginalPosterPath());
                    result.add(favoriteMovies);
                }
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<FavoriteMovies> result) {
        delegate.onPostExecute(result);
    }

}

