    package com.yato.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yato.projet.Sprite;

import java.util.ArrayList;
import java.util.List;

    public class Activity3 extends Activity implements Runnable {
    //-----------------------SurfaceView----------------------//
    private Thread thread;
    private boolean isPlaying;
    Background background1;
    private int screenX, screenY;
    private float scaleX, scaleY;
    private Paint paint;
    private SurfaceView surface;
    //-----------------------SurfaceView----------------------//

    //-----------------------Layout--------------------------//
    TextView score, textinvisible;
    int compteur = 0;
    private Button button, buttong, buttond , buttona, buttonb;
    private LinearLayout invisible;
    Canvas canvas;
    Bitmap player;
    Bitmap coinsheet;
    Bitmap sol, solHauteur, sautRef;
    Sprite sprite;
    Coin coin, coin2, coin3, coin4;
    Ground ground, ground2, sautRefGround;
    //-----------------------Layout--------------------------//

    //-----------------------Musique-------------------------//
    MediaPlayer jump;
    MediaPlayer song;
    //-----------------------Musique--------------------------//


    //-------------------------Control------------------------//
     boolean touchControl = false; //Etat de marche
     boolean touchControl2 = false; //Etat de saut
     boolean sautControl = false; //Etat de debut de jeu
     boolean lockPos = false;
    private String action;
    private String option;
    //-------------------------Control------------------------//


    //-------------------------Camera------------------------//
    private FrameLayout frameLayout;
    int posX; //Positions
    int posY;

    int screenWidth; //Dimentions ecran
    int screenHeight;
    //-------------------------Camera------------------------/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        //-----------------------SurfaceView----------------------//
        surface = findViewById(R.id.surfaceV);
        player = BitmapFactory.decodeResource(getResources(), R.drawable.playersheet);
        coinsheet = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        sol = BitmapFactory.decodeResource(getResources(), R.drawable.boxtest);
        solHauteur = BitmapFactory.decodeResource(getResources(), R.drawable.texture2);
        sautRef = BitmapFactory.decodeResource(getResources(), R.drawable.sautref);
        scaleX = 1980f;
        scaleY = 1080f;
        //-----------------------SurfaceView----------------------//

        //-----------------------Layout--------------------------//
        frameLayout = findViewById(R.id.frame0);
        button = findViewById(R.id.button);
        buttong = findViewById(R.id.buttonG);
        buttond = findViewById(R.id.buttonD);
        buttona = findViewById(R.id.buttonA);
        buttonb = findViewById(R.id.buttonB);
        score = findViewById(R.id.score);
        //-----------------------Layout--------------------------//

        //-----------------------Musique--------------------------//
        song = MediaPlayer.create(this,R.raw.theme);
        jump = MediaPlayer.create(this,R.raw.jump);
        jump.setVolume(0.2f, 0.2f);
        //-----------------------Musique--------------------------//

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Retire la bordure haut
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        background1 = new Background(point.x, point.y, getResources());
        sprite = new Sprite(this, player);
        ground = new Ground(this,sol,0,background1.getHeight() - sol.getHeight());
        ground2 = new Ground(this, solHauteur, sol.getWidth() * 2 ,background1.getHeight() - sol.getHeight() * 3);
        sautRefGround = new Ground(this, sautRef, sol.getWidth() + 16 ,  background1.getHeight() - sautRef.getHeight());
        coin2 = new Coin(this,coinsheet,400,background1.getHeight() - coinsheet.getHeight() - sol.getHeight() * 2);
        coin3 = new Coin(this,coinsheet,600,background1.getHeight() - coinsheet.getHeight() - sol.getHeight() * 2);
        coin4 = new Coin(this,coinsheet,800,background1.getHeight() - coinsheet.getHeight() - sol.getHeight() * 2);
        coin = new Coin(this,coinsheet, 200, background1.getHeight() - coinsheet.getHeight() - sol.getHeight() * 2);

        //-----------------------Playerpos----------------------//
        posX = sprite.getX();
        posY = sprite.getY();
        option = "static";
        //-----------------------Playerpos----------------------//

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE); am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        paint = new Paint();
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
        song.setLooping(true);
        song.start(); //La musique principale du jeu



        //-----------------------HOMELISTENER-----------------------//
        //----------------------------------------------------------//
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("score", 100);
            setResult(0, intent);
            finish();
        });
        //-----------------------------------------------------------//
        // -----------------------HOMELISTENER-----------------------//
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            seDeplace();
            draw();
            sleep();

            runOnUiThread(() -> {
                posX = background1.x;
                int boxPlayer = background1.x - (sprite.getWidth() * 2);
                int inverseX = posX - posX * 2;
                score.setText("pos Y : "+ (posY+sprite.getHeight()) + " ground : " + (ground.getY()));
                if (posY >= screenHeight - sprite.getHeight() || sautControl) { //Exeption : on attent d'étre au sol pour pouvoir resauté
                    buttona.setEnabled(true);
                }
                if (!sautControl) {
                    buttona.setEnabled(false);
                }
                if (lockPos) {
                    score.setText("lol");
                }
            });

        }
    }



        public void update() {
        //Limites
        if (background1.x + background1.background.getWidth() <= 0) {
            background1.x = screenX;
        }
    }

    public void draw() {
        if (surface.getHolder().getSurface().isValid()) {
            Canvas canvas = surface.getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x, background1.y, paint);

            sprite.onDraw(canvas, option);
            coin.onDraw(canvas);
            coin2.onDraw(canvas);
            coin3.onDraw(canvas);
            coin4.onDraw(canvas);

            ground.onDraw(canvas);
            ground2.onDraw(canvas);
            sautRefGround.onDraw(canvas);

            surface.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @SuppressLint("ClickableViewAccessibility")

    public void seDeplace() {
        //-----------------------Viewport-----------------------//
        screenWidth = frameLayout.getWidth();
        screenHeight = frameLayout.getHeight();
        //-----------------------Viewport-----------------------//

        //---------------------------Control------------------------//
        buttond.setOnTouchListener((v, event) -> {
            action = "AVANCE";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                option = "droite";
                touchControl = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchControl = false;
                option = "static";
            }
            return false;
        });
        buttong.setOnTouchListener((v, event) -> {
            action = "RECULE";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                option = "gauche";
                touchControl = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchControl = false;
                option = "staticgauche";
            }
            return false;
        });
        buttona.setOnTouchListener((v, event) -> {
            action = "SAUTE";
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchControl2 = true;
                jump.start(); //La musique du saut
                buttona.setEnabled(false);
            }
            return false;
        });
        //---------------------------Control------------------------//

        //---------------------------Limites------------------------//

        //if (posY <= 0) { //HAUT
        //    posY = 0;
        //}
        if (posX <= 0) { //GAUCHE
            posX = 0;
        }
        //if (posY >= (screenHeight - sprite.getHeight())) { //BAS
        //    posY = (screenHeight - sprite.getHeight());
        //}
        if (posX >= (screenWidth - sprite.getWidth())) { //DROITE
            posX = (screenWidth - sprite.getWidth());
        }
        //---------------------------Limites------------------------//

        sprite.setY(posY); //Update
        sprite.setX(posX);

        mouve(action,touchControl, touchControl2); //UPDATE
    }

    // -----------------------DEPLACEMENTS-----------------------//
    //-----------------------------------------------------------//
    public void mouve(String action, boolean etat, boolean etat2) {
        //------------------------AXE X--------------------------//
        if (action == "AVANCE" && etat) {
            background1.x -= (screenWidth / 130);
        }
        if (action == "RECULE" && etat) {
            background1.x += (screenWidth / 130);
        }
        //------------------------AXE X--------------------------//

        //------------------------AXE Y--------------------------//
        if (action == "SAUTE" && etat2) {
            touchControl2 = true; //Etat de saut
        }
        if (!touchControl2) { // On descend "gravité"
            posY += 50;
        }
        if (touchControl2 && sprite.getDst().bottom >  sautRefGround.height) { //On monte jusqu'a la limite du saut
            posY -= 50;
            sautControl = false;
        }
        else { //Quand on atteint la limite du saut on redescend
            touchControl2 = false;
        }
        //------------------------AXE Y--------------------------//
    }
    //-----------------------------------------------------------//
    // -----------------------DEPLACEMENTS-----------------------//
}






