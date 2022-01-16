package com.example.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adapters.MovieRecyclerView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.List;
import java.util.Locale;

public class PopMovieListActivity extends AppCompatActivity implements OnMovieListener {



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


    boolean isPopular = true;
    //boolean isUpcoming = true;


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

        //Langues
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                item = parent.getItemAtPosition(position).toString();
                Log.v("Abiiiiiiiiiiiiiiiiiir", "ispop " +isPopular);
                Log.v("Abiiiiiiiiiiiiiiiiiir", "query " +Search);

                if(item == "Allemand"){
                    setLocal(PopMovieListActivity.this, "de");
                    pass = "de";
                    Intent intent = new Intent(PopMovieListActivity.this, MovieListActivity.class);
                    intent.putExtra("langue", pass);
                    startActivity(intent);




                }
                else if(item == "Français"){
                    //movieListViewModel.searchMovieUpcom(1,"fr");
                    pass = "fr";
                    setLocal(PopMovieListActivity.this, "fr");
                    Intent intent = new Intent(PopMovieListActivity.this, MovieListActivity.class);
                    intent.putExtra("langue", pass);

                    startActivity(intent);
                }
                else {

                    pass = "en";
                    setLocal(PopMovieListActivity.this, "en");
                    Intent intent = new Intent(PopMovieListActivity.this, MovieListActivity.class);
                    intent.putExtra("langue", pass);


                    startActivity(intent);
                }

            }
        });


        //Movie Type
        type = findViewById(R.id.type);
        if(langue.equals("en") ) {
            type.setText("Popular movies");
        }
        else if (langue.equals("de")) {
            type.setText("Beliebte Filme");
        }
        else {
            type.setText("Films populaires");
        }
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        SetupSearchView();

        Button bouton = findViewById(R.id.button);
        if(langue.equals("en") ) {
            bouton.setText("Upcoming");
        }
        else if (langue.equals("de")) {
            bouton.setText( "Bevorstehende");
        }
        else {
            bouton.setText("A venir");
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


                Intent intent1 = new Intent(PopMovieListActivity.this, MovieListActivity.class);
                intent1.putExtra("langue", langue);


                startActivity(intent1);
            }
        });

        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PopMovieListActivity.this,FavMovieListActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);



        ConfigureRecyclerView();

        //Calling the observers
        ObserveAnyChange();
        Log.v("Tag1", "Observe ");
        //searchMovieApi("beauty", 1);
        ObservePopularMovies();
        //ObserveUpcomingMovies();

        //Getting popular movies
        movieListViewModel.searchMoviePop(1, langue);

    }

    public void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void ObserveUpcomingMovies() {
        movieListViewModel.getUpcom().observe(this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag33", "Observe ");
                //observing any data change
                if (movieModels != null) {

                    Log.v("Tag44", "Observe ");
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in log
                        Log.v("Tag55", "onChanged: " + movieModel.getTitle());
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
                Log.v("Tag3", "Observe ");
                //observing any data change
                if (movieModels != null) {

                    Log.v("Tag4", "Observe ");
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });

    }


    //Observing any data changes such as a listener
    private void ObserveAnyChange() {
        Log.v("Tag2", "Observe ");
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag3", "Observe ");
                //observing any data change
                if (movieModels != null) {
                    Log.v("Tag4", "Observe ");
                    for (MovieModel movieModel : movieModels) {
                        //Get the data in log
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    private void ConfigureRecyclerView() {
        //Live Data cant  be passed via the constructor
        movieRecyclerAdapter = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //ReyclerView Pagination
        //Loading next page of api response
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    //Here we need to display the next search results on the next page of api
                    if(Search != null)
                        movieListViewModel.searchNextPage();
                    else
                        movieListViewModel.searchNextPage2();
                }
            }
        });


    }


    @Override
    public void onMovieClick(int position) {

        //Toast.makeText(this, "The position: " +position, Toast.LENGTH_SHORT).show();

        //We dont need the position of the movie recylerView
        //We need the id of the movie in order to get its details

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
                Log.v("Alaaaaaaaaaaaae", "langue searched " +langue);
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
                 isPopular = false;




            }
        });
    }

}