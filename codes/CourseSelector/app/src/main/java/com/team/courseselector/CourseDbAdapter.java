package com.team.courseselector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tamier on 19/10/15.
 */
public class CourseDbAdapter {
    public static final String KEY_COURSENUMBER = "coursenumber";
    public static final String KEY_DIFFICULTY = "difficulty";
    public static final String KEY_TEACHER = "teacher";
    public static final String KEY_EXAM = "exam";
    public static final String KEY_USABILITY = "usability";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_MARK="mark";
    private final Context mCtx;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME="CourseDB";
    private static final int DATABASE_VERSION=2;
    public static final String TABLE_NAME="CourseTB";
    private static final String TABLE_CREATE = "create table "+TABLE_NAME+"(_id integer primary key autoincrement, "
            + "coursenumber text not null, difficulty integer not null,  teacher integer not null, exam integer not null, usability integer not null,mark integer);";




    private  class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
            //db.execSQL("PRAGMA auto_vacuum = FULL;");
            Log.i("tamier log", "table created");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CourseTB");
            onCreate(db);
        }

    }
    public CourseDbAdapter(Context context){
        this.mCtx=context;
    }
    //Create a new database using DatabaseHelper, which inherits from SQLiteOpenHelper
    public CourseDbAdapter open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mCtx);
        mDb = mDatabaseHelper.getWritableDatabase();
        Log.i("tamier log","mDb created");
        return this;
    }
    //Close database
    public void close() {
        mDatabaseHelper.close();
    }

    public long create(String coursenumber, Integer difficulty, Integer teacher, Integer exam, Integer usability) {
        ContentValues mContentValues = new ContentValues();

        //Log.i("tamier log",coursenumber);
        mContentValues.put(KEY_DIFFICULTY, difficulty);
        mContentValues.put(KEY_TEACHER, teacher);
        mContentValues.put(KEY_EXAM, exam);
        mContentValues.put(KEY_USABILITY, usability);
        mContentValues.put(KEY_COURSENUMBER, coursenumber);
        return mDb.insert(TABLE_NAME, null, mContentValues);
    }

    public boolean update(long rowid, String coursenumber, Integer difficulty, Integer teacher, Integer exam, Integer usability) {
        ContentValues mContenValues2 = new ContentValues();
        mContenValues2.put(KEY_DIFFICULTY, difficulty);
        mContenValues2.put(KEY_COURSENUMBER, coursenumber);
        mContenValues2.put(KEY_TEACHER, teacher);
        mContenValues2.put(KEY_EXAM,exam);
        mContenValues2.put(KEY_USABILITY,usability);
        return mDb.update(TABLE_NAME, mContenValues2, KEY_ROWID + "=" + rowid, null) > 0;
    }

    public void delete(long rowid) {


        mDb.delete(TABLE_NAME, KEY_ROWID + "=" + rowid, null);
        //mDb.execSQL("delete * from "+TABLE_NAME+" where "+KEY_COURSENUMBER+" is NULL");
        //mDatabaseHelper.resettable(mDb);

    }

    public Cursor getRow(long rowid) throws SQLException {
        Cursor d = mDb.query(true, TABLE_NAME, new String[] { KEY_ROWID,KEY_COURSENUMBER, KEY_DIFFICULTY,
                        KEY_TEACHER, KEY_EXAM, KEY_USABILITY}, KEY_ROWID + "=" + rowid, null, null,
                null, null, null);
        if (d!= null) {
            d.moveToFirst();
        }
        return d;

    }

    public Cursor getAll() throws SQLException{
        Cursor c= mDb.query(TABLE_NAME, new String[]{KEY_ROWID, KEY_COURSENUMBER, KEY_DIFFICULTY,
                KEY_TEACHER, KEY_EXAM, KEY_USABILITY}, null, null, null, null, null);
        return c;
    }

    public Cursor getAllByMarkOrder() throws SQLException{
        Cursor c= mDb.query(TABLE_NAME, new String[]{KEY_ROWID,KEY_COURSENUMBER, KEY_MARK}, null, null, null, null,KEY_MARK+" DESC");
        return c;
    }

    public boolean getmark(Integer row_id,Integer mark){
        ContentValues mContenValues3 = new ContentValues();
        mContenValues3.put(KEY_MARK,mark);
        return mDb.update(TABLE_NAME, mContenValues3, KEY_ROWID + "=" + row_id, null) > 0;

    }
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public boolean isExist(String coursenumber){
        Cursor c;
        String Query = "SELECT * from " + TABLE_NAME + " where " + KEY_COURSENUMBER + " = " + "\""+coursenumber+"\"";
        c=mDb.rawQuery(Query, null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }
}
