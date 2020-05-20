package com.example.neonrunner.RetroShit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    private static Retrofit retrofit; // клиент ретрофита
    private static final String BASE_URL = "https://pastebin.com/"; // базовая урла пастбина

    // получаем объект клиента ретрофита, реализуя синглтон
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build(); // билдим, добавляее гсон конвертер
        }
        return retrofit;
    }
}
