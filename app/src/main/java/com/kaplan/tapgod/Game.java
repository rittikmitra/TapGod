package com.kaplan.tapgod;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class Game extends AppCompatActivity {
    int score = 0;
    boolean run;
    double x =1;
    ImageView[] array;
    public static int highScore = 0;
    CountDownTimer countDownTimer;
    SoundPool soundPool;
    int soundPing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView TextGameOver = (TextView)findViewById(R.id.gameOver);
        final TextView textViewScore = (TextView)findViewById(R.id.score);

        setTimer();


        array = new ImageView[16];
        array[0] = (ImageView) findViewById(R.id.disc1);
        array[1] = (ImageView) findViewById(R.id.disc2);
        array[2] = (ImageView) findViewById(R.id.disc3);
        array[3] = (ImageView) findViewById(R.id.disc4);
        array[4] = (ImageView) findViewById(R.id.disc5);
        array[5] = (ImageView) findViewById(R.id.disc6);
        array[6] = (ImageView) findViewById(R.id.disc7);
        array[7] = (ImageView) findViewById(R.id.disc8);
        array[8] = (ImageView) findViewById(R.id.disc9);
        array[9] = (ImageView) findViewById(R.id.disc10);
        array[10] = (ImageView) findViewById(R.id.disc11);
        array[11] = (ImageView) findViewById(R.id.disc12);
        array[12] = (ImageView) findViewById(R.id.disc13);
        array[13] = (ImageView) findViewById(R.id.disc14);
        array[14] = (ImageView) findViewById(R.id.disc15);
        array[15] = (ImageView) findViewById(R.id.disc16);


        for(int i = 0; i < 16; i++) {
            array[i].setTag(1);
            array[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initiizeSoundPool();

                    soundPool.play(soundPing, 0.9f, 0.9f, 1, 0, 0.99f);
                    int tag = (int) ((ImageView) view).getTag();
                    if(tag == 2) {
                        ((ImageView) view).setImageResource(R.drawable.orange);
                        ((ImageView) view).setTag(1);
                        score++;
                        if (score==((int)(10 + Math.pow(1.5,x+1)))) {
                            countDownTimer.cancel();
                            setTimer();
                            x++;
                            run = true;
                        } else {
                            run = false;
                        }
                        setNextDisc();
                        TextGameOver.setText("");
                    } else {
                        TextGameOver.setText("  WRONG BUTTON OMG");
                        score--;
                    }
                }

            });
        }

        setNextDisc();


    }

    private void initiizeSoundPool() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();
            int soundPing = soundPool.load(this, R.raw.ping,1);
        }else {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 1);
            int soundPing = soundPool.load(this, R.raw.ping,1);

        }
    }


    public void setTimer () {
        final TextView textViewScore = (TextView)findViewById(R.id.score);
        final TextView TextTimer = (TextView)findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                TextTimer.setText("Timer = " + (l / 1000));
                textViewScore.setText("" + score);
            }

            @Override
            public void onFinish() {
                if (run == true){

                } else {
                    TextTimer.setText("Time's up!");
                    AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                    builder.setMessage("" + score)
                            .setTitle("Score");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            textViewScore.setText("Game Over");
                            Intent intent = new Intent(Game.this, MainActivity.class);
                            startActivity(intent);
                            if (score>highScore) {
                                highScore = score + 1;
                            }
                        }
                    });

                    builder.setCancelable(false);

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();

                    dialog.show();
                }


            }

        };
        countDownTimer.start();
    }



    public void setNextDisc() {
        Random r = new Random();
        int i = r.nextInt(16);
        array[i].setImageResource(R.drawable.red);
        array[i].setTag(2);
    }
}
