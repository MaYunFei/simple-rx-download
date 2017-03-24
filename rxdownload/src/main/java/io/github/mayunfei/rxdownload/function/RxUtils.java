package io.github.mayunfei.rxdownload.function;

import io.github.mayunfei.rxdownload.entity.DownloadEvent;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import java.util.Map;

/**
 * Created by yunfei on 17-3-23.
 */

public class RxUtils {
  public static void dispose(Disposable disposable) {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }

  /**
   * 下载事件的监听
   */
  public static FlowableProcessor<DownloadEvent> createProcessor(String key,
      Map<String, FlowableProcessor<DownloadEvent>> processorMap) {

    if (processorMap.get(key) == null) {
      FlowableProcessor<DownloadEvent> processor =
          BehaviorProcessor.<DownloadEvent>create().toSerialized();
      processorMap.put(key, processor);
    }
    return processorMap.get(key);
  }
}
