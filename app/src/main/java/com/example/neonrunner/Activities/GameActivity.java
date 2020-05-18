package com.example.neonrunner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.neonrunner.R;

import java.util.ArrayList;

public class GameActivity extends NeonActivity {
    String level_raw;
    ArrayList<ArrayList<String>> game_levels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        level_raw = bundle.getString("level");
        String levels[] = level_raw.split("\\r?\\n");
        ArrayList<String> temp_level = new ArrayList<String>();
        for (String level : levels) {
            if (level.contains("|")) {
                game_levels.add((ArrayList<String>) temp_level.clone());
                temp_level.clear();
            } else {
                temp_level.add(level);

            }
        }
    }
}
