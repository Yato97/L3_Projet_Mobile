package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {
    Sprite sprite;
    int x, y;
    int xSpeed, ySpeed;
    int width, height, widthB, heightB;
    int frameX, frameY;
    int currentFrame, direcrion;
    Rect dst;
    Bitmap bitmap;
    Activity3 a;
    Paint paint;

    public Rect getDst() {
        return dst;
    }

    //[x,y]
    public Sprite(Activity3 activity, Bitmap player) {
        sprite = this;
        a = activity;
        bitmap = Bitmap.createScaledBitmap(player,player.getWidth(),player.getHeight(),false);
        x = 0;
        y =  0;

        //-----------------------Frame--------------------------//
        width = (bitmap.getWidth() / 5); // On divise le spritesheet par le nombre de colones
        height = (bitmap.getHeight() / 2); // On divise le spritesheet par le nombre de lignes
        //width = bitmap.getWidth() / 9; // Fille
        //height = bitmap.getHeight() / 16; // Fille
        widthB = bitmap.getWidth();
        heightB = bitmap.getHeight();

        //-----------------------Frame--------------------------//

        currentFrame = 0; // La position du sprite voulu sur une ligne
        direcrion = 0; // La ligne voulu

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
        //Log.v("Test : ", " Height : "+bitmap.getHeight()+" width : "+bitmap.getWidth());
        frameX = width  * currentFrame;
        frameY = height  * direcrion;
        if (option == "droite") {
            direcrion = 0;
            update();
        }
        if (option == "gauche") {
            direcrion = 1;
            update();
        }
        if (option == "static") {
            direcrion = 0;
        }
        if (option == "staticgauche") {
            direcrion = 1;
        }
        if (currentFrame >= 5) {
            currentFrame = 0;
        }

        Rect src = new Rect(frameX,frameY, width+frameX, height + frameY);
        dst = new Rect(x-frameX/26 ,y ,(width+x)-frameX/26 ,height+y);

        c.drawBitmap(bitmap,src,dst,null);
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
