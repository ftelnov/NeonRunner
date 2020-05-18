package com.example.neonrunner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.neonrunner.R;

public class GameActivity extends NeonActivity {
    String level_raw;

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
        showPrimaryToast(level_raw);
    }
}
