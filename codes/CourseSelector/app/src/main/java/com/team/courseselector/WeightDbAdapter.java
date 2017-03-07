package com.team.courseselector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tamier on 25/10/15.
 */
public class WeightDbAdapter {

    public static final String KEY_DIFFICULTY = "difficulty";
    public static final String KEY_TEACHER = "teacher";
    public static final String KEY_EXAM = "exam";
    public static final String KEY_USABILITY = "usability";
    public static final String KEY_ROWID = "_id";

    private Context mContext;
    public  WeightDbAdapter(Context context){

        this.mContext=context;
    }

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME="WeightDB";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="WeightTB";
    private static final String TABLE_CREATE = "create table "+TABLE_NAME+"(_id integer primary key autoincrement, "
            + "difficulty integer not null, teacher integer not null, exam integer not null, usability integer not null);";

    private  class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
            ContentValues mContentValues= new ContentValues();
            mContentValues.put(KEY_DIFFICULTY,0);
            mContentValues.put(KEY_TEACHER,0);
            mContentValues.put(KEY_EXAM,0);
            mContentValues.put(KEY_USABILITY,0);
            db.insert(TABLE_NAME,null,mContentValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CourseTB");
            onCreate(db);
        }
    }

    public void open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDb = mDatabaseHelper.getWritableDatabase();
        Log.i("tamier log", "mDb created");
    }

    public long create(Integer difficulty, Integer teacher, Integer exam, Integer usability) {
        ContentValues mContentValues = new ContentValues();

        //Log.i("tamier log",coursenumber);
        mContentValues.put(KEY_DIFFICULTY, difficulty);
        mContentValues.put(KEY_TEACHER, teacher);
        mContentValues.put(KEY_EXAM, exam);
        mContentValues.put(KEY_USABILITY, usability);
        return mDb.insert(TABLE_NAME, null, mContentValues);
    }

    public boolean update(long rowid,Integer difficulty, Integer teacher, Integer exam, Integer usability) {
        ContentValues mContenValues2 = new ContentValues();
        mContenValues2.put(KEY_DIFFICULTY, difficulty);
        mContenValues2.put(KEY_TEACHER, teacher);
        mContenValues2.put(KEY_EXAM,exam);
        mContenValues2.put(KEY_USABILITY,usability);
        return mDb.update(TABLE_NAME, mContenValues2, KEY_ROWID + "=" + rowid, null) > 0;
    }

    public Cursor getAll() throws SQLException{
        Cursor c= mDb.query(TABLE_NAME, new String[]{KEY_ROWID, KEY_DIFFICULTY,
                KEY_TEACHER, KEY_EXAM, KEY_USABILITY}, null, null, null, null, null);
        return c;
    }

}
