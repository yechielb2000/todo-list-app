package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import com.example.todolist.objects.VisibilityControl;

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

            //todo -> check if the email match with the password the user entered
            // if yes log the user in, if not make error message
            startActivity(new Intent(this, MainActivity.class));
        });

    }
}