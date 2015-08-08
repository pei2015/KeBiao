package com.lipei.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lipei.DataBase.DataManager;
import com.lipei.bean.ClassInfo;
import com.lipei.kebiao.InfoActivity;
import com.lipei.kebiao.R;
import com.lipei.util.DateUtils;
import com.lipei.view.PopMenu;
import com.lipei.view.ScheduleView;
import com.lipei.view.ScheduleView.OnItemClassClickListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_KaoChang extends Fragment implements OnClickListener{
	private ScheduleView scheduleView;
	private LinearLayout popLinearLayout;
	private TextView popTextView;
	private PopMenu popMenu;
	private List<ClassInfo> classList;
	private List<ClassInfo> classlist_week1;
	private List<ClassInfo> classlist_week2;
    private SharedPreferences preferences;
    private DataManager mUserDataManager;
    private String week1;
    private String week2;
    private String week1_fday;
    private String week2_fday;
/**
 * 考试安排界面
 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View view = inflater.inflate(R.layout.fragment_kaochang, container,false);
		init(view);		
		return view;		
	}
	private void init(View view) {
		// TODO Auto-generated method stub
        preferences=getActivity().getSharedPreferences("user",0);
        if (mUserDataManager == null) {
			mUserDataManager = new DataManager(getActivity());
			mUserDataManager.openDataBase();
         }
        popTextView = (TextView)view.findViewById(R.id.pop_tv);
        scheduleView =(ScheduleView)view.findViewById(R.id.scheduleView);
        scheduleView.setOnItemClassClickListener(onItemClassClickListener);
        getClassDate();
    	scheduleView.setDateList(DateUtils.convertWeekByDate(DateUtils.getDateFromString(week1_fday)));
		scheduleView.setClassList(classlist_week1);
		
        if (week1_fday!=null) {
        	 if (DateUtils.isSameDate(week1_fday,DateUtils.ConverToString(DateUtils.getLastDay(new Date()), "MM-dd"))) {
     			scheduleView.setDateList(DateUtils.convertWeekByDate(DateUtils.getDateFromString(week1_fday)));
     			scheduleView.setClassList(classlist_week1);
     			popTextView.setText("第一周");
     		}
		}
       else if (week2_fday!=null) {
    	    if (DateUtils.isSameDate(week2_fday, DateUtils.ConverToString(DateUtils.getLastDay(new Date()), "MM-dd"))) {
			scheduleView.setDateList(DateUtils.convertWeekByDate(DateUtils.getDateFromString(week2_fday)));
			scheduleView.setClassList(classlist_week2);
			popTextView.setText("第二周");
		}
	    }
       else  if (week1_fday==null&&week2_fday==null) {
		    scheduleView.setDateList(DateUtils.convertWeekByDate(new Date()));
	    }

        popLinearLayout = (LinearLayout)view.findViewById(R.id.pop_layout);
        popLinearLayout.setOnClickListener(this);
        
        popMenu = new PopMenu(getActivity());
        popMenu.addItems(new String[]{week1,week2});
        popMenu.setOnItemClickListener(popMenuOnItemClickListener);
	}
	//获取本地sqlite数据库数据
	public void getClassDate(){
		String user_id = preferences.getString("user_id",null);
		classList = new ArrayList<ClassInfo>();
		classlist_week1 = new ArrayList<ClassInfo>();
		classlist_week2 = new ArrayList<ClassInfo>();
		classList = mUserDataManager.findClassListByUserId(user_id);
		if (classList!=null&&classList.size()>0) {
	        for (int i = 0; i <classList.size(); i++) {
	        	String weeks = classList.get(i).getWeeks();	        	
				if (weeks.equals("第一周")) {
					classlist_week1.add(classList.get(i));
				}
				else if (weeks.equals("第二周")) {
					classlist_week2.add(classList.get(i));
				}
			}
	        if (classlist_week1!=null&&classlist_week1.size()>0) {
				week1 = classlist_week1.get(0).getWeeks();
				week1_fday = classlist_week1.get(0).getTime1();
			}
	        if (classlist_week2!=null&&classlist_week2.size()>0) {
				week2 = classlist_week2.get(0).getWeeks();
				week2_fday = classlist_week2.get(0).getTime1();
			}
		}
	}
	//表格监听器
	OnItemClassClickListener onItemClassClickListener = new OnItemClassClickListener() {
		
		@Override
		public void onClick(ClassInfo classInfo) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(getActivity(), InfoActivity.class);
			intent.putExtra("classid",classInfo.getClassid());
			startActivity(intent);
		}
	};
	// 弹出菜单监听器
	OnItemClickListener popMenuOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub			
			switch (arg2) {
			case 0:
				if (classlist_week1!=null&&classlist_week1.size()>0) {
					scheduleView.setDateList(DateUtils.convertWeekByDate(DateUtils.getDateFromString(week1_fday)));
					scheduleView.setClassList(classlist_week1);
					popTextView.setText(week1);					
				}				
				popMenu.dismiss();
				break;
            case 1:
            	if (classlist_week2!=null&&classlist_week2.size()>0) {
            		scheduleView.setDateList(DateUtils.convertWeekByDate(DateUtils.getDateFromString(week2_fday)));
        			scheduleView.setClassList(classlist_week2);
    				popTextView.setText(week2);	
				}       	
				popMenu.dismiss();
            	break;
			default:
				break;
			}
		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.pop_layout:{
			popMenu.showAsDropDown(arg0);
		}		
			break;

		default:
			break;
		}
	}
}
