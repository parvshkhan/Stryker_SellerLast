package com.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;


public class SellerApplication extends Application {
	private static Context context;
	private static boolean activityVisible = false;

	@Override
	public void onCreate() {
		try{
		// TODO Auto-generated method stub
		super.onCreate();
	SellerApplication.context=getApplicationContext();
		}catch (Exception e){
			e.printStackTrace();
		}
	
	}
	public static Context getAppContext() {
        return SellerApplication.context;
    }
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);}

	public static boolean isActivityVisible() {
		return activityVisible;
	}

	public static void activityResumed() {
		activityVisible = true;
		Toast.makeText(context, "Resumed Call", Toast.LENGTH_SHORT).show();
	}

	public static void activityPaused() {
		activityVisible = false;
	}

}
