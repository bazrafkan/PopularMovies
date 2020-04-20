package com.example.popularmovies.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.popularmovies.databse.FavoritesMovies;
import com.example.popularmovies.databse.entry.Favorites;
import com.example.popularmovies.databse.entry.Genre;

import java.util.List;

@Dao
public interface FavoritesMoviesDao {
    @Transaction
    @Query("SELECT * FROM favorites ORDER BY updateAt")
    LiveData<List<FavoritesMovies>> loadAllFavoritesMovies();

    @Transaction
    @Query("SELECT * FROM favorites WHERE favorites.id = :id")
    FavoritesMovies getFavoritesMovies(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoritesMovies(Favorites favorites, List<Genre> genres);

    @Query("DELETE FROM favorites WHERE favorites.id = :id")
    void deleteFavoritesMovies(int id);
}