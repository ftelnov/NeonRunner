package com.example.neonrunner.Core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running; // Поток запущен?
    private GameSurface gameSurface; // Главный экран игры

    public GameThread(GameSurface gameSurface) {
        this.gameSurface = gameSurface;
    }

    @Override
    public void run() {
        while (running) {
            gameSurface.update(); // обновляем экран отрисовки
            try {
                sleep(2); // каждые 2 мс
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void setRunning(boolean running) {
        this.running = running;
    }
}