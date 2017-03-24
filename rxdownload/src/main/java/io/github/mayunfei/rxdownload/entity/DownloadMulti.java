package io.github.mayunfei.rxdownload.entity;

/**
 * Created by mayunfei on 17-3-24.
 */

public class DownloadMulti {
  public static final String TABLE_NAME = "download_task";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_KEY = "key";
  public static final String COLUMN_ARGS0 = "args0";
  public static final String COLUMN_ARGS1 = "args1";
  public static final String COLUMN_ARGS2 = "args2";
  public static final String COLUMN_ARGS3 = "args3";
  public static final String COLUMN_ARGS4 = "args4";

  public static final String CREAT_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
      COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
      COLUMN_KEY + " TEXT NOT NULL," +
      COLUMN_ARGS0 + " TEXT," +
      COLUMN_ARGS1 + " TEXT," +
      COLUMN_ARGS2 + " TEXT," +
      COLUMN_ARGS3 + " TEXT," +
      COLUMN_ARGS4 + " TEXT" +
      " )";

  private int id;
  private String key;
  private String args0;
  private String args1;
  private String args2;
  private String args3;
  private String args4;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getArgs0() {
    return args0;
  }

  public void setArgs0(String args0) {
    this.args0 = args0;
  }

  public String getArgs1() {
    return args1;
  }

  public void setArgs1(String args1) {
    this.args1 = args1;
  }

  public String getArgs2() {
    return args2;
  }

  public void setArgs2(String args2) {
    this.args2 = args2;
  }

  public String getArgs3() {
    return args3;
  }

  public void setArgs3(String args3) {
    this.args3 = args3;
  }

  public String getArgs4() {
    return args4;
  }

  public void setArgs4(String args4) {
    this.args4 = args4;
  }
}
