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
 * 获取考场信息后台线程类
 * @author 赵李沛
 *
 */
public class Task_GetExamInfo extends AsyncTask<String,integer,String>{

	private Activity activity;
	private HttpDataListener httpDataListener;
	private ProgressDialog dialog;
	public Task_GetExamInfo(Activity activity,HttpDataListener httpDataListener) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.httpDataListener = httpDataListener;
		dialog = new ProgressDialog(activity);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setMessage("导入用户数据...");
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
		NameValuePair[] pairs = new NameValuePair[2];
		pairs[0] = new BasicNameValuePair("type",params[0]);
		pairs[1] = new BasicNameValuePair("jsonString",params[1]);
		result = CustomHttpClient.PostFromWebByHttpClient(activity, Constants.URL+"json_findByFd_code",pairs);
		return result;
	}

}
