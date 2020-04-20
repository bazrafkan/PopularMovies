package com.example.popularmovies.databse.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.popularmovies.databse.entry.Favorite;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite WHERE id = :id")
    Favorite getFavorite(int id);
}
