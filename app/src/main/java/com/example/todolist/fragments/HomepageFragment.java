package com.example.todolist.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.todolist.R;

public class HomepageFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view =inflater.inflate(R.layout.fragment_homepage, container, false);

        TextView textView = view.findViewById(R.id.text_homepage);
        textView.setTextColor(Color.RED);
        return view;
    }
}
