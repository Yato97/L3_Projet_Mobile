package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

public class Coin {
    Coin sprite;
    int x, y;
    int xSpeed, ySpeed;
    int width, height, widthB, heightB;
    int currentFrame, direcrion;
    Bitmap bitmap;
    Activity3 a;
    Sprite perso;
    Paint paint;
    Background background;
    int boxPlayer;
    MediaPlayer hit;


    //[x,y]
    public Coin(Activity3 activity, Bitmap coin, int x,int y) {
        this.x = x;
        this.y = y;
        a = activity;
        hit = MediaPlayer.create(a, R.raw.lvl);
        hit.setVolume(0.2f, 0.2f);
        sprite = this;
        bitmap = coin;
        //-----------------------Frame--------------------------//
        width = (bitmap.getWidth() / 8); // On divise le spritesheet par le nombre de colones
        height = bitmap.getHeight(); // On divise le spritesheet par le nombre de lignes

        widthB = bitmap.getWidth();
        heightB = bitmap.getHeight();
        //-----------------------Frame--------------------------//

        currentFrame = 0; // La position du sprite voulu sur une ligne

        paint = new Paint();
    }

    public void onDraw(Canvas c) {
        int frameX = width * currentFrame;

        if (currentFrame >= 7) {
            currentFrame = 0;
        }
        update();
        //Rect src = new Rect(frameX,height, width+frameX, height);
        //Rect dst = new Rect(x ,y ,(width+x)  ,height+y);
        Rect selec = new Rect(frameX,0,width+frameX,height);
        Rect src = new Rect(x+a.background1.x,y+a.background1.y, width+x+a.background1.x, height+y+a.background1.y);

        c.drawBitmap(bitmap,selec,src,null);

        if (collision()) {
            a.compteur++;
            x = 0;
            y = 0;
        }
    }

    public void update() {
        currentFrame ++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean collision() {
        boxPlayer = a.background1.x - (width * 2);
        int inverseX = x - x*2;
        if (boxPlayer <= inverseX && a.background1.x >= inverseX-width) {
            if (a.posY + a.sprite.getHeight() >= y && a.posY <= y+height) {
                hit.start(); //La musique du coin
                return true;
            }
        }
        return false;
    }

}

