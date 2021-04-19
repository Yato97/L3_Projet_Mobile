package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Coin {
    Coin sprite;
    int x, y;
    int xSpeed, ySpeed;
    int width, height, widthB, heightB;
    int currentFrame, direcrion;
    Bitmap bitmap;
    Activity3 a;
    Paint paint;


    //[x,y]
    public Coin(Activity3 activity, Bitmap coin) {
        sprite = this;
        a = activity;
        bitmap = coin;
        x = 0;
        y = 0;

        //-----------------------Frame--------------------------//
        width = (bitmap.getWidth() / 8); // On divise le spritesheet par le nombre de colones
        height = bitmap.getHeight(); // On divise le spritesheet par le nombre de lignes

        widthB = bitmap.getWidth();
        heightB = bitmap.getHeight();
        //-----------------------Frame--------------------------//

        currentFrame = 0; // La position du sprite voulu sur une ligne

        paint = new Paint();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void onDraw(Canvas c) {
        int frameX = width * currentFrame;

        if (currentFrame >= 7) {
            currentFrame = 0;
        }
        update();
        //Rect src = new Rect(frameX,height, width+frameX, height);
        //Rect dst = new Rect(x ,y ,(width+x)  ,height+y);
        Rect selec = new Rect(0,0,width+frameX,height);
        Rect src = new Rect(0,0, widthB, heightB);
        Rect dst = new Rect(0 ,0 ,widthB  ,heightB);

        c.drawRect(dst,paint);
        c.drawBitmap(bitmap,src,selec,null);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update() {
        currentFrame += 1;
    }

}

