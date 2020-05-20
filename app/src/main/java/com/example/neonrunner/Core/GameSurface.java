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

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback, HeroHandler {
    public void lastLevelFinished() {
        currentLevelIndex += 1;
        level = gameLevels.get(currentLevelIndex);
        level.main_hero.setHeroHandler(this);
    }

    private GameLevel level;
    private GameThread gameThread;
    private Integer currentLevelIndex = 0;
    private ArrayList<GameLevel> gameLevels;

    public GameSurface(Context context, ArrayList<GameLevel> levels) {
        super(context);
        level = levels.get(0); // устанавливаем уровень игры
        level.main_hero.setHeroHandler(this);
        gameLevels = levels;
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