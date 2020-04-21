package com.example.popularmovies.databse.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.popularmovies.databse.entry.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovie(int id);
}
