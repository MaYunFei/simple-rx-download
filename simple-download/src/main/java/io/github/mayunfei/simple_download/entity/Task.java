package io.github.mayunfei.simple_download.entity;

import android.database.Cursor;
import io.github.mayunfei.simple_download.db.DBHelper;
import rx.functions.Func1;

/**
 * 基础下载
 * Created by mayunfei on 17-3-21.
 */

public class Task {

  public static final String ID = "id";
  public static final String URL = "url";
  public static final String PATH = "path";
  public static final String TOTAL_SIZE = "total_size";
  public static final String COMPLETED_SIZE = "completed_size";
  public static final String TABLE_NAME = "tasks";
  public static final String STATUS = "status";

  public static final String CREATE_SQL = "CREATE TABLE if not exists "
      + TABLE_NAME
      + "("
      + ID
      + " INTEGER PRIMARY KEY AUTOINCREMENT,"
      + URL
      + " TEXT UNIQUE,"
      + PATH
      + " TEXT UNIQUE,"
      + TOTAL_SIZE
      + " LONG,"
      + COMPLETED_SIZE
      + " LONG,"
      + STATUS
      + " INTEGER "
      + ");";
  private int id;
  private String url;
  private String path;
  //已经完成的大小 用于断点续传
  private long totalSize;
  private long completedSize;
  private int status = TaskStatus.PAUSE;

  private Task(Builder builder) {
    id = builder.id;
    url = builder.url;
    path = builder.path;
    totalSize = builder.totalSize;
    completedSize = builder.completedSize;
    status = builder.status;
  }

  public static final Func1<Cursor, Task> MAPPER = new Func1<Cursor, Task>() {
    @Override public Task call(Cursor cursor) {
      int id = DBHelper.getInt(cursor, ID);
      String url = DBHelper.getString(cursor, URL);
      String path = DBHelper.getString(cursor, PATH);
      long totalSize = DBHelper.getLong(cursor, TOTAL_SIZE);
      long completedSize = DBHelper.getLong(cursor, COMPLETED_SIZE);
      int status = DBHelper.getInt(cursor, STATUS);

      return Task.newBuilder()
          .id(id)
          .url(url)
          .path(path)
          .totalSize(totalSize)
          .completedSize(completedSize)
          .status(status)
          .build();
    }
  };

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

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

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private int id;
    private String url;
    private String path;
    private long totalSize;
    private long completedSize;
    private int status;

    private Builder() {
    }

    public Builder id(int val) {
      id = val;
      return this;
    }

    public Builder url(String val) {
      url = val;
      return this;
    }

    public Builder path(String val) {
      path = val;
      return this;
    }

    public Builder totalSize(long val) {
      totalSize = val;
      return this;
    }

    public Builder completedSize(long val) {
      completedSize = val;
      return this;
    }

    public Builder status(int val) {
      status = val;
      return this;
    }

    public Task build() {
      return new Task(this);
    }
  }
}
