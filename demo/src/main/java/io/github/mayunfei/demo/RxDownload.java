package io.github.mayunfei.demo;

import android.os.Environment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Response;

import static io.github.mayunfei.demo.DownloadStatus.DOWNLOADING;
import static io.github.mayunfei.demo.DownloadStatus.ERROR;
import static io.github.mayunfei.demo.DownloadStatus.FINISH;

/**
 * Created by yunfei on 17-3-25.
 */

public class RxDownload {

  private static final long DOWNLOAD_CHUNK_SIZE = 2048;

  public static FlowableProcessor<DownloadEvent> download(DownloadService downloadService,
      final String url) {
    final BehaviorProcessor<DownloadEvent> processor = BehaviorProcessor.create();

    final DownloadEvent downloadEvent = new DownloadEvent();
    Disposable subscribe = downloadService.download(url)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(new Consumer<Response<ResponseBody>>() {
          @Override public void accept(@NonNull Response<ResponseBody> response) throws Exception {
            if (response.isSuccessful()) {
              ResponseBody body = response.body();
              long total = body.contentLength();
              downloadEvent.setTotalSize(total);
              BufferedSink sink = null;
              BufferedSource source = null;
              try {
                File file = new File(getDefPath(), getFileName(url));
                sink = Okio.buffer(Okio.sink(file));
                long totalRead = 0;
                long read = 0;
                source = body.source();
                while ((read = (source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE))) != -1) {
                  totalRead += read;
                  downloadEvent.setStatus(DOWNLOADING);
                  downloadEvent.setCompletedSize(totalRead);
                  processor.onNext(downloadEvent);
                }
                sink.writeAll(source);
                source.close();
                downloadEvent.setStatus(FINISH);
                processor.onNext(downloadEvent);
              } finally {
                IOUtils.close(sink, source);
              }
            } else {
              downloadEvent.setStatus(ERROR);
              processor.onNext(downloadEvent);
            }
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            downloadEvent.setStatus(ERROR);
            processor.onNext(downloadEvent);
          }
        });
    return processor;
  }

  public static String getDefPath() {
    return Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator
        + "rxdownload";
  }

  public static String getFileName(String url) {
    return url.substring(url.lastIndexOf('/') + 1);
  }
}
