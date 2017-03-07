package com.team.courseselector;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tamier on 21/11/15.
 */
public class CommentPageFragment extends Fragment {


    RatingBar mRatingBarDifficulty;
    RatingBar mRatingBarExam;
    RatingBar mRatingBarTeacher;
    RatingBar mRatingBarUsability;
    float difficulty;
    float exam;
    float teacher;
    float usability;
    View v;
    public static final String TAG_DIFFICULTY="difficulty";
    public static final String TAG_EXAM="exam";
    public static final String TAG_TEACHER="teacher";
    public static final String TAG_USABILITY="usability";
    public static final String TAG_SUCCESS="success";
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mJSONObject=((CourseLiteInfoActivity)activity).getJSONObject();
        //init();
        //render();
    }
*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_comments,container,false);
        init();
        //render();
        return v;
    }

    protected void init(){
        mRatingBarDifficulty=(RatingBar)v.findViewById(R.id.rb_difficulty);
        mRatingBarExam=(RatingBar)v.findViewById(R.id.rb_exam);
        mRatingBarTeacher=(RatingBar)v.findViewById(R.id.rb_teacher);
        mRatingBarUsability=(RatingBar)v.findViewById(R.id.rb_usability);
        /*
        mRatingBarDifficulty.setStepSize(0.5f);
        mRatingBarDifficulty.setMax(5);
        mRatingBarExam.setStepSize(0.5f);
        mRatingBarExam.setMax(5);
        mRatingBarTeacher.setStepSize(0.5f);
        mRatingBarTeacher.setMax(5);
        mRatingBarUsability.setStepSize(0.5f);
        mRatingBarUsability.setMax(5);
        */
    }
    protected void render(JSONObject mJSONObject) {


        if (mJSONObject != null) {
            try {
                int success = mJSONObject.getInt(TAG_SUCCESS);
                if (success == 1) {
                    difficulty = Float.parseFloat(mJSONObject.getString(TAG_DIFFICULTY));
                    //Log.d("tamier log", String.valueOf(difficulty));
                    exam = Float.parseFloat(mJSONObject.getString(TAG_EXAM));
                    teacher = Float.parseFloat(mJSONObject.getString(TAG_TEACHER));
                    usability = Float.parseFloat(mJSONObject.getString(TAG_USABILITY));
                    mRatingBarDifficulty.setRating(difficulty);
                    mRatingBarExam.setRating(exam);
                    mRatingBarTeacher.setRating(teacher);
                    mRatingBarUsability.setRating(usability);
                }
                else {

                    Toast.makeText(getActivity(), "Failed to fetch comments!", Toast.LENGTH_SHORT).show();


                }
            }catch(JSONException e) {

                Toast.makeText(getActivity(), "Error parsing JSON data!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Log.i("tamier log", "JSONException occrued!");

            };


        }
    }

}
