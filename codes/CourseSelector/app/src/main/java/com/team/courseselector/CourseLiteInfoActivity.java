package com.team.courseselector;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class CourseLiteInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    protected String description;
    String course_id;
    String subjcourse;
    MyJSONParser mMyJSONParser;
    JSONObject mJSONObject;
    TextView mTextViewSubjcourse;
    ImageView mImageView;
    Dialog mDialog;
    TextView mTextViewPeopleEvaluated;
    CommentPageFragment mCommentPageFragment;
    HumanCommentPageFragment mHumanCommentPageFragment;


    public static final String KEY_COURSENUMBER = "coursenumber";
    public static final String TAG_SUBJCOURSE="subjcourse";
    public static final String TAG_COURSE_ID="course_id";
    public static final String TAG_TITLE="title";
    public static final String TAG_DESCRIPTION="description";
    public static final String TAG_DIFFICULTY="difficulty";
    public static final String TAG_EXAM="exam";
    public static final String TAG_TEACHER="teacher";
    public static final String TAG_USABILITY="usability";
    //private static final String url="http://192.168.43.200/courseselector/getcourseinfo.php";
    //private static final String url="http://172.27.35.1/courseselector/getcourseinfo.php";
    private static final String url="http://172.27.35.1/courseselector/getcourseinfo.php";
    public static final String TAG_COMMENT="comment";
    public static final String TAG_DATE="date";
    public static final String TAG_COMMENTS="comments";
    public static final String TAG_SUCCESS="success";
    public static final String TAG_COUNT="count";

    private int[] tabIcons = {
            R.drawable.ic_mark,
            R.drawable.ic_comments,
            R.drawable.ic_description,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_lite_info);
        mMyJSONParser=new MyJSONParser();
        mCommentPageFragment=new CommentPageFragment();
        mHumanCommentPageFragment=new HumanCommentPageFragment();
        mDialog=new Dialog(this);
        mImageView=(ImageView)findViewById(R.id.iv_light);
        mTextViewSubjcourse=(TextView)findViewById(R.id.tv_subjcourse);
        TextView mTextViewTitle=(TextView)findViewById(R.id.tv_title);
        mTextViewPeopleEvaluated=(TextView)findViewById(R.id.tv_peopleevaluated);
        //TextView mTextViewDescription=(TextView)findViewById(R.id.tv_description);
        Intent mIntent=getIntent();
        course_id=mIntent.getStringExtra(TAG_COURSE_ID);
        Log.i("tamier log",course_id);
        subjcourse=mIntent.getStringExtra(TAG_SUBJCOURSE);
        Log.i("tamier log", subjcourse);
        String title=mIntent.getStringExtra(TAG_TITLE);
        description=mIntent.getStringExtra(TAG_DESCRIPTION);
        mTextViewSubjcourse.setText(subjcourse);
        mTextViewTitle.setText(title);
        //mTextViewDescription.setText(description);

        toolbar = (Toolbar) findViewById(R.id.tb_appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Course Details");
        //getSupportActionBar().hide();
        viewPager = (ViewPager) findViewById(R.id.vp_fragment);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_entries);
        //mDescriptionFragment=new DescriptionFragment();
        //mCommentFragment=new CommentPageFragment();
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        render();
        Button mButtonCompare=(Button)findViewById(R.id.btn_add);
        mButtonCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mGoToCourseEditActivity=new Intent(CourseLiteInfoActivity.this,CourseEdit.class);
                mGoToCourseEditActivity.putExtra(KEY_COURSENUMBER,subjcourse);
                startActivity(mGoToCourseEditActivity);
            }
        });
        Button mButtonComment=(Button)findViewById(R.id.btn_comment);
        mButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mGoToAddEvaluationIntent=new Intent(CourseLiteInfoActivity.this,AddEvaluationActivity.class);
                mGoToAddEvaluationIntent.putExtra(TAG_COURSE_ID,course_id);
                startActivityForResult(mGoToAddEvaluationIntent, 200);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            render();
            //CommentPageFragment commentPageFragment=(CommentPageFragment)getSupportFragmentManager().findFragmentByTag("comment");
            //commentPageFragment.render();

        }
    }

    protected void render(){
        Log.i("tamier log","reder() called!");
        new GetAndShowComments().execute();
    }

    public JSONObject getJSONObject(){
        return mJSONObject;
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        //tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    protected class GetAndShowComments extends AsyncTask<String,String,String>{
        int id;
        int count;
        int peopleevaluated;
        float difficulty;
        float exam;
        float teacher;
        float usability;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setTitle("Getting commments...");
            mDialog.setCancelable(false);
            mDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> param=new ArrayList<NameValuePair>();
            //NameValuePair mNameValuePairnew=new BasicNameValuePair(TAG_COURSE_ID,course_id);
            NameValuePair mNameValuePairnew=new BasicNameValuePair(TAG_COURSE_ID,course_id);
            param.add(mNameValuePairnew);
            mJSONObject=mMyJSONParser.HttpPost(url,param);
            if(mJSONObject!=null) {
                Log.d("internet log", mJSONObject.toString());
            }
            /*
            mJSONObject= new JSONObject();
            try {
                mJSONObject.put(TAG_SUCCESS,1);
                mJSONObject.put(TAG_DIFFICULTY, "1.0");
                mJSONObject.put(TAG_EXAM,"2.0");
                mJSONObject.put(TAG_TEACHER,"2.0");
                mJSONObject.put(TAG_USABILITY,"3.0");
                mJSONObject.put("count",2);
                JSONArray mJSONArray=new JSONArray();
                JSONObject comment1=new JSONObject();
                comment1.put(TAG_DATE, "22,Nov.,2015");
                comment1.put(TAG_COMMENT,"very good course");
                mJSONArray.put(comment1);
                JSONObject comment2=new JSONObject();
                comment2.put(TAG_DATE,"21,Nov.,2015");
                comment2.put(TAG_COMMENT,"Interesting and fun!");
                mJSONArray.put(comment2);
                mJSONObject.put(TAG_COMMENTS,mJSONArray);
            }catch (JSONException e){
                Toast.makeText(CourseLiteInfoActivity.this,"failed to create JSONObject",Toast.LENGTH_SHORT).show();
            }
            */

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            mDialog.dismiss();
            if (mJSONObject==null){
                Runnable mRunnableConnectionError=new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CourseLiteInfoActivity.this, "Connect to server error!", Toast.LENGTH_SHORT).show();
                    }
                };
                runOnUiThread(mRunnableConnectionError);

            }
            else{
                try {
                    peopleevaluated=mJSONObject.getInt(TAG_COUNT);
                    difficulty = Float.parseFloat(mJSONObject.getString(TAG_DIFFICULTY));
                    exam = Float.parseFloat(mJSONObject.getString(TAG_EXAM));
                    teacher = Float.parseFloat(mJSONObject.getString(TAG_TEACHER));
                    usability = Float.parseFloat(mJSONObject.getString(TAG_USABILITY));
                    if(difficulty>=3.0&&exam>=3.0&&teacher>=3.0&&usability>=3.0){
                        id=R.drawable.ic_green;
                    }
                    else{

                        if(difficulty<=2.0){
                            count++;
                        }
                        if (exam<=2.0){
                            count++;
                        }
                        if (teacher<=2.0){
                            count++;
                        }
                        if (usability<=2.0){
                            count++;
                        }
                        if(count>=3){
                            id=R.drawable.ic_red;
                        }
                        else{
                            id=R.drawable.ic_yellow;
                        }
                    }
                }catch (JSONException e){
                    Log.e("tamier log","failed parsing JSON data!");
                }
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        mTextViewPeopleEvaluated.setText(String.valueOf(peopleevaluated)+" people evaluated");
                        //mImageView.setImageResource(id);
                        if (peopleevaluated!=0) {
                            mTextViewSubjcourse.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
                        }

                    }
                };
                runOnUiThread(runnable);
                mCommentPageFragment.render(mJSONObject);
                mHumanCommentPageFragment.render(mJSONObject);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFrag(new CommentPageFragment(), "comment");
        adapter.addFrag(mCommentPageFragment, "comment");
        adapter.addFrag(mHumanCommentPageFragment, "hcomment");
        adapter.addFrag(new DescriptionFragment(), "description");
        //adapter.addFrag(new ApiInfoFragment(), "more");
        viewPager.setAdapter(adapter);
    }
    public String getDescription(){
        return description;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}