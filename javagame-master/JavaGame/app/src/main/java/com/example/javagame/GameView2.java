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

public class GameView2 extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying, isGameOver = false, Progress = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX2, screenRatioY2;
    private Paint paint;
    private Cats2[] cats;
    private SharedPreferences prefs;
    private Random random;
    private List<Bullet2> bullets;
    private flight flight;
    private GameActivity2 activity;
    private Sky sky1, sky2;

    public GameView2(GameActivity2 activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX2 = 1920f / screenX;
        screenRatioY2 = 1080f / screenY;
        sky1 = new Sky(screenX, screenY, getResources());
        sky2 = new Sky(screenX, screenY, getResources());
        flight = new flight(this, screenY, getResources());
        bullets = new ArrayList<>();
        sky2.x = screenX;

        paint = new Paint();
        paint.setTextSize(120);
        paint.setColor(Color.WHITE);

        cats = new Cats2[1];
        for (int i = 0;i < 1;i++) {

            Cats2 cat = new Cats2(getResources());
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
        sky1.x -= 10 * screenRatioX2;
        sky2.x -= 10 * screenRatioX2;
        if (sky1.x + sky1.sky.getWidth() < 0){
            sky1.x = screenX;

        }
        if (sky2.x + sky2.sky.getWidth() < 0){
            sky2.x = screenX;

        }

        if (flight.IsGoingUp){
            flight.y -= 30 * screenRatioY2;
        }
        else{
            flight.y += 30 * screenRatioY2;
        }

        if (flight.y < 0){
            flight.y = 0;
        }

        if (flight.y >= screenY - flight.height){
            flight.y = screenY - flight.height;
        }


        List<Bullet2> trash =  new ArrayList<>();

        for (Bullet2 bullet : bullets){

            if (bullet.x > screenX){
                trash.add(bullet);
            }

            bullet.x += 50 * screenRatioX2;

            for (Cats2 cat : cats){
                if (Rect.intersects(cat.getCollisionShape(), bullet.getCollisionShape())){
                    score++;
                    cat.x = -500;
                    bullet.x = screenX + 500;
                    cat.wasShot = true;
                    if(score == 20){
                        Progress = true;
                    }
                }
            }

        }

        for (Bullet2 bullet : trash){
            bullets.remove(bullet);
        }

        for (Cats2 cat : cats) {

            cat.x -= cat.speed;
            if (cat.x + cat.width < 0){

                if (!cat.wasShot){
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX2);
                cat.speed = random.nextInt(bound);

                if (cat.speed < 10 * screenRatioX2){
                    cat.speed = (int) (12 * screenRatioX2);
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

            for (Cats2 cat : cats){
                canvas.drawBitmap(cat.getCat2(), cat.x, cat.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(flight.getDeadL2(), flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;

            }

            if(Progress){
                waitBeforeExitingProgression();
            }

            canvas.drawBitmap(flight.getFlightL2(), flight.x, flight.y, paint);

            for (Bullet2 bullet : bullets){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitBeforeExitingProgression() {
        try {
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, GameActivity3.class));
            activity.finish();
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
        Bullet2 bullet = new Bullet2(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);

    }
}
