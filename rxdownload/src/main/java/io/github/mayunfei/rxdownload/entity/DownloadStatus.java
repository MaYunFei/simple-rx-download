package io.github.mayunfei.rxdownload.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.NumberFormat;

public class DownloadStatus {

  private long totalSize;
  private long downloadSize;

  public DownloadStatus() {
  }

  public DownloadStatus(long downloadSize, long totalSize) {
    this.downloadSize = downloadSize;
    this.totalSize = totalSize;
  }

  public DownloadStatus(boolean isChunked, long downloadSize, long totalSize) {
    this.downloadSize = downloadSize;
    this.totalSize = totalSize;
  }

  protected DownloadStatus(Parcel in) {
    this.totalSize = in.readLong();
    this.downloadSize = in.readLong();
  }

  public long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(long totalSize) {
    this.totalSize = totalSize;
  }

  public long getDownloadSize() {
    return downloadSize;
  }

  public void setDownloadSize(long downloadSize) {
    this.downloadSize = downloadSize;
  }

  /**
   * 获得下载的百分比数值
   *
   * @return example: 5%  will return 5, 10% will return 10.
   */
  public long getPercentNumber() {
    double result;
    if (totalSize == 0L) {
      result = 0.0;
    } else {
      result = downloadSize * 1.0 / totalSize;
    }
    return (long) (result * 100);
  }
}
