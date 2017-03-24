package io.github.mayunfei.rxdownload.entity;

import io.github.mayunfei.rxdownload.db.DownloadDao;
import io.github.mayunfei.rxdownload.function.IOUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.Semaphore;
import okhttp3.ResponseBody;
import org.reactivestreams.Publisher;
import retrofit2.Response;

import static io.github.mayunfei.rxdownload.function.RxUtils.createProcessor;

/**
 * Created by yunfei on 17-3-23.
 */

public class SingleTask extends DownloadTask {

  private DownloadBean bean;
  private String multiKey;

  public SingleTask(DownloadBean bean) {
    this.bean = bean;
  }

  @Override public void init(Map<String, DownloadTask> taskMapMap,
      Map<String, FlowableProcessor<DownloadEvent>> processorMap) {
    DownloadTask task = taskMapMap.get(getKey());
    if (task == null) {
      taskMapMap.put(getKey(), this);
    } else {
      if (task.isCanceled()) {
        taskMapMap.put(getKey(), this);
      } else {
        throw new IllegalArgumentException("下载已经存在" + getKey());
      }
    }
    this.processor = createProcessor(getKey(), processorMap);
  }

  @Override public void start(final Semaphore semaphore) throws InterruptedException {
    if (isCanceled()) {
      return;
    }

    semaphore.acquire();

    if (isCanceled()) {
      semaphore.release();
      return;
    }

    downloadApi.download(null, "").doFinally(new Action() {
      @Override public void run() throws Exception {
        semaphore.release();
      }
    }).flatMap(new Function<Response<ResponseBody>, Publisher<DownloadStatus>>() {
      @Override
      public Publisher<DownloadStatus> apply(@NonNull final Response<ResponseBody> response)
          throws Exception {
        return Flowable.create(new FlowableOnSubscribe<DownloadStatus>() {
          @Override public void subscribe(FlowableEmitter<DownloadStatus> emitter)
              throws Exception {
            saveFile(emitter, new File(bean.getSavePath(), bean.getSaveName()), response);
          }
        }, BackpressureStrategy.LATEST);
      }
    });
  }

  @Override protected String getKey() {
    return bean.getUrl();
  }

  @Override public void insertOrUpdateDownloadTask(DownloadDao dao) {
      if (dao.existsSingleTask(getKey())){
        dao.insert(bean,DownloadFlag.WAITING,multiKey);
      }
  }

  /**
   * 真正的下载
   */
  public void saveFile(FlowableEmitter<DownloadStatus> emitter, File saveFile,
      Response<ResponseBody> resp) {

    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      try {
        int readLen;
        int downloadSize = 0;
        byte[] buffer = new byte[8192];

        DownloadStatus status = new DownloadStatus();
        inputStream = resp.body().byteStream();
        outputStream = new FileOutputStream(saveFile);

        long contentLength = resp.body().contentLength();

        status.setTotalSize(contentLength);

        while ((readLen = inputStream.read(buffer)) != -1 && !emitter.isCancelled()) {
          outputStream.write(buffer, 0, readLen);
          downloadSize += readLen;
          status.setDownloadSize(downloadSize);
          emitter.onNext(status);
        }

        outputStream.flush(); // This is important!!!
        emitter.onComplete();
      } finally {
        IOUtils.close(inputStream, outputStream, resp.body());
      }
    } catch (IOException e) {
      emitter.onError(e);
    }
  }
}
