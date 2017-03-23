package io.github.mayunfei.rxdownload.function;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import io.reactivex.Observable;

public class DownloadService extends Service {
  public static final String INTENT_KEY = "io.github.mayunfei.rxdownload.max_download_number";
  private DownloadBinder mBinder;

  public DownloadService() {
  }

  @Override public void onCreate() {
    super.onCreate();
    mBinder = new DownloadBinder();
  }

  @Override public IBinder onBind(Intent intent) {
    return mBinder;
  }

  public class DownloadBinder extends Binder {
    public DownloadService getService() {
      return DownloadService.this;
    }
  }
}
