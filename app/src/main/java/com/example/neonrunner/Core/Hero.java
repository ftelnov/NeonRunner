package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Hero extends GameObject {

    public GameSurface getGameSurface() {
        return gameSurface;
    }

    public void setGameSurface(GameSurface gameSurface) {
        this.gameSurface = gameSurface;
    }

    private GameSurface gameSurface;

    public Hero(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
    }

    public void draw(GameSurface surface){

    }
}
