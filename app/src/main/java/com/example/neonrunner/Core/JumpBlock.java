package com.example.neonrunner.Core;

import android.graphics.Bitmap;

public class JumpBlock extends Block {

    static Integer jumpSpeedBoost = 100; // добавочная скорость прыжка, если персонаж прыгает с этого блока
    static Integer jumpHeightBoost = 1000; // добавочная высота прыжка, если персонаж прыгает с этого блока

    public JumpBlock(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
    }
}
