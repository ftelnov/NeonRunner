package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Hero extends GameObject {

    public GameSurface getGameSurface() {
        return gameSurface;
    }

    public void setGameSurface(GameSurface gameSurface) {
        this.gameSurface = gameSurface;
    }

    private GameSurface gameSurface;

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    private GameLevel gameLevel;

    public Hero(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
    }

    @Override
    public void update() {
        super.update();
        this.setPos_y(getPos_y() + 5);
    }
}
