package com.example.neonrunner.RetroShit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataService {
    @GET("/raw/{paste_id}")
    Call<ResponseBody> getRawPaste(@Path(value = "paste_id") String paste_id);
}
