package com.example.neonrunner.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonrunner.Fragments.MainFragment;
import com.example.neonrunner.R;

public class MainActivity extends AppCompatActivity {
    public void showPrimaryToast(String text) {
        Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackground(getDrawable(R.drawable.toast_background));
        TextView textview = view.findViewById(android.R.id.message);
        textview.setTextColor(getColor(R.color.white));
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, MainFragment.newInstance(), "main").commit();
    }
}
