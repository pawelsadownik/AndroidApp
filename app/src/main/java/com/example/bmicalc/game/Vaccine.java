package com.example.bmicalc.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.bmicalc.R;

import static com.example.bmicalc.game.GameView.screenRatioX;
import static com.example.bmicalc.game.GameView.screenRatioY;


public class Vaccine {

    int x, y, width, height;
    Bitmap bullet;

    Vaccine(Resources res) {

        bullet = BitmapFactory.decodeResource(res, R.drawable.vaccine);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);

    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

}
