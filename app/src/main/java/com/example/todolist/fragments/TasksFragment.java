package com.example.todolist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.objects.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

   RecyclerView todayTasks, moreTasks;
   ArrayList<Task> todayTasksList, moreTasksList;

    @Override  @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> {
            Snackbar.make(view, "It works", Snackbar.LENGTH_SHORT).show();
        });

        todayTasks = view.findViewById(R.id.today_tasks);
        moreTasks = view.findViewById(R.id.more_tasks);

        todayTasksList = new ArrayList<>();
        moreTasksList = new ArrayList<>();



        return view;
    }
}
