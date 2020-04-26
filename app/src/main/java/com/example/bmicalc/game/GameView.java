package com.example.bmicalc.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.example.bmicalc.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Virus[] viruses;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Vaccine> vaccines;
    private int sound;
    private Running running;
    private GameActivity activity;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shot, 1);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        running = new Running(this, screenY, getResources());

        vaccines = new ArrayList<>();

        background2.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        viruses = new Virus[4];

        for (int i = 0;i < 4;i++) {

            Virus virus = new Virus(getResources());
            viruses[i] = virus;

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

    private void update () {

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }

        if (running.isGoingUp)
            running.y -= 30 * screenRatioY;
        else
            running.y += 30 * screenRatioY;

        if (running.y < 0)
            running.y = 0;

        if (running.y >= screenY - running.height)
            running.y = screenY - running.height;

        List<Vaccine> trash = new ArrayList<>();

        for (Vaccine vaccine : vaccines) {

            if (vaccine.x > screenX)
                trash.add(vaccine);

            vaccine.x += 50 * screenRatioX;

            for (Virus virus : viruses) {

                if (Rect.intersects(virus.getCollisionShape(),
                        vaccine.getCollisionShape())) {

                    score++;
                    virus.x = -500;
                    vaccine.x = screenX + 500;
                    virus.wasShot = true;

                }

            }

        }

        for (Vaccine vaccine : trash)
            vaccines.remove(vaccine);

        for (Virus virus : viruses) {

            virus.x -= virus.speed;

            if (virus.x + virus.width < 0) {

                if (!virus.wasShot) {
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                virus.speed = random.nextInt(bound);

                if (virus.speed < 10 * screenRatioX)
                    virus.speed = (int) (10 * screenRatioX);

                virus.x = screenX;
                virus.y = random.nextInt(screenY - virus.height);

                virus.wasShot = false;
            }

            if (Rect.intersects(virus.getCollisionShape(), running.getCollisionShape())) {

                isGameOver = true;
                return;
            }

        }

    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            for (Virus virus : viruses)
                canvas.drawBitmap(virus.getBird(), virus.x, virus.y, paint);

            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(running.getDead(), running.x, running.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting ();
                return;
            }

            canvas.drawBitmap(running.getFlight(), running.x, running.y, paint);

            for (Vaccine vaccine : vaccines)
                canvas.drawBitmap(vaccine.bullet, vaccine.x, vaccine.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, StartGameActivity.class));
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

    private void sleep () {
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    running.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                running.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    running.toShoot++;
                break;
        }

        return true;
    }

    public void newBullet() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);

        Vaccine vaccine = new Vaccine(getResources());
        vaccine.x = running.x + running.width;
        vaccine.y = running.y + (running.height / 2);
        vaccines.add(vaccine);

    }
}
