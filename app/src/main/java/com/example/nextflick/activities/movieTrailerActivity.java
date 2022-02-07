package com.example.nextflick.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.nextflick.R;

import com.example.nextflick.models.Movie;
import com.example.nextflick.networking.movieDBClient;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class movieTrailerActivity extends YouTubeBaseActivity {
    public String trailerKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_trailer);
        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        final String youtubeAPI = getString(R.string.youtube_secret);

        trailerKey = movie.getMovieTrailerKey();

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.ytPlayer);

        youTubePlayerView.initialize(youtubeAPI,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.cueVideo(trailerKey);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.e("MovieTrailerActivity", "Youtube Player could not load");

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
