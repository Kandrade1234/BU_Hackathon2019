package com.example.realfake;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.media.MediaPlayer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer bg;
    private Button start;
    private Button play_Pause;
    private Button button_onion;
    private Button button_HowToPlay;
    private TextView alertTextView;
    private TextView alertTextViewHTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.button_start);
        play_Pause =findViewById(R.id.play_Pause);
        button_onion = findViewById(R.id.button_Onion);
        button_HowToPlay = findViewById(R.id.button_HowToPlay);
        alertTextView = findViewById(R.id.AlertTextView);
        bg = MediaPlayer.create(MainActivity.this, R.raw.intro);
        bg.start();

        button_onion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setTitle("About The Onion");
                builder.setMessage("‘The Onion’ is a media company that publishes satirical articles ranging from international news to local news. In other words, ‘The Onion’ is not a real news source. To find out more about ‘The Onion,’ click here https://en.wikipedia.org/wiki/The_Onion. \n" +
                        "Disclaimer: This game should not be taken seriously. The photos and titles are taken from the internet and we do not claim to have ownership over the creator’s work. \n");
                builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Go to site", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertTextView.setVisibility(View.VISIBLE);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.addCategory(Intent.CATEGORY_BROWSABLE);
                        i.setData(Uri.parse("https://en.wikipedia.org/wiki/The_Onion"));
                        startActivity(i);
                    }
                });
                builder.show();
            }
        });

        button_HowToPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setTitle("How To Play");
                builder.setMessage("You are given two titles of news articles, one will be from ‘The Onion’ and the other from a cable news source like ‘CNN’. The goal of the game is to choose the article title that comes from ‘The Onion’ at least 8 of the 11 rounds to win. Each round will have a timer counting down from 20 seconds. If you do not choose an option after that time has elapsed, you lose that round. \n" +
                        "Find out what ‘The Onion’ is by going back to the home screen and clicking on the onion. \n");
                builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
                /*
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertTextViewHTP.setVisibility(View.VISIBLE);
                    }
                });*/
                builder.show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityGame();
            }
        });
        play_Pause.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View view) {
                if(isClicked == true) {
                    play(view);
                    Snackbar.make(view, "Music: ON", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    isClicked = false;
                }
                else {
                    pause(view);
                    Snackbar.make(view, "Music: OFF", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    isClicked = true;
                }
            }
        });
    }


    public void openActivityGame() {
        Intent intent = new Intent(this, ActivityGame.class);
        startActivity(intent);
    }

    public void pause ( View view){
        bg.pause();
        play_Pause.setBackgroundResource(R.drawable.play);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        bg.stop();
    }

    public void play(View view){
        bg.start();
        play_Pause.setBackgroundResource(R.drawable.pause);
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
