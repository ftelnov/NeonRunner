package com.example.neonrunner.Core;

import android.content.Context;
import android.content.Intent;
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

import com.example.neonrunner.Activities.FinishGameActivity;
import com.example.neonrunner.Activities.GameActivity;
import com.example.neonrunner.Activities.NeonActivity;
import com.example.neonrunner.R;

import java.util.ArrayList;

// Основной экран игры, отрисовывается через поток
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback, HeroHandler {
    // Вызывается, когда герой закончил последний выданный уровень
    public void lastLevelFinished() {
        currentLevelIndex += 1; // Инкрементируем счетчик уровней
        // Если перевалил за максимальное количество уровней в игре, то завершаем активность
        if (currentLevelIndex >= gameLevels.size()) {
            gameThread.setRunning(false);
            gameThread.interrupt();
            context.runFinnishScreen(); // вызываем финальный экран
            return;
        }
        level = gameLevels.get(currentLevelIndex); // получаем уровень
        level.main_hero.setHeroHandler(this); // устанавливаем для героя это полотно как основное
    }

    private GameLevel level; // текущий лвл игры
    private GameThread gameThread; // поток, контролирующий поведение полотна
    private Integer currentLevelIndex = 0; // текущий индекс уровня
    private GameActivity context; // контекст, в котором полотно было вызвано
    private ArrayList<GameLevel> gameLevels; // уровни игры

    // конструктор полотно, принимает контекст и уровни игры
    public GameSurface(GameActivity context, ArrayList<GameLevel> levels) {
        super(context);
        level = levels.get(0); // устанавливаем уровень игры
        level.main_hero.setHeroHandler(this); // устанавливаем как хэндлер
        gameLevels = levels; // устанавливаем уровни
        this.context = context; // контекст
        this.setFocusable(true); // для того, чтобы проходили клики по области
        this.getHolder().addCallback(this); // подключаем holder
    }

    // вызывается, когда происходит любое движение пальцем по экрану
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        level.main_hero.touchAppears(event); // передает герою информацию о пальце на экране
        return true;
    }

    public void update() {
        invalidate(); // открываем для изменения
        Canvas canvas = getHolder().lockCanvas(null); // получаем очищенное полотно
        this.level.update(); // обновляем уровень
        this.draw(canvas); // отрисовываем на полотне уровень
        getHolder().unlockCanvasAndPost(canvas);
    }

    // Главный метод отрисовки, делегирует задачу по отрисовке уровню
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.level.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        this.gameThread = new GameThread(this); // подключаем поток
        this.gameThread.setRunning(true); // запускаем
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

}