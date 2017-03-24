package io.github.mayunfei.rxmultidownload.entity;

import android.database.Cursor;
import java.util.List;

/**
 * 下载整体
 * Created by yunfei on 17-3-14.
 */

public class TaskBundle {

  public static final String TASK_BUNDLE_TABLE_NAME = "taskBundle";
  public static final String COLUMN_BUNDLE_ID = "bundleId";
  public static final String COLUMN_KEY = "key";
  public static final String COLUMN_FILEPATH = "filePath";
  public static final String COLUMN_TOTAL_SIZE = "totalSize";
  public static final String COLUMN_COMPLETED_SIZE = "completeSize";
  public static final String COLUMN_STATUS = "status";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_ARG0 = "arg0";
  public static final String COLUMN_ARG1 = "arg1";
  public static final String COLUMN_ARG2 = "arg2";
  public static final String COLUMN_ARG3 = "arg3";
  public static final String COLUMN_ARG4 = "arg4";

  public static final String CREATE_SQL = "CREATE TABLE if not exists "
      +
      TASK_BUNDLE_TABLE_NAME
      + "("
      + COLUMN_BUNDLE_ID
      + " INTEGER PRIMARY KEY AUTOINCREMENT,"
      + COLUMN_KEY
      + " TEXT UNIQUE,"
      //唯一
      + COLUMN_TOTAL_SIZE
      + " INTEGER,"
      + COLUMN_COMPLETED_SIZE
      + " INTEGER,"
      + COLUMN_STATUS
      + " INTEGER,"
      + COLUMN_TYPE
      + " INTEGER,"
      //应该有默认值
      + COLUMN_FILEPATH
      + " TEXT,"
      + COLUMN_ARG0
      + " TEXT,"
      + COLUMN_ARG1
      + " TEXT,"
      + COLUMN_ARG2
      + " TEXT,"
      + COLUMN_ARG3
      + " TEXT,"
      + COLUMN_ARG4
      + " TEXT"
      + ");";

  private int bundleId = -1;
  //唯一值
  private String key;
  private String filePath;
  private int totalSize;
  private int completeSize;
  private int status;
  private int type;
  private List<TaskEntity> taskList;
  private String arg0;
  private String arg1;
  private String arg2;
  private String arg3;
  private String arg4;

  @Override public String toString() {
    String tmpStatus = "";
    switch (status) {
      case TaskStatus.STATUS_CANCEL:
        tmpStatus = "取消";
        break;
      case TaskStatus.STATUS_QUEUE:
        tmpStatus = "等待";
        break;
      case TaskStatus.STATUS_CONNECTING:
        tmpStatus = "连接";
        break;
      case TaskStatus.STATUS_PAUSE:
        tmpStatus = "暂停";
        break;
      case TaskStatus.STATUS_ERROR:
        tmpStatus = "错误";
        break;
      case TaskStatus.STATUS_FINISHED:
        tmpStatus = "完成";
        break;
    }

    return "TaskBundle{" +
        "bundleId=" + bundleId +
        ", key='" + key + '\'' +
        ", totalSize=" + totalSize +
        ", completeSize=" + completeSize +
        ", status=" + tmpStatus +
        ", filePath='" + filePath + '\'' +
        ", taskList=" + taskList +
        '}';
  }

  public TaskBundle() {

  }

  public void init(TaskBundle taskBundle) {
    bundleId = taskBundle.bundleId;
    key = taskBundle.key;
    filePath = taskBundle.filePath;
    totalSize = taskBundle.totalSize;
    completeSize = taskBundle.completeSize;
    status = taskBundle.status;
    type = taskBundle.type;
    arg0 = taskBundle.arg0;
    arg1 = taskBundle.arg1;
    arg2 = taskBundle.arg2;
    arg3 = taskBundle.arg3;
    arg4 = taskBundle.arg4;
  }
}
