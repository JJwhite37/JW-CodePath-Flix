package com.example.nextflick.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.Target;
import com.example.nextflick.R;
import com.example.nextflick.models.Movie;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieTitleView;
        TextView movieDescriptionView;
        ImageView moviePosterView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieDescriptionView = itemView.findViewById(R.id.movieDescriptionView);
            movieTitleView = itemView.findViewById(R.id.movieTitleView);
            moviePosterView = itemView.findViewById(R.id.moviePosterView);

        }

        public void bind(Movie movie) {
            movieTitleView.setText(movie.getMovieTitle());
            movieDescriptionView.setText(movie.getMovieOverview());
            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                imageUrl = movie.getMoviePosterPath();
            } else {
                imageUrl = movie.getMovieBackdropPath();
            }
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.flixir)
                    .into(moviePosterView);
        }
    }
}
