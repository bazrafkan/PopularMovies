package com.example.popularmovies.databse.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "favorite")
public class Favorite implements Serializable {

    @PrimaryKey
    private int id;

    private String title;

    private String posterPath;

    private String overview;

    private String releaseDate;

    private float voteAverage;

    private String duration;

    private Date updateAt;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @Ignore
    public Favorite() {
    }

    public Favorite(int id,
                     String title,
                     String posterPath,
                     String overview,
                     String releaseDate,
                     float voteAverage,
                     String duration,
                     byte[] image,
                     Date updateAt) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.duration = duration;
        this.image = image;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getDuration() {
        return duration;
    }

    public byte[] getImage() {
        return image;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}