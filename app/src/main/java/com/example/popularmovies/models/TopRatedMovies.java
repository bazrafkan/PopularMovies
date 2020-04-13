package com.example.popularmovies.models;

import android.net.Uri;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRatedMovies implements Serializable {
    private final static String TMDB_URL =
            "https://api.themoviedb.org/3/movie/top_rated";
    private final static String PARAM_API_KEY = "api_key";

    @SerializedName("results")
    List<ListMovies> listMovies = null;

    public static List<ListMovies> getTopRatedMovies() {
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put(PARAM_API_KEY, BuildConfig.tmdb_api_key);
            URL url = NetworkUtils.buildUrl(TMDB_URL, params);
            String result = NetworkUtils.getResponseFromHttpUrl(url);
            return parseJSON(result).listMovies;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TopRatedMovies parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        TopRatedMovies topRatedMoviesResponse = gson.fromJson(response, TopRatedMovies.class);
        return topRatedMoviesResponse;
    }

}
