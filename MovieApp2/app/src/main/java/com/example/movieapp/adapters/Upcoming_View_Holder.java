package com.example.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;


    public class Upcoming_View_Holder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        //widgets
        TextView title, release_date, duration;
        ImageView imageView33;
        RatingBar ratingBar33;

        //Click Listener
        OnMovieListener onMovieListener;

        public Upcoming_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            this.onMovieListener = onMovieListener;

            imageView33 = itemView.findViewById(R.id.movie_img_upcoming);
            ratingBar33 = itemView.findViewById(R.id.rating_bar_upcom);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            //onMovieListener.onMovieClick(getAdapterPosition());

        }
}
