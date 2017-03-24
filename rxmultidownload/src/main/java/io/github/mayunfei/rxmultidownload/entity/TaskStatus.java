package io.github.mayunfei.rxmultidownload.entity;

/**
 * Created by yunfei on 17-3-14.
 */

public class TaskStatus {
  //线程等待状态
  public static final int STATUS_QUEUE = 0;
  //下载中
  public static final int STATUS_CONNECTING = 1;
  //暂停
  public static final int STATUS_PAUSE = 2;
  //取消
  public static final int STATUS_CANCEL = 3;
  //异常
  public static final int STATUS_ERROR = 4;
  //下载完成
  public static final int STATUS_FINISHED = 5;
}
