package com.example.appact4_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appact4_5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public CardView games, google_map, google_play;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){ openActivity2();}

        });


        google_map=(CardView) findViewById(R.id.GoogleMap);
        google_map.setOnClickListener(this);

        google_play=(CardView) findViewById(R.id.GooglePlay);
        google_play.setOnClickListener(this);

    }

    public void openActivity2 () {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.GoogleMap) {
            Intent i = new Intent(this, GoogleMap.class);
            startActivity(i);
        } else if (id == R.id.GooglePlay){
            Intent i = new Intent(this, BottomNav.class);
            startActivity(i);
        }

    }
    }
