package com.example.neonrunner.Core;

import java.util.ArrayList;

public class BoundedCamera {
    public GameObject getAim() {
        return aim;
    }

    public void setAim(GameObject aim) {
        this.aim = aim;
    }

    GameObject aim;
    GameLevel level;

    public BoundedCamera(GameObject bounded_object, GameLevel level) {
        aim = bounded_object;
        this.level = level;
    }

    public void syncWithAim() {
        int offset_x, offset_y;
        offset_x = aim.getPos_x() - level.display_width / 2;
        offset_y = aim.getPos_y() - level.display_height / 2;
        for (ArrayList<GameObject> level_line : level.getLevel()) {
            for (GameObject object : level_line) {
                object.move(offset_x, offset_y);
            }
        }
    }
}
