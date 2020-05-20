package com.example.neonrunner.Activities;

import android.app.AppComponentFactory;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.neonrunner.R;

// Класс, наследующийся от обычной активности с целью содержания доп функций - все активности наследуются от него
public class NeonActivity extends AppCompatActivity {
    public void showPrimaryToast(String text) {
        Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG); // Создаем тост
        View view = toast.getView(); // получаем его вью
        view.setBackground(getDrawable(R.drawable.toast_background)); // устанавливаем ей написанный ранее бэк
        TextView textview = view.findViewById(android.R.id.message); // получаем часть вью, где прописан текст тоста
        textview.setTextColor(getColor(R.color.white)); // устанавливаем цвет текста
        toast.show(); // Показываем!
    }
}
