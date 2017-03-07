package com.team.courseselector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.support.v7.widget.Toolbar;

public class WeightActivity extends AppCompatActivity {

    private WeightDbAdapter mWeightDbAdapter;
    private CourseDbAdapter mCourseDbAdapter;
    protected Cursor mCursor,mCursor2;
    private SeekBar mSeekBar1;
    private SeekBar mSeekBar2;
    private SeekBar mSeekBar3;
    private SeekBar mSeekBar4;
    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        mToolBar=(Toolbar)findViewById(R.id.tb_appbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Put weights");
        mSeekBar1=(SeekBar)findViewById(R.id.seekBar6);
        mSeekBar2=(SeekBar)findViewById(R.id.seekBar7);
        mSeekBar3=(SeekBar)findViewById(R.id.seekBar8);
        mSeekBar4=(SeekBar)findViewById(R.id.seekBar9);
        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBar2.setProgress((100-mSeekBar1.getProgress())/3);
                mSeekBar3.setProgress((100-mSeekBar1.getProgress())/3);
                mSeekBar4.setProgress((100-mSeekBar1.getProgress())/3);
                //mSeekBar2.setProgress(100 - mSeekBar1.getProgress() -mSeekBar3.getProgress()-mSeekBar4.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSeekBar2.setMax(100-mSeekBar1.getProgress());
                mSeekBar3.setMax(100-mSeekBar1.getProgress());
                mSeekBar4.setMax(100-mSeekBar1.getProgress());

            }
        });
        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBar3.setProgress((100-mSeekBar1.getProgress()-mSeekBar2.getProgress())/2);
                mSeekBar4.setProgress((100-mSeekBar1.getProgress()-mSeekBar2.getProgress())/2);
                //mSeekBar3.setProgress(100 - mSeekBar1.getProgress() - mSeekBar2.getProgress() -mSeekBar4.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mSeekBar1.setMax(100-mSeekBar1.getProgress());
                mSeekBar3.setMax(100-mSeekBar1.getProgress()-mSeekBar2.getProgress());
                mSeekBar4.setMax(100-mSeekBar1.getProgress()-mSeekBar2.getProgress());
            }
        });
        mSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBar4.setProgress(100-mSeekBar1.getProgress()-mSeekBar3.getProgress()-mSeekBar2.getProgress());
                //mSeekBar4.setProgress(100 - mSeekBar1.getProgress() -mSeekBar2.getProgress()-mSeekBar3.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mSeekBar1.setMax(100-mSeekBar1.getProgress());
                //mSeekBar2.setMax(100-mSeekBar1.getProgress());
                mSeekBar4.setMax(100-mSeekBar1.getProgress()-mSeekBar3.getProgress()-mSeekBar2.getProgress());
                ;
            }
        });
        mSeekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                 @Override
                                                 public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                     mSeekBar3.setProgress(100-mSeekBar1.getProgress()-mSeekBar3.getProgress()-mSeekBar4.getProgress());
                                                 }

                                                 @Override
                                                 public void onStartTrackingTouch(SeekBar seekBar) {

                                                 }

                                                 @Override
                                                 public void onStopTrackingTouch(SeekBar seekBar) {
                                                     mSeekBar3.setMax(100-mSeekBar1.getProgress()-mSeekBar4
                                                             .getProgress()-mSeekBar2.getProgress());
                                                 }
                                             }
        );
        mWeightDbAdapter=new WeightDbAdapter(this);
        mWeightDbAdapter.open();
        mCourseDbAdapter=new CourseDbAdapter(this);
        mCourseDbAdapter.open();
        show();

    }

    protected void show() {
        mCursor = mWeightDbAdapter.getAll();
        startManagingCursor(mCursor);
        mCursor.moveToFirst();
        if(mCursor!=null) {
            mSeekBar1.setProgress(mCursor.getInt(mCursor.getColumnIndex(WeightDbAdapter.KEY_DIFFICULTY)));

            mSeekBar2.setProgress(mCursor.getInt(mCursor.getColumnIndex(WeightDbAdapter.KEY_TEACHER)));

            mSeekBar3.setProgress(mCursor.getInt(mCursor.getColumnIndex(WeightDbAdapter.KEY_EXAM)));

            mSeekBar4.setProgress(mCursor.getInt(mCursor.getColumnIndex(WeightDbAdapter.KEY_USABILITY)));
        }
        else{
            mSeekBar1.setProgress(0);

            mSeekBar2.setProgress(0);

            mSeekBar3.setProgress(0);

            mSeekBar4.setProgress(0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weight_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_next) {
            int p1,p2,p3,p4;
            p1=mSeekBar1.getProgress();
            p2=mSeekBar2.getProgress();
            p3=mSeekBar3.getProgress();
            p4=mSeekBar4.getProgress();
            Log.i("tamier log", String.valueOf(p1) + ',' + String.valueOf(p2) + ',' + String.valueOf(p3) + ',' + String.valueOf(p4));
            //Write back to database
            mWeightDbAdapter.update(1,p1,p2,p3,p4);
            //Calculate
            mCursor2 = mCourseDbAdapter.getAll();
            startManagingCursor(mCursor2);
            mCursor2.moveToFirst();
            int row_id;
            //String cname;
            int a1;
            int a2;
            int a3;
            int a4;
            //
            int i=mCourseDbAdapter.getProfilesCount();
            while(i>0) {
                row_id=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_ROWID));
                Log.i("tamier log","This is the row_id: "+String.valueOf(row_id));
                a1=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_DIFFICULTY));
                a2=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_TEACHER));
                a3=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_EXAM));
                a4=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_USABILITY));
                //a1=50;a2=50;a3=40;a4=30;
                Log.i("tamier log", String.valueOf(a1) + ',' + String.valueOf(a2) + ',' + String.valueOf(a3) + ',' + String.valueOf(a4));
                int mark=(a1*p1+a2*p2+a3*p3+a4*p4)/100;
                Log.i("tamier log", String.valueOf(mark));
                //mCourseDbAdapter.getmark(count_id,mark);
                mCourseDbAdapter.getmark(row_id,mark);
                mCursor2.moveToNext();
                i--;
                //Log.i("tamier log", "Before" + String.valueOf(count_id));
                //count_id++;
                //Log.i("tamier log", "After:"+String.valueOf(count_id));

            }
            //mCursor2.moveToLast();
            //row_id=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_COURSENUMBER));
            //Log.i("tamier log","This is the row_id: "+String.valueOf(row_id));
            //a1=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_DIFFICULTY));
            //a2=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_TEACHER));
            //a3=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_EXAM));
            //a4=mCursor2.getInt(mCursor2.getColumnIndex(CourseDbAdapter.KEY_USABILITY));
            //a1=50;a2=50;a3=40;a4=30;
            //Log.i("tamier log", String.valueOf(a1) + ',' + String.valueOf(a2) + ',' + String.valueOf(a3) + ',' + String.valueOf(a4));
            //int mark=(a1*p1+a2*p2+a3*p3+a4*p4)/100;
            //Log.i("tamier log", String.valueOf(mark));
            //mCourseDbAdapter.getmark(row_id,mark);
            Log.i("tamier log", "End iterating CourseTB");
            //jump to ResultPageActivity
            Intent mIntent=new Intent(WeightActivity.this,ResultPageActivity.class);
            startActivity(mIntent);
            finish();
            return true;
        }
        else if(id==android.R.id.home){
            Intent mIntent = new Intent();
            setResult(RESULT_OK, mIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
