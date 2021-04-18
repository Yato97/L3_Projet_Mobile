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
import android.os.Bundle;
import android.os.Handler;
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

public class Activity3 extends Activity implements Runnable {
    //-----------------------SurfaceView----------------------//
    private Thread thread;
    private boolean isPlaying;
    private Background background1, background2;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private SurfaceView surface;
    //-----------------------SurfaceView----------------------//

    //-----------------------Layout--------------------------//
    private TextView score, textinvisible;
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
        player = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
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


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Retire la bordure haut
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(point.x, point.y, getResources());


        sprite = new Sprite(this,player);
        //-----------------------Playerpos----------------------//
        posX = sprite.getX();
        posY = sprite.getY();
        option = "static";
        //-----------------------Playerpos----------------------//



        paint = new Paint();
        isPlaying = true;
        thread = new Thread(this);
        thread.start();



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
                score.setText("PosX : " + posX + " PosY : " + posY);
                if (posY == screenHeight - sprite.getHeight()) { //Exeption : on attent d'étre au sol pour pouvoir resauté
                    buttona.setEnabled(true);
                }
            });
        }
    }

    public void update() {
        posX = (screenWidth/2);
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
            surface.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void sleep() {
        try {
            Thread.sleep(10);
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
                option = "static";
                touchControl = false;
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
                option = "static";
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
        //---------------------------Control------------------------//

        //---------------------------Limites------------------------//
        if (posY <= 0) { //HAUT
            posY = 0;
        }
        if (posX <= 0) { //GAUCHE
            posX = 0;
        }
        if (posY >= (screenHeight - sprite.getHeight())) { //BAS
            posY = (screenHeight - sprite.getHeight());
        }
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
            posY += (screenHeight / 40);
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






