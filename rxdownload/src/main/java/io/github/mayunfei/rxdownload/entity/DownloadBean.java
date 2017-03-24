package io.github.mayunfei.rxdownload.entity;

/**
 * Created by mayunfei on 17-3-24.
 */

public class DownloadBean {
  private String url;
  private String saveName;
  private String savePath;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSaveName() {
    return saveName;
  }

  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }

  public String getSavePath() {
    return savePath;
  }

  public void setSavePath(String savePath) {
    this.savePath = savePath;
  }
}
