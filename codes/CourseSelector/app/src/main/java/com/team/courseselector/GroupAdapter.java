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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {


    private List<String>  mStringList;
    private int[] mIntList;
    static OnItemClickListener mItemClickListener;

    public GroupAdapter(List<String> arg0,int[] arg1) {
        this.mStringList=arg0;
        this.mIntList=arg1;
    }
    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_online_item,parent,false);
        GroupViewHolder mGroupViewHolder=new GroupViewHolder(mView);
        return mGroupViewHolder;
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        String groupName=mStringList.get(position);
        int drawableSource=mIntList[position];
        holder.mTextView.setText(groupName);
        holder.mImageView.setImageResource(drawableSource);
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position,String subjectname);
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;
        ImageView mImageView;


        public GroupViewHolder(View itemView) {
            super(itemView);
            mImageView=(ImageView)itemView.findViewById(R.id.iv_group);
            mTextView=(TextView)itemView.findViewById(R.id.tv_group);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String mString=mTextView.getText().toString();
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v, getPosition(),mString);
            }

        }
    }

}
