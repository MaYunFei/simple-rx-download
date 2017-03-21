package io.github.mayunfei.simple_download;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.InputStream;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by mayunfei on 17-3-21.
 */

public class RxDownLoad {
  private static final long DOWNLOAD_CHUNK_SIZE = 1024;
  private DownloadApi mDownloadApi;
  private Retrofit mRetrofit;

  private static RxDownLoad instance;

  public static RxDownLoad getInstance() {
    if (instance == null) {
      instance = new RxDownLoad();
    }
    return instance;
  }

  public void init(Retrofit retrofit) {
    mDownloadApi = retrofit.create(DownloadApi.class);
  }

  public Observable<Integer> download(final String url) {
    return download(url, null, null);
  }

  public Observable<Integer> download(final String url, final String path, final String name) {
    return mDownloadApi.RxDownload(url)
        .flatMap(new Func1<Response<ResponseBody>, Observable<Integer>>() {
          @Override public Observable<Integer> call(final Response<ResponseBody> response) {
            return Observable.create(new Observable.OnSubscribe<Integer>() {
              @Override public void call(Subscriber<? super Integer> subscriber) {
                if (response.isSuccessful()) {
                  BufferedSink sink = null;
                  BufferedSource source = null;
                  String filePath = path;
                  String fileName = name;
                  try {
                    ResponseBody body = response.body();
                    long totalSize = body.contentLength();
                    if (TextUtils.isEmpty(path)) {
                      filePath = getDefPath();
                    }
                    if (TextUtils.isEmpty(name)) {
                      fileName = getFileName(url);
                    }

                    File fileDir = new File(filePath);
                    if (!fileDir.exists()) {
                      fileDir.mkdirs();
                    }

                    File file = new File(filePath, fileName);
                    sink = Okio.buffer(Okio.sink(file));
                    long totalRead = 0;
                    long read = 0;
                    source = body.source();
                    while ((read = (source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE))) != -1) {
                      totalRead += read;
                      subscriber.onNext(Long.valueOf(totalRead * 100 / totalSize).intValue());
                    }
                    sink.writeAll(source);
                    source.close();
                  } catch (Exception e) {
                    subscriber.onError(e);
                  } finally {
                    IOUtils.close(sink, source);
                  }
                } else {
                  subscriber.onError(new SocketException("请求不成功"));
                }
              }
            });
          }
        })
        .debounce(1000, TimeUnit.MICROSECONDS)
        .subscribeOn(AndroidSchedulers.mainThread());
  }

  private String getDefPath() {
    return Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator
        + "rxdownload";
  }

  private String getFileName(String url) {
    return url.substring(url.lastIndexOf('/') + 1);
  }
}
