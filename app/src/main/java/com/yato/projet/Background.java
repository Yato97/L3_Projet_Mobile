package com.yato.projet;

import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class Background {
    int x = 0,y;
    Bitmap background;
    int width;
    int height;
    float aspectRatio;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    Background(int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.intro2);

        aspectRatio = background.getWidth() / (float)background.getHeight();

        height = screenY;
        width = Math.round(height * aspectRatio);
        Log.v("SCALR :",""+aspectRatio);
        Log.v("SCREENY :",""+screenY);
        Log.v("SCALRWIDTH :",""+width);
        Log.v("SCALRHEIGHT :",""+height);

        background = Bitmap.createScaledBitmap(background, width , height, false);
     }
}
