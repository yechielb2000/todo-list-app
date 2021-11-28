package com.example.todolist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.objects.PasswordVisibilityControl;
import com.example.todolist.objects.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, name, password, confirmPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_email);
        name = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);

        progressBar = findViewById(R.id.progress_bar);

        new PasswordVisibilityControl(password, findViewById(R.id.visibility_control));

        findViewById(R.id.register_button).setOnClickListener(view -> {

            String _name = name.getText().toString(),
                    _email  = email.getText().toString(),
                    _password = password.getText().toString(),
                    _confirmPassword = confirmPassword.getText().toString();

            if(_email.length() <= 8) {
                email.setError("Please enter a legitimate email");
                return;
            }

            if(_password.length() <= 6){
                password.setError("Password has to contain more than 6 characters");
                return;
            }

            if(!_confirmPassword.equals(_password)) {
                confirmPassword.setError("Password is incorrect");
                return;
            }

            if(_name.length() <= 1){
                name.setError("Username has to contain more than 1 character");
            } else{

                progressBar.setVisibility(View.VISIBLE);

                Retrofit2Init retrofit2Init = new Retrofit2Init();

                Call<User> call = retrofit2Init.retrofitInterface.executeNewUser(_email, _name, _password);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        Toast.makeText(getBaseContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            /*(option) todo -> if email is confirmed send data to database
                if not ask him to confirm or to change email
                and save email and user name in shared preferences
                after sign up let user sign in by opening activity_login.xml
            */
        });
    }
}