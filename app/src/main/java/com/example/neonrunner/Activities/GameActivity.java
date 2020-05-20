package com.example.neonrunner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.neonrunner.Core.GameLevel;
import com.example.neonrunner.Core.GameSurface;
import com.example.neonrunner.R;

import java.util.ArrayList;

// Активити, содержащая основной экран игры
public class GameActivity extends NeonActivity {
    String level_raw; // просто строки, содержащие устройство уровней
    ArrayList<ArrayList<String>> game_levels = new ArrayList<>(); // Разбиение строк на составляющие

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Убираем тайтл
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Делаем полноэкранным

        // достаем из мешка уровень, переданный из прошлой активити
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        level_raw = bundle.getString("level");
        String levels[] = level_raw.split("\\r?\\n"); // разбиваем регуляркой по строковым разделителям
        ArrayList<String> temp_level = new ArrayList<String>(); // уровень(raw)
        for (String level : levels) {
            if (level.contains("|")) { // Если был найден разделитель, пушим в основной массив собранные строки
                game_levels.add((ArrayList<String>) temp_level.clone());
                temp_level.clear();
            } else {
                temp_level.add(level);
            }
        }
        ArrayList<GameLevel> gameLevels = new ArrayList<>(); // Финальный массив
        for (ArrayList<String> level : game_levels) { // На ходу генерируем уровни, пушим в массив
            gameLevels.add(new GameLevel(level, this));
        }
        GameSurface surface = new GameSurface(this, gameLevels); // Наконец создаем полотно, передавая уровни как параметр
        surface.setBackgroundResource(R.drawable.purple_sunset); // Устанавливаем бэкграунд вьюшки
        this.setContentView(surface); // Устанавливаем полотно как основную вьюшку активити
    }

    // Метод, вызываем в полотне для запуска концовки
    public void runFinnishScreen() {
        // Создаем интент
        Intent intent = new Intent(this, FinishGameActivity.class);
        this.startActivity(intent); // запускаем его
    }
}
