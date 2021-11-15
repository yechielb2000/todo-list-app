package com.example.todolist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.objects.VisibilityControl;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.not_register_button).setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        name = findViewById(R.id.sign_in_name);
        password = findViewById(R.id.sign_in_password);
        SwitchCompat rememberMe = findViewById(R.id.remember_me_switch);

        //todo -> read from shared if rememberMe is checked if it is let him in without login

        new VisibilityControl(password, findViewById(R.id.visibility_control));

        findViewById(R.id.login_button).setOnClickListener(view -> {

            String _name = name.getText().toString(),
                    _password = password.getText().toString();

            Retrofit2Init retrofit2Init = new Retrofit2Init();

            HashMap<String, String> map = new HashMap<>();

            map.put("name", _name);
            map.put("password", _password);

            Call<Void> call = retrofit2Init.retrofitInterface.executeGetUser(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if(response.code() == 200){
                        //todo -> save id in shared, if remember me is on let him in without checking
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }else if(response.code() == 400) {
                        Toast.makeText(getBaseContext(), "Your name or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}