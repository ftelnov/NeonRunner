package com.example.neonrunner.Core;

import android.graphics.Bitmap;
// Класс, наследующийся от объекта, нужен для построения хорошей иерархии
public class Block extends GameObject {

    public Block(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
    }
}
