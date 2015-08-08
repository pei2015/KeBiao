package com.lipei.kebiao;

import java.util.ArrayList;
import java.util.List;

import com.lipei.DataBase.DataManager;
import com.lipei.bean.ClassInfo;
import com.lipei.fragment.Fragment_KaoChang;
import com.lipei.fragment.Fragment_KeBiao;
import com.lipei.fragment.Fragment_other;
import com.lipei.util.SysApplication;
import com.lipei.view.PagerSlidingTabStrip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
/**
 * 主布局界面
 * @author 赵李沛
 *
 */
public class MainActivity extends FragmentActivity{
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private List<Fragment> mFragmentList = new ArrayList<Fragment>(); 
    private DataManager mUserDataManager;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mUserDataManager == null) {
			mUserDataManager = new DataManager(this);
			mUserDataManager.openDataBase();
       }
      	 SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
    	 final String user_id = preferences.getString("user_id", null);
    	 ArrayList<String> list = new ArrayList<String>();
    	 List<ClassInfo>  classList = new ArrayList<ClassInfo>();
    		  if (user_id!=null) {
    			  classList = mUserDataManager.findClassListByUserId(user_id);
    			  for (int i = 0; i <classList.size(); i++) {
    				list.add(classList.get(i).getTime1());
    			}
    		}
	    intent = new Intent();
		intent.setAction("com.lipei.kebiao.action.MY_ACTION");
		if (list!=null&&list.size()>0) {
			intent.putStringArrayListExtra("timelist",list);
			startService(intent);
		}
		

        SysApplication.getInstance().addActivity(this);
        Fragment_KaoChang mKaoChang = new Fragment_KaoChang();
        Fragment_KeBiao mKeBiao = new Fragment_KeBiao();
        Fragment_other other = new Fragment_other();
        mFragmentList.add(mKaoChang);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager(),mFragmentList);

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);  
		
		ImageView user = (ImageView)findViewById(R.id.user_iv);
		user.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				  startActivity(new Intent(MainActivity.this,StuInfoActivity.class));
			}
		});
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "考场"}; 
		List<Fragment> fragmentList = new ArrayList<Fragment>();  
		public MyPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
			super(fm);
			 this.fragmentList = fragmentList;  
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
			
		}

	}
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	stopService(intent);
    }
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	//super.onBackPressed();
    	  Intent i= new Intent(Intent.ACTION_MAIN); 
    	    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    	    i.addCategory(Intent.CATEGORY_HOME); 
    	    startActivity(i); 
    }
}
