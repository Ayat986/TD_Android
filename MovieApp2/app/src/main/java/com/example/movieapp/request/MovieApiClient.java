package com.example.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.AppExecutors;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //11  LiveData for ---Search---
    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    //making global RUNNABLE request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;


    //22  LiveData for ---Popular movies---
    private MutableLiveData<List<MovieModel>> mMoviesPop;
    //making popular RUNNABLE request
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;



    //33  LiveData for ---Upcoming movies---
    private MutableLiveData<List<MovieModel>> mMoviesUpcom;
    //making upcoming RUNNABLE request
    private RetrieveMoviesRunnableUpcom retrieveMoviesRunnableUpcom;




    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }



    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
        mMoviesUpcom = new MutableLiveData<>();
    }


    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }
    public LiveData<List<MovieModel>> getMoviesPop() {
        return mMoviesPop;
    }
    public LiveData<List<MovieModel>> getMoviesUpcom() {
        return mMoviesUpcom;
    }


    //1- This method that we are going to call through the classes
    public void searchMovieApi(String query, int pageNumber, String language) {

        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber, language);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 20000, TimeUnit.DAYS);

    }
    public void searchMoviesPop(int pageNumber, String language) {

        if (retrieveMoviesRunnablePop != null) {
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber, language);
        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler2.cancel(true);
            }
        }, 20000, TimeUnit.DAYS);

    }
    public void searchMoviesUpcom(int pageNumber, String language) {

        if (retrieveMoviesRunnableUpcom != null) {
            retrieveMoviesRunnableUpcom = null;
        }



        retrieveMoviesRunnableUpcom = new RetrieveMoviesRunnableUpcom(pageNumber, language);
        final Future myHandler3 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableUpcom);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler3.cancel(true);
            }
        }, 20000, TimeUnit.DAYS);

    }



    //retrieving recuperer data from restApi by runnable class
    //we have 2 types : ID & search queries
    private class RetrieveMoviesRunnable implements Runnable {


        private final String query;
        private final int pageNumber;
        boolean cancelRequest;
        private final String language;

        public RetrieveMoviesRunnable(String query, int pageNumber, String language) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.language = language;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //Getting Request objects
            try {
                Response response = getMovies(query, pageNumber, language).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to live data
                        //postvalue :  used for background thread
                        //setValue : not for background thread

                        mMovies.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        if (currentMovies != null) {
                            currentMovies.addAll(list);
                        }
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    Log.v("Tag : ", "Error : " + response.errorBody().string());
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }


        }

        //Search Method/Query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber, String language) {
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber,
                    this.language
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }


    }
    private class RetrieveMoviesRunnablePop implements Runnable {

        private final String language;
        private final int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber, String language) {
            this.pageNumber = pageNumber;
            this.language = language;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //Getting Request objects
            try {
                Response response2 = getPop(pageNumber, language).execute();
                if (cancelRequest) {
                    return;
                }
                if (response2.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to live data
                        //postvalue :  used for background thread
                        //setValue : not for background thread

                        mMoviesPop.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        if (currentMovies != null) {
                            currentMovies.addAll(list);
                        }
                        mMoviesPop.postValue(currentMovies);
                    }
                } else {
                    Log.v("Tag : ", "Error : " + response2.errorBody().string());
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }


        }

        //Search Method/Query
        private Call<MovieSearchResponse> getPop(int pageNumber, String language) {
            return Service.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber,
                    language
            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }



    }
    private class RetrieveMoviesRunnableUpcom implements Runnable {


        private final int pageNumber;
        boolean cancelRequest;
        private final String language;

        public RetrieveMoviesRunnableUpcom(int pageNumber, String language) {
            this.pageNumber = pageNumber;
            this.language = language;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //Getting Request objects
            try {
                Response response3 = getUpcom(pageNumber, language).execute();
                if (cancelRequest) {
                    return;
                }
                if (response3.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response3.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to live data
                        //postvalue :  used for background thread
                        //setValue : not for background thread

                        mMoviesUpcom.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mMoviesUpcom.getValue();
                        if (currentMovies != null) {
                            currentMovies.addAll(list);
                        }
                        mMoviesUpcom.postValue(currentMovies);
                    }
                } else {
                    Log.v("Tag : ", "Error : " + response3.errorBody().string());
                    mMoviesUpcom.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }


        }

        //Search Method/Language
        private Call<MovieSearchResponse> getUpcom(int pageNumber, String language) {
            return Service.getMovieApi().getUpcoming(
                    Credentials.API_KEY,
                    pageNumber,
                    language

            );
        }

        private void cancelRequest() {
            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }



    }
}




