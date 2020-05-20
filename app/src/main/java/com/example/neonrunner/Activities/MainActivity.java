package com.example.neonrunner.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonrunner.Fragments.MainFragment;
import com.example.neonrunner.R;

public class MainActivity extends NeonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, MainFragment.newInstance(), "main").commit(); // грузим фрагмент, где прописна основная логика
    }
}
