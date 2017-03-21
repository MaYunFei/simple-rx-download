package io.github.mayunfei.simpledownload;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.tbruyelle.rxpermissions.RxPermissions;
import io.github.mayunfei.simple_download.RxDownLoad;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private OkHttpClient okHttpClient;
  private Retrofit retrofit;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.btn_download).setOnClickListener(this);
    findViewById(R.id.btn_start).setOnClickListener(this);
    findViewById(R.id.btn_stop).setOnClickListener(this);
    findViewById(R.id.btn_binding).setOnClickListener(this);

    okHttpClient = new OkHttpClient.Builder().build();
    retrofit = new Retrofit.Builder().addCallAdapterFactory(
        RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl("http://gank.io/api/")
        .build();
    new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(new Action1<Boolean>() {
          @Override public void call(Boolean aBoolean) {

          }
        });
    RxDownLoad.getInstance().init(this,retrofit);
  }

  @Override public void onClick(View v) {
    Intent intent = new Intent(this,TestService.class);
    switch (v.getId()){
      case R.id.btn_download:
        break;
      case R.id.btn_start:

        startService(intent);
        break;
      case R.id.btn_stop:
        stopService(intent);
        break;
      case R.id.btn_binding:
        bindService(intent, new ServiceConnection() {
          @Override public void onServiceConnected(ComponentName name, IBinder service) {

          }

          @Override public void onServiceDisconnected(ComponentName name) {

          }
        },BIND_ABOVE_CLIENT);
        break;
    }

  }

  private void download(){
    RxDownLoad.getInstance()
        .download("http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk")
        .subscribe(new Action1<Integer>() {
          @Override public void call(Integer integer) {
            Log.i("download", "  " + integer);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Log.e("download", "  " + throwable.toString());
          }
        });
  }
}
