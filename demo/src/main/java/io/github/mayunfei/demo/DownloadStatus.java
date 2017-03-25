package io.github.mayunfei.demo;

/**
 * Created by yunfei on 17-3-25.
 */

public class DownloadStatus {
  private DownloadStatus() {
  }

  public static final int DOWNLOADING = 0;
  public static final int PAUSE = 2;
  public static final int QUEUE = 3;
  public static final int FINISH = 4;
  public static final int ERROR = 5;
}
