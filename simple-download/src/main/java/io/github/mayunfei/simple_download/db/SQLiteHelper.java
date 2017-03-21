package io.github.mayunfei.simple_download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.github.mayunfei.simple_download.entity.Task;

/**
 * Created by mayunfei on 17-3-21.
 */

class SQLiteHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "download.db";
  private static final int VERSION = 1;

  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(Task.CREATE_SQL);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //数据库升级
  }
}
