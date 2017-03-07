package com.team.courseselector;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v7.widget.Toolbar;

public class ResultPageActivity extends AppCompatActivity {

    protected ListView mListView;
    private Toolbar mToolBar;
    private CourseDbAdapter mCouseDbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        mListView=(ListView)findViewById(R.id.listview);
        mToolBar=(Toolbar)findViewById(R.id.tb_appbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Results");
        mCouseDbAdapter=new CourseDbAdapter(this);
        mCouseDbAdapter.open();
        show();
        /*
        Button mButton=(Button)findViewById(R.id.button4);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ResultPageActivity.this,GroupActivity.class);
                startActivity(mIntent);
                finish();
            }
        });*/
    }

    private void show(){
        Cursor mCursor=mCouseDbAdapter.getAllByMarkOrder();
        startManagingCursor(mCursor);
        //String[] from=new String[]{mCouseDbAdapter.KEY_ROWID,mCouseDbAdapter.KEY_COURSENUMBER,mCouseDbAdapter.KEY_MARK};
        String[] from=new String[]{mCouseDbAdapter.KEY_COURSENUMBER,mCouseDbAdapter.KEY_MARK};
        //int[] to=new int[]{R.id.textView2,R.id.textView8,R.id.textView9};
        int[] to=new int[]{R.id.textView9,R.id.textView8};
        SimpleCursorAdapter msimpleCursorAdapter= new SimpleCursorAdapter(this,R.layout.layout_resultitem,mCursor,from,to);
        mListView.setAdapter(msimpleCursorAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_finish) {

            finish();
            return true;
        }
        else if (id==android.R.id.home){
            Intent mIntent = new Intent(this,WeightActivity.class);
            startActivity(mIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
