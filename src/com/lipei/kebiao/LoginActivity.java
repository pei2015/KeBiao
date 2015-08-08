package com.lipei.kebiao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lipei.DataBase.DataManager;
import com.lipei.bean.ClassInfo;
import com.lipei.listener.HttpDataListener;
import com.lipei.task.Task_GetExamInfo;
import com.lipei.task.Task_Login;
import com.lipei.util.DateUtils;
import com.lipei.util.JsonUtils;
import com.lipei.util.SysApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 登陆
 * @author 赵李沛
 *
 */
public class LoginActivity extends Activity implements OnClickListener{
	private Button loginButton;
	private EditText user_id_edit,user_pwd_edit;
	private HttpDataListener httpDataListener;
	private String user_type = null;
    private SharedPreferences preferences;
	private DataManager mUserDataManager;
	private Map<String,Object> userinfo= null;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);
	init();
}
private void init() {
	// TODO Auto-generated method stub
	 if (mUserDataManager == null) {
			mUserDataManager = new DataManager(this);
			mUserDataManager.openDataBase();
  }
loginButton = (Button)findViewById(R.id.login_btn);
loginButton.setOnClickListener(this);
user_id_edit = (EditText)findViewById(R.id.user_id);
user_pwd_edit = (EditText)findViewById(R.id.user_passwd);

}
@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	Intent intent = new Intent();
	intent.setClass(LoginActivity.this,MainActivity.class);
	UserLogin();
	//startActivity(intent);	
	//SysApplication.getInstance().exit();
	//finish();

}
void UserLogin(){
	final String user_id = user_id_edit.getText().toString();
	final String user_pwd = user_pwd_edit.getText().toString();
	Map<String,Object> map = new HashMap<String, Object>();
	map.put("fd_code",user_id);
	map.put("fd_pwd",user_pwd);
	if (TextUtils.isEmpty(user_id)||TextUtils.isEmpty(user_pwd)) {
		Toast.makeText(getApplicationContext(), "用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
	}
	else {
		String jsonString = JsonUtils.getJsonFromMap(map).toString();
		httpDataListener = new HttpDataListener() {
			
			public void ShowResponse(String result) {
				// TODO Auto-generated method stub
				
			   if (result!=null) {
					userinfo = new HashMap<String, Object>();	
					 userinfo = JsonUtils.getJosn(result);
					user_type = userinfo.get("fd_user_type").toString();
					if (user_type.equals("0")||user_type.equals("1")) {
						 preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
							Editor editor = preferences.edit();
							editor.clear();
							editor.putString("user_id", user_id);
							editor.putString("user_pwd", user_pwd);
							editor.putString("user_type",userinfo.get("fd_user_type").toString());
						    editor.putString("user_name",userinfo.get("fd_name").toString());
							editor.putString("user_college",userinfo.get("fd_college_id").toString());
							if (userinfo.get("fd_grade")!=null) {
								editor.putString("user_grade",userinfo.get("fd_grade").toString());
							}
							editor.commit();
							SysApplication.getInstance().exit();
							getExamInfoList();
					}
					else {
						Toast.makeText(getApplication(), "用户名或密码错误！",Toast.LENGTH_SHORT).show();
					}
				}	
			   else {
				Toast.makeText(getApplication(), "服务器链接失败！",Toast.LENGTH_SHORT).show();
			}
			}
		};
		Task_Login task_Login = new Task_Login(this, httpDataListener);
		task_Login.execute(jsonString);
	}	
}
private void getExamInfoList(){
	
	String user_id = preferences.getString("user_id", null);
	String user_type = preferences.getString("user_type",null);
	Map<String, Object> map = new HashMap<String, Object>();
	if (user_id!=null) {
		map.put("fd_code", user_id);
		String jsonString = JsonUtils.getJsonFromMap(map).toString();
		HttpDataListener httpDataListener = new HttpDataListener() {				
			@Override
			public void ShowResponse(String result) {
				// TODO Auto-generated method stub	
				List<Map<String, Object>> list = null;
				list = JsonUtils.getListMapFromJsonString("result",result);
			
				if (list!=null&&list.size()>0) {
                    saveClassData(list);
				}
				else {
					Toast.makeText(getApplication(), "您没有考试安排，暂不能登陆！",Toast.LENGTH_SHORT).show();
				}
			}
		};
		Task_GetExamInfo task_GetExamInfo = new Task_GetExamInfo(this, httpDataListener);
		task_GetExamInfo.execute(user_type,jsonString);
	}
	
}
private void saveClassData(List<Map<String, Object>> list) {
	mUserDataManager.deleteAllUserDatas();
	String user_id = preferences.getString("user_id", null);
	String user_type = preferences.getString("user_type",null);
	long flag = 0;
	for (int i = 0; i < list.size(); i++) {
		ClassInfo classInfo = new ClassInfo();
		classInfo.setClassname(list.get(i).get("fd_course_id").toString());
		classInfo.setFromClassNum(DateUtils.getFromClassNum(list.get(i).get("time2").toString()));
		classInfo.setClassNumLen(DateUtils.getClassLen(list.get(i).get("time3").toString(),
				list.get(i).get("time2").toString()));
		classInfo.setClassRoom(list.get(i).get("fd_exam_name").toString());
		classInfo.setWeekday(DateUtils.getWeek(DateUtils.getDateFromString(list.get(i).get("time1").toString())));
		classInfo.setClassid(list.get(i).get("fd_exam_id").toString());
		classInfo.setUser_id(user_id);
		classInfo.setWeeks(list.get(i).get("fd_week").toString());
		classInfo.setTime1(list.get(i).get("time1").toString());
		classInfo.setTime2(list.get(i).get("time2").toString());
		classInfo.setTime3(list.get(i).get("time3").toString());
		classInfo.setTeachter_name(list.get(i).get("fd_teacher_name").toString());
		classInfo.setNumber(list.get(i).get("fd_number").toString());
		if (user_type.equals("1")) {
			classInfo.setSeat_num(list.get(i).get("fd_seat_num").toString());	
		}
	    flag = mUserDataManager.insertClassData(classInfo);			
		}
	if (flag!=-1) {
		Toast.makeText(getApplication(), "登陆成功！", Toast.LENGTH_SHORT).show();	
		startActivity(new Intent(LoginActivity.this,MainActivity.class));
		finish();
	}
	else {
		Toast.makeText(getApplication(), "登陆失败！", Toast.LENGTH_SHORT).show();
	}
	}
}

