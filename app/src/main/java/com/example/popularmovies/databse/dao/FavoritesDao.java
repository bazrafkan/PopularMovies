package com.example.popularmovies.databse.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.popularmovies.databse.entry.Favorites;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites ORDER BY updateAt")
    LiveData<List<Favorites>> loadAllFavorites();

    @Query("SELECT * FROM favorites WHERE id = :id")
    Favorites getFavorites(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorites(Favorites favorites);

    @Delete
    void deleteFavorites(Favorites favorites);
}
