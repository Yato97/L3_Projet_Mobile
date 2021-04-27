package com.yato.projet;

import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class Background {
    int x = 0,y;
    Bitmap background;
    int width, originaWidth;
    int height, originalHeight;
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

    public int getX() {
        return x;
    }

    public int getOriginaWidth() {
        return originaWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    Background(int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.mapfinal);
        originaWidth = background.getWidth();
        originalHeight = background.getHeight();

        aspectRatio = background.getWidth() / (float)background.getHeight();

        height = screenY;
        width = Math.round(height * aspectRatio);

        background = Bitmap.createScaledBitmap(background, width , height, false);
     }
}
