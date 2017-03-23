package io.github.mayunfei.rxdownload.function;

import android.text.TextUtils;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static java.lang.String.format;
import static java.util.Locale.getDefault;

/**
 * Created by mayunfei on 17-3-23.
 */

public class Utils {
  private static boolean DEBUG = false;

  public static void setDebug(boolean flag) {
    DEBUG = flag;
  }

  public static void log(String message) {
    if (empty(message)) return;
    if (DEBUG) {
      Log.i(TAG, message);
    }
  }

  public static void log(String message, Object... args) {
    log(format(getDefault(), message, args));
  }

  public static void log(Throwable throwable) {
    Log.w(TAG, throwable);
  }

  public static boolean empty(String string) {
    return TextUtils.isEmpty(string);
  }

}
