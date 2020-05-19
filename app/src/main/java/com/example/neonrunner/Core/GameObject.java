package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GameObject {
    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;
    int marginLeft = 500;
    int marginTop = 500;

    protected final int width;
    protected final int height;

    public Integer getAbs_x() {
        return abs_x;
    }

    public void setAbs_x(Integer abs_x) {
        this.abs_x = abs_x;
    }

    public Integer getAbs_y() {
        return abs_y;
    }

    public void setAbs_y(Integer abs_y) {
        this.abs_y = abs_y;
    }

    private Integer abs_x;
    private Integer abs_y;

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    private int pos_x;
    private int pos_y;

    public GameObject(Bitmap image, int rowCount, int colCount, int width, int height) {

        this.image = Bitmap.createScaledBitmap(image, width, height, false);
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.width = width;
        this.height = height;
        pos_y = rowCount * height;
        pos_x = colCount * width;
        abs_x = pos_x + this.marginLeft;
        abs_y = pos_y + this.marginTop;
    }

    protected Bitmap createSubImageAt(int row, int col) {
        Bitmap subImage = Bitmap.createBitmap(image, this.marginLeft + col * width, this.marginTop + row * height, width, height);
        return subImage;
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
