package io.github.mayunfei.rxdownload.function;

import io.reactivex.disposables.Disposable;

/**
 * Created by yunfei on 17-3-23.
 */

public class RxUtils {
  public static void dispose(Disposable disposable) {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
}
