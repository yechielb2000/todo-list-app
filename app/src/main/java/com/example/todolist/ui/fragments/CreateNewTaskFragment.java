package com.example.todolist.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.objects.MemoryStringsList;
import com.example.todolist.objects.SharedPreferencesObject;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewTaskFragment extends DialogFragment{

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private long deadlineDate;
    private ProgressBar progressBar;
    private EditText title, text;
    private SharedPreferencesObject preferencesObject;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_new_task, container, false);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("New Task");
        dateButton = view.findViewById(R.id.date_picker);
        Button submitTask = view.findViewById(R.id.submit_task);
        title = view.findViewById(R.id.edit_text_title);
        text = view.findViewById(R.id.edit_text_text);
        progressBar = view.findViewById(R.id.progress_bar);

        preferencesObject = new SharedPreferencesObject(getContext());

        initDatePicker();

        dateButton.setOnClickListener(v -> datePickerDialog.show());

        submitTask.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            String _title = title.getText().toString(),
            _text = text.getText().toString();
            long _deadlineDate = deadlineDate;

            Retrofit2Init retrofit2Init = new Retrofit2Init();

            Call<Void> call = retrofit2Init.retrofitInterface.executeNewTask(_title, _text, _deadlineDate, preferencesObject.getString(MemoryStringsList.USER_ID)
            );

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.isSuccessful()) {

                        title.setText(null);
                        text.setText(null);

                        Snackbar.make(view.findViewById(R.id.new_tasks_layout),
                                "Task added to your list.\nYou can add another or go back.",
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction("BACK", actionView -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TasksFragment()).commit())
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        return view;
    }

    private void initDatePicker() {

        Calendar cal = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            String date = getMonthFormat(month  + 1) + " " + day + " " + year;
            dateButton.setText(date);

            TimePicker timePicker = new TimePicker(getContext());

            cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            cal.set(Calendar.MONTH, datePicker.getMonth());
            cal.set(Calendar.YEAR, datePicker.getYear());
            cal.set(Calendar.HOUR, timePicker.getCurrentHour());
            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());

            deadlineDate = cal.getTimeInMillis();
        };

        datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private String getMonthFormat(int month) {
        if(month == 1) return "JAN";
        if(month == 2) return "FEB";
        if(month == 3) return "MAR";
        if(month == 4) return "APR";
        if(month == 5) return "MAY";
        if(month == 6) return "JUN";
        if(month == 7) return "JUL";
        if(month == 8) return "AUG";
        if(month == 9) return "SEP";
        if(month == 10) return "OCT";
        if(month == 11) return "NOV";
        if(month == 12) return "DEC";

        return "Select date";
    }
}
