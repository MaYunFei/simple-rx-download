package io.github.mayunfei.rxdownload;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import io.github.mayunfei.rxdownload.function.DownloadService;
import io.github.mayunfei.rxdownload.function.ServiceHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

import static io.github.mayunfei.rxdownload.function.Utils.log;

/**
 * Created by mayunfei on 17-3-23.
 */

public class RxDownload {

  @SuppressLint("StaticFieldLeak") private volatile static RxDownload instance;

  private final Context context;
  private ServiceHelper serviceHelper;

  private DownloadService downloadService;

  private RxDownload(Context context) {
    this.context = context.getApplicationContext();
    serviceHelper = new ServiceHelper(context);
  }

  static {
    RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) throws Exception {
        if (throwable instanceof InterruptedException) {
          log("Thread interrupted");
        } else if (throwable instanceof InterruptedIOException) {
          log("Io interrupted");
        } else if (throwable instanceof SocketException) {
          log("Socket error");
        }
      }
    });
  }
}
