package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

public class Ground {
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
    float aspectRatio;


    //[x,y]
    public Ground(Activity3 activity, Bitmap ground, int x,int y) {
        this.x = x;
        this.y = y;
        a = activity;
        aspectRatio = a.sol.getWidth() / (float)a.sol.getHeight();

        bitmap = ground;
        //-----------------------Frame--------------------------//
        height = bitmap.getHeight(); // On divise le spritesheet par le nombre de lignes
        width = bitmap.getWidth(); // On divise le spritesheet par le nombre de colones
        //-----------------------Frame--------------------------//
        bitmap = Bitmap.createScaledBitmap(ground,width,height,false);
        paint = new Paint();
    }

    public void onDraw(Canvas c) {
        collision();
        Rect selec = new Rect(0,0,width,height);
        Rect src = new Rect(x+a.background1.x,y+a.background1.y, width+x+a.background1.x, height+y+a.background1.y);
        c.drawBitmap(bitmap,selec,src,null);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void collision() {
        boxPlayer = a.background1.x;
        int inverseX = x - x * 2;

        if (boxPlayer - a.sprite.getWidth() < inverseX && boxPlayer >= inverseX-width) {
            if (a.posY + a.sprite.getHeight()  > y && a.posY + a.sprite.getHeight() < y + bitmap.getHeight()) {
                Log.v("TEST : ", "AVANT : "+inverseX+" APRES : "+ (boxPlayer - a.sprite.getWidth()));
                a.sautControl = true;
                a.posY = y - a.sprite.getHeight();
            }
        }

        if (boxPlayer - a.sprite.getWidth() >= inverseX && boxPlayer <= inverseX-width) {
            if (a.posY + a.sprite.getHeight()  < y && a.posY + a.sprite.getHeight() > y + bitmap.getHeight()) {
                a.lockPos = false;
            }
        }

        if (a.posY + a.sprite.getHeight() >= y) {
            //Log.v("TEST : ", "AVANT : "+y+" APRES : "+(a.posY+a.sprite.getHeight()));
                a.lockPos = true;
        }
        a.lockPos = false;
    }
}
