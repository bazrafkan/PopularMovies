package com.example.popularmovies.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Movie;
import com.example.popularmovies.databse.entry.Genre;
import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.databse.entry.Video;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {
    @Transaction
    @Query("SELECT * FROM movie ORDER BY updateAt")
    LiveData<List<FavoriteMovies>> loadListFavoriteMovies();

    @Transaction
    @Query("SELECT * FROM movie WHERE Movie.id = :id")
    FavoriteMovies getFavoriteMovies(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovies(Movie movie, List<Genre> genres, List<Review> reviews, List<Video> videos);

    @Query("DELETE FROM movie WHERE Movie.id = :id")
    void deleteFavoriteMovies(int id);
}