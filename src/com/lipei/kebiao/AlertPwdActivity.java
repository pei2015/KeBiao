package com.lipei.kebiao;

import java.util.HashMap;
import java.util.Map;

import com.lipei.listener.HttpDataListener;
import com.lipei.task.Task_AlertPwd;
import com.lipei.util.JsonUtils;
import com.lipei.util.SysApplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 修改密码界面
 * @author 赵李沛
 *
 */
public class AlertPwdActivity extends SwipeBackActivity implements OnClickListener{
	private TextView tv_back;
	private Button alertButton;
    private SharedPreferences preferences;
    private Editor editor;
    private EditText ed_user_id,ed_old_pwd,ed_new_pwd;
    private String user_id,old_pwd,new_pwd;
	private HttpDataListener httpDataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_alertpwd);
    	SysApplication.getInstance().addActivity(this);
    	init();
    }
    private void init() {
		// TODO Auto-generated method stub
       tv_back = (TextView)findViewById(R.id.mime_back);
       tv_back.setOnClickListener(this);
       alertButton = (Button)findViewById(R.id.alert_btn);
       alertButton.setOnClickListener(this);
       ed_user_id = (EditText)findViewById(R.id.ed_user_id);
       ed_old_pwd = (EditText)findViewById(R.id.old_user_passwd);
       ed_new_pwd = (EditText)findViewById(R.id.new_user_passwd);
		 preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		 editor = preferences.edit();
	}
    private void alertPwd(){
    	user_id = ed_user_id.getText().toString();
    	old_pwd = ed_old_pwd.getText().toString();
    	new_pwd = ed_new_pwd.getText().toString();
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("user_id",user_id);
    	map.put("old_pwd",old_pwd);
    	map.put("new_pwd",new_pwd);
    	if (TextUtils.isEmpty(user_id)||TextUtils.isEmpty(old_pwd)||TextUtils.isEmpty(new_pwd)) {
			Toast.makeText(getApplication(), "用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
		}
    	else {
			String jsonString = JsonUtils.getJsonFromMap(map).toString();
			httpDataListener = new HttpDataListener() {
				
				@Override
				public void ShowResponse(String result) {
					// TODO Auto-generated method stub
					String _result = JsonUtils.GetStringFromJsonString(result);
					if (_result.equals("1")) {
						Toast.makeText(getApplication(), "修改成功！",Toast.LENGTH_SHORT).show();
						editor.clear();
						editor.commit();
						startActivity(new Intent().setClass(AlertPwdActivity.this,LoginActivity.class));
						SysApplication.getInstance().exit();
					}
					else {
						Toast.makeText(getApplication(), "修改失败！"+result,Toast.LENGTH_SHORT).show();
					}
				}
			};
			Task_AlertPwd task_AlertPwd = new Task_AlertPwd(this, httpDataListener);
			task_AlertPwd.execute(jsonString);
		}
    }
    @Override
    public void startActivity(Intent intent) {
    	// TODO Auto-generated method stub
    	super.startActivity(intent);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.mime_back:
			finish();
			break;
		case R.id.alert_btn:
			alertPwd();
			break;
		default:
			break;
		}
		
	}
}
