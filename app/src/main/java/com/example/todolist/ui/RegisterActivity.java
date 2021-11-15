package com.example.todolist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText email, name, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_email);
        name = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);

        new VisibilityControl(password, findViewById(R.id.visibility_control));

        findViewById(R.id.register_button).setOnClickListener(view -> {

            String _name = name.getText().toString(),
                    _email  = email.getText().toString(),
                    _password = password.getText().toString(),
                    _confirmPassword = confirmPassword.getText().toString();

            if(_name.length() <= 1){
                name.setError("Username has to contain more than 1 character");
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

            if(_email.length() <= 8) {
                email.setError("Please enter a legitimate email");
            }else{
                    //todo -> confirm email

                Retrofit2Init retrofit2Init = new Retrofit2Init();

                HashMap<String, String> map = new HashMap<>();

                map.put("name", _name);
                map.put("email", _email);
                map.put("password", _password);

                Call<Void> call = retrofit2Init.retrofitInterface.executeNewUser(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if(response.code() == 200){
                            Toast.makeText(getBaseContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();
                        }else if(response.code() == 400) {
                            Toast.makeText(getBaseContext(), "You are already signed up", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            /*todo -> if email is confirmed send data to database
                if not ask him to confirm or to change email
                and save email and user name in shared preferences
                after sign up let user sign in by opening activity_login.xml
            */
        });
    }
}