package com.yato.projet;

import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Background {
    int x = 0,y;
    Bitmap background;
    int width;
    int height;
    float aspectRatio;

     Background(int screenX, int screenY, Resources res) {
         background = BitmapFactory.decodeResource(res, R.drawable.intro);


        aspectRatio = background.getWidth() / (float)background.getHeight();
        height = screenY;
        width = Math.round(height * aspectRatio);

        background = Bitmap.createScaledBitmap(background, width , screenY, false);
     }

}
