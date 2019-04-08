package com.example.realfake;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EndGameLose extends AppCompatActivity {
    private Button home;
    MediaPlayer bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_lose);
        home = findViewById(R.id.next);
        bg = MediaPlayer.create(EndGameLose.this, R.raw.winning);
        bg.start();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityHome();
            }
        });
    }


    public void openActivityHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        bg.stop();
    }
}


