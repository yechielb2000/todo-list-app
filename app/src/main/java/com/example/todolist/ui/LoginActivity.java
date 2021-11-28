package com.example.todolist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.objects.MemoryStringsList;
import com.example.todolist.objects.SharedPreferencesObject;
import com.example.todolist.objects.PasswordVisibilityControl;
import com.example.todolist.objects.User;
import com.google.android.material.snackbar.Snackbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText name, password;
    private boolean rememberMeStatus = false;
    private SharedPreferencesObject prefObject;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.not_register_button).setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        name = findViewById(R.id.sign_in_name);
        password = findViewById(R.id.sign_in_password);
        SwitchCompat rememberMe = findViewById(R.id.remember_me_switch);
        TextView forgotPassword = findViewById(R.id.forgot_password_textview);

        forgotPassword.setOnClickListener(view -> Snackbar.make(findViewById(R.id.forgot_password_textview) , "Currently unavailable" ,Snackbar.LENGTH_SHORT).show());

        prefObject = new SharedPreferencesObject(this);

        new PasswordVisibilityControl(password, findViewById(R.id.visibility_control));

        if(prefObject.getSharedPreferences().contains(MemoryStringsList.REMEMBER_ME)){
            if (prefObject.getBoolean(MemoryStringsList.REMEMBER_ME))
                startActivity(new Intent(getBaseContext(), MainActivity.class));
        }

        rememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> rememberMeStatus = isChecked);

        findViewById(R.id.login_button).setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);

            String _name = name.getText().toString(),
                    _password = password.getText().toString();

            if(_name.isEmpty()){
                name.setError("Fill name.");
                return;
            }else if(_password.isEmpty()){
                password.setError("Fill password.");
                return;
            }

            Retrofit2Init retrofit2Init = new Retrofit2Init();

            Call<User> call = retrofit2Init.retrofitInterface.executeLoginUser(_name, _password);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.isSuccessful()){
                        prefObject.putString(MemoryStringsList.USER_ID, response.body() != null ? response.body().get_id() : null);
                        prefObject.putString(MemoryStringsList.USER_NAME, response.body().getName());

                        if(rememberMeStatus) prefObject.putBoolean(MemoryStringsList.REMEMBER_ME, true);
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }else{
                        Toast.makeText(getBaseContext(), "Your name or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}