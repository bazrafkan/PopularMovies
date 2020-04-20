package com.example.popularmovies.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Favorite;
import com.example.popularmovies.databse.entry.Genre;
import com.example.popularmovies.databse.entry.Review;

import java.util.List;

@Dao
public interface FavoriteMoviesDao {
    @Transaction
    @Query("SELECT * FROM favorite ORDER BY updateAt")
    LiveData<List<FavoriteMovies>> loadListFavoriteMovies();

    @Transaction
    @Query("SELECT * FROM favorite WHERE favorite.id = :id")
    FavoriteMovies getFavoriteMovies(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovies(Favorite favorite, List<Genre> genres, List<Review> reviews);

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    void deleteFavoriteMovies(int id);
}