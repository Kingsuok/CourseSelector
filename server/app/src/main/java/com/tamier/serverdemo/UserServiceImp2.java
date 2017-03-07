package com.tamier.serverdemo;

import android.content.Entity;
import android.net.http.HttpResponseCache;
import android.provider.Settings;
import android.util.Log;


import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tamier on 27/10/15.
 */
public class UserServiceImp2 implements UserService {

    private static final String TAG="UserServiceImp2";
    public void userLogin(String loginName,String loginPassword) throws Exception{


        HttpParams mHttpParams=new BasicHttpParams();
        HttpProtocolParams.setContentCharset(mHttpParams, HTTP.UTF_8);
        HttpConnectionParams.setConnectionTimeout(mHttpParams, 3000);
        HttpConnectionParams.setSoTimeout(mHttpParams, 3000);

        SchemeRegistry mSchemeRegistry=new SchemeRegistry();
        mSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
        mSchemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(),433));

        ClientConnectionManager mClientConnectionManage=new ThreadSafeClientConnManager(mHttpParams,mSchemeRegistry);

        HttpClient mHttpClient=new DefaultHttpClient(mClientConnectionManage,mHttpParams);

        String uri="http://192.168.43.245:8080/Tamier_Client_Server_Data_Exchange/login.do?LoginName="+loginName+"&LoginPassword="+loginPassword;
        HttpPost mHttpPost=new HttpPost(uri);
        NameValuePair paramloginName=new BasicNameValuePair("LoginName",loginName);
        NameValuePair paramloginPassword=new BasicNameValuePair("LoginPassword",loginPassword);
        List<NameValuePair> mparameters=new ArrayList<NameValuePair>();
        mparameters.add(paramloginName);
        mparameters.add(paramloginPassword);
        mHttpPost.setEntity(new UrlEncodedFormEntity(mparameters,HTTP.UTF_8));
        HttpResponse mHttpResponse=mHttpClient.execute(mHttpPost);
        int statuscode=mHttpResponse.getStatusLine().getStatusCode();
        Log.i("tamier log",String.valueOf(statuscode));

        if(statuscode!= HttpStatus.SC_OK){
            throw new ServiceRulesException(LoginActivity.SERVER_ERROR);
        }

        HttpEntity mEntity=mHttpResponse.getEntity();
        String result= EntityUtils.toString(mEntity, HTTP.UTF_8);
        Log.i("tamier log",result);
        if(result.equals("login failed!")){
            throw new ServiceRulesException(LoginActivity.HING_LOGIN_FAILED);
        }
        else if(result.equals("success!")){
            Log.i(TAG,LoginActivity.STRING_LOGIN_SUCCESS);
        }

    };
}
