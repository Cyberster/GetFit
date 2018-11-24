package com.w3epic.getfit.SQLiteHelperClasss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anonymouse on 6/26/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME          = "getfit.db";

    public static final String TABLE_FOOD_LOG   = "food_log";
    public static final String TABLE_WORKOUT_LOG = "workout_log";

    public static final String COL_UID          = "uid";
    public static final String COL_WID          = "wid";
    public static final String COL_RESOURCE_ID  = "resource_id";
    public static final String COL_NAME         = "name";
    public static final String COL_SERVING      = "serving";
    public static final String COL_QUANTITY     = "quantity";
    public static final String COL_UNIT         = "unit";
    public static final String COL_CALORIES     = "calories";
    public static final String COL_THUMBNAIL    = "thumbnail";
    public static final String COL_MET          = "met";
    public static final String COL_SET_COUNT    = "set_count";
    public static final String COL_DURATION_MIN = "duration_min";
    public static final String COL_REPETITION   = "repetation";
    public static final String COL_KCAL_BURNT   = "kcal_burnt";
    public static final String COL_TIMESTAMP    = "timestamp";



    private static final String SQL_CREATE_TABLE_FOOD_LOG =
            "CREATE TABLE IF NOT EXISTS " + TABLE_FOOD_LOG + " (" +
                        COL_UID         + " VARCHAR, " +
                        COL_RESOURCE_ID + " VARCHAR, " +
                        COL_NAME        + " VARCHAR, " +
                        COL_SERVING     + " VARCHAR, " +
                        COL_QUANTITY    + " VARCHAR, " +
                        COL_UNIT        + " VARCHAR, " +
                        COL_CALORIES    + " VARCHAR, " +
                        COL_THUMBNAIL   + " VARCHAR, " +
                        COL_TIMESTAMP   + " VARCHAR PRIMARY KEY" +
                    ")";

    private static final String SQL_CREATE_TABLE_WORKOUT_LOG =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUT_LOG + " (" +
                    COL_UID             + " VARCHAR, " +
                    COL_WID             + " VARCHAR, " +
                    COL_NAME            + " VARCHAR, " +
                    COL_MET             + " VARCHAR, " +
                    COL_RESOURCE_ID     + " VARCHAR, " +
                    COL_SET_COUNT       + " VARCHAR, " +
                    COL_DURATION_MIN    + " VARCHAR, " +
                    COL_REPETITION      + " VARCHAR, " +
                    COL_UNIT            + " VARCHAR, " +
                    COL_KCAL_BURNT      + " VARCHAR, " +
                    COL_TIMESTAMP       + " VARCHAR PRIMARY KEY " +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_FOOD_LOG + "; " +
            "DROP TABLE IF EXISTS " + TABLE_WORKOUT_LOG;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FOOD_LOG);
        db.execSQL(SQL_CREATE_TABLE_WORKOUT_LOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_LOG);
        onCreate(db);
    }
}
