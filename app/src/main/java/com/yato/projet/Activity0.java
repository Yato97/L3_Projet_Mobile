package com.yato.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity0 extends AppCompatActivity {
    //-----------------------Layout--------------------------//
    private TextView score;
    private ImageView perso;
    private Button button, buttong, buttond , buttona, buttonb;

    //-----------------------Layout--------------------------//


    //-------------------------Control------------------------//
    private boolean touchControl = false;
    private boolean startControl = false;

    private Runnable runnable;
    private Handler handler;
    private String action;
    //-------------------------Control------------------------//


    //-------------------------Camera------------------------//
    private FrameLayout frameLayout;
    int posX; //Positions
    int posY;

    int screenWidth; //Dimentions ecran
    int screenHeight;
    //-------------------------Camera------------------------//

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0);

        //-------------------------Camera------------------------//
        frameLayout = findViewById(R.id.frame0);
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




        frameLayout.setOnTouchListener((View.OnTouchListener) (v, event) -> {
            if (!startControl) { //1er click
                startControl = true;

                //-----------------------Viewport-----------------------//
                screenWidth = (int)frameLayout.getWidth();
                screenHeight = (int)frameLayout.getHeight();
                //-----------------------Viewport-----------------------//

                //-----------------------Playerpos----------------------//
                posX = (int) perso.getX();
                posY = (int) perso.getY();
                //-----------------------Playerpos----------------------//

                handler = new Handler();
                runnable = () -> {
                    seDeplace();
                    handler.postDelayed(runnable,20);
                };
                handler.post(runnable);
            }
            return true;
        });
        //-----------------------HOMELISTENER-----------------------//
        //----------------------------------------------------------//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("score", 100);
                setResult(0, intent);
                finish();
            }
        });
        //-----------------------------------------------------------//
        // -----------------------HOMELISTENER-----------------------//
    }

    @SuppressLint("ClickableViewAccessibility")
    public void seDeplace() {
        //---------------------------Control------------------------//
        buttond.setOnTouchListener((v, event) -> {
            action = "AVANCE";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchControl = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchControl = false;
            }
            return false;
        });
        buttong.setOnTouchListener((v, event) -> {
            action = "RECULE";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchControl = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchControl = false;
            }
            return false;
        });
        buttona.setOnTouchListener((v, event) -> {
            action = "SAUTE";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchControl = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchControl = false;
            }
            return false;
        });

        mouve(action,touchControl);
        //---------------------------Control------------------------//

        //---------------------------Limites------------------------//
        if (posY <= 0) {
            posY = 0;
        }
        if (posX <= 0) {
            posX = 0;
        }
        if (posY >= (screenHeight - perso.getHeight())) {
            posY = (screenHeight - perso.getHeight());
        }
        if (posX >= (screenWidth - perso.getWidth())) {
            posX = (screenWidth - perso.getWidth());
        }
        //---------------------------Limites------------------------//

        perso.setY(posY); //Update
        perso.setX(posX);
    }

    public void mouve(String action, boolean etat) {
        if (action == "AVANCE") {
            if (etat) {
                posX += (screenWidth / 130);
            }
        }
        if (action == "RECULE") {
            if (etat) {
                posX -= (screenWidth / 130);
            }
        }
        if (action == "SAUTE") {
            if (etat) {
                posY -= (screenHeight / 50);
            }
            else posY += (screenHeight / 50);
        }
    }
}