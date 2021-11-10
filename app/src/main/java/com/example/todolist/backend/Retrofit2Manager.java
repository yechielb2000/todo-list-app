package com.example.todolist.backend;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Manager {

    private final RetrofitInterface retrofitInterface;
    private String resSignup;
    private HashMap<String, String> resLogin;

    public Retrofit2Manager() {
        String BASE_URL = "http://10.0.2.2:3000";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public String createNewUser(String name, String password, String email){

        HashMap<String, String> map = new HashMap<>();

        map.put("name", name);
        map.put("password", password);
        map.put("email", email);

        Call<Void> call = retrofitInterface.executeSignup(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.code() == 200){
                    resSignup = "Signed up successfully!";
                }else if(response.code() == 400){
                    resSignup = "Already registered";
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resSignup = t.getMessage();
            }
        });

        return resSignup;
    }

    public HashMap<String, String> login(String name, String password) {

        resLogin = new HashMap<>();

        HashMap<String, String> map = new HashMap<>();

        map.put("name", name);
        map.put("password", password);

        Call<LoginResult> call = retrofitInterface.executeLogin(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                resLogin.put("status", String.valueOf(response.code()));

                if(response.code() == 200){
                    LoginResult loginResult = new LoginResult();
                    resLogin.put("name", loginResult.getName());
                    resLogin.put("email", loginResult.getEmail());
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                   resLogin.put("message", t.getMessage());
            }
        });

        return resLogin;
    }
}

