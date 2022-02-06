package com.example.nextflick.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Movie {
    String moviePosterPath;
    String movieBackdropPath;
    String movieTitle;
    String movieOverview;
    String movieRelease;
    Double movieRating;
    Boolean movieAdult;


    public Movie() {
    }

    public Movie(JSONObject movieJsonObject) throws JSONException {
        moviePosterPath = movieJsonObject.getString("poster_path");
        movieBackdropPath = movieJsonObject.getString("backdrop_path");
        movieTitle = movieJsonObject.getString("title");
        movieOverview = movieJsonObject.getString("overview");
        movieRelease = movieJsonObject.getString("release_date");
        movieRating = movieJsonObject.getDouble("vote_average");
        movieAdult = movieJsonObject.getBoolean("adult");
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

    public String getMovieRelease() { return movieRelease; }

    public Double getMovieRating() { return movieRating; }
}