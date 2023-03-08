package com.example.javagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyletscode.ihavetofly.R;

import static com.example.javagame.GameView.screenRatioX;
import static com.example.javagame.GameView.screenRatioY;

public class CatBoss {
    public int speed = 3;
    public boolean wasShot = true;
    int x, y, width, height, catCounter=1, catH = 25;
    Bitmap catboss;

    CatBoss(Resources res){
        catboss = BitmapFactory.decodeResource(res, R.drawable.catboss);

        width = catboss.getWidth();
        height = catboss.getHeight();

        width /= 3;
        height /= 3;

        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);

        catboss = Bitmap.createScaledBitmap(catboss, width, height, false);

        y = -height;
    }

    Bitmap getCatBoss (){
        if (catCounter == 1) {
            catCounter++;
            return catboss;
        }
        catCounter = 1;

        return catboss;

    }

    Rect getCollisionShape (){
        return new Rect(x, y, x + width, y + height);
    }

}
