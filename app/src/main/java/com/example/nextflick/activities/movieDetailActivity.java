package com.example.nextflick.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nextflick.R;
import com.example.nextflick.models.Movie;

import org.parceler.Parcels;

public class movieDetailActivity extends AppCompatActivity {
    private TextView movieDescriptionView;
    private TextView movieTitleView;
    private TextView movieRelease;
    private RatingBar movieRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        movieDescriptionView = findViewById(R.id.tvDescription);
        movieTitleView = findViewById(R.id.tvTitle);
        movieRelease = findViewById(R.id.tvReleaseDate);
        movieRating = findViewById(R.id.rbMovie);

        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        String mDescription = movie.getMovieOverview();
        String mTitle = movie.getMovieTitle();
        String mRelease = movie.getMovieRelease();
        Double mRating = movie.getMovieRating();
        movieDescriptionView.setText(mDescription);
        movieTitleView.setText(mTitle);
        movieRelease.setText(mRelease);
        movieRating.setRating(mRating.floatValue() / 2.0f);
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
