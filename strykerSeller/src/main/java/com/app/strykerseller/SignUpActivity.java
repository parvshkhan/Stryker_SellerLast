package com.app.strykerseller;

import java.util.List;

import org.json.JSONObject;

import com.app.jsoncall.JsonCall;


import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.sample.chat.core.ChatService;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends Activity {

	Context context;
	ProgressDialog dialog;
	EditText editEmail, editPassword, editCnfpassword, editPhone;
	private Double mLat = 0.0, mLong = 0.0;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller_activity_signup);
		context = this;

		init();
		setListener();
	}

	public void init() {
		GPSTracker mGPS = new GPSTracker(this);
		if (mGPS.canGetLocation) {
			mLat = mGPS.getLatitude();
			mLong = mGPS.getLongitude();
		}
		Typeface tfbold = Typeface.createFromAsset(context.getAssets(),
				"Roboto-Bold.ttf");
		Typeface tfRegular = Typeface.createFromAsset(context.getAssets(),
				"Roboto-Regular.ttf");
		Typeface tfThin = Typeface.createFromAsset(context.getAssets(),
				"Roboto-Thin.ttf");
		editEmail = ((EditText) findViewById(R.id.edt_email));
		editPassword = ((EditText) findViewById(R.id.edt_pass));
		editCnfpassword = ((EditText) findViewById(R.id.edt_confirm_pass));
		editPhone = ((EditText) findViewById(R.id.edt_phone));

		((Button) findViewById(R.id.btn_signup)).setTypeface(tfRegular);

		editEmail.setTypeface(tfRegular);
		editPassword.setTypeface(tfRegular);
		editCnfpassword.setTypeface(tfRegular);
		editPhone.setTypeface(tfRegular);
	}

	public void setListener() {

		((ImageView) findViewById(R.id.img_back))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		
		((Button) findViewById(R.id.btn_signup))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AppUtil.onKeyBoardDown(context);
						if (editEmail.getText().toString().trim()
								.equalsIgnoreCase("")) {
							AppUtil.showCustomToast(context,
									"please enter your email id");
						} else if (!AppUtil.isEmailValid(editEmail.getText()
								.toString().trim())) {
							AppUtil.showCustomToast(context,
									"please enter valid email id");
						} else if (editPhone.getText().toString().trim()
								.equalsIgnoreCase("")) {
							AppUtil.showCustomToast(context,
									"please enter your phone number");
						}/*
						 * else if
						 * (AppUtil.isNumeric(editPhone.getText().toString())) {
						 * AppUtil.showCustomToast(context,
						 * "Please enter valid 10 digit number"); }
						 */else if (editPassword.getText().toString().trim()
								.equalsIgnoreCase("")) {
							AppUtil.showCustomToast(context,
									"please enter your password");
						} else if (editCnfpassword.getText().toString().trim()
								.equalsIgnoreCase("")) {
							AppUtil.showCustomToast(context,
									"please confirm your password");
						} else if (!editPassword
								.getText()
								.toString()
								.trim()
								.equalsIgnoreCase(
										editCnfpassword.getText().toString()
												.trim())) {
							AppUtil.showCustomToast(context,
									"Password and confirm password did not match");
						} else if (AppUtil.isNetworkAvailable(context)) {
							dialog = ProgressDialog.show(context, "",
									"please wait..");
							RegisterTask task = new RegisterTask();
							task.execute(new String[] { "1",
									editEmail.getText().toString(),
									editPassword.getText().toString(), "", "",
									"", editPhone.getText().toString(), "",
									mLat + "", mLong + "",
									AppUtil.getDeviceId(context) });
						} else {
							AppUtil.showCustomToast(context,
									"Please check your internet");
						}

					}
				});

	}

	/** signup asyntask */
	private class RegisterTask extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		// email, password, phone, login_type, socialId, latitude, longitude,
		// name, age, gender, location, deviceToken
		protected String doInBackground(String... urls) {
			String response = "";
			try {
				JsonCall obj = new JsonCall();
				response = obj.getLogin(
						urls[0],
						urls[1],
						urls[2],
						urls[3],
						urls[4],
						urls[5],
						urls[6],
						urls[7],
						urls[8],
						urls[9],
						urls[10],
						context.getResources().getString(
								R.string.registration_url));
				Log.e("registration RRESPONSE", "" + response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result) {
			JSONObject jObject;
			try {
				if (result != null) {
					jObject = new JSONObject(result);
					JSONObject job = jObject.getJSONObject("commandResult");
					int success = job.getInt("success");
					String message = job.getString("message");
					if (success == 1) {
						JSONObject job1 = job.getJSONObject("data");
						JSONObject data = job1.getJSONObject("user");
						final String userId = data.getString("Id");
						final String userName = data.getString("FullName");
						String userProfilePic = data.getString("ProfileImage");
						final String email = data.getString("Email");
						String phoneNo = data.getString("Phone");
						int isPhoneVerified = data.getInt("IsPhoneVerified");
						String VerificationCode = data
								.getString("VerificationCode");
						AppUtil.setUserId(context, userId);

		

						
						//Log.e("SellerId",chatuserid);
						AppUtil.setUserName(context, userName);
						AppUtil.setUserPic(context, userProfilePic);
						AppUtil.setLogin(context, false);
						AppUtil.setPhoneVerification(context, VerificationCode);
						
						
						Intent i = new Intent(SignUpActivity.this,
									SellerVerificationActivity.class);
						i.putExtra("email_id", email);
						i.putExtra("user_name",userName);
						startActivity(i);
						finish();

						

					} else {
						AppUtil.showCustomToast(context, message);
					}

				} else {
					AppUtil.showCustomToast(context,
							"please check your internet connection");
				}
				if (dialog != null)
					dialog.cancel();
			} catch (Exception e) {
				e.printStackTrace();
				if (dialog != null)
					dialog.cancel();

			}
		}
	}
	
}
