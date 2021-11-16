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
import com.example.todolist.objects.SharedPreferencesObject;
import com.example.todolist.objects.PasswordVisibilityControl;
import com.google.android.material.snackbar.Snackbar;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText name, password;
    private boolean rememberMeStatus = false;
    private SharedPreferencesObject prefObject;
    private ProgressBar progressBar;

    private final String REMEMBER_ME = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_bar);

        startActivity(new Intent(getBaseContext(), MainActivity.class));//todo ->delete after

        findViewById(R.id.not_register_button).setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        name = findViewById(R.id.sign_in_name);
        password = findViewById(R.id.sign_in_password);
        SwitchCompat rememberMe = findViewById(R.id.remember_me_switch);
        TextView forgotPassword = findViewById(R.id.forgot_password_textview);

        forgotPassword.setOnClickListener(view -> Snackbar.make(findViewById(R.id.forgot_password_textview) , "Currently u navailable" ,Snackbar.LENGTH_SHORT).show());

        prefObject = new SharedPreferencesObject(this);

        new PasswordVisibilityControl(password, findViewById(R.id.visibility_control));

        if(prefObject.getSharedPreferences().contains(REMEMBER_ME)){
            if (prefObject.getBoolean(REMEMBER_ME))
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

            HashMap<String, String> map = new HashMap<>();

            map.put("name", _name);
            map.put("password", _password);

            Call<Void> call = retrofit2Init.retrofitInterface.executeGetUser(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.code() == 200){

                        if(rememberMeStatus) prefObject.putBoolean(REMEMBER_ME, true);

                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }else{
                        Toast.makeText(getBaseContext(), "Your name or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}