package com.example.todolist.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Task> list;
    private List<Task> fullList; //for search option
    private LayoutInflater mInflater;
    ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, List<Task> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;

        //for search method
        fullList = new ArrayList<>();
        fullList.addAll(list);
    }

    // inflates the row layout from xml when needed
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, text;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            text = itemView.findViewById(R.id.task_text);
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