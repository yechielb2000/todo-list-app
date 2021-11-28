package com.example.todolist.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.example.todolist.backend.Retrofit2Init;
import com.example.todolist.objects.MemoryStringsList;
import com.example.todolist.objects.Share;
import com.example.todolist.objects.SharedPreferencesObject;
import com.example.todolist.objects.Task;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private final List<Task> list;
    private final List<Task> fullList; //for search option
    private final LayoutInflater mInflater;
    ItemClickListener mClickListener;
    private final Context context;

    private SharedPreferencesObject preferencesObject;

    public RecyclerViewAdapter(Context context, List<Task> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;

        //for search method
        fullList = new ArrayList<>();
        fullList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = list.get(position);

        holder.title.setText(task.getTitle());
        holder.text.setText(task.getText());

        holder.done_cb.setOnClickListener(view -> {
            if(holder.done_cb.isChecked()){

                preferencesObject = new SharedPreferencesObject(context);

                Retrofit2Init retrofit2Init = new Retrofit2Init();

                Call<Task> call = retrofit2Init.retrofitInterface.executeDeleteTask(preferencesObject.getString(MemoryStringsList.USER_ID), task.get_id());

                call.enqueue(new Callback<Task>() {
                    @Override
                    public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onFailure(@NonNull Call<Task> call, @NonNull Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.share_task.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(task.getDeadlineDate());

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            String deadline = mYear + "/" + mMonth + "/" + mDay;

            new Share("Hi!\nHere are my task details.\n" + task.getTitle() + "\n" + task.getText() + "\ndeadline : " + deadline, context);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, text;
        CheckBox done_cb;
        ImageButton share_task;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            text = itemView.findViewById(R.id.task_text);
            done_cb = itemView.findViewById(R.id.checkb);
            share_task = itemView.findViewById(R.id.share_task);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Task> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {

                    filteredList.addAll(fullList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Task task : fullList) {
                        if (task.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(task);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }
}