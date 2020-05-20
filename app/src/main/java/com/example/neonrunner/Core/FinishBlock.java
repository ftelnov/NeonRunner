package com.example.neonrunner.Core;

import android.graphics.Bitmap;
// блок, завершающий игру
public class FinishBlock extends Block {
    public FinishBlock(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
        transparent = true; // делаем его "проходимым"
    }
}
