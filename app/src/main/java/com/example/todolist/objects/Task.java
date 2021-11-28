package com.example.todolist.objects;

public class Task {

    private final String title, text, _id;
    private String message;
    private final long deadlineDate;

    public Task(String id, String title, String text, long deadlineDate) {
        this._id = id;
        this.title = title;
        this.text = text;
        this.deadlineDate = deadlineDate;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String get_id() {
        return _id;
    }

    public long getDeadlineDate() {
        return deadlineDate;
    }
}
