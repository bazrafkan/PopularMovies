package com.example.popularmovies.databse.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.popularmovies.databse.entry.Genre;

import java.util.List;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM genre WHERE favoriteId = :id")
    List<Genre> getListGenre(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListGenre(List<Genre> genres);

    @Query("DELETE FROM genre WHERE favoriteId = :id")
    void deleteListGenre(int id);
}
