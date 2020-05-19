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
    }

    protected Bitmap createSubImageAt(int row, int col) {
        Bitmap subImage = Bitmap.createBitmap(image, this.marginLeft + col * width, this.marginTop + row * height, width, height);
        return subImage;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, this.marginLeft + pos_x, this.marginTop + pos_y, null);
    }

    public void update() {

    }

    public void move(int offset_x, int offset_y) {
        this.pos_x += offset_x;
        this.pos_y += offset_y;
    }
}
