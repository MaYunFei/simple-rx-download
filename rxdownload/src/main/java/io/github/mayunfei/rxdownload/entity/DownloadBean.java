package io.github.mayunfei.rxdownload.entity;

import android.content.ContentValues;
import java.util.Date;

import static io.github.mayunfei.rxdownload.function.Utils.empty;

/**
 * 基本的下载信息
 * Created by mayunfei on 17-3-24.
 */

public class DownloadBean {

  public static final String TABLE_NAME = "download_task";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_URL = "url";
  public static final String COLUMN_SAVE_NAME = "save_name";
  public static final String COLUMN_SAVE_PATH = "save_path";
  public static final String COLUMN_DOWNLOAD_SIZE = "download_size";
  public static final String COLUMN_TOTAL_SIZE = "total_size";
  public static final String COLUMN_DOWNLOAD_FLAG = "download_flag";
  public static final String COLUMN_MULTIKEY = "multi_key";

  public static final String CREAT_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
      COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
      COLUMN_URL + " TEXT NOT NULL," +
      COLUMN_SAVE_NAME + " TEXT," +
      COLUMN_SAVE_PATH + " TEXT," +
      COLUMN_TOTAL_SIZE + " LONG," +
      COLUMN_DOWNLOAD_SIZE + " LONG," +
      COLUMN_DOWNLOAD_FLAG + " INTEGER" +
      " )";

  static ContentValues insert(DownloadBean bean, int flag, String multiKey) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_URL, bean.getUrl());
    values.put(COLUMN_SAVE_NAME, bean.getSaveName());
    values.put(COLUMN_SAVE_PATH, bean.getSavePath());
    values.put(COLUMN_DOWNLOAD_FLAG, flag);
    if (empty(multiKey)) {
      values.put(COLUMN_MULTIKEY, multiKey);
    }
    return values;
  }

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
