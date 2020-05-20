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
    private Integer jumpHeight = 300; // Оставшаяся высота прыжка
    private Integer jumpSpeed = 60;
    private Integer ABS_JUMP_HEIGHT = 300; // Максимальная высота прыжка персонажа
    private Integer ABS_JUMP_SPEED = 60;
    private Boolean onEarth = true;

    public Integer getMax_falling_height() {
        return max_falling_height;
    }

    public void setMax_falling_height(Integer max_falling_height) {
        this.max_falling_height = max_falling_height;
    }

    @Override
    public void setAbs_y(Integer abs_y) {
        max_falling_height += getAbs_y() - abs_y;
        if (max_falling_height <= 0) {
            heroHandler.died();
        }
        super.setAbs_y(abs_y);

    }

    private Integer max_falling_height = 0;
    private Integer ABS_MOVING_SPEED = 20; // Скорость передвижения персонажа
    private Integer RUN_RIGHT = 1; // Константа бега вправо
    private Integer RUN_LEFT = -1; // Константа бега влево
    private Integer running_phase = 0; // Фаза бега
    // Массивы форм для последовательной смены
    private ArrayList<Bitmap> staying_forms_left = new ArrayList<>();
    private ArrayList<Bitmap> staying_forms_right = new ArrayList<>();
    private ArrayList<Bitmap> running_right_forms = new ArrayList<>();
    private ArrayList<Bitmap> running_left_forms = new ArrayList<>();
    ArrayList<GameObject> nTransparents; // Массив непрозрачных блоков
    ArrayList<FinishBlock> finishBlocks; // Массив финальных блоков(на след. уровень)
    ArrayList<JumpBlock> jumpBlocks; // блоки на которых можно прыгать лучше
    GameObject underblock;

    public HeroHandler getHeroHandler() {
        return heroHandler;
    }

    public void setHeroHandler(HeroHandler heroHandler) {
        this.heroHandler = heroHandler;
    }

    HeroHandler heroHandler;
    // Переменные смены формы(инкрементируются, а затем сбрасываются, если дошло до конца массива форм
    private Integer form_left_switcher = 0;
    private Integer form_right_switcher = 0;

    // Метод прыжка - проверяет, не в прыжке ли персонаж, затем добавляет дополнительную высоту прыжка
    public void jump() {
        if (jumpHeight <= 0 && onEarth) {
            // Если под нами прыжковый блок, то увеличиваем амплитуду прыжка
            if (underblock != null && underblock.getClass() == JumpBlock.class) {
                jumpHeight = JumpBlock.jumpHeightBoost;
                jumpSpeed = JumpBlock.jumpSpeedBoost;
            } else {
                jumpHeight = ABS_JUMP_HEIGHT;
                jumpSpeed = ABS_JUMP_SPEED;
            }
        }
    }

    // Обрабатываем все касание экрана от юзера
    public void touchAppears(MotionEvent event) {
        Float ev_x = event.getX(); // получаем абсолютную позицию касания
        if (event.getAction() == MotionEvent.ACTION_UP) {
            running_phase = 0; // если кнопку отпустили, выключаем бег
            if (ev_x > getAbs_x()) {
                setImage(staying_forms_right.get(0)); // И поворачиваемся либо левой стороной, либо правой
            } else {
                setImage(staying_forms_left.get(0));
            }
            // Аналогично для опускания пальца на экран
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (ev_x > getAbs_x()) {
                running_phase = RUN_RIGHT;
            } else {
                running_phase = RUN_LEFT;
            }
        }
        // Если появился второй указатель(палец), прыгаем
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

    // проверяем, касаемся ли мы какого-то непрозрачного блока
    private Boolean checkIntersectionWithUntranparents() {
        // перебираем все непрозрачные блоки
        for (GameObject object : nTransparents) {
            if (checkIntersection(object)) return Boolean.TRUE; // Возвращаем истину, если касаемся
        }
        return Boolean.FALSE; // в противном случае - ложь
    }

    // основной метод обновления персонажа
    @Override
    public void update() {
        super.update();
        // проверяем на финиширование персонажем уровня
        for (FinishBlock finishBlock : finishBlocks) {
            if (finishBlock.checkIntersection(this)) {
                heroHandler.lastLevelFinished();
            }
        }
        onEarth = false; // устанавливаем, что он не на земле(для будущей проверки)
        this.setAbs_y(getAbs_y() + fallingSpeed); // искусственно увеличиваем его координату по y на величину скорости падения
        for (GameObject object : nTransparents) {
            if (checkIntersection(object)) {
                onEarth = true; // если пересеклись с непрозрачными блоками, возвращаемся на предидущую позицию
                underblock = object; // Устанавливаем нижним блоком пересекающийся с нами
                this.setAbs_y(getAbs_y() - fallingSpeed);
            }
        }
        if (!onEarth) {
            underblock = null;
        }
        if (running_phase.equals(RUN_RIGHT)) {
            // Последовательная смена форм из массива
            setImage(running_right_forms.get(form_right_switcher));
            form_right_switcher += 1; // Инкрементим свитчер форм(для последовательной смены)
            if (form_right_switcher >= running_right_forms.size()) {
                form_right_switcher = 0; // обнуляем, если перешло границу массива
            }
            setAbs_x(getAbs_x() + ABS_MOVING_SPEED); // Прибавляем координату
            if (checkIntersectionWithUntranparents()) {
                // Перебираем массив объектов, если находим пересечение - удаляем изменения путем откатывания
                setAbs_x(getAbs_x() - ABS_MOVING_SPEED);
            }
        } else if (running_phase.equals(RUN_LEFT)) { // Аналогично и с хождением влево
            // Последовательная смена форм из массива
            setImage(running_left_forms.get(form_left_switcher));
            form_left_switcher += 1; // Инкрементим свитчер форм(для последовательной смены)
            if (form_left_switcher >= running_left_forms.size()) {
                form_left_switcher = 0; // обнуляем, если перешло границу массива
            }
            setAbs_x(getAbs_x() - ABS_MOVING_SPEED);
            if (checkIntersectionWithUntranparents()) {
                setAbs_x(getAbs_x() + ABS_MOVING_SPEED);
            }
        }
        // Если высота полета еще не закончилась(еще в полете)
        if (jumpHeight > 0) {
            // отрезаем от высоты по размеру скорости полета
            jumpHeight -= jumpSpeed;
            this.setAbs_y(getAbs_y() - jumpSpeed);
            // Аналогично проводим проверку на пересечение с верхними блоками
            if (checkIntersectionWithUntranparents()) {
                this.setAbs_y(getAbs_y() + jumpSpeed);
                jumpHeight = 0;
            }
        }
    }
}
