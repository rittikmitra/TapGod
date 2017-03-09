package com.kaplan.tapgod;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String fileName =  "com.kaplan.tapgod.Game";

    int saveHScore;
    int highScore= Game.highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey",Context.MODE_PRIVATE);
        saveHScore = prefs.getInt("highScore", 0);

        if (saveHScore < highScore) {
            saveHScore = highScore;
            prefs.edit().putInt("highScore", saveHScore).apply();
        }

        final TextView TextHighScore = (TextView)findViewById(R.id.highScore);

        Button StartButton = (Button)findViewById(R.id.StartButton);
        Button infoButton = (Button)findViewById(R.id.infoButton);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Game.class);
                startActivity(intent);
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, info.class);
                startActivity(intent);
            }
        });

        TextHighScore.setText("HighScore: "+(saveHScore));
    }
}
