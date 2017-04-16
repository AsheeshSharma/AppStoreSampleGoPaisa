package com.animelabs.gopaisasampleproject.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by asheeshsharma on 09/04/17.
 */

public class SharedPrefUtility {
    private static SharedPreferences sharedpreferences;
    public static final String IS_COMPLETED = "IS_COMPLETED";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static void addBoolean(Context context, boolean value){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(IS_COMPLETED, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean is_completed = sharedpreferences.getBoolean(IS_COMPLETED,false);
        return is_completed;
    }
}
