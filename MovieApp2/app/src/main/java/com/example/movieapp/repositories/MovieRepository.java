package com.example.movieapp.repositories;

import androidx.lifecycle.LiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    //this class is acting as repositories


    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery, mLanguage;
    private int mPageNumber;


    public static MovieRepository getInstance() {
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private  MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }


    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviesPop();
    }
    public LiveData<List<MovieModel>> getUpcom(){
        return movieApiClient.getMoviesUpcom();
    }


    //2- Calling the method in repository
    public void searchMovieApi(String query, int pageNumber, String language){

        mLanguage = language;
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query, pageNumber, language);

    }
    public void searchMoviePop(int pageNumber, String language){

        mLanguage = language;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesPop(pageNumber, language);

    }
    public void searchMovieUpcom(int pageNumber, String language){


        mLanguage = language;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesUpcom(pageNumber, language);

    }
    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1, mLanguage);
    }
    public void searchNextPage1(){
        searchMovieUpcom( mPageNumber+1, mLanguage);
    }
    public void searchNextPage2(){
        searchMoviePop( mPageNumber+1, mLanguage);
    }


}
