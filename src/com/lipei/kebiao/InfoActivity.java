package com.lipei.kebiao;

import java.util.ArrayList;
import java.util.List;

import com.lipei.DataBase.DataManager;
import com.lipei.bean.ClassInfo;
import com.lipei.kebiao.R.string;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
/**
 * 详细信息界面
 * @author 赵李沛
 *
 */
public class InfoActivity extends SwipeBackActivity{
   	private TextView tv_classname,tv_seatnum,tv_examname,tv_teacher,tv_date,tv_number,tv_back;
   	private DataManager mUserDataManager;
   	private ClassInfo classInfo; 
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String classid = intent.getStringExtra("classid");
	        if (mUserDataManager == null) {
				mUserDataManager = new DataManager(this);
				mUserDataManager.openDataBase();
	         }
	    classInfo = new ClassInfo();
	    classInfo = mUserDataManager.findClassInfoByClassId(classid);
     tv_classname = (TextView)findViewById(R.id.tv_1);
     tv_classname.setText(classInfo.getClassname());
     tv_seatnum = (TextView)findViewById(R.id.tv_2);
     if (classInfo.getSeat_num()!=null) {
         tv_seatnum.setText("座号："+classInfo.getSeat_num());
	}

     tv_examname = (TextView)findViewById(R.id.tv_3);
     tv_examname.setText(classInfo.getClassRoom());
     tv_teacher = (TextView)findViewById(R.id.tv_6);
     tv_teacher.setText(classInfo.getTeachter_name());
     tv_date = (TextView)findViewById(R.id.tv_4);
     tv_date.setText(classInfo.getTime1()+" "+classInfo.getTime2()+"-"+classInfo.getTime3());
     tv_number = (TextView)findViewById(R.id.tv_8);
     tv_number.setText(classInfo.getNumber());
     tv_back = (TextView)findViewById(R.id.mimedetail_back);
     tv_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	}
@Override
public void startActivity(Intent intent) {
	// TODO Auto-generated method stub
	super.startActivity(intent);
}
}
