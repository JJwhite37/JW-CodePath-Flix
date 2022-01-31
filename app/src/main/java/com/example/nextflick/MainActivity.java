package com.example.nextflick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import com.example.nextflick.adapters.MovieAdapter;
import com.example.nextflick.models.Movie;

public class MainActivity extends AppCompatActivity {
    public static final String nowPlayingRequest="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();

        RecyclerView moviesRView = findViewById(R.id.moviesRView);

        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        moviesRView.setAdapter(movieAdapter);

        moviesRView.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(nowPlayingRequest, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MainActivity", "Request was successful");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray movieResults = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.resultsJsonArray(movieResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("MainActivity", "Json exception on results");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MainActivity", "Request failed");
            }
        });

    }
}