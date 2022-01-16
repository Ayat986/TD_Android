package com.example.movieapp.utils;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //search for movies
    //https://api.themoviedb.org/3/search/movie?api_key=d6d4c8009eb1d6b504301ba296a0d3a5&query=Jack+Reacher
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page,
            @Query("language") String language




    );




    //Get popular movie
    //https://api.themoviedb.org/3/movie/popular?api_key=d6d4c8009eb1d6b504301ba296a0d3a5
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page,
            @Query("language") String language


    );


    //Get upcoming movie
    //https://api.themoviedb.org/3/movie/upcoming?api_key=d6d4c8009eb1d6b504301ba296a0d3a5
    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getUpcoming(
            @Query("api_key") String key,
            @Query("page") int page,
            @Query("language") String language

    );




    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key




    );






}
