package com.example.movieapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
//make call methods in the background not the main
public class AppExecutors {
    ///Pattern
    private static AppExecutors instance;

    public  static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }
//v13 mn 3 and 2
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}
