package com.tamier.serverdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.conn.ConnectTimeoutException;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LoginActivity extends AppCompatActivity {

    private UserService userService=new UserServiceImp2();
    public ProgressDialog mProgressDialog;


    public final static int FLAG_LOGIN_SUCCESS=1;
    public final static String STRING_LOGIN_SUCCESS="Login succeeds!";
    public final static String HING_LOGIN_FAILED="Username or password error!";
    public final static String STRING_userLogin_method_itself_ERROR="Unknown Error!";
    public final static String SERVER_ERROR="SERVER ERROR!";
    public final static String SERVER_TIMEOUT="Timeout";
    public final static String SERVER_BUSY="Server is busy";

    IHandler mIHandler=new IHandler(this);

    public void showTip(String errMsg){
        Toast.makeText(this,errMsg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mEditText=(EditText)findViewById(R.id.editText);
        final EditText mEditText2=(EditText)findViewById(R.id.editText2);
        final Button mButton=(Button)findViewById(R.id.button);
        Button mButton2=(Button)findViewById(R.id.button2);

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginname = mEditText.getText().toString();
                final String loginpassword = mEditText2.getText().toString();

                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(LoginActivity.this);
                }
                mProgressDialog.setTitle("Please wait");
                mProgressDialog.setMessage("Logging in...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

     
                Thread mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            userService.userLogin(loginname, loginpassword);

                            mIHandler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
                        }
                        catch (ConnectTimeoutException e){
                            Message message = new Message();
                            Bundle data = new Bundle();

                            data.putSerializable("ErrorMsg",SERVER_TIMEOUT);
                            message.setData(data);
                            mIHandler.sendMessage(message);
                        }
                        catch (SocketTimeoutException e){
                            Message message = new Message();
                            Bundle data = new Bundle();
   
                            data.putSerializable("ErrorMsg", SERVER_BUSY);
                            message.setData(data);
                            mIHandler.sendMessage(message);
                        }
                        catch (ServiceRulesException e) {
                            Message message = new Message();
                            Bundle data = new Bundle();
 
                            data.putSerializable("ErrorMsg", e.getMessage());
                            message.setData(data);
                            mIHandler.sendMessage(message);
                        } catch (Exception e) {
                            Message message = new Message();
                            Bundle data = new Bundle();
 
                            data.putSerializable("ErrorMsg", STRING_userLogin_method_itself_ERROR);
                            message.setData(data);
                            mIHandler.sendMessage(message);

                        }

                    }
                });
                mThread.start();


            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText(null);
                mEditText2.setText(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
