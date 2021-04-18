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

        paint = new Paint();
        isPlaying = true;
        thread = new Thread(this);
        thread.start();


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
            if (!spriteLoaded) {
                sprite = new Sprite(this,player);
                spriteLoaded = true;
            }
    }



    @Override
    public void run() {
        while (isPlaying) {
            seDeplace();
            update();
            draw();
            sleep();
        }
    }

    public void update() {
        background1.x -= 10;
        //Limites
        if (background1.x + background1.background.getWidth() <= 0) {
            background1.x = screenX;
        }
    }

    public void draw() {
        if (surface.getHolder().getSurface().isValid()) {
            Canvas canvas = surface.getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x, background1.y, paint);
            sprite.onDraw(canvas);

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
}





