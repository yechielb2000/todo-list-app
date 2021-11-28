package com.example.todolist.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.objects.MemoryStringsList;
import com.example.todolist.objects.SharedPreferencesObject;
import com.example.todolist.objects.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsFragment extends Fragment {

    @SuppressLint("SetTextI18n") @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_user_details, container, false);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("User details");
        SharedPreferencesObject prefObject = new SharedPreferencesObject(getContext());

        TextView userDetails = view.findViewById(R.id.user_details);
        userDetails.setTextColor(Color.RED);

        Button deleteAcc = view.findViewById(R.id.delete_acc_button);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit2Init retrofit2Init = new Retrofit2Init();

        Call<User> call = retrofit2Init.retrofitInterface.executeGetUserById(prefObject.getString(MemoryStringsList.USER_ID));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    userDetails.setVisibility(View.VISIBLE);
                    userDetails.setText(
                            "name : " + response.body().getName()
                    + "\nemail : " + response.body().getEmail()
                    + "\npassword : " + response.body().getPassword()
                    + "\nid : " + response.body().get_id()
                    + "\n\nDo not share this information with anyone!");
                }

                deleteAcc.setVisibility(View.VISIBLE);
                deleteAcc.setOnClickListener(deleteAccView -> new AlertDialog.Builder(getContext())
                        .setTitle("Delete account")
                        .setMessage("Are you sure you want to delete this account?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                           progressBar.setVisibility(View.VISIBLE);

                           Call<User> deleteCall = retrofit2Init.retrofitInterface.executeDeleteUser(prefObject.getString(MemoryStringsList.USER_ID));

                           deleteCall.enqueue(new Callback<User>() {
                               @Override
                               public void onResponse(Call<User> call, Response<User> response) {
                                   progressBar.setVisibility(View.GONE);
                                   Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                   getActivity().finish();
                               }

                               @Override
                               public void onFailure(Call<User> call, Throwable t) {
                                   progressBar.setVisibility(View.GONE);
                               }
                           });

                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
