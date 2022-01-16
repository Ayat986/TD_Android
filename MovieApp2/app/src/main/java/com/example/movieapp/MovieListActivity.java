package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AbsActionBarView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.adapters.MovieRecyclerView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.Servicey;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieListActivity extends AppCompatActivity implements OnMovieListener {





    //Langues
    String[] items = {"Anglais", "Français", "Allemand"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;



    //TextView
    private TextView type;


    //RecyclerView
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;

    //Button btn;

    //viewModel
    private MovieListViewModel movieListViewModel;


    //boolean isPopular = true;
    boolean isUpcoming = true;

    //String
    private String Search;
    private String pass;
    private String item;

    private String langue = "fr";


    //linearlayout
    private LinearLayout hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent0 = getIntent();
        if(intent0.getStringExtra("langue")!= null )
            langue = intent0.getStringExtra("langue");
        Log.v("Abiiiiiiiiiiiiiiiiiir", "intent0 " +langue);

        //Languestem
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                item = parent.getItemAtPosition(position).toString();
                Log.v("Abiiiiiiiiiiiiiiiiiir", "isUpcoming " +isUpcoming);
                Log.v("Abiiiiiiiiiiiiiiiiiir", "query " +Search);

                if(item == "Allemand"){
                    Log.v("Yahya", "allem" );
                    setLocal(MovieListActivity.this, "de");
                    pass = "de";
                    Intent intent = new Intent(MovieListActivity.this, MovieListActivity.class);
                    intent.putExtra("langue", pass);
                    startActivity(intent);




                }
                else if(item == "Anglais"){


                    Log.v("Yahya", "eng" );

                    pass = "en";
                    setLocal(MovieListActivity.this, "en");
                    Intent intent = new Intent(MovieListActivity.this, MovieListActivity.class);
                    intent.putExtra("langue", pass);
                    startActivity(intent);
                }
                else {
                    Log.v("Yahya", "fra" );

                    //movieListViewModel.searchMovieUpcom(1,"fr");
                    pass = "fr";
                    setLocal(MovieListActivity.this, "fr");
                    Intent intent = new Intent(MovieListActivity.this, MovieListActivity.class);
                    intent.putExtra("langue", pass);

                    startActivity(intent);
                }

            }
        });



        //Movie Type
        type = findViewById(R.id.type);
        if(langue.equals("en") ) {
            type.setText("Upcoming movies");
        }
        else if (langue.equals("de")) {
            type.setText("Kommende Filme");
        }
        else {
            type.setText("Films à venir");
        }

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button bouton = findViewById(R.id.button);
        if(langue.equals("en") ) {
            bouton.setText("Popular");
        }
        else if (langue.equals("de")) {
            bouton.setText( "Beliebt");
        }
        else {
            bouton.setText("Populaires");
        }


        Button bouton2 = findViewById(R.id.button2);
        if(langue.equals("en") ) {
            bouton2.setText("Favorite");
        }
        else if (langue.equals("de")) {
            bouton2.setText( "Favoriten");
        }
        else {
            bouton2.setText("Favoris");
        }

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide.setVisibility(View.VISIBLE);
                Intent intent1 = new Intent(MovieListActivity.this, PopMovieListActivity.class);
                intent1.putExtra("langue", langue);


                startActivity(intent1);

            }
        });

        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MovieListActivity.this,FavMovieListActivity.class));
            }
        });

        //SearchView
        SetupSearchView();



        recyclerView = findViewById(R.id.recyclerView);
        //btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);




        ConfigureRecyclerView();

        //Calling the observers
        ObserveAnyChange();
        Log.v("Tag1", "Observe " );
        //searchMovieApi("beauty", 1);
        //ObservePopularMovies();
        ObserveUpcomingMovies();


        //Getting upcoming movies
        movieListViewModel.searchMovieUpcom(1,langue);


    }


    public void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    private void ObserveUpcomingMovies() {
        movieListViewModel.getUpcom().observe(this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag33", "Observe " );

                //observing any data change
                if (movieModels != null){



                    for(MovieModel movieModel: movieModels){
                        //Get the data in log
                        Log.v("Tag55", "onChanged: " +movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag3", "Observe " );
                //observing any data change
                if (movieModels != null){
                    type.setText("Popular movies");
                    Log.v("Tag4", "Observe " );
                    for(MovieModel movieModel: movieModels){
                        //Get the data in log
                        Log.v("Tag", "onChanged: " +movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });

    }


    //Observing any data changes such as a listener
    private void ObserveAnyChange(){
        Log.v("Tag2", "Observe " );
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag3", "Observe " );
                //observing any data change
                if (movieModels != null){
                    Log.v("Tag4", "Observe " );
                    for(MovieModel movieModel: movieModels){
                        //Get the data in log
                        Log.v("Tag", "onChanged: " +movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }



    //5- Initializing recyclerView and adding data to it
    private  void ConfigureRecyclerView(){
        //Live Data cant  be passed via the constructor
        movieRecyclerAdapter = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //ReyclerView Pagination
        //Loading next page of api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    //Here we need to display the next search results on the next page of api

                    if(Search != null)
                        movieListViewModel.searchNextPage();
                    else
                        movieListViewModel.searchNextPage1();

                }
            }
        });



    }


    @Override
    public void onMovieClick(int position) {


        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    //Getting data from searchView and query the api to get the results (Movies)
    private void SetupSearchView() {

        hide = (LinearLayout) findViewById(R.id.hide);
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v("Abiiiiiiiiiiiiiiiiiir", "langue search" +langue);
                Search = query;
                hide.setVisibility(View.GONE);
                    movieListViewModel.searchMovieApi(
                            //The search string got from searchview
                            query,
                            1,
                            langue
                    );




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(langue.equals("en") ) {
                    type.setText("Searched movies");
                }
                else if (langue.equals("de")) {
                    type.setText("Gesuchte Filme");
                }
                else {
                    type.setText("Films recherchés");
                }
                //isPopular = false;
                isUpcoming = false;


            }
        });
    }



}




