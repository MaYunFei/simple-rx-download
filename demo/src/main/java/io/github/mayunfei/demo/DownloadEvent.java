package io.github.mayunfei.demo;

import static io.github.mayunfei.demo.DownloadStatus.PAUSE;

/**
 * Created by yunfei on 17-3-25.
 */

public class DownloadEvent {
  private long totalSize;
  private long completedSize;
  private int status = PAUSE;

  public long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(long totalSize) {
    this.totalSize = totalSize;
  }

  public long getCompletedSize() {
    return completedSize;
  }

  public void setCompletedSize(long completedSize) {
    this.completedSize = completedSize;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override public String toString() {
    return "DownloadEvent{"
        + "totalSize="
        + totalSize
        + ", completedSize="
        + completedSize
        + ", status="
        + status
        + '}';
  }
}
