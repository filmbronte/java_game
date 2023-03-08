package com.example.javagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyletscode.ihavetofly.R;

import static com.example.javagame.GameView.screenRatioX;
import static com.example.javagame.GameView.screenRatioY;

public class Bullet2 {
    int x, y, width, height;
    Bitmap bullet;

    Bullet2(Resources res){
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
        width = bullet.getWidth();
        height = bullet.getHeight();
        width /= 4;
        height /= 4;
        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);
        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }
    Rect getCollisionShape (){
        return new Rect(x, y, x + width, y + height);
    }
}
