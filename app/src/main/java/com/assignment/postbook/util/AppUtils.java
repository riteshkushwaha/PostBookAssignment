package com.assignment.postbook.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppUtils {

    public static final String USER_ID = "USER_ID";
    public static final String USER_POST_DATA = "USER_POST";

    public static final String USER_ALLPOST_FRAGMENT = "ALLPOST_FRAGMENT";
    public static final String USER_FAVPOST_FRAGMENT = "FAVPOST_FRAGMENT";

    public static final String USER_MARK_POST = "FAV_POST_DATA";


    public boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
