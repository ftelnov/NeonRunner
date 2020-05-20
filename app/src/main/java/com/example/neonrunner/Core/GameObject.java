package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

// главный класс игры, от него наследуются все другие объекты. Умеет отрисовываться на полотне и обновляться
public class GameObject {
    public void setImage(Bitmap image) {
        this.image = image;
    }

    // функция отбрасывания на начальное положение
    public void regenerate() {
        this.abs_x = start_pos_x;
        this.abs_y = start_pos_y;
    }

    private Bitmap image; // текущее изображение объекта
    Integer fallingSpeed = 20; // скорость падения, использующаяся в движущихся блоках

    Boolean transparent = false; // прозрачен ли блок?

    // получаем прямоугольник, ограничивающий персонажа
    public Rect getRect() {
        return new Rect(abs_x, abs_y, abs_x + width, abs_y + height);
    }

    // Проверяем пересечение по прямоугольникам
    boolean checkIntersection(GameObject object) {
        return this.getRect().intersect(object.getRect());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private final int width;
    private final int height;
    private int start_pos_x;
    private int start_pos_y;

    Integer getAbs_x() {
        return abs_x;
    }

    void setAbs_x(Integer abs_x) {
        this.abs_x = abs_x;
    }

    Integer getAbs_y() {
        return abs_y;
    }

    public void setAbs_y(Integer abs_y) {
        this.abs_y = abs_y;
    }

    // абсолютное положение элемента на экране
    private Integer abs_x;
    private Integer abs_y;


    // инициализируем, передав в параметры положение в столбцах/строке, ширину/высоту предполагаемого элемента
    public GameObject(Bitmap image, int rowCount, int colCount, int width, int height) {
        this.image = Bitmap.createScaledBitmap(image, width, height, false); // Скалируем изображение под текущие параметры
        this.width = width;
        this.height = height;
        abs_x = colCount * width;
        abs_y = rowCount * height;
        start_pos_x = abs_x;
        start_pos_y = abs_y;
    }

    // отрисовываем на полотне элемент
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, abs_x, abs_y, null);
    }

    public void update() {

    }

    // передвигаемся по полотну через смещения
    public void move(int offset_x, int offset_y) {
        this.abs_x += offset_x;
        this.abs_y += offset_y;
    }
}
