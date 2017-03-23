package io.github.mayunfei.rxdownload;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import io.github.mayunfei.rxdownload.function.DownloadService;
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


  private static final Object object = new Object();
  @SuppressLint("StaticFieldLeak") private volatile static RxDownload instance;

  private volatile static boolean bound = false;
  private int maxDownloadNumber = 5;

  private final Context context;

  //信号量  用于绑定service
  private Semaphore semaphore;

  private DownloadService downloadService;

  private RxDownload(Context context) {
    this.context = context.getApplicationContext();
    semaphore = new Semaphore(1);
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

  private Observable<?> createGeneralObservable(final GeneralObservableCallback callback) {
    return Observable.create(new ObservableOnSubscribe<Object>() {
      @Override public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
        if (!bound) {
          semaphore.acquire();
          if (!bound) {
            startBindServiceAndDo(new ServiceConnectedCallback() {
              @Override public void call() {
                doCall(callback, emitter);
                semaphore.release();
              }
            });
          } else {
            doCall(callback, emitter);
            semaphore.release();
          }
        } else {
          doCall(callback, emitter);
        }
      }
    }).subscribeOn(Schedulers.io());
  }

  private void doCall(GeneralObservableCallback callback, ObservableEmitter<Object> emitter) {
    if (callback != null) {
      try {
        callback.call();
      } catch (Exception e) {
        emitter.onError(e);
      }
    }
    emitter.onNext(object);
    emitter.onComplete();
  }

  /**
   * start and bind service.
   *
   * @param callback Called when service connected.
   */
  private void startBindServiceAndDo(final ServiceConnectedCallback callback) {
    Intent intent = new Intent(context, DownloadService.class);
    intent.putExtra(DownloadService.INTENT_KEY, maxDownloadNumber);
    context.startService(intent);
    context.bindService(intent, new ServiceConnection() {
      @Override public void onServiceConnected(ComponentName name, IBinder binder) {
        DownloadService.DownloadBinder downloadBinder = (DownloadService.DownloadBinder) binder;
        downloadService = downloadBinder.getService();
        context.unbindService(this);
        bound = true;
        callback.call();
      }

      @Override public void onServiceDisconnected(ComponentName name) {
        //注意!!这个方法只会在系统杀掉Service时才会调用!!
        bound = false;
      }
    }, Context.BIND_AUTO_CREATE);
  }

  private interface GeneralObservableCallback {
    void call() throws Exception;
  }

  private interface ServiceConnectedCallback {
    void call();
  }
}
