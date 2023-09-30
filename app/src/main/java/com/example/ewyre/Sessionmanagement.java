package com.example.ewyre;

import android.content.Context;
import android.content.SharedPreferences;

public class Sessionmanagement {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public Sessionmanagement(Context context) {
        sharedpreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void saveSession(User user) {
        int id = user.getId();
        editor.putInt(SESSION_KEY, id).commit();
    }

    public int getSession() {
        return sharedpreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
