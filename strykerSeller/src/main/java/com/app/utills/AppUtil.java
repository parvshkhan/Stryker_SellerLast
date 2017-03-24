package com.app.utills;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AppUtil {
	
	
	public static void setChatUserLoginId(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("chatLoginid",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("chatLoginid", data);
		e.commit();
	}

	public static String getChatUserLoginId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("chatLoginid",
				Context.MODE_PRIVATE);
		return sp.getString("chatLoginid", "");

	}
	public static void setLogin(Context c, boolean data) {
		SharedPreferences sp = c.getSharedPreferences("islogin",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putBoolean("islogin", data);
		e.commit();

	}

	public static boolean getLogin(Context c) {

		SharedPreferences sp = c.getSharedPreferences("islogin",
				Context.MODE_PRIVATE);
		return sp.getBoolean("islogin", false);

	}
	/*public static void setUserChatID(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("chatid",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("chatid", data);
		e.commit();

	}*/
	
	public static void setDeviceId(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("deviceid",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("deviceid", data);
		e.commit();

	}

	public static String getDeviceId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("deviceid",
				Context.MODE_PRIVATE);
		return sp.getString("deviceid", "");

	}
	
	public static void setUserId(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("userid",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("userid", data);
		e.commit();
	}

	public static String getUserId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userid",
				Context.MODE_PRIVATE);
		return sp.getString("userid", "");

	}
	
	
	public static void setChatUserId(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("chatuser",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("chatuser", data);
		e.commit();
	}

	public static String getChatUserId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("chatuser",
				Context.MODE_PRIVATE);
		return sp.getString("chatuser", "");

	}
	
	
	
	
	public static void setUserName(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("username",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("username", data);
		e.commit();

	}

	public static String getUserName(Context c) {

		SharedPreferences sp = c.getSharedPreferences("username",
				Context.MODE_PRIVATE);
		return sp.getString("username", "");

	}

	public static void setPhoneVerification(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("isPhoneVerified",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("isPhoneVerified", data);
		e.commit();

	}
	public static String getPhoneVerification(Context c) {

		SharedPreferences sp = c.getSharedPreferences("isPhoneVerified",
				Context.MODE_PRIVATE);
		return sp.getString("isPhoneVerified", "");

	}

/*	public static Integer getIsVerified(Context c) {

		SharedPreferences sp = c.getSharedPreferences("isVerified",
				Context.MODE_PRIVATE);
		return sp.getInt("isVerified", 0);

	}

	public static void setIsVerified(Context c, int data) {
		SharedPreferences sp = c.getSharedPreferences("isVerified",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putInt("isVerified", data);
		e.commit();
	}
	*/
	public static void setUserLocation(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("userlocation",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("userlocation", data);
		e.commit();

	}

	public static String getUserLocation(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userlocation",
				Context.MODE_PRIVATE);
		return sp.getString("userlocation", "");

	}
	
	public static void setUserPic(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("userPic",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("userPic", data);
		e.commit();

	}

	public static String getUserPic(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userPic",
				Context.MODE_PRIVATE);
		return sp.getString("userPic", "");

	}
	
	public static void setIsGPSOn(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("gpson",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("gpson", data);
		e.commit();

	}

	public static String getIsGPSOn(Context c) {

		SharedPreferences sp = c.getSharedPreferences("gpson",
				Context.MODE_PRIVATE);
		return sp.getString("gpson", "");

	}
	
	public static void setIsChatDelete(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("chat",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("chat", data);
		e.commit();

	}

	public static String getIsChatDelete(Context c) {

		SharedPreferences sp = c.getSharedPreferences("chat",
				Context.MODE_PRIVATE);
		return sp.getString("chat", "");

	}

	public static Typeface getRegularTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"abeezeeregular.otf");
	}

	public static Typeface getLightTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(),
				"opensanshebrewlight.ttf");
	}

	public static String getDeviceGCMId(Context context) {
		String ud_id = "99999";
		try {
			SharedPreferences sub_share = context.getSharedPreferences("regid",
					0);

			if (!sub_share.equals(null)) {
				ud_id = sub_share.getString("rid", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ud_id;

		}

		return ud_id;

	}

	public static void showCustomToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static boolean isMyServiceRunning(Class<?> serviceClass,
			Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static void onKeyBoardDown(Context con) {
		try {
			InputMethodManager inputManager = (InputMethodManager) con
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(((Activity) con)
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			Log.e("Apputiles", "successful");
			return;
		}

		int totalHeight = 0;
		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}


	public static String getPath(Context context, Uri uri) throws URISyntaxException {
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		}
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}


	public static void setpreStoreId(Context c, String data) {
		SharedPreferences sp = c.getSharedPreferences("storeid",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putString("storeid", data);
		e.commit();
	}
	public static String getpreStoreId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("storeid",
				Context.MODE_PRIVATE);
		return sp.getString("storeid", "");
	}


	public static void setFirstLogin(Context c, boolean data) {
		SharedPreferences sp = c.getSharedPreferences("isfirstlogin",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putBoolean("isfirstlogin", data);
		e.commit();
	}

	public static boolean getFirstLogin(Context c) {

		SharedPreferences sp = c.getSharedPreferences("isfirstlogin",
				Context.MODE_PRIVATE);
		return sp.getBoolean("isfirstlogin", true);

	}


}
