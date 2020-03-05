package com.example.popularmovies.models;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsVideos {
    private final static String TMDB_URL =
            "https://api.themoviedb.org/3/movie/";
    private final static String TMDB_URL_VIDEO = "/videos";
    private final static String PARAM_API_KEY = "api_key";

    @SerializedName("results")
    List<Videos> listVideos = null;

    public static List<Videos> getDetailsVideos(int id) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put(PARAM_API_KEY, BuildConfig.tmdb_api_key);
            URL url = NetworkUtils.buildUrl(TMDB_URL + id + TMDB_URL_VIDEO, params);
            String result = NetworkUtils.getResponseFromHttpUrl(url);
            return parseJSON(result).listVideos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DetailsVideos parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        DetailsVideos detailsVideosResponse = gson.fromJson(response, DetailsVideos.class);
        return detailsVideosResponse;
    }
}
