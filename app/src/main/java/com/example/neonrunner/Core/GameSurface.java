package com.example.neonrunner.Core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.neonrunner.R;

import java.util.ArrayList;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameLevel level;
    private GameThread gameThread;

    public GameSurface(Context context, GameLevel _level) {
        super(context);
        level = _level; // устанавливаем уровень игры
        this.setFocusable(true); // для того, чтобы проходили клики по области
        this.getHolder().addCallback(this); // подключаем holder
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        level.main_hero.touchAppears(event);
        return true;
    }

    public void update() {
        invalidate(); // открываем для изменения
        Canvas canvas = getHolder().lockCanvas(null); // получаем очищенное полотно
        this.level.update(); // обновляем уровень
        this.draw(canvas); // отрисовываем на полотне уровень
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.level.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        this.gameThread = new GameThread(this);
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