package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    int x, y;
    int xSpeed, ySpeed;
    int width, height, widthB, heightB;
    int currentFrame, direcrion;
    Bitmap bitmap;
    Activity3 a;
    Paint paint;

    //[x,y]
    public Sprite(Activity3 activity, Bitmap player) {
        a = activity;
        bitmap = player;

        //-----------------------Frame--------------------------//
        width = bitmap.getWidth() / 13; // On divise le spritesheet par le nombre de colones
        height = bitmap.getHeight() / 21; // On divise le spritesheet par le nombre de lignes
        widthB = bitmap.getWidth();
        heightB = bitmap.getHeight();
        //-----------------------Frame--------------------------//

        currentFrame = 0; // La position du sprite voulu sur une ligne
        direcrion = 3; // La ligne voulu
        xSpeed = 5;
        ySpeed = 0;
        x = 0;
        y = 0;
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

    public void onDraw(Canvas c, String option) {
        int frameX = width * currentFrame;
        int frameY = direcrion * height;
        if (option == "droite") {
            direcrion = 11;
        }
        if (option == "gauche") {
            direcrion = 9;
        }
        update();
        if (currentFrame > 7) {
            currentFrame = 0;
        }
        Rect src = new Rect(frameX,frameY, width+frameX, height+frameY);
        Rect dst = new Rect(x ,y ,width+x  ,height+x);
        //Rect selec = new Rect(frameX,frameY,width+frameX,height+frameY);
        //Rect src = new Rect(0,0, widthB, heightB);
        //Rect dst = new Rect(0 ,0 ,widthB  ,heightB);

        c.drawBitmap(bitmap,src,dst,null);
        //c.drawRect(selec,paint);
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
