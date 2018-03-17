package com.castingmob.logger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

class LogUtil {

    static boolean DEBUGGABLE = true;

    /**
     * Initializes <code>LogUtil.DEBUGGABLE</code>
     * @param context - current context passed
     */
    public static void init(Context context) {
        boolean isDebuggable =  (ApplicationInfo.FLAG_DEBUGGABLE == ( context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        LogUtil.DEBUGGABLE = isDebuggable;
    }

    /**
     * Prints given tag and text as debug log, only if in debug mode.
     * @param tag - Tag to be set for logs
     * @param text - Log message
     */
    public static void debug(String tag, String text) {
        if (LogUtil.DEBUGGABLE) {
            Log.d(tag, text);
        }
    }

    /**
     * Prints given tag and text as info log, only if in debug mode.
     * @param tag - Tag to be set for logs
     * @param text - Log message
     */
    public static void info(String tag, String text) {
        if (LogUtil.DEBUGGABLE) {
            Log.d(tag, text);
        }
    }

    /**
     * Prints given tag and text as warning log, only if in debug mode.
     * @param tag - Tag to be set for logs
     * @param text - Log message
     */
    public static void warn(String tag, String text) {
        if (LogUtil.DEBUGGABLE) {
            Log.w(tag, text);
        }
    }

    /**
     * Prints given tag and text as error log, only if in debug mode.
     * @param tag - Tag to be set for logs
     * @param text - Log message
     */
    public static void error(String tag, String text) {
        if (LogUtil.DEBUGGABLE) {
            Log.e(tag, text);
        }
    }

    /**
     * Prints given tag and exception as error log, only if in debug mode with message.
     * @param tag - Tag to be set for logs
     * @param msg - Log message
     * @param ex  - Exception to be logged
     */
    public static void error(String tag, String msg, Throwable ex) {
        if (LogUtil.DEBUGGABLE) {
            Log.e(tag, msg, ex);
        }
    }

    /**
     * Prints given tag and exception as error log without message
     * @param tag - Tag to be set for logs
     * @param ex  - Exception to be logged
     */
    public static void error(String tag, Throwable ex) {
        error(tag,"",ex);
    }
}