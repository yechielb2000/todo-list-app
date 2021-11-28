package com.example.todolist.objects;

import android.content.Context;
import android.content.Intent;

public class Share {

    public Share(String message, Context context) {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(share, "Share using"));
    }
}
