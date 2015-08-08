package com.lipei.task;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.lipei.listener.HttpDataListener;
import com.lipei.util.Constants;
import com.lipei.util.CustomHttpClient;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
/**
 * 修改密码后台线程类
 * @author 赵李沛
 *
 */
public class Task_AlertPwd extends AsyncTask<String,integer,String>{
	private Activity activity;
	private HttpDataListener httpDataListener;
	private ProgressDialog dialog;
	public Task_AlertPwd(Activity activity,HttpDataListener httpDataListener) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.httpDataListener = httpDataListener;
		dialog = new ProgressDialog(activity);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("请稍后...");
		dialog.setCancelable(false);
		dialog.show();
	}
    @Override
    protected void onPostExecute(String result) {
    	// TODO Auto-generated method stub
    	super.onPostExecute(result);
	    dialog.dismiss();
	    httpDataListener.ShowResponse(result);
    }
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = null;
		NameValuePair[] pairs = new NameValuePair[1];
		pairs[0] = new BasicNameValuePair("jsonString",params[0]);
		result = CustomHttpClient.PostFromWebByHttpClient(activity, Constants.URL+"json_changePwd",pairs);
		return result;
	}

}
