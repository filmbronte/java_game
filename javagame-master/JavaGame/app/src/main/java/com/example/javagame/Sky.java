package com.example.javagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.heyletscode.ihavetofly.R;

public class Sky {
    int x = 0, y = 0;
    Bitmap sky;
    Sky (int screenX, int screenY, Resources res){
        sky = BitmapFactory.decodeResource(res, R.drawable.sky);
        sky = Bitmap.createScaledBitmap(sky,screenX, screenY, false);
    }
}
