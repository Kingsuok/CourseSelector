package com.team.courseselector;

/**
 * Created by tamier on 18/11/15.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


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
    public static final String TAG_COMMENT="comment";
    public static final String TAG_DATE="date";
    public static final String TAG_COMMENTS="comments";
    private List<HashMap<String,String>> mListHashMap;

    public CommentAdapter(List<HashMap<String, String>> arg0) {
        this.mListHashMap=arg0;
    }
    @Override
    public int getItemCount() {
        return mListHashMap.size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        CommentViewHolder mCourseViewHolder=new CommentViewHolder(mView);
        return mCourseViewHolder;
    }


    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        HashMap<String,String> mComment=mListHashMap.get(position);
        final String date=mComment.get(TAG_DATE);
        final String comment=mComment.get(TAG_COMMENT);
        holder.mTextViewDate.setText(date);
        holder.mTextViewComment.setText(comment);


    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewDate;
        TextView mTextViewComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mTextViewDate=(TextView)itemView.findViewById(R.id.tv_date);
            mTextViewComment=(TextView)itemView.findViewById(R.id.tv_comment_body);

        }


    }

}
