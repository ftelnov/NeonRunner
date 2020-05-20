package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameObject {
    private Bitmap image;
    Integer fallingSpeed = 10;

    private final int rowCount;
    private final int colCount;

    public Rect getRect() {
        rect = new Rect(abs_x, abs_y, abs_x + width, abs_y + height);
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    private Rect rect;

    boolean checkIntersection(GameObject object) {
        return this.getRect().intersect(object.getRect());
    }

    private final int width;
    private final int height;

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

    private Integer abs_x;
    private Integer abs_y;

    public GameObject(Bitmap image, int rowCount, int colCount, int width, int height) {
        this.image = Bitmap.createScaledBitmap(image, width, height, false);
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.width = width;
        this.height = height;
        abs_x = colCount * width;
        abs_y = rowCount * height;
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, abs_x, abs_y, null);
    }

    public void update() {

    }

    public void move(int offset_x, int offset_y) {
        this.abs_x += offset_x;
        this.abs_y += offset_y;
    }
}
