package com.team.courseselector;

/**
 * Created by tamier on 15/11/15.
 */

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Admin on 04-06-2015.
 */
public class CalculatorFragment extends Fragment {



    public Cursor c,mCursor;
    protected long id;
    View mView;
    ListView mv;
    ListView mListView;
    View v;
    public CourseDbAdapter mCourseDbAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.activity_calculator_main, container, false);
        mCourseDbAdapter = new CourseDbAdapter(getActivity());
        mCourseDbAdapter.open();

        mv = (ListView) v.findViewById(R.id.list);
        showListView();
        mv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c = mCursor;
                c.moveToPosition(position);
                Intent mIntent = new Intent(getActivity(), CourseEdit.class);
                mIntent.putExtra(CourseDbAdapter.KEY_ROWID, id);
                mIntent.putExtra(CourseDbAdapter.KEY_COURSENUMBER, c.getString(c.getColumnIndex(CourseDbAdapter.KEY_COURSENUMBER)));
                mIntent.putExtra(CourseDbAdapter.KEY_DIFFICULTY, c.getInt(c.getColumnIndex(CourseDbAdapter.KEY_DIFFICULTY)));
                mIntent.putExtra(CourseDbAdapter.KEY_TEACHER, c.getInt(c.getColumnIndex(CourseDbAdapter.KEY_TEACHER)));
                mIntent.putExtra(CourseDbAdapter.KEY_EXAM, c.getInt(c.getColumnIndex(CourseDbAdapter.KEY_EXAM)));
                mIntent.putExtra(CourseDbAdapter.KEY_USABILITY, c.getInt(c.getColumnIndex(CourseDbAdapter.KEY_USABILITY)));
                startActivityForResult(mIntent, 1);
            }
        });
        registerForContextMenu(mv);
        mv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final long deleteid=id;
                AlertDialog mAlertDialog=new AlertDialog.Builder(getActivity()).
                        setTitle("Delete").setMessage("Delete this course?").setIcon(android.R.drawable.ic_menu_delete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCourseDbAdapter.delete(deleteid);
                                showListView();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                return false;
            }
        });


        return v;
    }

    public void showListView(){
        //mListView=getListView();
        //mView=getLayoutInflater().inflate(R.layout.layout_aspect,null);
        //TextView mTextView7=(TextView)mView.findViewById(R.id.textView7);
        //mTextView7.setText("Homework");
        //SeekBar mSeekBar6=(SeekBar)mView.findViewById(R.id.seekBar6);
        //mSeekBar6.setProgress(50);
        //mListView.addHeaderView(mView);
        mCursor = mCourseDbAdapter.getAll();
            getActivity().startManagingCursor(mCursor);
        //String[] from = new String[] { CourseDbAdapter.KEY_ROWID, CourseDbAdapter.KEY_COURSENUMBER};
        String[] from = new String[] {  CourseDbAdapter.KEY_COURSENUMBER};
        //int[] to = new int[] { R.id.textViewList,R.id.textViewList2};
        int[] to = new int[] {R.id.textViewList2};
        SimpleCursorAdapter mSimpleCursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.layout_list, mCursor, from, to);
        mv.setAdapter(mSimpleCursorAdapter);
    }
/*
    @Override
     void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        showListView();
    }

*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_calculator_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_next) {
            Intent mIntent2 = new Intent(getActivity(), WeightActivity.class);
            startActivity(mIntent2);
            return true;
        }
        if(id==R.id.menu_add){
            Intent mIntent= new Intent(getActivity(),CourseEdit.class);
            startActivityForResult(mIntent,0);
        }

        return super.onOptionsItemSelected(item);
    }

}