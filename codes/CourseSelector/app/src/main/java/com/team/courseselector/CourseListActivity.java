package com.team.courseselector;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    Toolbar mToolbar;
    MyJSONParser mMyJSONParser;
    List<HashMap<String,String>> mListCourses,bakListCourses;
    Dialog mDialog;
    RecyclerView mRecyclerView;
    Intent mIntent;
    String subject;
    LinearLayoutManager mLinearLayoutManager;
    CourseAdapter mCourseAdapter;

    public static final String KEY_COURSENUMBER = "coursenumber";
    private static final String SUBJECT_NAME="Subject";
    public static final String url="https://api.uwaterloo.ca/v2/courses/";
    public static final String TAG_META="meta";
    public static final String TAG_STATUS="status";
    public static final String TAG_DATA="data";
    public static final String TAG_COURSE_ID="course_id";
    public static final String TAG_SUBJCOURSE="subjcourse";
    public static final String TAG_SUBJECT="subject";
    public static final String TAG_CATALOG_NUMBER="catalog_number";
    public static final String TAG_TITLE="title";
    public static final String TAG_DESCRIPTION="description";
    public static final String TAG_KEY="key";

    protected void init(){
        mToolbar=(Toolbar)findViewById(R.id.tb_appbar_course);
        mMyJSONParser=new MyJSONParser();
        mListCourses=new ArrayList<HashMap<String,String>>();
        mDialog=new Dialog(this);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_courses);
        mIntent=getIntent();
        subject=mIntent.getStringExtra(SUBJECT_NAME);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        init();
        setSupportActionBar(mToolbar);
        setTitle(subject);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        render();
    }

    protected void render(){
        new GetAndShowAllCourses().execute();
    }

    protected class GetAndShowAllCourses extends AsyncTask<String,String,String>{
        boolean success;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setTitle("Getting all courses...");
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> param=new ArrayList<NameValuePair>();
            NameValuePair mKeyNameValuePair=new BasicNameValuePair(TAG_KEY,"d54d96338a9b4d986e257e8d5b64d8c8");
            param.add(mKeyNameValuePair);
            String url_specific_subject;
            url_specific_subject=url+subject+".json";

            JSONObject mJSONObject=mMyJSONParser.HttpGet(url_specific_subject, param);

            try {
                if (mJSONObject!=null) {
                    JSONObject mJSONObject_META = mJSONObject.getJSONObject(TAG_META);
                    success = (mJSONObject_META.getInt(TAG_STATUS) == 200);
                    if (success) {
                        JSONArray mJSONArray = mJSONObject.getJSONArray(TAG_DATA);
                        int i;
                        for (i = 0; i < mJSONArray.length(); i++) {
                            JSONObject mJOSONEachObjectCourse = mJSONArray.getJSONObject(i);
                            String course_id;
                            String subject;
                            String catalog_number;
                            String title;
                            String description;
                            course_id = mJOSONEachObjectCourse.getString(TAG_COURSE_ID);
                            subject = mJOSONEachObjectCourse.getString(TAG_SUBJECT);
                            catalog_number = mJOSONEachObjectCourse.getString(TAG_CATALOG_NUMBER);
                            title = mJOSONEachObjectCourse.getString(TAG_TITLE);
                            description = mJOSONEachObjectCourse.getString(TAG_DESCRIPTION);
                            HashMap<String, String> mHashMap = new HashMap<String, String>();
                            mHashMap.put(TAG_COURSE_ID, course_id);
                            mHashMap.put(TAG_SUBJECT, subject);
                            mHashMap.put(TAG_CATALOG_NUMBER, catalog_number);
                            mHashMap.put(TAG_TITLE, title);
                            mHashMap.put(TAG_DESCRIPTION, description);
                            mListCourses.add(mHashMap);
                        }

                    } else {
                        Runnable mRunnable = new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CourseListActivity.this, "No courses found!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        };
                        runOnUiThread(mRunnable);
                    }
                mJSONObject=null;
                }
                else{
                    Runnable mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CourseListActivity.this, "Connect to server error!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    };
                    runOnUiThread(mRunnable);
                }
            }catch (JSONException e){
                e.printStackTrace();
                Log.i("tamier log","JSONException occured!");

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mDialog.dismiss();
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            if (mListCourses!=null) {
                mCourseAdapter = new CourseAdapter(mListCourses);
                bakListCourses=mListCourses;
            }
            else {
                Runnable mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CourseListActivity.this, "Connect to server error!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };
                runOnUiThread(mRunnable);
            }
            mCourseAdapter.SetOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String course_id,String subjcourse,String title, String description) {
                    Intent mGoToCourseLiteIntent=new Intent(CourseListActivity.this,CourseLiteInfoActivity.class);
                    mGoToCourseLiteIntent.putExtra(TAG_COURSE_ID,course_id);
                    mGoToCourseLiteIntent.putExtra(TAG_SUBJCOURSE,subjcourse);
                    mGoToCourseLiteIntent.putExtra(TAG_TITLE,title);
                    mGoToCourseLiteIntent.putExtra(TAG_DESCRIPTION,description);
                    startActivity(mGoToCourseLiteIntent);
                }
            });
            mCourseAdapter.SetOnButtonClickListener(new CourseAdapter.OnButtonClickListener(){

                @Override
                public void onButtonClick(String subjcourse) {
                    Intent mGoToCourseEditActivity=new Intent(CourseListActivity.this,CourseEdit.class);
                    mGoToCourseEditActivity.putExtra(KEY_COURSENUMBER,subjcourse);
                    startActivity(mGoToCourseEditActivity);
                }
            });
            mRecyclerView.setAdapter(mCourseAdapter);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<HashMap<String,String>> filteredList=filter(bakListCourses,newText);
        mCourseAdapter.setFilter(filteredList);
        return true;
    }

    public List<HashMap<String,String>> filter(List<HashMap<String,String>> originallist,String query){

        List<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
            for(HashMap<String,String> map:originallist){

                if (((map.get(TAG_SUBJECT)+map.get(TAG_CATALOG_NUMBER)).toLowerCase().contains(query))){
                    list.add(map);
                }
                else if(map.get(TAG_TITLE).toLowerCase().contains(query)){
                    list.add(map);
                }

            }
            //list.add("res1");
        return list;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        final MenuItem item=menu.findItem(R.id.menu_search);
        final SearchView mSearchView=(SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mCourseAdapter.setFilter(bakListCourses);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListCourses.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
