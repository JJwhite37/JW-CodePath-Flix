package com.example.nextflick.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String moviePosterPath;
    String movieBackdropPath;
    String movieTitle;
    String movieOverview;

    public Movie(JSONObject movieJsonObject) throws JSONException {
        moviePosterPath = movieJsonObject.getString("poster_path");
        movieBackdropPath = movieJsonObject.getString("backdrop_path");
        movieTitle = movieJsonObject.getString("title");
        movieOverview = movieJsonObject.getString("overview");
    }

    public static List<Movie> resultsJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getMoviePosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500%s" , moviePosterPath);
    }

    public String getMovieBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w500%s" , movieBackdropPath);
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }
}