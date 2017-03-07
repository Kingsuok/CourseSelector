package com.team.courseselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class CourseEdit extends AppCompatActivity {
    EditText mEditText;
    private Long rowid;
    Bundle extras;
    Toolbar mTooBar;
    SeekBar mSeekBar1,mSeekBar2,mSeekBar3,mSeekBar4;
    String coursenumber;
    Integer difficulty,teacher,exam,usability;
    private CourseDbAdapter mCourseDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        mTooBar=(Toolbar)findViewById(R.id.tb_appbar);
        setSupportActionBar(mTooBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Give marks");
        mCourseDbAdapter = new CourseDbAdapter(this);
        mCourseDbAdapter.open();
        mEditText = (EditText) findViewById(R.id.editText);

        mSeekBar1=(SeekBar)findViewById(R.id.seekBar2);
        mSeekBar2=(SeekBar)findViewById(R.id.seekBar3);
        mSeekBar3=(SeekBar)findViewById(R.id.seekBar4);
        mSeekBar4=(SeekBar)findViewById(R.id.seekBar5);
        //getSupportActionBar().hide();

        rowid = null;
        extras = getIntent().getExtras();
        if (extras != null) {
            coursenumber = extras.getString(CourseDbAdapter.KEY_COURSENUMBER);
            //Toast.makeText(CourseEdit.this,coursenumber,Toast.LENGTH_LONG).show();
            difficulty = extras.getInt(CourseDbAdapter.KEY_DIFFICULTY);
            teacher = extras.getInt(CourseDbAdapter.KEY_TEACHER);
            exam = extras.getInt(CourseDbAdapter.KEY_EXAM);
            usability = extras.getInt(CourseDbAdapter.KEY_USABILITY);
            rowid = extras.getLong(CourseDbAdapter.KEY_ROWID);
            Log.i("tamier log",String.valueOf(rowid));
            if (coursenumber != null) {
                mEditText.setText(coursenumber);
            }
            else{
                mEditText.setText("Adapter failed");
            }
            if (difficulty != null) {
                mSeekBar1.setProgress(difficulty);
            }
            if (teacher != null) {
                mSeekBar2.setProgress(teacher);
            }
            if (exam != null) {
                mSeekBar3.setProgress(exam);
            }
            if (usability != null) {
                mSeekBar4.setProgress(usability);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_ok) {

            Log.i("tamier log","button clicked");
            coursenumber=mEditText.getText().toString().toUpperCase();
            if(coursenumber.trim().length() == 0){
                Toast.makeText(CourseEdit.this,"Course Number cannot be empty",Toast.LENGTH_SHORT).show();

            }
            else if(mCourseDbAdapter.isExist(coursenumber)&&(extras==null)){
                Toast.makeText(CourseEdit.this,"Course already exists",Toast.LENGTH_SHORT).show();
            }
            else if(mCourseDbAdapter.isExist(coursenumber)&&(rowid==0)){
                Toast.makeText(CourseEdit.this,"Course already exists",Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(CourseEdit.this,coursenumber,Toast.LENGTH_LONG).show();
                difficulty = mSeekBar1.getProgress();
                teacher = mSeekBar2.getProgress();
                exam = mSeekBar3.getProgress();
                usability = mSeekBar4.getProgress();
                if ((rowid != null)&&(rowid!=0)) {
                    Log.i("tamier log","reached here");
                    mCourseDbAdapter.update(rowid, coursenumber, difficulty, teacher, exam, usability);
                    Toast.makeText(CourseEdit.this,"Successfully updated!",Toast.LENGTH_SHORT).show();
                } else {
                    mCourseDbAdapter.create(coursenumber, difficulty, teacher, exam, usability);
                    Log.i("tamier log", "created?!");
                    Toast.makeText(CourseEdit.this,"Successfully added!",Toast.LENGTH_SHORT).show();
                }
                Intent mIntent = new Intent();
                setResult(RESULT_OK, mIntent);
                finish();
            }
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
