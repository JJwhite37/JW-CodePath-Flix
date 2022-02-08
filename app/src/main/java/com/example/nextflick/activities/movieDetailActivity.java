package com.example.nextflick.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.nextflick.R;
import com.example.nextflick.models.Movie;
import com.example.nextflick.networking.movieDBClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class movieDetailActivity extends AppCompatActivity {
    private TextView movieDescriptionView;
    private TextView movieTitleView;
    private TextView movieRelease;
    private TextView movieAdult;
    private TextView movieGenre;
    private RatingBar movieRating;
    private ImageView movieTrailerImage;
    public String videoRequest;
    private movieDBClient client;
    public String genreRequest;
    private movieDBClient genreClient;
    String movieTrailerKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        movieDescriptionView = findViewById(R.id.tvDescription);
        movieTitleView = findViewById(R.id.tvTitle);
        movieRelease = findViewById(R.id.tvReleaseDate);
        movieRating = findViewById(R.id.rbMovie);
        movieTrailerImage = findViewById(R.id.ivMovieTrailer);
        movieAdult = findViewById(R.id.tvAdult);
        movieGenre = findViewById(R.id.tvGenre);

        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        String mDescription = movie.getMovieOverview();
        String mTitle = movie.getMovieTitle();
        String mRelease = movie.getMovieRelease();
        Double mRating = movie.getMovieRating();
        movieDescriptionView.setText(mDescription);
        movieTitleView.setText(mTitle);
        movieRelease.setText(mRelease);
        movieRating.setRating(mRating.floatValue() / 2.0f);

        if(movie.getMovieAdult() == true) {
            movieAdult.setText("Movie Contains Adult only Content");
            movieAdult.setTextColor(Color.parseColor("#ff0000"));
        } else {
            movieAdult.setText("Movie Does not contain Adult only Content");
            movieAdult.setTextColor(Color.parseColor("#00ff00"));
        }

        genreRequest = "https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        genreClient = new movieDBClient();
        genreClient.getMovieInfo(genreRequest, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("movieDetailActivity", "Request was successful");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray genreResults = jsonObject.getJSONArray("genres");
                    int genreNum = movie.getGenreID();
                    for(int i = 0; i < genreResults.length(); i++){
                        JSONObject arrayID = genreResults.getJSONObject(i);
                        if( genreNum == arrayID.getInt("id") ) {
                            movieGenre.setText(" - " + arrayID.getString("name"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("movieDetailActivity", "exception trying to get genre");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("movieDetailActivity", "Request failed");
            }
        });

        Glide.with(movieDetailActivity.this)
                .load(movie.getMovieBackdropPath())
                .placeholder(R.drawable.flixir)
                .transform(new RoundedCornersTransformation(40, 0))
                .into(movieTrailerImage);

        movieTrailerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int movieID = movie.getMovieID();
                videoRequest = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed" , movieID);
                client = new movieDBClient();
                client.getMovieInfo(videoRequest, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d("movieDetailActivity", "Request was successful");
                        JSONObject jsonObject = json.jsonObject;

                        try {
                            JSONArray movieResults = jsonObject.getJSONArray("results");
                            if (movieResults.length() != 0) {
                                movieTrailerKey = movieResults.getJSONObject(0).getString("key");
                                movie.setMovieTrailerKey(movieTrailerKey);
                                Intent i = new Intent(movieDetailActivity.this, movieTrailerActivity.class);
                                i.putExtra("movie", Parcels.wrap(movie));

                                Pair<View, String> p1 = Pair.create(movieTrailerImage, "trailer");

                                ActivityOptionsCompat options = ActivityOptionsCompat.
                                        makeSceneTransitionAnimation(movieDetailActivity.this, p1);
                                startActivity(i, options.toBundle());
                            } else {
                                Log.d("movieDetailActivity", "no video key found");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("movieDetailActivity", "exception trying to get video key");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.d("movieDetailActivity", "Request failed");
                    }
                });


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
