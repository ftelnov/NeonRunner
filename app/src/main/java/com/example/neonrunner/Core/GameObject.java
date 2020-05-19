package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameObject {
    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;

    protected final int width;
    protected final int height;
    private int pos_x;
    private int pos_y;

    public GameObject(Bitmap image, int rowCount, int colCount, int width, int height) {

        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.width = width;
        this.height = height;
        pos_y = rowCount * height;
        pos_x = colCount * width;
    }

    protected Bitmap createSubImageAt(int row, int col) {
        Bitmap subImage = Bitmap.createBitmap(image, col * width, row * height, width, height);
        return subImage;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, pos_x, pos_y, null);
    }

    public int getX() {
        return this.pos_x;
    }

    public int getY() {
        return this.pos_y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
