package com.example.nextflick.networking;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.nextflick.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;


public class movieDBClient {
    private AsyncHttpClient client;
    public static final String videoRequest="https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public movieDBClient() {
        this.client = new AsyncHttpClient();
    }
    public void getMovieInfo(final String query, JsonHttpResponseHandler handler) {
        client.get(query, handler);
    }
}
