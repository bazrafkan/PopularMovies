package com.example.popularmovies.databse.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "movie")
public class Movie implements Serializable {
    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private float voteAverage;

    @Ignore
    @SerializedName("genres")
    private List<Genre> genres = null;

    @SerializedName("runtime")
    private String duration;

    private Date updateAt;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @Ignore
    public Movie() {
    }

    public Movie(int id,
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

    @Ignore
    public Movie(int id,
                 String title,
                 String posterPath,
                 String overview,
                 String releaseDate,
                 float voteAverage,
                 String duration,
                 byte[] image,
                 List<Genre> genres,
                 Date updateAt) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.duration = duration;
        this.image = image;
        this.genres = genres;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return IMAGE_PATH + IMAGE_SIZE + posterPath;
    }

    public String getOriginalPosterPath() {
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

    public List<Genre> getGenres() {
        return genres;
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

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}