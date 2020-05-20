package com.example.neonrunner.Core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.neonrunner.R;

import java.util.ArrayList;

public class Hero extends GameObject {
    private Integer jumpHeight = 0; // Оставшаяся высота прыжка
    private Integer ABS_JUMP_HEIGHT = 300; // Максимальная высота прыжка персонажа
    private Integer ABS_JUMP_SPEED = 60;
    private Boolean onEarth = true;
    private Integer ABS_MOVING_SPEED = 10; // Скорость передвижения персонажа
    private Integer RUN_RIGHT = 1; // Константа бега вправо
    private Integer RUN_LEFT = -1; // Константа бега влево
    private Integer running_phase = 0; // Фаза бега
    // Массивы форм для последовательной смены
    private ArrayList<Bitmap> staying_forms_left = new ArrayList<>();
    private ArrayList<Bitmap> staying_forms_right = new ArrayList<>();
    private ArrayList<Bitmap> running_right_forms = new ArrayList<>();
    private ArrayList<Bitmap> running_left_forms = new ArrayList<>();
    ArrayList<FinishBlock> finishBlocks;
    // Переменные смены формы(инкрементируются, а затем сбрасываются, если дошло до конца массива форм
    private Integer form_left_switcher = 0;
    private Integer form_right_switcher = 0;

    // Метод прыжка - проверяет, не в прыжке ли персонаж, затем добавляет дополнительную высоту прыжка
    public void jump() {
        if (jumpHeight <= 0 && onEarth) {
            jumpHeight = ABS_JUMP_HEIGHT;
        }
    }

    public void touchAppears(MotionEvent event) {
        Float ev_x = event.getX();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            running_phase = 0;
            if (ev_x > getAbs_x()) {
                setImage(staying_forms_right.get(0));
            } else {
                setImage(staying_forms_left.get(0));
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (ev_x > getAbs_x()) {
                running_phase = RUN_RIGHT;
            } else {
                running_phase = RUN_LEFT;
            }
        }
        if (event.getPointerCount() != 1) {
            jump();
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
        // Генерируем всевозможные формы движения через контекст. Скалируем по дефолтной ширине/высоте персонажа
        Resources resources = gameLevel.activity.getResources();
        final int width = getWidth();
        final int height = getHeight();
        staying_forms_right.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.hero_stays_right), width, height, false));
        staying_forms_left.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.hero_stays_left), width, height, false));
        running_left_forms.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.hero_go_left_1), width, height, false));
        running_right_forms.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.hero_go_right_1), width, height, false));
        running_left_forms.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.hero_go_left_2), width, height, false));
        running_right_forms.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.hero_go_right_2), width, height, false));
    }

    // Уровень, который его содержит
    private GameLevel gameLevel;

    public Hero(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, image.getWidth(), y); // Создаем как обычный игровой объект, но без скалирования по ширине, чтобы не сжимать
    }

    @Override
    public void update() {
        super.update();
        for (FinishBlock finishBlock : finishBlocks) {
            if (finishBlock.checkIntersection(this)) {
                Log.e("finished", "true");
            }
        }
        onEarth = false;
        this.setAbs_y(getAbs_y() + fallingSpeed);
        for (ArrayList<GameObject> list : gameLevel.getLevel()) {
            for (GameObject object : list) {
                if (object != this && checkIntersection(object) && !object.transparent) {
                    onEarth = true;
                    this.setAbs_y(getAbs_y() - fallingSpeed);
                }
            }
        }
        if (running_phase.equals(RUN_RIGHT)) {
            // Последовательная смена форм из массива
            setImage(running_right_forms.get(form_right_switcher));
            form_right_switcher += 1; // Инкрементим свитчер форм(для последовательной смены)
            if (form_right_switcher >= running_right_forms.size()) {
                form_right_switcher = 0; // обнуляем, если перешло границу массива
            }
            setAbs_x(getAbs_x() + ABS_MOVING_SPEED); // Прибавляем координату
            for (ArrayList<GameObject> list : gameLevel.getLevel()) {
                for (GameObject object : list) {
                    // Перебираем массив объектов, если находим пересечение - удаляем изменения путем откатывания
                    if (object != this && checkIntersection(object) && !object.transparent) {
                        setAbs_x(getAbs_x() - ABS_MOVING_SPEED);
                    }
                }
            }
        } else if (running_phase.equals(RUN_LEFT)) { // Аналогично и с хождением влево
            // Последовательная смена форм из массива
            setImage(running_left_forms.get(form_left_switcher));
            form_left_switcher += 1; // Инкрементим свитчер форм(для последовательной смены)
            if (form_left_switcher >= running_left_forms.size()) {
                form_left_switcher = 0; // обнуляем, если перешло границу массива
            }
            setAbs_x(getAbs_x() - ABS_MOVING_SPEED);
            for (ArrayList<GameObject> list : gameLevel.getLevel()) {
                for (GameObject object : list) {
                    if (object != this && checkIntersection(object)) {
                        setAbs_x(getAbs_x() + ABS_MOVING_SPEED);
                    }
                }
            }
        }
        if (jumpHeight > 0) {
            jumpHeight -= ABS_JUMP_SPEED;
            this.setAbs_y(getAbs_y() - ABS_JUMP_SPEED);
            for (ArrayList<GameObject> list : gameLevel.getLevel()) {
                for (GameObject object : list) {
                    if (object != this && checkIntersection(object) && !object.transparent) {
                        this.setAbs_y(getAbs_y() + ABS_JUMP_SPEED);
                    }
                }
            }
        }
    }
}
