package com.example.neonrunner.Fragments;

import android.content.Intent;
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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Button startButton = view.findViewById(R.id.startLevelButton);
        final ProgressBar loadingbar = view.findViewById(R.id.loadingBar);
        final EditText inputField = view.findViewById(R.id.lvlUrl);
        final MainActivity activity = (MainActivity) getActivity();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingbar.setVisibility(ProgressBar.VISIBLE);
                RetroInstance.getRetrofitInstance().create(DataService.class).getRawPaste(inputField.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        loadingbar.setVisibility(ProgressBar.GONE);
                        try {
                            Intent intent = new Intent(activity, GameActivity.class);
                            intent.putExtra("level", response.body().string());
                            startActivity(intent);
                        } catch (IOException e) {
                            activity.showPrimaryToast(getString(R.string.connection_unstable));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loadingbar.setVisibility(ProgressBar.GONE);
                        activity.showPrimaryToast(getString(R.string.connection_unstable));
                    }
                });
            }
        });

    }
}
