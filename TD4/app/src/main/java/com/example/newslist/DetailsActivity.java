package com.example.newslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        setTitle(getLocalClassName());
        Button ok = (Button) findViewById(R.id.ok);
        TextView name = (TextView) findViewById(R.id.Name1);
        NewsListApplication app = (NewsListApplication) getApplicationContext();
        name.setText(app.getLogin());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,NewsActivity.class);
                startActivity(intent);
            }
        });
    }



}