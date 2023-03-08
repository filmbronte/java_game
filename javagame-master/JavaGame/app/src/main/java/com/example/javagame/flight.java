package com.example.javagame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.heyletscode.ihavetofly.R;

import static com.example.javagame.GameView.screenRatioX;
import static com.example.javagame.GameView.screenRatioY;
import static com.example.javagame.GameView2.screenRatioX2;
import static com.example.javagame.GameView2.screenRatioY2;
import static com.example.javagame.GameView3.screenRatioX3;
import static com.example.javagame.GameView3.screenRatioY3;

public class flight {
    boolean IsGoingUp = false;
    int toShoot = 0;
    int x, y, width, height, wingCounter = 0, shootCounter = 1;
    Bitmap flight1, flight2, shoot, dead;
    private GameView gameView;
    private GameView2 gameView2;
    private GameView3 gameView3;

    flight(GameView gameView, int screenY, Resources res) {
        this.gameView = gameView;
        flight1 = BitmapFactory.decodeResource(res, R.drawable.spaceship1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.spaceship2);
        width = flight1.getWidth();
        height = flight1.getHeight();
        width /= 8;
        height /= 8;
        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);
        shoot = BitmapFactory.decodeResource(res, R.drawable.shoot);
        shoot = Bitmap.createScaledBitmap(shoot, width, height, false);
        dead = BitmapFactory.decodeResource(res, R.drawable.spaceship1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);
        y = screenY/2;
        x = (int) (64 * screenRatioX);

    }

    flight(GameView2 gameView, int screenY, Resources res) {
        this.gameView2 = gameView;
        flight1 = BitmapFactory.decodeResource(res, R.drawable.spaceship1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.spaceship2);
        width = flight1.getWidth();
        height = flight1.getHeight();
        width /= 8;
        height /= 8;
        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);
        shoot = BitmapFactory.decodeResource(res, R.drawable.shoot);
        shoot = Bitmap.createScaledBitmap(shoot, width, height, false);
        dead = BitmapFactory.decodeResource(res, R.drawable.spaceship1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);
        y = screenY/2;
        x = (int) (64 * screenRatioX);

    }

    flight(GameView3 gameView, int screenY, Resources res) {
        this.gameView3 = gameView;
        flight1 = BitmapFactory.decodeResource(res, R.drawable.spaceship1);
        flight2 = BitmapFactory.decodeResource(res, R.drawable.spaceship2);
        width = flight1.getWidth();
        height = flight1.getHeight();
        width /= 8;
        height /= 8;
        width = (int) (width * screenRatioX);
        height = (int) (width * screenRatioY);
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false);
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);
        shoot = BitmapFactory.decodeResource(res, R.drawable.shoot);
        shoot = Bitmap.createScaledBitmap(shoot, width, height, false);
        dead = BitmapFactory.decodeResource(res, R.drawable.spaceship1);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);
        y = screenY/2;
        x = (int) (64 * screenRatioX);

    }
    Bitmap getFlight() {

        if (toShoot != 0){
            if (shootCounter == 1){
                shootCounter++;
                return shoot;
            }

            shootCounter = 1;
            toShoot--;
            gameView.newBullet();
            return shoot;

        }

        if (wingCounter == 0){
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;
    }

    Bitmap getFlightL2() {

        if (toShoot != 0){
            if (shootCounter == 1){
                shootCounter++;
                return shoot;
            }

            shootCounter = 1;
            toShoot--;
            gameView2.newBullet();
            return shoot;

        }

        if (wingCounter == 0){
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;
    }

    Bitmap getFlightL3() {

        if (toShoot != 0){
            if (shootCounter == 1){
                shootCounter++;
                return shoot;
            }

            shootCounter = 1;
            toShoot--;
            gameView3.newBullet();
            return shoot;

        }

        if (wingCounter == 0){
            wingCounter++;
            return flight1;
        }
        wingCounter--;
        return flight2;


    }
    Rect getCollisionShape (){
        return new Rect(x, y, x + width, y + height);
    }
    Bitmap getDead(){
        return dead;
    }
    Bitmap getDeadL2(){
        return dead;
    }
    Bitmap getDeadL3(){
        return dead;
    }
}
