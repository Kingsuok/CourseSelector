package com.tamier.serverdemo;

import android.app.Activity;
import android.os.Message;



import java.lang.ref.WeakReference;

/**
 * Created by tamier on 28/10/15.
 */

public class IHandler extends android.os.Handler{
    private WeakReference<Activity> mActivity;
    public IHandler(LoginActivity activity){

        mActivity=new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg){


        int flag=msg.what;
        switch (flag) {
            case 0:
                String errorMsg = (String) msg.getData().getSerializable("ErrorMsg");
              
                ((LoginActivity) mActivity.get()).showTip(errorMsg);
                break;
            case 1:
                ((LoginActivity) mActivity.get()).showTip(LoginActivity.STRING_LOGIN_SUCCESS);
                break;
        }

        if(((LoginActivity) mActivity.get()).mProgressDialog!=null){
            ((LoginActivity) mActivity.get()).mProgressDialog.dismiss();
        }
    }

}
