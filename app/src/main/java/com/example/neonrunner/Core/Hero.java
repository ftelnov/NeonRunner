package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class Hero extends GameObject {
    private Integer jumpHeight = 0; // Оставшаяся высота прыжка
    private Integer ABS_JUMP_HEIGHT = 50; // Максимальная высота прыжка персонажа

    // Метод прыжка - проверяет, не в прыжке ли персонаж, затем добавляет дополнительную высоту прыжка
    public void jump() {
        if (jumpHeight == 0) {
            jumpHeight -= ABS_JUMP_HEIGHT;
        }
    }

    public GameSurface getGameSurface() {
        return gameSurface;
    }

    public void setGameSurface(GameSurface gameSurface) {
        this.gameSurface = gameSurface;
    }

    // Экран, на котором отрисовывается
    private GameSurface gameSurface;

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    // Уровень, который его контролирует
    private GameLevel gameLevel;

    public Hero(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, image.getWidth(), y); // Создаем как обычный игровой объект, но без скалирования по ширине, чтобы не сжимать
    }

    @Override
    public void update() {
        super.update();
        this.setAbs_y(getAbs_y() + fallingSpeed);
        for (ArrayList<GameObject> list : gameLevel.getLevel()) {
            for (GameObject object : list) {
                if (object != this && checkIntersection(object)) {
                    this.setAbs_y(getAbs_y() - fallingSpeed);
                }
            }
        }
    }
}
