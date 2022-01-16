package com.example.movieapp;

import android.app.Application;

import com.example.movieapp.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private List<MovieModel> favMovies = new ArrayList<>();

    public List<MovieModel> getMovies() {
        return favMovies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.favMovies = movies;
    }
}
