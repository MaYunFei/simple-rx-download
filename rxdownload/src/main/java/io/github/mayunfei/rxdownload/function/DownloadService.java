package io.github.mayunfei.rxdownload.function;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import io.github.mayunfei.rxdownload.db.DownloadDao;
import io.github.mayunfei.rxdownload.entity.DownloadEvent;
import io.github.mayunfei.rxdownload.entity.DownloadTask;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import static io.github.mayunfei.rxdownload.function.Utils.log;

public class DownloadService extends Service {
  public static final String INTENT_KEY = "io.github.mayunfei.rxdownload.max_download_number";

  private DownloadBinder mBinder;

  private BlockingQueue<DownloadTask> downloadQueue;
  /**
   * 当前任务列表
   */
  private Map<String, DownloadTask> taskMap;
  /**
   * 用于监听的列表
   */
  private Map<String, FlowableProcessor<DownloadEvent>> processorMap;

  //信号量控制线程个数
  private Semaphore semaphore;
  private DownloadDao dao;

  public DownloadService() {
  }

  @Override public void onCreate() {
    super.onCreate();
    mBinder = new DownloadBinder();
    dao = DownloadDao.getSingleton(this);
    downloadQueue = new LinkedBlockingQueue<>();
    processorMap = new ConcurrentHashMap<>();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
      int maxDownloadNumber = intent.getIntExtra(INTENT_KEY, 5); //默认5个线程
      semaphore = new Semaphore(maxDownloadNumber);
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override public IBinder onBind(Intent intent) {
    startDispatch();
    return mBinder;
  }

  /**
   * 等待任务
   */
  private void startDispatch() {
    Disposable disposable = Observable.create(new ObservableOnSubscribe<DownloadTask>() {
      @Override public void subscribe(ObservableEmitter<DownloadTask> emitter) throws Exception {
        DownloadTask task;
        while (!emitter.isDisposed()) {
          try {
            log("DownloadQueue waiting");
            task = downloadQueue.take();
            log("Task is take");
          } catch (InterruptedException e) {
            log("Interrupt blocking queue.");
            continue;
          }
          emitter.onNext(task);
        }
        emitter.onComplete();
      }
    }).subscribeOn(Schedulers.newThread()).subscribe(new Consumer<DownloadTask>() {
      @Override public void accept(@NonNull DownloadTask downloadTask) throws Exception {
        downloadTask.start(semaphore);
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(@NonNull Throwable throwable) throws Exception {
        log(throwable);
      }
    });
  }

  public class DownloadBinder extends Binder {
    public DownloadService getService() {
      return DownloadService.this;
    }
  }

  public void addDownloadTask(DownloadTask downloadTask) throws InterruptedException {
    downloadTask.init(taskMap,processorMap);
    downloadQueue.put(downloadTask);
  }
}
