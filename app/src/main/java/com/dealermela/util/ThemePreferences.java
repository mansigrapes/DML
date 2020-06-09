package com.dealermela.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class ThemePreferences {
    private final SharedPreferences sharedPreferences;
    private final Editor editor;
    private static final String SHARED = AppConstants.MY_PREF;
    private static final String THEME = AppConstants.WHITE_THEME;

    public ThemePreferences(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //Get and Save User Id
    public String getTheme() {
        return sharedPreferences.getString(THEME, "");
    }

    public void saveTheme(String id) {
        editor.putString(THEME, id);
        editor.commit();
    }

}
