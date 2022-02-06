package com.example.nextflick.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.nextflick.R;
import com.example.nextflick.networking.movieDBClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import com.example.nextflick.adapters.MovieAdapter;
import com.example.nextflick.models.Movie;

public class MainActivity extends AppCompatActivity {
    public static final String nowPlayingRequest="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;
    private movieDBClient client;
    private View movieDescriptionView;
    private View movieTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();


        RecyclerView moviesRView = findViewById(R.id.moviesRView);

        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                Intent i = new Intent(MainActivity.this, movieDetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movies.get(position)));
                movieDescriptionView = itemView.findViewById(R.id.movieDescriptionView);
                movieTitleView = itemView.findViewById(R.id.movieTitleView);
                Pair<View, String> p1 = Pair.create(movieTitleView, "title");
                Pair<View, String> p2 = Pair.create(movieDescriptionView, "description");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, p1, p2);
                startActivity(i, options.toBundle());
            }
        });

        moviesRView.setAdapter(movieAdapter);

        moviesRView.setLayoutManager(new LinearLayoutManager(this));

        client = new movieDBClient();
        client.getMovieInfo(nowPlayingRequest, new JsonHttpResponseHandler() {
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