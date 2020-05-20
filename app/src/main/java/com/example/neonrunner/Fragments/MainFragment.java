package com.example.neonrunner.Fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.neonrunner.Activities.GameActivity;
import com.example.neonrunner.Activities.MainActivity;
import com.example.neonrunner.R;
import com.example.neonrunner.RetroShit.DataService;
import com.example.neonrunner.RetroShit.RetroInstance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Фрагмент, отвечающий за основную логику приложения на этапе входа
public class MainFragment extends Fragment {
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button startButton = view.findViewById(R.id.startLevelButton); // получаем кнопку начала игры
        final ProgressBar loadingbar = view.findViewById(R.id.loadingBar); // прогресс бар реквеста к паст бину
        final EditText inputField = view.findViewById(R.id.lvlUrl); // поле ввода ссылки для паст бина
        final MainActivity activity = (MainActivity) getActivity(); // основная активность
        final Button startOriginalGame = view.findViewById(R.id.origGame); // кнопка запуска основной игры
        startOriginalGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream = getResources().openRawResource(R.raw.originals); // читаем файл основной игры
                    InputStreamReader isr = new InputStreamReader(inputStream); // заводим поток чтения
                    // читаем построково из файла
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                    Intent intent = new Intent(activity, GameActivity.class);
                    intent.putExtra("level", sb.toString()); // сохраняем уровень в интент
                    startActivityForResult(intent, 1); // запускаем активность игры
                } catch (IOException e) {
                    e.printStackTrace();
                    ((MainActivity) getActivity()).showPrimaryToast("Невозможно прочитать файл уровня"); // если файл каким-то образом удалился
                }
            }
        });
        // запуск уровня
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingbar.setVisibility(ProgressBar.VISIBLE); // проявляем лоадинг бар для реквеста
                // запускаем ассинхронный обработчик запроса
                RetroInstance.getRetrofitInstance().create(DataService.class).getRawPaste(inputField.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        loadingbar.setVisibility(ProgressBar.GONE); // скрываем прогресс бар, если пришел ответ
                        try {
                            Intent intent = new Intent(activity, GameActivity.class); // получаем интент
                            intent.putExtra("level", response.body().string()); // суем в него уровень из запроса
                            startActivity(intent); // запускаем активность через реквест
                        } catch (IOException e) {
                            activity.showPrimaryToast(getString(R.string.connection_unstable));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loadingbar.setVisibility(ProgressBar.GONE); // прячем прогресс бар, ведь пришел ответ
                        activity.showPrimaryToast(getString(R.string.connection_unstable)); // если нет интернета / произошла какая-то ошибка
                    }
                });
            }
        });

    }
}