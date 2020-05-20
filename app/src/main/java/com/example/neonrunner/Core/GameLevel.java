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

public class GameLevel {
    Integer display_height;
    Integer display_width;
    BoundedCamera camera;
    Hero main_hero;

    public ArrayList<ArrayList<GameObject>> getLevel() {
        return level;
    }

    public void setLevel(ArrayList<ArrayList<GameObject>> level) {
        this.level = level;
    }

    private ArrayList<ArrayList<GameObject>> level = new ArrayList<>();

    public GameLevel(ArrayList<String> raw_level, NeonActivity activity) {
        int level_index = 0;
        Bitmap block = BitmapFactory.decodeResource(activity.getResources(), R.drawable.block_main);
        Bitmap hero = BitmapFactory.decodeResource(activity.getResources(), R.drawable.hero_stays_right);
        display_height = activity.getResources().getDisplayMetrics().heightPixels;
        display_width = activity.getResources().getDisplayMetrics().widthPixels;
        for (String line : raw_level) {
            char[] arr = line.toCharArray();
            ArrayList<GameObject> temp_objects = new ArrayList<>();

            for (int i = 0; i < line.length(); ++i) {
                if (arr[i] == '#') {
                    temp_objects.add(new GameObject(block, level_index, i, 100, 100));
                } else if (arr[i] == 'H') {
                    Hero _hero = new Hero(hero, level_index, i, 100, 100);
                    _hero.setGameLevel(this);
                    camera = new BoundedCamera(_hero, this);
                    main_hero = _hero;
                    temp_objects.add(_hero);
                }
            }
            level.add(temp_objects);
            level_index++;
        }
        camera.syncWithAim();
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> level_line : level) {
            for (GameObject object : level_line) {
                object.draw(canvas);
            }
        }
    }

    public void update() {
        for (ArrayList<GameObject> level_line : level) {
            for (GameObject object : level_line) {
                object.update();
            }
        }
        camera.syncWithAim();
    }
}
