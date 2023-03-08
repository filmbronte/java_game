package com.example.javagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyletscode.ihavetofly.R;

import static com.example.javagame.GameView.screenRatioX;
import static com.example.javagame.GameView.screenRatioY;

public class Cats {
    public int speed = 10;
    public boolean wasShot = true;
    int x, y, width, height, catCounter=1;
    Bitmap cat1;

    Cats (Resources res){
        cat1 = BitmapFactory.decodeResource(res, R.drawable.cat1);

        width = cat1.getWidth();
        height = cat1.getHeight();

        width /= 20;
        height /= 20;

        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);

        cat1 = Bitmap.createScaledBitmap(cat1, width, height, false);

        y = -height;
    }

    Bitmap getCat1 (){
        if (catCounter == 1) {
            catCounter++;
            return cat1;
        }

        if (catCounter == 2) {
            catCounter++;
            return cat1;
        }

        if (catCounter == 3) {
            catCounter++;
            return cat1;
        }

        catCounter = 1;

        return cat1;

    }

    Rect getCollisionShape (){
        return new Rect(x, y, x + width, y + height);
    }

}
