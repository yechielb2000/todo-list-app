package com.example.todolist.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.todolist.R;
import com.example.todolist.objects.Share;
import com.example.todolist.objects.SharedPreferencesObject;
import com.example.todolist.ui.fragments.CreateNewTaskFragment;
import com.example.todolist.ui.fragments.UserDetailsFragment;
import com.example.todolist.ui.fragments.TasksFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SharedPreferencesObject preferencesObject;

    @SuppressLint("SetTextI18n") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home page");

        preferencesObject = new SharedPreferencesObject(this);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserDetailsFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId") @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_tasks:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TasksFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserDetailsFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_new_task:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateNewTaskFragment()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.log_out:
                finish();
                break;

            case R.id.share_app:
                new Share("Hi! i'm using Todo list app.\nCome join me!", this);
                break;
        }
        return true;
    }
}