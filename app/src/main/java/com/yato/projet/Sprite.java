package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    Sprite sprite;
    int x, y;
    int xSpeed, ySpeed;
    int width, height, widthB, heightB;
    int frameX, frameY;
    int currentFrame, direcrion;
    Rect boxPlayer;
    Bitmap bitmap;
    Activity3 a;
    Paint paint;

    public Sprite getSprite() {
        return sprite;
    }

    //[x,y]
    public Sprite(Activity3 activity, Bitmap player) {
        sprite = this;
        a = activity;
        bitmap = player;
        x = 0;
        y = 0;

        //-----------------------Frame--------------------------//
        width = (bitmap.getWidth() / 13); // On divise le spritesheet par le nombre de colones
        height = bitmap.getHeight() / 21; // On divise le spritesheet par le nombre de lignes
        //width = bitmap.getWidth() / 9; // Fille
        //height = bitmap.getHeight() / 16; // Fille
        widthB = bitmap.getWidth();
        heightB = bitmap.getHeight();
        //-----------------------Frame--------------------------//

        currentFrame = 0; // La position du sprite voulu sur une ligne
        direcrion = 11; // La ligne voulu
        xSpeed = 5;
        ySpeed = 0;

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

    public int getxSpeed() {
        return xSpeed;
    }

    public int getFrameX() {
        return frameX;
    }

    public int getFrameY() {
        return frameY;
    }

    public void onDraw(Canvas c, String option) {
        frameX = width * currentFrame;
        frameY = direcrion * height;
        if (option == "droite") {
            direcrion = 11;
            update();
        }
        if (option == "gauche") {
            direcrion = 9;
            update();
        }
        if (option == "static") {
            direcrion = 11;
        }
        if (option == "staticgauche") {
            direcrion = 9;
        }
        if (currentFrame > 7) {
            currentFrame = 0;
        }

        Rect src = new Rect(frameX,frameY, width+frameX, height+frameY);
        Rect dst = new Rect(x-frameX/26 ,y ,(width+x)-frameX / 26  ,height+y);
        //Rect selec = new Rect(frameX,frameY,width+frameX,height+frameY);
        //Rect test = new Rect(0,0, width - width/ 2, height);
        //Rect dst = new Rect(0 ,0 ,widthB  ,heightB);

        //c.drawRect(test,paint);
        c.drawBitmap(bitmap,src,dst,null);

    }

    public int getWidth() {
        return width;
    }

    public void setBoxPlayer(Rect boxPlayer) {
        this.boxPlayer = boxPlayer;
    }


    public int getHeight() {
        return height;
    }

    public void update() {
        currentFrame += 1;
    }


}
