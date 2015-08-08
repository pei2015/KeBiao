
package com.lipei.DataBase;

import java.util.ArrayList;
import java.util.List;

import com.lipei.bean.ClassInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {

	private List<ClassInfo> list;
	
	private static final String TAG = "DataManager";
	private static final String DB_NAME = "class_data";
	private static final String TABLE_NAME = "class";
	
	public static final String ID = "_id";
	public static final String USER_ID = "user_id";
	public static final String CLASS_ID = "classid";
	public static final String CLASS_NAME = "classname";
	public static final String FROMCLASSNUM = "fromClassNum";
	public static final String CLASSNUMLEN = "classNumLen";
	public static final String WEEKDAY = "weekday";
	public static final String CLASSROOM = "classroom";
	public static final String WEEKS = "weeks";
	public static final String TIME_1 = "time_1";
	public static final String TIME_2 = "time_2";
	public static final String TIME_3 = "time_3";
	public static final String TEACHER_NAME = "teacher_name";
	public static final String NUMBER = "number";
	public static final String SEAT_NUM = "seat_num";

	public static final String USER_NAME = "user_name";
	public static final String USER_PWD = "user_pwd";
	public static final String SILENT = "silent";
	public static final String VIBRATE = "vibrate";

	private static final int DB_VERSION = 2;
	private Context mContext = null;

	private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
			+ ID + " integer primary key," + USER_ID + " varchar,"
			+ CLASS_ID + " varchar," + CLASS_NAME + " varchar,"+FROMCLASSNUM+" int,"+CLASSNUMLEN+" int,"
			+WEEKDAY+" int,"+CLASSROOM+" varchar,"+WEEKS+" varchar,"+TIME_1+" varchar,"+TIME_2+" varchar,"
			+TIME_3+" varchar,"+TEACHER_NAME+" varchar,"+NUMBER+" varchar,"+SEAT_NUM+" varchar"+");";

	private SQLiteDatabase mSQLiteDatabase = null;
	private DataBaseManagementHelper mDatabaseHelper = null;

	private static class DataBaseManagementHelper extends SQLiteOpenHelper {

		DataBaseManagementHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG,"db.getVersion()="+db.getVersion());
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
			db.execSQL(DB_CREATE);
			Log.i(TAG, "db.execSQL(DB_CREATE)");
			Log.e(TAG, DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "DataBaseManagementHelper onUpgrade");
			onCreate(db);
		}
	}

	public DataManager(Context context) {
		mContext = context;
		Log.i(TAG, "DataManager construction!");
	}

	public void openDataBase() throws SQLException {
		
		mDatabaseHelper = new DataBaseManagementHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	public void closeDataBase() throws SQLException {

		mDatabaseHelper.close();
	}

	public long insertClassData(ClassInfo classInfo) {
		
		  String user_id = classInfo.getUser_id();
		  String classid = classInfo.getClassid();
		  String classname = classInfo.getClassname();
		  int fromClassNum = classInfo.getFromClassNum();
		  int classNumLen = classInfo.getClassNumLen();
		  int weekday = classInfo.getWeekday();
		  String classroom = classInfo.getClassRoom();
		  String weeks = classInfo.getWeeks();
		  String time1 = classInfo.getTime1();
		  String time2 = classInfo.getTime2();
		  String time3 = classInfo.getTime3();
		  String teacher_name = classInfo.getTeachter_name();
		  String number = classInfo.getNumber();
		  String seat_num = classInfo.getSeat_num();

		ContentValues values = new ContentValues();
		values.put(USER_ID,user_id);
		values.put(CLASS_ID,classid);
		values.put(CLASS_NAME,classname);
		values.put(FROMCLASSNUM,fromClassNum);
		values.put(CLASSNUMLEN,classNumLen);
		values.put(WEEKDAY,weekday);
		values.put(CLASSROOM,classroom);
		values.put(WEEKS,weeks);
		values.put(TIME_1,time1);
		values.put(TIME_2,time2);
		values.put(TIME_3,time3);
		values.put(TEACHER_NAME,teacher_name);
		values.put(NUMBER,number);
		values.put(SEAT_NUM,seat_num);
		return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
	}

	public List<ClassInfo> findClassListByUserId(String user_id){
		Log.i(TAG,"findClassListByUserId , userName="+user_id);
		
		list = new ArrayList<ClassInfo>();
		Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_ID+"="+"'"+user_id+"'", null, null, null, null);
		if (mCursor.moveToFirst()) {
			while(!mCursor.isAfterLast()){				
			    ClassInfo classInfo = new ClassInfo();
				classInfo.setUser_id(mCursor.getString(mCursor.getColumnIndex(USER_ID)));
				classInfo.setClassid(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
				classInfo.setClassname(mCursor.getString(mCursor.getColumnIndex(CLASS_NAME)));
				classInfo.setFromClassNum(mCursor.getInt(mCursor.getColumnIndex(FROMCLASSNUM)));
				classInfo.setClassNumLen(mCursor.getInt(mCursor.getColumnIndex(CLASSNUMLEN)));
				classInfo.setWeekday(mCursor.getInt(mCursor.getColumnIndex(WEEKDAY)));
				classInfo.setClassRoom(mCursor.getString(mCursor.getColumnIndex(CLASSROOM)));
				classInfo.setWeeks(mCursor.getString(mCursor.getColumnIndex(WEEKS)));
				classInfo.setTime1(mCursor.getString(mCursor.getColumnIndex(TIME_1)));
				classInfo.setTime2(mCursor.getString(mCursor.getColumnIndex(TIME_2)));
				classInfo.setTime3(mCursor.getString(mCursor.getColumnIndex(TIME_3)));
				classInfo.setTeachter_name(mCursor.getString(mCursor.getColumnIndex(TEACHER_NAME)));
				classInfo.setNumber(mCursor.getString(mCursor.getColumnIndex(NUMBER)));
				classInfo.setSeat_num(mCursor.getString(mCursor.getColumnIndex(SEAT_NUM)));
		        list.add(classInfo);
		        mCursor.moveToNext();
			}
		}
		Log.i(TAG,"findClassListByUserId ,查询结果共"+mCursor.getCount()+"行数据");
		return list;	
	}
    public ClassInfo findClassInfoByClassId(String classid){
    	Log.i(TAG,"findClassInfoByClassId , classid="+classid);
    	ClassInfo classInfo = new ClassInfo();
    	Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME, null,CLASS_ID+"="+"'"+classid+"'",null,null,null,null);
    	if (mCursor.moveToFirst()) {
    		classInfo.setClassname(mCursor.getString(mCursor.getColumnIndex(CLASS_NAME)));
        	classInfo.setSeat_num(mCursor.getString(mCursor.getColumnIndex(SEAT_NUM)));
        	classInfo.setClassRoom(mCursor.getString(mCursor.getColumnIndex(CLASSROOM)));
        	classInfo.setTeachter_name(mCursor.getString(mCursor.getColumnIndex(TEACHER_NAME)));
        	classInfo.setNumber(mCursor.getString(mCursor.getColumnIndex(NUMBER)));
        	classInfo.setTime1(mCursor.getString(mCursor.getColumnIndex(TIME_1)));
        	classInfo.setTime2(mCursor.getString(mCursor.getColumnIndex(TIME_2)));
        	classInfo.setTime3(mCursor.getString(mCursor.getColumnIndex(TIME_3)));
		}    	
		Log.i(TAG,"findClassInfoByClassId ,查询结果共"+mCursor.getCount()+"行数据");
		return classInfo;
    	
    }
	public boolean deleteAllUserDatas() {

		return mSQLiteDatabase.delete(TABLE_NAME, null, null) > 0;
	}

}
