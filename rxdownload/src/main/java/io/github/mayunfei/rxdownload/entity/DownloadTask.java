package io.github.mayunfei.rxdownload.entity;

import io.github.mayunfei.rxdownload.db.DownloadDao;
import io.github.mayunfei.rxdownload.function.DownloadApi;
import io.reactivex.processors.FlowableProcessor;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * 下载任务
 * Created by yunfei on 17-3-23.
 */

public abstract class DownloadTask {

  /**
   * 通过监听这个可以得到下载状态
   */
  FlowableProcessor<DownloadEvent> processor;
  private boolean canceled = false;
  private boolean completed = false;
  protected DownloadApi downloadApi;

  public abstract void init(Map<String, DownloadTask> taskMapMap,
      Map<String, FlowableProcessor<DownloadEvent>> processorMap);

  public boolean isCanceled() {
    return canceled;
  }

  public abstract void start(final Semaphore semaphore) throws InterruptedException;

  protected abstract String getKey();

  public abstract void insertOrUpdateDownloadTask(DownloadDao dao);
}
