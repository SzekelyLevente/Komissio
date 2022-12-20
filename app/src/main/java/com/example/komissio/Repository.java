package com.example.komissio;

import android.content.Context;
import android.content.SharedPreferences;

public class Repository implements IRepository{

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public Repository(Context context) {
        sharedPref = context.getSharedPreferences("kom", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    @Override
    public String Read(String kulcs) {
        return sharedPref.getString(kulcs,"");
    }

    @Override
    public void Update(String kulcs, String ertek) {
        editor.putString(kulcs, ertek);
        editor.apply();
    }
}
