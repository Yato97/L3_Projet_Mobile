package com.yato.projet;

import android.annotation.SuppressLint;
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
    Rect groundBox,playerBox,selec;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    //[x,y]
    public Ground(Activity3 activity, Bitmap ground, int x,int y) {
        this.x = x;
        this.y = y;

        a = activity;
        bitmap = ground;

        aspectRatio = bitmap.getWidth() / (float)bitmap.getHeight();

        //-----------------------Frame--------------------------//
        height = (bitmap.getHeight() * a.background1.getHeight()) / a.background1.originalHeight;
        width = (bitmap.getWidth() *  a.background1.getWidth()) / a.background1.originaWidth;
        //-----------------------Frame--------------------------//
        bitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
        groundBox = new Rect(x+a.background1.x,y+a.background1.y, width+x+a.background1.x, height+y+a.background1.y);
        paint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    public void onDraw(Canvas c) {
        collision();
        selec = new Rect(0,0,width,height);
        c.drawBitmap(bitmap,selec,groundBox,null);
    }


    public void collision() {
        playerBox = a.sprite.getDst();
        groundBox.set(x+a.background1.x,y+a.background1.y, width+x+a.background1.x, height+y+a.background1.y);
        if (playerBox.right / 2 >= groundBox.left && playerBox.right / 2 <= groundBox.right) {
            if (a.posY + a.sprite.getHeight()  >= groundBox.top &&  playerBox.top <= groundBox.top) {
                a.sautControl = true;
                a.posY = groundBox.top - a.sprite.getHeight();
            }
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
