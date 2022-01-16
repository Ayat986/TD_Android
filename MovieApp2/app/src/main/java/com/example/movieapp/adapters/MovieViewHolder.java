package com.example.movieapp.adapters;
import android.view.View;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;


public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //widgets
    TextView title, release_date, duration;
    ImageView imageView;
    RatingBar ratingBar;
    ImageView addFav;

    //Click Listener
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        title = itemView.findViewById(R.id.movie_title);
        release_date = itemView.findViewById(R.id.movie_category);
        duration = itemView.findViewById(R.id.movie_duration);

        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);

        addFav = itemView.findViewById(R.id.addFav);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
