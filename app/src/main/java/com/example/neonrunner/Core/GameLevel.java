package com.example.neonrunner.Core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonrunner.Activities.NeonActivity;
import com.example.neonrunner.R;

import java.util.ArrayList;
import java.util.Arrays;

// Класс, отвечающий за отрисовку и обновления элементов уровня
public class GameLevel {
    Integer display_height; // высота в пикселях дисплея устройства
    Integer display_width; // ширина в пикселях дисплея устройства
    BoundedCamera camera; // основная камера, которая следит за героем
    Hero main_hero; // сам герой
    NeonActivity activity; // активность, в которой запускается уровень
    Integer BLOCK_WIDTH = 100;
    Integer BLOCK_HEIGHT = 100;

    public ArrayList<ArrayList<GameObject>> getLevel() {
        return level;
    }

    public void setLevel(ArrayList<ArrayList<GameObject>> level) {
        this.level = level;
    }

    private ArrayList<ArrayList<GameObject>> level = new ArrayList<>(); // сам уровень

    public GameLevel(ArrayList<String> raw_level, NeonActivity activity) {
        int level_index = 0;
        this.activity = activity; // получаем и устанавливаем активность
        // битмапы всех элементов
        Bitmap block = BitmapFactory.decodeResource(activity.getResources(), R.drawable.block_main);
        Bitmap hero = BitmapFactory.decodeResource(activity.getResources(), R.drawable.hero_stays_right);
        Bitmap finish = BitmapFactory.decodeResource(activity.getResources(), R.drawable.finish_icon);
        Bitmap jump_block = BitmapFactory.decodeResource(activity.getResources(), R.drawable.jump_block);
        ArrayList<FinishBlock> finishes = new ArrayList<>(); // блоки, на которых персонаж может завершить лвл
        ArrayList<GameObject> nTransparents = new ArrayList<>(); // прозрачные, проваливающиеся блоки
        ArrayList<JumpBlock> jumpBlocks = new ArrayList<>(); // блоки, на которых персонаж может прыгать лучше и выше
        // Берем высоту и ширину уровня
        display_height = activity.getResources().getDisplayMetrics().heightPixels;
        display_width = activity.getResources().getDisplayMetrics().widthPixels;
        boolean hero_occurs = false; // запомнить, на каком уровне был обнаружен герой (с целью подсчета максимальной высоты падения;
        Integer levels_from_hero_counter = 0; // считаем уровни от первого обнаружения героя
        for (String line : raw_level) {
            char[] arr = line.toCharArray();
            ArrayList<GameObject> temp_objects = new ArrayList<>();
            if (hero_occurs) levels_from_hero_counter += 1;

            for (int i = 0; i < line.length(); ++i) {
                switch (arr[i]) {
                    // Генерируем элемент, опираясь на текущий уровень
                    case '#': {
                        GameObject object = new GameObject(block, level_index, i, BLOCK_WIDTH, BLOCK_HEIGHT);
                        temp_objects.add(object);
                        nTransparents.add(object);
                        break;
                    }
                    case 'H': {
                        Hero _hero = new Hero(hero, level_index, i, BLOCK_WIDTH, BLOCK_HEIGHT);
                        _hero.setGameLevel(this);
                        hero_occurs = true;
                        // Устанавливаем камеру на персонажа
                        camera = new BoundedCamera(_hero, this);
                        main_hero = _hero; // Сохраняем на него ссылку
                        temp_objects.add(_hero);
                        break;
                    }
                    case 'X': {
                        FinishBlock finishBlock = new FinishBlock(finish, level_index, i, BLOCK_WIDTH, BLOCK_HEIGHT);
                        finishes.add(finishBlock);
                        temp_objects.add(finishBlock);
                        break;
                    }
                    case 'J': {
                        JumpBlock jumpBlock = new JumpBlock(jump_block, level_index, i, BLOCK_WIDTH, BLOCK_HEIGHT);
                        jumpBlocks.add(jumpBlock);
                        temp_objects.add(jumpBlock);
                        nTransparents.add(jumpBlock);
                        break;
                    }
                }
            }
            level.add(temp_objects);
            level_index++;
        }
        camera.syncWithAim(); // синхронизируем все объекты с камерой
        // сохраняем ссылки на особые блоки в персонаже
        main_hero.finishBlocks = finishes;
        main_hero.nTransparents = nTransparents;
        main_hero.jumpBlocks = jumpBlocks;
    }

    // отрисовываем на полотне уровень
    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> level_line : level) {
            for (GameObject object : level_line) {
                object.draw(canvas);
            }
        }
    }

    // обновляем уровень, синхронизируя камеру
    public void update() {
        for (ArrayList<GameObject> level_line : level) {
            for (GameObject object : level_line) {
                object.update();
            }
        }
        camera.syncWithAim();
    }
}
