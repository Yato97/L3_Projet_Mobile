package com.yato.projet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {
    private Activity2 activity;

    //-----------------------Layout--------------------------//
    private TextView score, textinvisible;
    ImageView perso;
    private Button button, buttong, buttond , buttona, buttonb;
    private LinearLayout invisible;
    Bitmap player;
    Sprite sprite;
    //-----------------------Layout--------------------------//


    //-------------------------Camera------------------------//
    private FrameLayout frameLayout;
    int posX; //Positions
    int posY;

    int screenWidth; //Dimentions ecran
    int screenHeight;
    //-------------------------Camera------------------------/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-------------------------Camera------------------------//
        //-------------------------Camera------------------------//


        //-----------------------Layout--------------------------//
        button = findViewById(R.id.button);
        buttong = findViewById(R.id.buttonG);
        buttond = findViewById(R.id.buttonD);
        buttona = findViewById(R.id.buttonA);
        buttonb = findViewById(R.id.buttonB);


        score = findViewById(R.id.score);
        perso = findViewById(R.id.player);
        //-----------------------Layout--------------------------//

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Retire la bordure haut
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        activity = new Activity2(this, point.x , point.y);

        setContentView(activity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity.resume();
    }
}
