package com.example.neonrunner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.neonrunner.R;

public class FinishGameActivity extends NeonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);
        // Устанавливаем события на кнопки выхода из игры и возвращения ко старту
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Выходим из приложение
            }
        });
        Button toStart = findViewById(R.id.toTheStart);
        final NeonActivity activity = this;
        toStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Возвращаемся на старт
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
