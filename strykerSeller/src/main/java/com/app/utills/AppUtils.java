package com.app.utills;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

	SharedPreferences sp;
	
	public static void setUserDetails(Context c, String userId, String userName, String imageUrl, String sessionId, Integer notificationCount) {

		SharedPreferences sp = c.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();

		e.putString("userid", userId);
		e.putString("name",userName);
		e.putString("image",imageUrl);
		e.putString("sessionid",sessionId);
		e.putInt("count", notificationCount);
		e.commit();
	}

	public static String getUserId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userdetails",
				Context.MODE_PRIVATE);
		return sp.getString("userid", "");

	}
	public static String getUserName(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userdetails",
				Context.MODE_PRIVATE);
		return sp.getString("name", "");

	}

	public static String getUserImage(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userdetails",
				Context.MODE_PRIVATE);
		return sp.getString("image", "");

	}

	public static String getSessionId(Context c) {

		SharedPreferences sp = c.getSharedPreferences("userdetails",
				Context.MODE_PRIVATE);
		return sp.getString("sessionid", "");

	}

	public static Integer getNotification(Context c ) {

		SharedPreferences sp = c.getSharedPreferences("userdetails",
				Context.MODE_PRIVATE);
		return sp.getInt("count", 0);

	}

	public static void setLoggedIn(Context c, boolean data) {
		SharedPreferences sp = c.getSharedPreferences("login",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sp.edit();
		e.putBoolean("login", data);
		e.commit();

	}

	public static boolean isLoggedIn(Context c) {

		SharedPreferences sp = c.getSharedPreferences("login",
				Context.MODE_PRIVATE);
		return sp.getBoolean("login", false);

	}


	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	public static boolean isEven(String number) {
		boolean isEven = false;
		try {
			int num = Integer.parseInt(number);
			if ((num % 2) == 0) {
				// even
				isEven = true;
			} else {// odd
				isEven = false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isEven;

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
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();

			Log.e("Network Info : ", "" +networkInfo);

			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

/*	public static boolean isNetworkAvailable(Context context) {


		ConnectivityManager cm =  (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return  cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}*/


	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
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

}
