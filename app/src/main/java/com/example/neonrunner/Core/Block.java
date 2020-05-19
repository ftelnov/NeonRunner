package com.example.neonrunner.Core;

import android.graphics.Bitmap;

public class Block extends GameObject {

    public Boolean getTransparent() {
        return transparent;
    }

    public void setTransparent(Boolean transparent) {
        this.transparent = transparent;
    }

    private Boolean transparent = false;

    public Block(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
    }
}
