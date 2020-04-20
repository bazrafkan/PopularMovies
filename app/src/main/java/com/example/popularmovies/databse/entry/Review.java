package com.example.popularmovies.databse.entry;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.popularmovies.models.Reviews;

import java.io.Serializable;

@Entity(tableName = "review")
public class Review implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int favoriteId;

    private String author;

    private String content;

    @Ignore
    public Review() {
    }

    @Ignore
    public Review(Reviews reviews, int favoriteId) {
        this.favoriteId = favoriteId;
        this.author = reviews.getAuthor();
        this.content = reviews.getContent();
    }

    @Ignore
    public Review(Reviews reviews) {
        this.author = reviews.getAuthor();
        this.content = reviews.getContent();
    }

    public Review(int id, int favoriteId, String author, String content) {
        this.id = id;
        this.favoriteId = favoriteId;
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
