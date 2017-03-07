package com.team.courseselector;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by tamier on 21/11/15.
 */
public class DescriptionFragment extends Fragment {

    String description;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //description=((TextView)activity.findViewById(R.id.tv_title)).getText().toString();
        description=((CourseLiteInfoActivity)activity).getDescription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_description,container,false);
        TextView mTextViewDescription=(TextView)v.findViewById(R.id.tv_f_description);
        mTextViewDescription.setText(description);
        /*
        View pv=inflater.inflate(R.layout.activity_course_lite_info,null);
        TextView mTextView=(TextView)pv.findViewById(R.id.tv_description);
        String description=mTextView.getText().toString();
        Log.i("tamier log",description);
        TextView mTextView2=(TextView)v.findViewById(R.id.tv_description);
        mTextView2.setText(description);
        */
        return v;
    }
}
