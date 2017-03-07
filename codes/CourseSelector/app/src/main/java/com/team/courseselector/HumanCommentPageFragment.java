package com.team.courseselector;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tamier on 21/11/15.
 */
public class HumanCommentPageFragment extends Fragment {


    RecyclerView mRecyclerView;
    View v;
    List<HashMap<String,String>> mCommentList=new ArrayList<HashMap<String, String>>();
    public static final String TAG_DIFFICULTY="difficulty";
    public static final String TAG_EXAM="exam";
    public static final String TAG_TEACHER="teacher";
    public static final String TAG_USABILITY="usability";
    public static final String TAG_COMMENTS="comments";
    public static final String TAG_DATE="date";
    public static final String TAG_COMMENT="comment";
    public static final String TAG_SUCCESS="success";
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mJSONObject=((CourseLiteInfoActivity)activity).getJSONObject();
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_human_comment,container,false);
        init();
        return v;
    }

    protected void init(){
       mRecyclerView=(RecyclerView)v.findViewById(R.id.rv_comments);
    }
    protected void render(JSONObject mJSONObject){
        try{
            if (mJSONObject!=null){
                int success=mJSONObject.getInt(TAG_SUCCESS);
                if (success==1) {
                    JSONArray mJSONArray = mJSONObject.getJSONArray(TAG_COMMENTS);
                    if(mCommentList!=null){
                        mCommentList.clear();
                    }
                    int i;
                    for (i = 0; i < mJSONArray.length(); i++) {
                        JSONObject mJSONEachObject = mJSONArray.getJSONObject(i);
                        String date = mJSONEachObject.getString(TAG_DATE);
                        String comment = mJSONEachObject.getString(TAG_COMMENT);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_DATE, date);
                        map.put(TAG_COMMENT, comment);
                        mCommentList.add(map);
                    }
                }
                else{
                    Runnable mRunnableFailure=new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to fetch comments!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    getActivity().runOnUiThread(mRunnableFailure);
                }
            }

        }catch (JSONException e){
            Runnable mRunnable=new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Error parsing JSON data!", Toast.LENGTH_SHORT).show();
                }
            };
            getActivity().finish();
            getActivity().runOnUiThread(mRunnable);
            Log.i("tamier log", "JSONException occrued!");
        }catch (Exception e){
            Log.i("tamier log","Exception occrued!");
        }

        CommentAdapter mCommentAdapter=new CommentAdapter(mCommentList);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mCommentAdapter);
    }

}