package com.lipei.kebiao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lipei.DataBase.DataManager;
import com.lipei.kebiao.R;
import com.lipei.kebiao.SplashActivity;
import com.lipei.util.DateUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
/**
 * 消息推送Service
 * @author 赵李沛
 *
 */
public class MessageService extends Service {	
        //获取消息线程
	    private MessageThread messageThread = null;
	 
	    //点击查看
	    private Intent messageIntent = null;
	    private PendingIntent messagePendingIntent = null;
	 
	    //通知栏消息
	    private int messageNotificationID = 1000;
	    private Notification messageNotification = null;
	    private NotificationManager messageNotificatioManager = null;
	    private List<String> list=new ArrayList<String>();
	 
	    public IBinder onBind(Intent intent) {
	        return null;
	    }
	     
	    @Override
		public void onCreate() {
	    	 //初始化
	        messageNotification = new Notification();
	        messageNotification.icon = R.drawable.icon;
	        messageNotification.tickerText = "新消息";
            messageNotification.defaults = Notification.DEFAULT_SOUND;
	        messageNotificatioManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	        //点击跳转的activity
	        messageIntent = new Intent(this, SplashActivity.class);
	        messagePendingIntent = PendingIntent.getActivity(this,0,messageIntent,0);
	        //开启线程
	        messageThread = new MessageThread();
	        messageThread.isRunning = true;
	        messageThread.start();
			super.onCreate();
		}

      @Override
       public void onStart(Intent intent, int startId) {
            // TODO Auto-generated method stub
            super.onStart(intent, startId);
            list =intent.getStringArrayListExtra("timelist");
        }
		/**
	     * 从服务器端获取消息
	     *
     */
	    class MessageThread extends Thread{
	        //运行状态，下一步骤有大用
	        public boolean isRunning = true;
	        public void run() {
	            while(isRunning){
	                try {
	                    //获取服务器消息
	                    String serverMessage = getServerMessage();
	                 
	                    if(serverMessage!=null&&!"".equals(serverMessage)){
	                        //更新通知栏
	                        messageNotification.setLatestEventInfo(MessageService.this,"新消息",serverMessage,messagePendingIntent);
	                        messageNotificatioManager.notify(messageNotificationID, messageNotification);
                        //每次通知完，通知ID递增一下，避免消息覆盖掉
                            messageNotificationID++;
		                    //休息
		                    Thread.sleep(10000000);
                    }
               } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
	    }
		@Override
	public void onDestroy() {
		//  System.exit(0);
		//或者，二选一，推荐使用System.exit(0)，这样进程退出的更干净
	       messageThread.isRunning = false;
	       super.onDestroy();
	}
    /**
     *
    * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    public String getServerMessage(){

    	if (DateUtils.ConverToString(new Date(),"HH").equals("19")||DateUtils.ConverToString(new Date(),"HH").equals("20")
    			||DateUtils.ConverToString(new Date(),"HH").equals("21")) {
    		//提前一天推送通知
    		for (int i = 0; i <list.size(); i++) {
				if (DateUtils.ConverToString(DateUtils.getNextDay(new Date()),"MM-dd").equals(list.get(i).toString())) {
					return "您明天有新的考试！";	
				}
			}
		} 
		return "";

    }
}