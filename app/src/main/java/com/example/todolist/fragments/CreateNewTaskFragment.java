package com.example.todolist.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.todolist.R;
import java.util.Calendar;
import java.util.Date;

public class CreateNewTaskFragment extends DialogFragment {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private long pickedDate;
    private final long currentDate = new Date().getTime();
    private EditText title, text;


    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_new_task, container, false);

        dateButton = view.findViewById(R.id.date_picker);
        Button submitTask = view.findViewById(R.id.submit_task);

        title = view.findViewById(R.id.edit_text_title);
        text = view.findViewById(R.id.edit_text_text);

        initDatePicker();

        dateButton.setOnClickListener(v -> datePickerDialog.show());

        submitTask.setOnClickListener(v -> {

            Toast.makeText(getContext(), title.getText().toString() + "\n" + text.getText().toString() + "\n" + currentDate + "\n" + pickedDate, Toast.LENGTH_LONG).show();
           //todo new String[]{title.getText().toString(), text.getText().toString(), currentDate, pickedDate}
           // send [title, text, current time, deadline time] to database
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

            pickedDate = cal.getTimeInMillis();
        };

        datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "Select date";
    }

}
