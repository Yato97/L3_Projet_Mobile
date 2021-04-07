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

public class Activity1 extends AppCompatActivity {
    //-----------------------Layout--------------------------//
    private TextView score;
    private ImageView perso;
    private ImageView button, buttong, buttond, buttona, buttonb;

    //-----------------------Layout--------------------------//


    //-------------------------Control------------------------//
    private boolean touchControl = false;
    private boolean startControl = false;

    private Runnable runnable;
    private Handler handler;
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
        setContentView(R.layout.activity_1);

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

            else  {
                //Relachement du click
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchControl = true;
                }
                //Au click
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    touchControl = false;
                }
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

    public void seDeplace() {
        //---------------------------Control------------------------//
        if (touchControl) {
            posY -= (screenHeight / 50);
        }
        else {
            posY += (screenHeight / 50);
        }
        //---------------------------Control------------------------//

        //---------------------------Limites------------------------//
        if (posY <= 0) {
            posY = 0;
        }
        if (posY >= (screenHeight - perso.getHeight())) {
            posY = (screenHeight - perso.getHeight());
        }
        //---------------------------Limites------------------------//

        perso.setY(posY); //Update
    }
}