package io.github.mayunfei.rxdownload.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mayunfei on 17-3-23.
 */

public class DownloadDao {

  private volatile static DownloadDao singleton;
  private SQLiteHelper sqLiteHelper;
  private final Object databaseLock = new Object();
  private volatile SQLiteDatabase readableDatabase;
  private volatile SQLiteDatabase writableDatabase;

  private DownloadDao(Context context) {
    sqLiteHelper = new SQLiteHelper(context.getApplicationContext());
  }

  public static DownloadDao getSingleton(Context context) {
    if (singleton == null) {
      synchronized (DownloadDao.class) {
        if (singleton == null) {
          singleton = new DownloadDao(context);
        }
      }
    }
    return singleton;
  }

  public void closeDataBase() {
    synchronized (databaseLock) {
      readableDatabase = null;
      writableDatabase = null;
      sqLiteHelper.close();
    }
  }

  private SQLiteDatabase getWritableDatabase() {
    SQLiteDatabase db = writableDatabase;
    if (db == null) {
      synchronized (databaseLock) {
        db = writableDatabase;
        if (db == null) {
          db = writableDatabase = sqLiteHelper.getWritableDatabase();
        }
      }
    }
    return db;
  }

  private SQLiteDatabase getReadableDatabase() {
    SQLiteDatabase db = readableDatabase;
    if (db == null) {
      synchronized (databaseLock) {
        db = readableDatabase;
        if (db == null) {
          db = readableDatabase = sqLiteHelper.getReadableDatabase();
        }
      }
    }
    return db;
  }
}
