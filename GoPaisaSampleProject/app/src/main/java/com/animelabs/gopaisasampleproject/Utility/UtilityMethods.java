package com.animelabs.gopaisasampleproject.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by asheeshsharma on 16/04/17.
 */

public class UtilityMethods {
    private static ConnectivityManager mCM;
    public static boolean hasInternetAccess(Context context) {
        if (context == null) {
            return false;
        }
        if (mCM == null) {
            mCM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo netInfo = mCM.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }


}
