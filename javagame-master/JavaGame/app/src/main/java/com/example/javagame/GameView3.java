package com.example.javagame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView3 extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX3, screenRatioY3;
    private Paint paint;
    private CatBoss[] cats;
    private SharedPreferences prefs;
    private Random random;
    private List<Bullet3> bullets;
    private flight flight;
    private GameActivity3 activity;
    private Sky sky1, sky2;

    public GameView3(GameActivity3 activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX3 = 1920f / screenX;
        screenRatioY3 = 1080f / screenY;
        sky1 = new Sky(screenX, screenY, getResources());
        sky2 = new Sky(screenX, screenY, getResources());
        flight = new flight(this, screenY, getResources());
        bullets = new ArrayList<>();
        sky2.x = screenX;

        paint = new Paint();
        paint.setTextSize(120);
        paint.setColor(Color.WHITE);

        cats = new CatBoss[1];
        for (int i = 0;i < 1;i++) {

            CatBoss cat = new CatBoss(getResources());
            cats[i] = cat;

        }
        random = new Random();
    }

    @Override
    public void run() {

        while (isPlaying) {
            update ();
            draw ();
            sleep ();

        }

    }

    private void update (){
        sky1.x -= 10 * screenRatioX3;
        sky2.x -= 10 * screenRatioX3;
        if (sky1.x + sky1.sky.getWidth() < 0){
            sky1.x = screenX;

        }
        if (sky2.x + sky2.sky.getWidth() < 0){
            sky2.x = screenX;

        }

        if (flight.IsGoingUp){
            flight.y -= 30 * screenRatioY3;
        }
        else{
            flight.y += 30 * screenRatioY3;
        }

        if (flight.y < 0){
            flight.y = 0;
        }

        if (flight.y >= screenY - flight.height){
            flight.y = screenY - flight.height;
        }


        List<Bullet3> trash =  new ArrayList<>();

        for (Bullet3 bullet : bullets){

            if (bullet.x > screenX){
                trash.add(bullet);
            }

            bullet.x += 50 * screenRatioX3;

            for (CatBoss cat : cats){
                if (Rect.intersects(cat.getCollisionShape(), bullet.getCollisionShape())){
                    cat.catH--;
                    //cat.x = -500;
                    bullet.x = screenX + 500;
                    if(cat.catH == 0){
                        score+= 130;
                        isGameOver = true;
                        cat.wasShot = true;
                    }
                }
            }

        }

        for (Bullet3 bullet : trash){
            bullets.remove(bullet);
        }

        for (CatBoss cat : cats) {

            cat.x -= cat.speed;
            if (cat.x + cat.width < 0){

                if (!cat.wasShot){
                    isGameOver = true;
                    return;
                }

                int bound = 5;
                cat.speed = random.nextInt(bound);

                if (cat.speed < 2){
                    cat.speed = 3;
                }
                cat.x = screenX;
                cat.y = random.nextInt(screenY - cat.height);

                cat.wasShot = false;

            }
            if (Rect.intersects(cat.getCollisionShape(), flight.getCollisionShape())){
                isGameOver = true;
                return;
            }
        }

    }

    private void draw (){

        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(sky1.sky, sky1.x, sky1.y, paint);
            canvas.drawBitmap(sky2.sky, sky2.x, sky2.y, paint);

            for (CatBoss cat : cats){
                canvas.drawBitmap(cat.getCatBoss(), cat.x, cat.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(flight.getDeadL3(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;

            }

            canvas.drawBitmap(flight.getFlightL3(), flight.x, flight.y, paint);

            for (Bullet3 bullet : bullets){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();

        }
    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    flight.IsGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.IsGoingUp = false;
                if (event.getX() > screenX / 2)
                    flight.toShoot++;
                break;
        }
        return true;
    }

    public void newBullet() {
        Bullet3 bullet = new Bullet3(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);

    }
}
