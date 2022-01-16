package com.example.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //this class is used for ViewModel

    private MovieRepository movieRepository;

    //Constructor
    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }
    public LiveData<List<MovieModel>> getUpcom(){
        return movieRepository.getUpcom();
    }

   //3- Calling method in view-Model
    public void searchMovieApi(String query, int pageNumber,String language) {
        movieRepository.searchMovieApi(query, pageNumber, language);
    }
    public void searchMoviePop(int pageNumber, String language) {

        movieRepository.searchMoviePop(pageNumber, language);
    }
    public void searchMovieUpcom(int pageNumber, String language) {
        movieRepository.searchMovieUpcom(pageNumber, language);
    }


    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
    public void searchNextPage1(){
        movieRepository.searchNextPage1();
    }
    public void searchNextPage2(){
        movieRepository.searchNextPage2();
    }

}
