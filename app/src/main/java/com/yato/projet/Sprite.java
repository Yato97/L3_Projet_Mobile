package com.yato.projet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Sprite {
    int x, y;
    int xSpeed, ySpeed;
    int width, height;
    int currentFrame, direcrion;
    Bitmap bitmap;
    Activity3 a;

    //[x,y]
    public Sprite(Activity3 activity, Bitmap player) {
        a = activity;
        bitmap = player;

        //-----------------------Frame--------------------------//
        width = bitmap.getWidth() / 13; // On divise le spritesheet par le nombre de colones
        height = bitmap.getHeight() / 22; // On divise le spritesheet par le nombre de lignes
        //-----------------------Frame--------------------------//

        currentFrame = 2; // La position du sprite voulu sur une ligne
        direcrion = 1; // La ligne voulu
        xSpeed = 5;
        ySpeed = 0;
        x = y = 0;
    }

    public void onDraw(Canvas c) {
        int frameX = width * currentFrame;
        int frameY = direcrion * height;
        bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),false);
        Rect src = new Rect(0,0, frameX, frameY);
        Rect dst = new Rect(0 ,0 ,frameX  ,frameY);

        c.drawBitmap(bitmap,src,dst,null);
    }

    public void update() {
        currentFrame = ++currentFrame % 4;
        x += xSpeed;
    }
}
