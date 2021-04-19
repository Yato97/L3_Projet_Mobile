package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
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


    //[x,y]
    public Coin(Activity3 activity, Bitmap coin, int x,int y, Background background, Sprite perso) {
        this.x = x;
        this.y = y;
        this.background = background;
        this.perso = perso;
        sprite = this;
        a = activity;
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
        collision();
        //Rect src = new Rect(frameX,height, width+frameX, height);
        //Rect dst = new Rect(x ,y ,(width+x)  ,height+y);
        Rect selec = new Rect(frameX,0,width+frameX,height);
        Rect src = new Rect(x+background.x,y+background.y, width+x+background.x, height+y+background.y);

        c.drawBitmap(bitmap,selec,src,null);

        if (collision()) {
            a.compteur++;
            x = 1;
            y = 1;
        }
    }

    public void update() {
        currentFrame += 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean collision() {
        boxPlayer = background.x - (width * 2);
        int inverseX = x - x*2;
        if (boxPlayer < inverseX && boxPlayer > inverseX-width) {
            return true;
        }
        return false;
    }

}

