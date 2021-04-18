package com.yato.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yato.projet.Sprite;

public class Activity0 extends Activity {
    //-----------------------Layout--------------------------//
    private TextView score, textinvisible;
    ImageView perso;
    private Button button, buttong, buttond , buttona, buttonb;
    private LinearLayout invisible;
    Bitmap player;
    Sprite sprite;
    //-----------------------Layout--------------------------//


    //-------------------------Control------------------------//
    private boolean touchControl = false; //Etat de marche
    private boolean touchControl2 = false; //Etat de saut
    private boolean startControl = false; //Etat de debut de jeu
    private boolean spriteLoaded = false;

    private Runnable runnable; //Code a executer
    private Handler handler;
    private String action;
    private int ground = 650;
    //-------------------------Control------------------------//


    //-------------------------Camera------------------------//
    private FrameLayout frameLayout;
    int posX; //Positions
    int posY;

    int screenWidth; //Dimentions ecran
    int screenHeight;
    //-------------------------Camera------------------------/

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0);


        //-------------------------Camera------------------------//
        frameLayout = findViewById(R.id.frame0);
        textinvisible = findViewById(R.id.textinvisible);
        invisible = findViewById(R.id.transparent);
        invisible.setBackgroundColor(Color.argb(80,255,255,255));
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

        frameLayout.setOnTouchListener((v, event) -> {
            textinvisible.setVisibility(View.INVISIBLE);
            invisible.setVisibility(View.INVISIBLE);
            if (!startControl) { //1er click
                startControl = true;

                //-----------------------Viewport-----------------------//
                screenWidth = frameLayout.getWidth();
                screenHeight = frameLayout.getHeight();
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
                touchControl2 = true;
                buttona.setEnabled(false);
            }
            return false;
        });
        mouve(action,touchControl, touchControl2); //UPDATE
        score.setText("posY : "+posY + " ecran : " + screenHeight);
        //---------------------------Control------------------------//


        //---------------------------Limites------------------------//
        if (posY <= 0) { //HAUT
            posY = 0;
        }
        if (posX <= 0) { //GAUCHE
            posX = 0;
        }
        if (posY >= (screenHeight - perso.getHeight())) { //BAS
            posY = (screenHeight - perso.getHeight());
        }
        if (posX >= (screenWidth - perso.getWidth())) { //DROITE
            posX = (screenWidth - perso.getWidth());
        }
        //---------------------------Limites------------------------//
        perso.setY(posY); //Update
        perso.setX(posX);
    }

    // -----------------------DEPLACEMENTS-----------------------//
    //-----------------------------------------------------------//
    public void mouve(String action, boolean etat, boolean etat2) {
        //------------------------AXE X--------------------------//
        if (action == "AVANCE" && etat) {
            posX += (screenWidth / 130);
        }
        if (action == "RECULE" && etat) {
            posX -= (screenWidth / 130);
        }
        //------------------------AXE X--------------------------//

        //------------------------AXE Y--------------------------//
        if (action == "SAUTE" && etat2) {
            touchControl2 = true; //Etat de saut
        }

        if (!touchControl2) { // On descend
            posY += (screenHeight / 40);
            if (posY == screenHeight - perso.getHeight()) { buttona.setEnabled(true);} //Valeur a changer quand les canevas seront crée
        }
        if (touchControl2 && posY > screenHeight / 2) { //On monte jusqu'a la limite du saut
            posY -= (screenHeight / 40);
        }
        else { //Quand on atteint la limite du saut on redescend sans prendre en compte les deplacement latéral
            touchControl2 = false;
        }
        //------------------------AXE Y--------------------------//
    }
    //-----------------------------------------------------------//
    // -----------------------DEPLACEMENTS-----------------------//
}

