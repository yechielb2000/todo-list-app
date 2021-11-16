package com.example.todolist.backend;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/users")
    Call<Void> executeGetUser(@Body HashMap<String, String > map);

    @POST("/users/new")
    Call<Void> executeNewUser(@Body HashMap<String, String> map);

    @POST("/tasks/new")
    Call<Void> executeNewTask(@Body HashMap<String, String> map);
}
