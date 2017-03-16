package com.mgaga.starterapp.base;

import android.util.Log;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

public class ErrorHandler {

    public static boolean debuggable;

    public static void reportAndHandle(String error) {
        ErrorHandler.reportAndHandle(new Exception(error));
    }

    public static void reportAndHandle(Throwable ex) {
        ErrorHandler.reportAndHandle(ex, null, true);
    }

    public static void reportAndHandle(Throwable ex, String message) {
        ErrorHandler.reportAndHandle(ex, message, true);
    }

    public static void reportAndHandle(Throwable ex, String message, boolean submitCrashlytics) {
        if (ex == null) {
            return;
        }

        if (submitCrashlytics) {
            ErrorHandler.sendReport(ex);
        }

        if (ErrorHandler.debuggable) {
            Log.e("StarterApp", Log.getStackTraceString(ex));
        }
    }

    private static void sendReport(Throwable throwable) {
        //if (!ErrorHandler.isDebuggable())
        //    Crashlytics.getInstance().core.logException(throwable);
    }
}
