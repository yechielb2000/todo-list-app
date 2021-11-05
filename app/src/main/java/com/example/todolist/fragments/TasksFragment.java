package com.example.todolist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.example.todolist.RecyclerViewAdapter;
import com.example.todolist.objects.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

   RecyclerView todayTasks, moreTasks;
    RecyclerViewAdapter adapter;
    ArrayList<Task> todayTasksList = new ArrayList<>(), moreTasksList = new ArrayList<>();

    @Override  @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar();
        setHasOptionsMenu(true);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
//            bundle.putString("date", newDate);

            CreateNewTaskFragment dialogFragment = new CreateNewTaskFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog fragment");

        });


        todayTasks = view.findViewById(R.id.today_tasks);
        moreTasks = view.findViewById(R.id.more_tasks);

        getTasks(todayTasksList);
        getTasks(moreTasksList);

        recyclerViewAdapter(todayTasks, todayTasksList);
        recyclerViewAdapter(moreTasks, moreTasksList);
        return view;
    }

    public void getTasks(ArrayList tasks){
        for (int i = 0; i < 10; i++)
            tasks.add(new Task("title " + i, "Some Text for example "));
    }

    private void recyclerViewAdapter(RecyclerView recyclerView, ArrayList<Task> list){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter = new RecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater =  getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }
}
