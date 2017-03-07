package com.team.courseselector;

/**
 * Created by tamier on 18/11/15.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    private static final String SUBJECT_NAME="Subject";
    public static final String TAG_META="meta";
    public static final String TAG_STATUS="status";
    public static final String TAG_DATA="data";
    public static final String TAG_COURSE_ID="course_id";
    public static final String TAG_SUBJECT="subject";
    public static final String TAG_CATALOG_NUMBER="catalog_number";
    public static final String TAG_TITLE="title";
    public static final String TAG_DESCRIPTION="description";
    public static final String TAG_KEY="key";
    public static final String TAG_COMMENTS="comments";
    public static final String TAG_DATE="date";

    private List<HashMap<String,String>> mListHashMap;
    static OnItemClickListener mItemClickListener;
    static OnButtonClickListener mButtonClickListener;
    public CourseAdapter( List<HashMap<String,String>> arg0) {
        this.mListHashMap=arg0;
    }
    @Override
    public int getItemCount() {
        return mListHashMap.size();
    }

    public void setFilter(List<HashMap<String,String>> arg0){
        mListHashMap=arg0;
        notifyDataSetChanged();
    }
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course,parent,false);
        CourseViewHolder mCourseViewHolder=new CourseViewHolder(mView);
        return mCourseViewHolder;
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public void SetOnButtonClickListener(OnButtonClickListener mButtonClickListener){
        this.mButtonClickListener=mButtonClickListener;
    }
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        HashMap<String,String> mCourse=mListHashMap.get(position);
        final String mStringSubjcourse=mCourse.get(TAG_SUBJECT).toString()+mCourse.get(TAG_CATALOG_NUMBER).toString();
        holder.mTextViewSubjcourse.setText(mStringSubjcourse);
        String mStringTitle=mCourse.get(TAG_TITLE).toString();
        holder.mTextViewTitle.setText(mStringTitle);
        String mStringDescription=mCourse.get(TAG_DESCRIPTION).toString();
        holder.mTextViewDescription.setText(mStringDescription);
        String mStringCourseid=mCourse.get(TAG_COURSE_ID).toString();
        holder.mTextViewCourseid.setText(mStringCourseid);

    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position,String course_id,String subjcourse,String title, String description);
    }

    public interface OnButtonClickListener{
        void onButtonClick(String subjcourse);
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewSubjcourse;
        TextView mTextViewTitle;
        TextView mTextViewDescription;
        TextView mTextViewCourseid;
        Button mButtonWish;
        public CourseViewHolder(View itemView) {
            super(itemView);
            mTextViewSubjcourse=(TextView)itemView.findViewById(R.id.tv_subjcourse);
            mTextViewTitle=(TextView)itemView.findViewById(R.id.tv_title);
            mTextViewDescription=(TextView)itemView.findViewById(R.id.tv_description);
            mTextViewCourseid=(TextView)itemView.findViewById(R.id.tv_courseid);
            mButtonWish=(Button)itemView.findViewById(R.id.btn_wish);
            mButtonWish.setOnClickListener(this);
            mButtonWish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String subjcourse = mTextViewSubjcourse.getText().toString();
                    if (mButtonClickListener!=null){
                        mButtonClickListener.onButtonClick(subjcourse);
                    }

                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String course_id=mTextViewCourseid.getText().toString();
            String subjcourse=mTextViewSubjcourse.getText().toString();
            String title=mTextViewTitle.getText().toString();
            String description=mTextViewDescription.getText().toString();
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v, getPosition(),course_id,subjcourse,title,description);
            }

        }
    }

}
