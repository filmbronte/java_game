package com.example.javagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyletscode.ihavetofly.R;

import static com.example.javagame.GameView.screenRatioX;
import static com.example.javagame.GameView.screenRatioY;

public class Cats2 {
    public int speed = 15;
    public boolean wasShot = true;
    int x, y, width, height, catCounter=1;
    Bitmap cat2;

    Cats2(Resources res){
        cat2 = BitmapFactory.decodeResource(res, R.drawable.cat2);

        width = cat2.getWidth();
        height = cat2.getHeight();

        width /= 20;
        height /= 20;

        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);

        cat2 = Bitmap.createScaledBitmap(cat2, width, height, false);

        y = -height;
    }

    Bitmap getCat2 (){
        return cat2;

    }

    Rect getCollisionShape (){
        return new Rect(x, y, x + width, y + height);
    }

}
