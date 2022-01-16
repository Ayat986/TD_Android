package com.example.movieapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adapters.MovieRecyclerView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavMovieListActivity extends AppCompatActivity implements OnMovieListener {

    private List<MovieModel> movies = new ArrayList<>();
    private MyApplication globalClass;

    //RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    //viewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_movies_layout);

        recyclerView = findViewById(R.id.recyclerView);

      globalClass = (MyApplication) getApplicationContext();
        movies.addAll(globalClass.getMovies());


        ConfigureRecyclerView();


        for(MovieModel movieModel: movies) {
            movieRecyclerAdapter.setmMovies(movies);
        }
    }




    private void ConfigureRecyclerView() {

        movieRecyclerAdapter = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));




    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}