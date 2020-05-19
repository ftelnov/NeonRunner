package com.example.neonrunner.Core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.neonrunner.R;

import java.util.ArrayList;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameLevel level;
    private GameThread gameThread;
    private Hero hero;
    private ArrayList<Block> blocks;

    public GameSurface(Context context, GameLevel levelt) {
        super(context);
        level = levelt;
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);
        // Sét callback.
        this.getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            int x = (int) event.getX();
//            int y = (int) event.getY();
//
//            int movingVectorX = x - this.hero.getX();
//            int movingVectorY = y - this.hero.getY();
//
//            this.hero.setMovingVector(movingVectorX, movingVectorY);
//            return true;
//        }
//        return false;
        return false;
    }

    public void update() {
        this.level.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.level.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }

}