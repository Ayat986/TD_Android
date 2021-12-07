package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = (Button) findViewById(R.id.button);

        final EditText  editText = (EditText) findViewById(R.id.editText1);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast toast = Toast.makeText(MainActivity.this, "Dé lancé!", Toast.LENGTH_SHORT);
                //toast.show();
                TextView text = (TextView) findViewById(R.id.textView);
                TextView text1 = (TextView) findViewById(R.id.textView1);

                int random = new Random().nextInt(Integer.parseInt(editText.getText().toString()));
                random += 1;

                int random1 = new Random().nextInt(Integer.parseInt(editText.getText().toString()));
                random1 += 1;

                text.setText(" "+random);
                text1.setText(" "+random1);

            }
        });
    }

}