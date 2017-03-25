package io.github.mayunfei.demotest;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.github.mayunfei.demo.DownloadEvent;
import io.github.mayunfei.demo.DownloadService;
import io.github.mayunfei.demo.RxDownload;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.btn).setOnClickListener(this);

    ActivityCompat.requestPermissions(this,
        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
  }

  @Override public void onClick(View v) {
    Retrofit retrofit =
        new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            .baseUrl("http://wwww.exam.com/").build();
    DownloadService downloadService = retrofit.create(DownloadService.class);
    FlowableProcessor<DownloadEvent> download = RxDownload.download(downloadService,
        "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");

    download.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DownloadEvent>() {
      @Override public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
        Log.i("YunFei -----------", downloadEvent.toString());
      }
    });
  }
}
