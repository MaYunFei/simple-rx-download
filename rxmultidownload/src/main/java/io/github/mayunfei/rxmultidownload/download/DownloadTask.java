package io.github.mayunfei.rxmultidownload.download;

import io.github.mayunfei.rxmultidownload.entity.TaskBundle;
import io.reactivex.Observer;
import io.reactivex.processors.FlowableProcessor;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 下载类
 * Created by mayunfei on 17-3-24.
 */

public class DownloadTask {
  private TaskBundle taskBundle;
  private boolean canceled = false;
  private boolean completed = false;
  protected DownloadApi downloadApi;

  private AtomicInteger completeNumber;
  private AtomicInteger failedNumber;

  private Observer<TaskBundle> observer;

  public DownloadTask(TaskBundle taskBundle) {
    this.taskBundle = taskBundle;
  }

  public void init(Map<String, DownloadTask> taskMap,
      Map<String, FlowableProcessor<TaskBundle>> processorMap) {

  }

  public void start(Semaphore semaphore) throws InterruptedException {
    if (isCanceled()) {
      return;
    }
    semaphore.acquire();
    if (isCanceled()) {
      semaphore.release();
      return;
    }

  }

  public boolean isCanceled() {
    return canceled;
  }
}
