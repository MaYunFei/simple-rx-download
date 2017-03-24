package io.github.mayunfei.rxdownload;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import io.github.mayunfei.rxdownload.entity.DownloadStatus;
import io.github.mayunfei.rxdownload.function.DownloadHelper;
import io.github.mayunfei.rxdownload.function.DownloadService;
import io.github.mayunfei.rxdownload.function.IOUtils;
import io.github.mayunfei.rxdownload.function.ServiceHelper;
import io.reactivex.FlowableEmitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.concurrent.Semaphore;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

import static io.github.mayunfei.rxdownload.function.Utils.log;

/**
 * Created by mayunfei on 17-3-23.
 */

public class RxDownload {

  @SuppressLint("StaticFieldLeak") private volatile static RxDownload instance;

  private final Context context;
  private ServiceHelper serviceHelper;
  private DownloadService downloadService;
  private DownloadHelper downloadHelper;

  private RxDownload(Context context) {
    this.context = context.getApplicationContext();
    serviceHelper = new ServiceHelper(context);
    downloadHelper = new DownloadHelper();
  }

  public static RxDownload getInstance(Context context) {
    if (instance == null) {
      synchronized (RxDownload.class) {
        if (instance == null) {
          instance = new RxDownload(context);
        }
      }
    }
    return instance;
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
