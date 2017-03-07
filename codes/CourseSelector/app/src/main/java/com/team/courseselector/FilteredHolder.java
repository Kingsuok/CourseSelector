package com.team.courseselector;

import java.util.List;

/**
 * Created by tamier on 30/11/15.
 */
public class FilteredHolder {
    public List<String> mlist;
    public List<String> mlistshown;

    public void setList(List<String> list){
        mlist=list;
    }

    public void setListShown(List<String> list){
        mlistshown=list;
    }

    public List<String> getList(){
        return mlist;
    }

    public List<String> getListShown(){
        return mlistshown;
    }
}
