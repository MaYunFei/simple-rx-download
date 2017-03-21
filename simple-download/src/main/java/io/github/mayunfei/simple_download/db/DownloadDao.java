package io.github.mayunfei.simple_download.db;

import android.content.Context;
import android.database.Cursor;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import io.github.mayunfei.simple_download.entity.Task;
import io.github.mayunfei.simple_download.entity.TaskStatus;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mayunfei on 17-3-21.
 */

public class DownloadDao {
  private final BriteDatabase db;

  public DownloadDao(Context context) {
    db = new SqlBrite.Builder().build()
        .wrapDatabaseHelper(new SQLiteHelper(context.getApplicationContext()), Schedulers.io());
  }

  public <T> Observable<List<T>> getTaskByUrl(final String url, Func1<Cursor, T> func1) {
    return Observable.create(new Observable.OnSubscribe<List<T>>() {

      @Override public void call(Subscriber<? super List<T>> subscriber) {

      }
    });
  }
}
