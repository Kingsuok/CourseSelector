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

        import java.util.Arrays;
        import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {


    private String[] mStringList;
    private String[] mStringShown;
    static OnItemClickListener mItemClickListener;

    public SubjectAdapter(String[] arg0,String[] arg1) {
        this.mStringList=arg0;
        this.mStringShown=arg1;
    }

    public String[] getmStringList(){
        return mStringList;
    }
    public String[] getmStringShown(){
        return mStringShown;
    }
    @Override
    public int getItemCount() {
        return mStringList.length;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject,parent,false);
        SubjectViewHolder mSubjectViewHolder=new SubjectViewHolder(mView);
        return mSubjectViewHolder;
    }

    public void setFilter(String[] arg0,String[] arg1){
        //Arrays.fill(mStringList,null);
        mStringList=arg0;
        mStringShown=arg1;
        notifyDataSetChanged();
    }
    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        String groupName=mStringList[position];
        String groupNameShown=mStringShown[position];
        holder.mTextView.setText(groupName);
        holder.mTextViewShown.setText(groupNameShown);
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position,String subjectname);
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;
        TextView mTextViewShown;
        public SubjectViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.tv_subject);
            mTextViewShown=(TextView)itemView.findViewById(R.id.tv_subject_SHOW);
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
