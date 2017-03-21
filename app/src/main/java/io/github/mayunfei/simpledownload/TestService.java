package io.github.mayunfei.simpledownload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {
  public static final  String TAG = "SERVICE";
  public TestService() {
  }

  @Override public void onCreate() {
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i(TAG,"onStartCommand");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override public void onRebind(Intent intent) {
    Log.i(TAG,"onRebind");
    super.onRebind(intent);
  }

  @Override public boolean onUnbind(Intent intent) {
    Log.i(TAG,"onUnbind");
    return super.onUnbind(intent);
  }


  @Override public void onDestroy() {
    Log.i(TAG,"onDestroy");
    super.onDestroy();
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    //throw new UnsupportedOperationException("Not yet implemented");
    return  null;
  }
}
