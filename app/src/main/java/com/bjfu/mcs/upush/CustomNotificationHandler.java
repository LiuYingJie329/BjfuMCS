package com.bjfu.mcs.upush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Map;

//使用自定义的NotificationHandler，来结合友盟统计处理消息通知
public class CustomNotificationHandler extends UmengNotificationClickHandler {
	
	private static final String TAG = CustomNotificationHandler.class.getName();
	
	@Override
	public void dismissNotification(Context context, UMessage msg) {
		Log.d(TAG, "dismissNotification");
		super.dismissNotification(context, msg);
	}
	
	@Override
	public void launchApp(Context context, UMessage msg) {
		Log.d(TAG, "launchApp");
		super.launchApp(context, msg);
	}
	
	@Override
	public void openActivity(Context context, UMessage msg) {
		Log.d(TAG, "openActivity");
		Log.i("友盟自定义行为","打开Activity");


		//D/MobclickAgent: constructMessage:{"sessions":[{"id":"E7B09837584CE93D8E4E1F00ABFAD73E","start_time":"1528282863932","end_time":"1528282866151","duration":2219,"pages":[{"page_name":"com.bjfu.mcs.upush.UpushActivity","duration":2222}]}],"sdk_version":"7.5.0","device_id":"865372020318579","device_model":"MI 4LTE","version":1,"appkey":"5b0faa348f4a9d2fa2000036","channel":"Umeng"}
		//I/System.out: true
		//I/MobclickAgent: Start new session: 327E2E0BA211C37674A308971D25045A
		//I/UMLog_com.umeng.message.inapp.e: get card message success{"success":"ok","data":{"t":1528282864302,"check_num":"","sduration":1,"pduration":1000,"card":{"msg_info":{"plain_text":{"activity":"com.bjfu.mcs.activity.MainActivity","action_type":"go_activity","button_text":"确定","title":"qweqweqwe","content":"qeqweqweqweqw"},"display_button":false},"msg_id":"uat0a0c152825044859700","msg_type":5,"policy":{"show_type":0,"show_times":0,"start_time":"2018-06-06 01:24:46","expire_time":"2018-06-07 01:24:46"}}}}
		for (Map.Entry entry : msg.extra.entrySet()) {

			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			Log.i("接收参数","value--->"+value+"--->key--->"+key);

		}

		super.openActivity(context, msg);
	}
	
	@Override
	public void openUrl(Context context, UMessage msg) {
		Log.d(TAG, "openUrl");
		super.openUrl(context, msg);
	}
	
	@Override
	public void dealWithCustomAction(Context context, UMessage msg) {
		for (Map.Entry entry : msg.extra.entrySet()) {

			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			Log.i("接收参数dealWithCustomAction","value--->"+value+"--->key--->"+key);

		}
		Log.d(TAG, "dealWithCustomAction");
		super.dealWithCustomAction(context, msg);
	}
	
	@Override
	public void autoUpdate(Context context, UMessage msg) {
		Log.d(TAG, "autoUpdate");
		super.autoUpdate(context, msg);
	}

}
