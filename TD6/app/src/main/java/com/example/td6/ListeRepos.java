package com.example.td6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListeRepos extends AppCompatActivity {
    RecyclerView rvReposList;
    Button search;
    EditText userName;
    GithubService githubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listerepos);

        rvReposList = findViewById(R.id.rvReposList);
        search = findViewById(R.id.search);
        userName = findViewById(R.id.userName);

        githubService = new Retrofit.Builder()
                .baseUrl(GithubService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListRepos();
            }
        });

    }

    public void getListRepos(){
        githubService.listRepos(userName.getText().toString()).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                if(response.body()!= null) {
                    Adapter adapter = new Adapter(response.body());
                    rvReposList.setAdapter(adapter);
                    rvReposList.setLayoutManager(new LinearLayoutManager(ListeRepos.this));
                }else {
                    Toast.makeText(ListeRepos.this,"Aucune corresponsance trouvée", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(ListeRepos.this,"Une erreur s'est produit", Toast.LENGTH_LONG).show();
            }
        });
    }



}
