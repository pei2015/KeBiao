package com.lipei.kebiao;

import com.lipei.util.SysApplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 用户信息及菜单界面
 * @author 赵李沛
 *
 */
public class StuInfoActivity extends Activity implements OnClickListener{

	private LinearLayout layout;
	private TextView tv_user_name,tv_user_id,tv_college,tv_grade;
	private RelativeLayout rl_us,rl_qiehuan,rl_xiugai;
	private SharedPreferences preferences;
	private String user_id,user_name,college,grade;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_stu_info);
	SysApplication.getInstance().addActivity(this);
	init();
}
private void init() {
	// TODO Auto-generated method stub
	layout = (LinearLayout)findViewById(R.id.pop_layout);
	layout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
					Toast.LENGTH_SHORT).show();	
		}
	});
	getUserInfo();
	tv_user_name = (TextView)findViewById(R.id.tv_name);
	tv_user_name.setText(user_name);
	tv_user_id = (TextView)findViewById(R.id.tv_xuehao);
	tv_user_id.setText(user_id);
	tv_college = (TextView)findViewById(R.id.tv_xueyuan);
	tv_college.setText(college);
	tv_grade = (TextView)findViewById(R.id.tv_zhuanye);
	if (grade!=null) {
		tv_grade.setText(grade+"级");
	}
	rl_us = (RelativeLayout)findViewById(R.id.rl_us);
	rl_us.setOnClickListener(this);
	rl_qiehuan = (RelativeLayout)findViewById(R.id.rl_qiehuan);
	rl_qiehuan.setOnClickListener(this);
	rl_xiugai = (RelativeLayout)findViewById(R.id.rl_xiugai);
	rl_xiugai.setOnClickListener(this);

}
private void getUserInfo(){
	  preferences=getSharedPreferences("user",0);
	  user_id = preferences.getString("user_id",null);
	  user_name = preferences.getString("user_name",null);
	  college = preferences.getString("user_college",null);
	  grade = preferences.getString("user_grade",null);	
}
@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
	    finish();
		return true;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.rl_us:
            Toast.makeText(getApplication(), "暂未开放！",Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_qiehuan:
			startActivity(new Intent().setClass(StuInfoActivity.this,LoginActivity.class));
			
			break;
		case R.id.rl_xiugai:
			startActivity(new Intent().setClass(StuInfoActivity.this,AlertPwdActivity.class));
			break;
		default:
			break;
		}
	}

}
