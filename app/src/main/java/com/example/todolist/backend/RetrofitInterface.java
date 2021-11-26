package com.example.todolist.backend;

import com.example.todolist.objects.NewUserResult;
import com.example.todolist.objects.Task;
import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {

    @GET("/users/user")
    Call<Void> executeGetUser(@Query("id") String id, @Query("name") String name, @Query("password") String password);

    @GET("/users/new")
    Call<NewUserResult> executeNewUser(@Query("email") String email, @Query("name") String name, @Query("password") String password);

    @POST("/tasks/new")
    Call<Void> executeNewTask(@Query("title") String title, @Query("text") String text, @Query("deadlineDate") long deadlineDate, @Query("collectionId") String collectionId);

    @DELETE("users/delete")
    Call<Void> executeDeleteUser(@Query("id") String id);

    @DELETE("tasks/delete")
    Call<Void> executeDeleteTask(@Query("collectionId") String collectionId, @Query("id") String id);

    @GET("/tasks")
    Call<ArrayList<Task>> executeGetTasks(@Query("collectionId") String collectionId);
}
