package com.team.courseselector;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tamier on 17/11/15.
 */
public class OnlineFragment extends Fragment{

    View v;
    private static final String SUBJECT_NAME="Subject";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_online,container,false);
        RecyclerView mRecyclerView=(RecyclerView)v.findViewById(R.id.rv_group);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        List<String> mStringList=new ArrayList<String>();
        mStringList.add("VPA");
        mStringList.add("ART");
        mStringList.add("MAT");
        mStringList.add("ENG");
        mStringList.add("ENV");
        mStringList.add("AHS");
        mStringList.add("REN");
        mStringList.add("SCI");
        mStringList.add("STP");
        mStringList.add("CGC");
        mStringList.add("STJ");
        mStringList.add("GRAD");
        mStringList.add("IS");/*
        //int[] mIntList=new int[]{R.drawable.background,R.drawable.background,R.drawable.background,R.drawable.background,
                R.drawable.background,R.drawable.background,R.drawable.background,R.drawable.background,R.drawable.background,
                R.drawable.background,R.drawable.background,R.drawable.background,R.drawable.background};*/
        int[] mIntList=new int[]{R.drawable.vpa,R.drawable.art,R.drawable.math,R.drawable.engineering,
            R.drawable.environment,R.drawable.appliedhealthscience,R.drawable.rension,R.drawable.science,R.drawable.stpaul,
            R.drawable.conrad,R.drawable.stj,R.drawable.graduate,R.drawable.independent};
        GroupAdapter mGroupAdapter=new GroupAdapter(mStringList,mIntList);
        mGroupAdapter.SetOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String subjectname) {
                Intent mIntent=new Intent(getActivity(),SubjectActivity.class);
                mIntent.putExtra(SUBJECT_NAME,subjectname);
                //Toast.makeText(getActivity(),subjectname,Toast.LENGTH_SHORT).show();
                startActivity(mIntent);
            }
        });
        mRecyclerView.setAdapter(mGroupAdapter);

        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_online_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
