package com.example.neonrunner.Activities;

import android.app.AppComponentFactory;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonrunner.R;

public class NeonActivity extends AppCompatActivity {
    public void showPrimaryToast(String text) {
        Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackground(getDrawable(R.drawable.toast_background));
        TextView textview = view.findViewById(android.R.id.message);
        textview.setTextColor(getColor(R.color.white));
        toast.show();
    }
}
