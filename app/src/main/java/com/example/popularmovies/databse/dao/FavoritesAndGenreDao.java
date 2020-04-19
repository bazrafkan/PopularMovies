package com.example.popularmovies.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.popularmovies.databse.entry.Favorites;
import com.example.popularmovies.databse.entry.FavoritesAndGenre;
import com.example.popularmovies.databse.entry.Genre;

import java.util.List;

@Dao
public interface FavoritesAndGenreDao {

    @Query("SELECT * FROM favorites ORDER BY updateAt")
    LiveData<List<FavoritesAndGenre>> loadAllFavoritesAndGenre();

    @Query("SELECT * FROM favorites WHERE favorites.id = :id")
    FavoritesAndGenre getFavoritesAndGenre(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoritesWithGenre(Favorites favorites, List<Genre> genres);

    @Query("DELETE FROM favorites WHERE favorites.id = :id")
    void deleteFavoritesWithGenre(int id);
}
