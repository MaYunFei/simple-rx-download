package io.github.mayunfei.rxdownload.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.github.mayunfei.rxdownload.entity.DownloadBean;
import io.github.mayunfei.rxdownload.entity.DownloadMulti;

/**
 * Created by mayunfei on 17-3-21.
 */

class SQLiteHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "rxdownload.db";
  private static final int VERSION = 1;

  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(DownloadBean.CREAT_SQL);
    db.execSQL(DownloadMulti.CREAT_SQL);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //数据库升级
  }
}
