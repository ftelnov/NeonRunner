package com.example.neonrunner.Core;

import android.util.Log;

import java.util.ArrayList;

// Камера для слежки за объектами
public class BoundedCamera {
    public GameObject getAim() {
        return aim;
    }

    public void setAim(GameObject aim) {
        this.aim = aim;
    }

    GameObject aim; // цель, за которой нужно следить (любой объект)
    GameLevel level; // уровень, на котором находится этот объект

    // Конструктор, принимает параметры для вышеуказанных атрибутов
    public BoundedCamera(GameObject bounded_object, GameLevel level) {
        aim = bounded_object;
        this.level = level;
    }

    // синхронизируем камеру с целью
    public void syncWithAim() {
        int offset_x, offset_y; // расстояния от цели
        offset_x = level.display_width / 2 - aim.getAbs_x(); // получаем смещение от центра экрана данного объекта по x
        offset_y = level.display_height / 2 - aim.getAbs_y(); // получаем смещение от центра экрана данного объекта по y
        for (ArrayList<GameObject> level_line : level.getLevel()) {
            for (GameObject object : level_line) {
                object.move(offset_x, offset_y); // двигаем каждый объект на определенное смещение
            }
        }
    }
}
