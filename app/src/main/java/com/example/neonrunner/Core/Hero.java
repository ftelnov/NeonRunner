package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Hero extends GameObject {
    private Integer jumpHeight = 0; // Оставшаяся высота прыжка
    private Integer ABS_JUMP_HEIGHT = 50; // Максимальная высота прыжка персонажа
    private Integer ABS_MOVING_SPEED = 5; // Скорость передвижения персонажа
    private Integer RUN_RIGHT = 1;
    private Integer RUN_LEFT = -1;
    private Integer running_phase = 0;
    private Bitmap staying_form;
    private ArrayList<Bitmap> running_right_forms = new ArrayList<>();
    private ArrayList<Bitmap> running_left_forms = new ArrayList<>();

    // Метод прыжка - проверяет, не в прыжке ли персонаж, затем добавляет дополнительную высоту прыжка
    public void jump() {
        if (jumpHeight == 0) {
            jumpHeight -= ABS_JUMP_HEIGHT;
        }
    }

    public void touchAppears(MotionEvent event) {
        Float ev_x = event.getX();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            running_phase = 0;
        } else {
            if (ev_x > getAbs_x()) {
                running_phase = RUN_RIGHT;
            } else {
                running_phase = RUN_LEFT;
            }
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
        // Генерируем всевозможные формы движения через контекст

    }

    // Уровень, который его контролирует
    private GameLevel gameLevel;

    public Hero(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, image.getWidth(), y); // Создаем как обычный игровой объект, но без скалирования по ширине, чтобы не сжимать
    }

    @Override
    public void update() {
        super.update();
        if (running_phase.equals(RUN_RIGHT)) {
            setAbs_x(getAbs_x() + ABS_MOVING_SPEED);
            for (ArrayList<GameObject> list : gameLevel.getLevel()) {
                for (GameObject object : list) {
                    if (object != this && checkIntersection(object)) {
                        setAbs_x(getAbs_x() - ABS_MOVING_SPEED);
                    }
                }
            }
        } else if (running_phase.equals(RUN_LEFT)) {
            setAbs_x(getAbs_x() - ABS_MOVING_SPEED);
            for (ArrayList<GameObject> list : gameLevel.getLevel()) {
                for (GameObject object : list) {
                    if (object != this && checkIntersection(object)) {
                        setAbs_x(getAbs_x() + ABS_MOVING_SPEED);
                    }
                }
            }
        }

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
