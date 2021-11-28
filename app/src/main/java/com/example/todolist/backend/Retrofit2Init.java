package com.example.todolist.backend;

import android.annotation.SuppressLint;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Init {

    public final RetrofitInterface retrofitInterface;

    public Retrofit2Init() {
        @SuppressLint("AuthLeak") String BASE_URL = "http://10.0.2.2:3000/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }
}

