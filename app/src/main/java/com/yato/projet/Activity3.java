    package com.yato.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
    Runnable context = this;
    private Thread thread;
    private boolean isPlaying;
    Background background1;
    private int screenX, screenY;
    private Paint paint;
    private SurfaceView surface;
    Dialog dialog_win, dialog_lose;
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
    Ground ground, ground2, sautRefGround, sautRef22, pillar3, goldislant, landnextgold, longend,endF, littlebox, multplate, multplate2,multhaut, littlebox2, littlebox3, endscale, multplateScale, duoScale;
    //-----------------------Layout--------------------------//

    //-----------------------Musique-------------------------//
    MediaPlayer jump;
    MediaPlayer song;
    //-----------------------Musique--------------------------//


    //-------------------------Control------------------------//
        boolean touchControl = false; //Etat de marche
        boolean touchControl2 = false; //Etat de saut
        boolean sautControl = false;
        boolean lockPos = false;
        private String action;
        private String option;
    //-------------------------Control------------------------//


    //-------------------------Camera------------------------//
    private FrameLayout frameLayout;
    int posX; //Positions
    int posY;

    Point point;

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
        sol = BitmapFactory.decodeResource(getResources(), R.drawable.texture);
        solHauteur = BitmapFactory.decodeResource(getResources(), R.drawable.pont);
        sautRef = BitmapFactory.decodeResource(getResources(), R.drawable.sautref);
        Bitmap sautRef2 = BitmapFactory.decodeResource(getResources(), R.drawable.sauterefmoyen);
        Bitmap sautRef3 = BitmapFactory.decodeResource(getResources(), R.drawable.pillarthree);
        Bitmap goldIsland = BitmapFactory.decodeResource(getResources(), R.drawable.goldisland);
        Bitmap landNextGold = BitmapFactory.decodeResource(getResources(), R.drawable.goldnextstate);
        Bitmap multPlate = BitmapFactory.decodeResource(getResources(), R.drawable.multplate);
        Bitmap multHaut = BitmapFactory.decodeResource(getResources(), R.drawable.multihaut);
        Bitmap lastLong = BitmapFactory.decodeResource(getResources(), R.drawable.lastlong);
        Bitmap end = BitmapFactory.decodeResource(getResources(), R.drawable.end);
        Bitmap boxaera = BitmapFactory.decodeResource(getResources(), R.drawable.littlebox);
        Bitmap endScale = BitmapFactory.decodeResource(getResources(), R.drawable.endscale);
        //-----------------------SurfaceView----------------------//

        //-----------------------Layout--------------------------//
        frameLayout = findViewById(R.id.frame0);
        button = findViewById(R.id.button);
        buttong = findViewById(R.id.buttonG);
        buttond = findViewById(R.id.buttonD);
        buttona = findViewById(R.id.buttonA);
        buttonb = findViewById(R.id.buttonB);
        score = findViewById(R.id.score);

        dialog_win =new Dialog(this);
        dialog_lose =new Dialog(this);
        //-----------------------Layout--------------------------//

        //-----------------------Musique--------------------------//
        song = MediaPlayer.create(this,R.raw.theme);
        jump = MediaPlayer.create(this,R.raw.jump);
        jump.setVolume(0.2f, 0.2f);
        //-----------------------Musique--------------------------//

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Retire la bordure haut
        point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        background1 = new Background(point.x, point.y, getResources());
        ground = new Ground(this,sol,0,point.y * 59 / 64);
        sprite = new Sprite(this, player);
        ground2 = new Ground(this, solHauteur, (ground.getWidth()), (int) ((double)point.y * 48.5/64));
        duoScale = new Ground(this, sautRef,0, (int) ((double)point.y ));
        sautRefGround = new Ground(this, sautRef,  (ground2.getX() + ground2.getWidth() + duoScale.getWidth()),  (int) ((double)point.y * 48.5/64));
        sautRef22 = new Ground(this, sautRef2,  (sautRefGround.getX() + sautRefGround.getWidth() + sautRefGround.getWidth()),  (int) ((double)point.y * 43.64/64));
        pillar3 = new Ground(this, sautRef3,  (sautRef22.getX() + sautRefGround.getWidth() + duoScale.getWidth() + duoScale.getWidth() / 16),  (int) ((double)point.y * 38.54/64));
        goldislant = new Ground(this, goldIsland,  (pillar3.getX() + pillar3.getWidth() + duoScale.getWidth()),  (int) ((double)point.y * 38.54/64));
        landnextgold = new Ground(this, landNextGold,  (goldislant.getX() + goldislant.getWidth() - pillar3.getWidth()/2),  (int) ((double)point.y * 59 / 64));
        multhaut = new Ground(this, multHaut,  (landnextgold.getX() + landnextgold.getWidth()),  (int) ((double)point.y * 43.64/64));
        multplateScale = new Ground(this, multPlate,  (landnextgold.getX() + landnextgold.getWidth()),  (int) ((double)point.y));
        multplate = new Ground(this, multPlate,  (landnextgold.getX() + landnextgold.getWidth() + multplateScale.getWidth()),  (int) ((double)point.y * 59 / 64));
        multplate2 = new Ground(this, lastLong,  (multplate.getX() + multplate.getWidth() + multplateScale.getWidth()),  (int) ((double)point.y * 59 / 64));
        endscale = new Ground(this,endScale,0,point.y);
        endF = new Ground(this, end,  (multplate2.getX() + multplate2.getWidth() + endscale.getWidth() + endScale.getWidth() / 64),  (int) ((double)point.y * 59 / 64));
        littlebox = new Ground(this, boxaera,  (endF.getX() + duoScale.getWidth()),  (int) ((double)point.y * 43.64 / 64));
        littlebox2 = new Ground(this, boxaera,  (littlebox.getX()+ littlebox.getWidth() + duoScale.getWidth()),  (int) ((double)point.y * 38.52 / 64));
        littlebox3 = new Ground(this, boxaera,  (littlebox2.getX() + littlebox2.getWidth() + duoScale.getWidth()),  (int) ((double)point.y * 33.20 / 64));


        coin2 = new Coin(this,coinsheet,((ground2.getX() + ground2.getWidth()/2)),ground2.getY() - coinsheet.getHeight() - ground.getHeight());
        coin3 = new Coin(this,coinsheet,((sautRef22.getX() + sautRef22.getWidth()/2) - coin2.width / 2),sautRef22.getY() - coinsheet.getHeight() - ground.getHeight());
        coin4 = new Coin(this,coinsheet,(multplate.getX() + multplate.getWidth()/2) - coin2.width / 2,multplate.getY() - coinsheet.getHeight() - ground.getHeight());
        coin = new Coin(this,coinsheet, landnextgold.getX() + landnextgold.getWidth() * 3/4, ground.getY() - coinsheet.getHeight() - ground.getHeight());
        dialog_lose.setContentView(R.layout.defaite);
        dialog_win.setContentView(R.layout.victoire);
        //-----------------------Playerpos----------------------//
        posX = sprite.getX();
        posY = sprite.getY();
        option = "static";
        //-----------------------Playerpos----------------------//
        paint = new Paint();
        isPlaying = true;
        thread = new Thread(context);
        thread.start();
        song.setLooping(true);
        song.start(); //La musique principale du jeu
        song.setVolume(0.5f,0.5f);

        //-----------------------HOMELISTENER-----------------------//
        //----------------------------------------------------------//
        button.setOnClickListener(v -> {
            song.stop();
            Intent intent = new Intent();
            intent.putExtra("score", compteur);
            setResult(3, intent);
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
                Log.v("POSX : ", ""+(background1.x)+"   LILMITE : "+ (-littlebox3.getX()));
                posX = background1.x;
                score.setText(""+compteur);
                if (posY >= screenHeight - sprite.getHeight() || sautControl) { //Exeption : on attent d'??tre au sol pour pouvoir resaut??
                    buttona.setEnabled(true);
                }
                if (!sautControl) {
                    buttona.setEnabled(false);
                }
                if (posY >= (screenHeight - sprite.getHeight())) { //BAS
                    TextView scoreT = dialog_lose.findViewById(R.id.score);
                    scoreT.setText(""+compteur);
                    openLooseDialog();
                }
                if (background1.x  <= -(endF.getX())) {
                    TextView scoreT = dialog_win.findViewById(R.id.score);
                    scoreT.setText(""+compteur);
                    openWinDialog();
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

            ground.onDraw(canvas);
            ground2.onDraw(canvas);
            sautRefGround.onDraw(canvas);
            sautRef22.onDraw(canvas);
            pillar3.onDraw(canvas);
            goldislant.onDraw(canvas);
            landnextgold.onDraw(canvas);
            multplate.onDraw(canvas);
            multhaut.onDraw(canvas);
            multplate2.onDraw(canvas);
            endF.onDraw(canvas);
            littlebox.onDraw(canvas);
            littlebox2.onDraw(canvas);
            littlebox3.onDraw(canvas);

            coin.onDraw(canvas);
            coin2.onDraw(canvas);
            coin3.onDraw(canvas);
            coin4.onDraw(canvas);
            surface.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void sleep() {
        try {
            Thread.sleep(45);
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
        Rect spriteDst = sprite.getDst();
        //------------------------AXE X--------------------------//
        if (action == "AVANCE" && etat) {
            background1.x -= (screenWidth / 50);
        }
        if (action == "RECULE" && etat) {
            background1.x += (screenWidth / 50);
        }
        //------------------------AXE X--------------------------//

        //------------------------AXE Y--------------------------//
        if (action == "SAUTE" && etat2) {
            touchControl2 = true; //Etat de saut
        }
        if (!touchControl2) { // On descend "gravit??"
            posY += screenHeight / 20;
        }
        if (etat2) {
            if (posY + sprite.getHeight() <= point.y && posY + sprite.getHeight() >= pillar3.getY() - ground.getHeight()) {//On monte jusqu'a la limite du saut
                posY -= screenHeight / 20;
                sautControl = false;
            }
            else { //Quand on atteint la limite du saut on redescend
                touchControl2 = false;
            }
        }
        //------------------------AXE Y--------------------------//
    }
    //-----------------------------------------------------------//
    // -----------------------DEPLACEMENTS-----------------------//
    private void openWinDialog() {
        pause();
        Button quiter = dialog_win.findViewById(R.id.quiter);
        quiter.setOnClickListener(v -> {
            song.stop();
            Intent intent = new Intent();
            intent.putExtra("score", compteur);
            setResult(3, intent);
            finish();
            dialog_win.dismiss();
        });
        dialog_win.show();
    }

    private void openLooseDialog() {
        pause();
        Button quiter2 = dialog_lose.findViewById(R.id.quiter);
        quiter2.setOnClickListener(v -> {
            song.stop();
            Intent intent = new Intent();
            intent.putExtra("score", compteur);
            setResult(3, intent);
            finish();
            dialog_lose.dismiss();
        });
        dialog_lose.show();
    }
}






