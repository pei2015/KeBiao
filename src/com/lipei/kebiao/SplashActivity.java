package com.lipei.kebiao;

import java.util.ArrayList;
import java.util.List;

import com.lipei.DataBase.DataManager;
import com.lipei.bean.ClassInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity{
    private DataManager mUserDataManager;
	private List<ClassInfo> classList;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_splash);
	 SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
	 final String user_id = preferences.getString("user_id", null);
	  if (mUserDataManager == null) {
			mUserDataManager = new DataManager(this);
			mUserDataManager.openDataBase();
       }
	  classList = new ArrayList<ClassInfo>();
	  if (user_id!=null) {
		  classList = mUserDataManager.findClassListByUserId(user_id);
	}
	// 延迟2秒进入主界面
	new Handler().postDelayed(new Runnable() {

		@Override
		public void run() {
			Intent intent = new Intent();
			if (user_id!=null&&classList!=null&&classList.size()>0) {							
				intent.setClass(SplashActivity.this, MainActivity.class);
    			startActivity(intent);
			}
            else {
            	intent.setClass(SplashActivity.this, LoginActivity.class);
    			startActivity(intent);
			}			
			finish();
		}
	}, 2000);
}
}

