package io.github.mayunfei.rxdownload.entity;

import io.github.mayunfei.rxdownload.function.IOUtils;
import io.reactivex.FlowableEmitter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by yunfei on 17-3-23.
 */

public class SingleTask extends DownloadTask {
  @Override public void start(Semaphore semaphore) throws InterruptedException {

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
