package io.github.mayunfei.simpledownload;

import android.Manifest;
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
    RxDownLoad.getInstance().init(retrofit);
  }

  @Override public void onClick(View v) {
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
