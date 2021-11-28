package com.example.todolist.backend;

import com.example.todolist.objects.Task;
import com.example.todolist.objects.User;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("/users/user")
    Call<User> executeLoginUser(@Query("name") String name, @Query("password") String password);

    @GET("/users/user-by-id")
    Call<User> executeGetUserById(@Query("id") String id);

    @GET("/users/new")
    Call<User> executeNewUser(@Query("email") String email, @Query("name") String name, @Query("password") String password);

    @POST("/tasks/new")
    Call<Void> executeNewTask(@Query("title") String title, @Query("text") String text, @Query("deadlineDate") long deadlineDate, @Query("collectionId") String collectionId);

    @DELETE("users/delete")
    Call<User> executeDeleteUser(@Query("id") String id);

    @DELETE("tasks/delete")
    Call<Task> executeDeleteTask(@Query("collectionId") String collectionId, @Query("id") String id);

    @GET("/tasks")
    Call<ArrayList<Task>> executeGetTasks(@Query("collectionId") String collectionId);
}
