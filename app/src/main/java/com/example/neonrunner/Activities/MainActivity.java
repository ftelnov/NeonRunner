package com.example.neonrunner.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonrunner.Fragments.MainFragment;
import com.example.neonrunner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, MainFragment.newInstance(), "main").commit();
    }
}
