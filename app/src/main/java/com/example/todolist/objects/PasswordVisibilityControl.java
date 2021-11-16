package com.example.todolist.objects;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.todolist.R;

public class PasswordVisibilityControl {

    public PasswordVisibilityControl(EditText password, ImageButton passwordVisibility) {

        passwordVisibility.setTag(R.drawable.ic_invisible);
        passwordVisibility.setOnClickListener(view -> {

            if ((int)passwordVisibility.getTag() == R.drawable.ic_invisible){
                passwordVisibility.setTag(R.drawable.ic_visible);
                passwordVisibility.setBackgroundResource(R.drawable.ic_visible);
                password.setTransformationMethod(null);
            }else if ((int)passwordVisibility.getTag() == R.drawable.ic_visible){
                passwordVisibility.setTag(R.drawable.ic_invisible);
                passwordVisibility.setBackgroundResource(R.drawable.ic_invisible);
                password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
    }


}
