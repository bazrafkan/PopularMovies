package com.example.popularmovies.databse;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.popularmovies.databse.dao.FavoritesAndGenreDao;
import com.example.popularmovies.databse.dao.FavoritesDao;
import com.example.popularmovies.databse.dao.GenreDao;
import com.example.popularmovies.databse.entry.Favorites;
import com.example.popularmovies.databse.entry.Genre;

@Database(version = 1, entities = {Favorites.class, Genre.class}, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "pop_movies";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Create new database instance");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Database instance");
        return sInstance;
    }

    abstract public FavoritesDao favoritesDao();

    abstract public GenreDao genreDao();

    abstract public FavoritesAndGenreDao favoritesAndGenreDao();
}
