package com.example.bmicalc.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.bmicalc.R;

import static com.example.bmicalc.game.GameView.screenRatioX;
import static com.example.bmicalc.game.GameView.screenRatioY;


public class Virus {

    public int speed = 20;
    public boolean wasShot = true;
    int x = 0, y, width, height, birdCounter = 1;
    Bitmap bird1, bird2, bird3, bird4;

    Virus(Resources res) {

        bird1 = BitmapFactory.decodeResource(res, R.drawable.virus);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.virus);
        bird3 = BitmapFactory.decodeResource(res, R.drawable.virus);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.virus);

        width = bird1.getWidth();
        height = bird1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false);
        bird3 = Bitmap.createScaledBitmap(bird3, width, height, false);
        bird4 = Bitmap.createScaledBitmap(bird4, width, height, false);

        y = -height;
    }

    Bitmap getBird () {

        if (birdCounter == 1) {
            birdCounter++;
            return bird1;
        }

        if (birdCounter == 2) {
            birdCounter++;
            return bird2;
        }

        if (birdCounter == 3) {
            birdCounter++;
            return bird3;
        }

        birdCounter = 1;

        return bird4;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}