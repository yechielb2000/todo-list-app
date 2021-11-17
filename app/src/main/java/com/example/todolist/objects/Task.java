package com.example.todolist.objects;

public class Task {

    private final String title, text, id;
    private final long pickedDate;

    public Task(String id, String title, String text, long pickedDate) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.pickedDate = pickedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public long getPickedDate() {
        return pickedDate;
    }
}
