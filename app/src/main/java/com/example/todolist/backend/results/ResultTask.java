package com.example.todolist.backend.results;

public class ResultTask {

    private final String title, text;
    private long pickedDate;

    public ResultTask(String title, String tex, long pickedDate) {
        this.title = title;
        this.text = tex;
        this.pickedDate = pickedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getPickedDate() {
        return pickedDate;
    }
}

