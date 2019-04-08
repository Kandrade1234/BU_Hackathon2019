package com.example.realfake;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;
import static java.util.Arrays.fill;

public class ActivityGame extends AppCompatActivity {

    MediaPlayer bg;
    CountDownTimer timeCount;
    private int time;
    private int score;
    private Button option1;
    private Button option2;
    private Button next;
    private TextView text_score;
    private TextView text_time;
    private String realArr[] = new String[11];
    private String fakeArr[] = new String[11];
    private boolean hasChosen = true;
    private int tracker = 0;
    private int sideNum = 0;
    private boolean isUsed[] = {false, false, false, false, false, false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        option1 = findViewById(R.id.button_option1);
        option2 = findViewById(R.id.button_option2);
        text_score = findViewById(R.id.score);
        text_time = findViewById(R.id.time);
        next = findViewById(R.id.next);
        bg = MediaPlayer.create(ActivityGame.this, R.raw.bgmusic);
        bg.start();
          timeCount = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                text_time.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                text_time.setText("Times up!");
                option1.setEnabled(false);
                option2.setEnabled(false);
            }
        }.start();

        //String input = readText();
        try {
            InputStream is = getAssets().open("RealNews.txt");
            readFile(is, true);
        } catch(IOException ex){ ex.printStackTrace();}
        try {
            InputStream is2 = getAssets().open("FakeNews.txt");
            readFile(is2, false);
        } catch(IOException ex){ ex.printStackTrace();};
        execute();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCount.cancel();

                timeCount = new CountDownTimer(20000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        text_time.setText("Time: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        text_time.setText("Times up!");
                        option1.setEnabled(true);
                        option2.setEnabled(true);
                    }
                }.start();
               execute();
                option1.setEnabled(true);
                option2.setEnabled(true);
            }
        });
    }
    private int chooseSide(){
        int random;
        Random rand = new Random();
        random = rand.nextInt(2)+1;
        return random;
    }
/*
    private int chooseText(){
        int random;
        Random rand = new Random();
        random = rand.nextInt(10);
        while(isUsed[random]) {
            random = rand.nextInt(10);
            if (!isUsed[random]) break;
        }
        return random;
    }
*/
    private boolean checkArr(){
        for(int i = 0; i <= 10; i++){
            if(!isUsed[i]) return true;
        }
        return false;
    }

    private void readFile(InputStream is, boolean one){
        String text = "";
        try{
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);


        } catch(IOException ex){
            ex.printStackTrace();
        }
        if(one) realArr = text.split("\\r?\\n");
        if(!one) fakeArr = text.split("\\r?\\n");
    }


    private void execute(){

        sideNum = chooseSide();

        int index = 0;
        if (checkArr() == true)
        {
            //index = chooseText();
            index = tracker;
            isUsed[index] = true;

            if (sideNum == 1) {
                option1.setText(realArr[index]);
                option2.setText(fakeArr[index]);
            }
            else {
                option1.setText(fakeArr[index]);
                option2.setText(realArr[index]);
            }
        }
        else if(checkArr() == false) {

            if(score >=8) openActivityEndGame();
            else openActivityEndGameLose();


        }

        if (sideNum == 1) {
            option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option2.setText("Correct!");
                    score++;
                    text_score.setText("Score: " + score);
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                }
            });
            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option1.setText("Wrong!");
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                }
            });
        }
        else {
            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option1.setText("Correct!");
                    score++;
                    text_score.setText("Score: " + score);
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                }
            });
            option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    option2.setText("Wrong!");
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                }
            });
        }
        tracker++;
    }

    public void openActivityEndGame() {
        Intent intent = new Intent(this, EndGame.class);
        startActivity(intent);
    }
    public void openActivityEndGameLose(){
        Intent intent = new Intent(this, EndGameLose.class);
        startActivity(intent);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        bg.stop();
    }
}


