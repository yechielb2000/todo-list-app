package com.example.todolist.objects;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesObject {

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesObject(Context context) {

        sharedPreferences = context.getSharedPreferences("com.example.todolist", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public void putBoolean(String memoryString, boolean data) {
        editor.putBoolean(memoryString, data);
        save();
    }

    public void putInt(String memoryString, int data){
        editor.putInt(memoryString, data);
        save();
    }

    public void putString(String memoryString, String data){
        editor.putString(memoryString, data);
        save();
    }

    public boolean getBoolean(String memoryString){
        return sharedPreferences.getBoolean(memoryString, false);
    }

    public int getInt(String memoryString){
        return sharedPreferences.getInt(memoryString, 0);
    }

    public String getString(String memoryString){
        return sharedPreferences.getString(memoryString, null);
    }

    public void deleteData(String memoryString){
        editor.remove(memoryString);
        editor.apply();
    }

    public void resetSharedPreferences(){
        editor.clear();
    }

    public void save(){
        editor.apply();
    }
}
