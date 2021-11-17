package com.example.todolist.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.ui.RecyclerViewAdapter;
import com.example.todolist.objects.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksFragment extends Fragment {

    private RecyclerView todayTasks, moreTasks;
    private RecyclerViewAdapter adapter;
    private final ArrayList<Task> todayTasksList = new ArrayList<>(), moreTasksList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Tasks");

        setHasOptionsMenu(true);

        progressBar = view.findViewById(R.id.progress_bar);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.tasks_fragment);
        swipeRefreshLayout.setOnRefreshListener(() ->
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TasksFragment()).commit());

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> {

            Bundle bundle = new Bundle();

            CreateNewTaskFragment dialogFragment = new CreateNewTaskFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog fragment");
        });

        todayTasks = view.findViewById(R.id.today_tasks);
        moreTasks = view.findViewById(R.id.more_tasks);

        Retrofit2Init retrofit2Init = new Retrofit2Init();

        Call<ArrayList<Task>> call = retrofit2Init.retrofitInterface.executeGetTasks();

        call.enqueue(new Callback<ArrayList<Task>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Task>> call, @NonNull Response<ArrayList<Task>> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful() && !response.body().isEmpty()){

                    for (Task task : response.body()) {
                        if(task.getPickedDate() == System.currentTimeMillis()){
                            addTask(todayTasksList, task.getTitle(), task.getText(), task.getId(), task.getPickedDate());
                        }else{
                            addTask(moreTasksList, task.getTitle(), task.getText(), task.getId(), task.getPickedDate());
                        }
                    }

                    view.findViewById(R.id.lists_layout).setVisibility(View.VISIBLE);

                    if(!todayTasksList.isEmpty()) {
                        recyclerViewAdapter(todayTasks, todayTasksList);
                        view.findViewById(R.id.today_tv).setVisibility(View.VISIBLE);
                    }
                    if(!moreTasksList.isEmpty()) {
                        recyclerViewAdapter(moreTasks, moreTasksList);
                        view.findViewById(R.id.more_tv).setVisibility(View.VISIBLE);
                    }

                }else{
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Task>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void addTask(ArrayList<Task> task, String title, String text, String id, long pickedDate){
            task.add(new Task(id, title, text, pickedDate));
    }

    private void recyclerViewAdapter(RecyclerView recyclerView, ArrayList<Task> list){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter = new RecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater){

        menuInflater =  getActivity().getMenuInflater();
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
    }
}
