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
        for (String line : raw_level) {
            char[] arr = line.toCharArray();
            ArrayList<GameObject> temp_objects = new ArrayList<>();

            for (int i = 0; i < line.length(); ++i) {
                if (arr[i] == '#') {
                    temp_objects.add(new GameObject(block, level_index, i, 100, 100));
                } else if (arr[i] == 'H') {
                    temp_objects.add(new Hero(hero, level_index, i, 100, 100));
                }
            }
            level.add(temp_objects);
            level_index++;
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> level_line : level) {
            for (GameObject object : level_line) {
                object.draw(canvas);
            }
        }
    }
}
