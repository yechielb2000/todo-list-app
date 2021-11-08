package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import com.example.todolist.objects.VisibilityControl;

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
                return;
            }else{
                //todo -> confirm email

                Thread t = new Thread(() ->{
                    MongoInit mongoInit = new MongoInit();
                        try {
                            mongoInit.createNewUser(_name, _email, _password);
                        }catch (Exception e){
                            Log.d("TAG", e.getMessage());
                        }
                });
                t.start();
            }

            /*todo -> if email is confirmed send data to database
                if not ask him to confirm or to change email
                and save email and user name in shared preferences
                after sign up let user sign in by opening activity_login.xml
            */
        });
    }
}