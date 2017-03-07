package com.team.courseselector;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddEvaluationActivity extends AppCompatActivity {

    RatingBar mRatingBarDifficulty;
    RatingBar mRatingBarExam;
    RatingBar mRatingBarTeacher;
    RatingBar mRatingBarUsability;
    EditText  mEditTextComment;
    MyJSONParser mMyJSONParser;
    float difficulty;
    float exam;
    float teacher;
    float usability;
    String comment;
    String course_id;
    public static final String TAG_DIFFICULTY="difficulty";
    public static final String TAG_EXAM="exam";
    public static final String TAG_TEACHER="teacher";
    public static final String TAG_USABILITY="usability";
    public static final String TAG_COURSE_ID="course_id";
    public static final String TAG_COMMENT="comment";
    public static final String TAG_SUCCESS="success";
    //public static final String url="http://192.168.43.200/courseselector/create_info.php";
    public static final String url="http://172.27.35.1/courseselector/create_info.php";
    protected void init(){
        mRatingBarDifficulty=(RatingBar)findViewById(R.id.rb_difficulty);
        mRatingBarExam=(RatingBar)findViewById(R.id.rb_exam);
        mRatingBarTeacher=(RatingBar)findViewById(R.id.rb_teacher);
        mRatingBarUsability=(RatingBar)findViewById(R.id.rb_usability);
        mEditTextComment=(EditText)findViewById(R.id.et_comment);
        mMyJSONParser=new MyJSONParser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evaluation);
        Toolbar mToolbar=(Toolbar)findViewById(R.id.tb_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add a comment");
        Intent mIntent=getIntent();
        course_id=mIntent.getStringExtra(TAG_COURSE_ID);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_evaluation, menu);
        return true;
    }

    public void submit(){
        new CollectAndSubmit().execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_submit) {
            difficulty= mRatingBarDifficulty.getRating();
            exam=mRatingBarExam.getRating();
            teacher=mRatingBarTeacher.getRating();
            usability=mRatingBarUsability.getRating();
            comment=mEditTextComment.getText().toString();
            submit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected class CollectAndSubmit extends AsyncTask<String,String,String>{
        JSONObject mJSONObject;

        int success;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> param=new ArrayList<NameValuePair>();
            NameValuePair mNameValuePairDifficulty=new BasicNameValuePair(TAG_DIFFICULTY,Float.toString(difficulty));
            NameValuePair mNameValuePairExam=new BasicNameValuePair(TAG_EXAM,Float.toString(exam));
            NameValuePair mNameValuePairTeacher=new BasicNameValuePair(TAG_TEACHER,Float.toString(teacher));
            NameValuePair mNameValuePairUsability=new BasicNameValuePair(TAG_USABILITY,Float.toString(usability));
            NameValuePair mNameValuePairComment=new BasicNameValuePair(TAG_COMMENT,comment);
            NameValuePair mNameValuePairCourseId=new BasicNameValuePair(TAG_COURSE_ID,course_id);
            param.add(mNameValuePairDifficulty);
            param.add(mNameValuePairExam);
            param.add(mNameValuePairTeacher);
            param.add(mNameValuePairUsability);
            param.add(mNameValuePairComment);
            param.add(mNameValuePairCourseId);
            //mJSONObject=null;

            mJSONObject=mMyJSONParser.HttpPost(url,param);



            /*
            try {
                mJSONObject = new JSONObject();
                mJSONObject.put(TAG_SUCCESS, 1);
            }catch (JSONException jse){
                Log.i("tamier log","failed to create JSONObject.");
            }
            */
            try{
                Log.i("tamier log","reached here!");
                if (mJSONObject!= null) {
                    Log.e("tamier log","mJSONObject!=null");
                    success = mJSONObject.getInt(TAG_SUCCESS);
                    Log.d("tamier log",mJSONObject.toString());
                    if (success==1){
                        Runnable mRunnableSuccess=new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddEvaluationActivity.this, "Successfully submitted comment!", Toast.LENGTH_SHORT).show();
                            }
                        };
                        runOnUiThread(mRunnableSuccess);
                        Intent mIntent=new Intent();
                        setResult(200, mIntent);
                        finish();
                    }
                    else{
                        Runnable mRunnableFailure=new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddEvaluationActivity.this, "Failed to submit comment!", Toast.LENGTH_SHORT).show();
                            }
                        };
                        runOnUiThread(mRunnableFailure);
                    }
                }
                else{
                    Log.e("tamier log","mJSONObject==null");
                    Runnable mRunnable2=new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddEvaluationActivity.this, "Connect to server error!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    runOnUiThread(mRunnable2);
                    //finish();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
