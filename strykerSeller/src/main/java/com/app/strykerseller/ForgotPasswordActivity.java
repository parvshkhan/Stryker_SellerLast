package com.app.strykerseller;

import org.json.JSONObject;

import com.app.jsoncall.JsonCall;
import com.app.utills.AppUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgotPasswordActivity extends Activity {

	Context context;
	ProgressDialog dialog;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyer_activity_forgot_password);
		context = this;
		init();
		setListener();
	}

	public void init() {
		Typeface tfbold = Typeface.createFromAsset(context.getAssets(),
				"Roboto-Bold.ttf");
		Typeface tfRegular = Typeface.createFromAsset(context.getAssets(),
				"Roboto-Regular.ttf");
		Typeface tfThin = Typeface.createFromAsset(context.getAssets(),
				"Roboto-Thin.ttf");
		((TextView) findViewById(R.id.forgotpwd_forgotpassword_text))
				.setTypeface(tfRegular);
		((TextView) findViewById(R.id.txt_center)).setTypeface(tfRegular);
		((Button) findViewById(R.id.forgotpwd_submit_bton))
				.setTypeface(tfRegular);
		((EditText) findViewById(R.id.forgotpwd_emailaddress_text))
				.setTypeface(tfRegular);

	}

	public void setListener() {

		((ImageView) findViewById(R.id.backimage_in_forgotpwd))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AppUtil.onKeyBoardDown(context);
						finish();
					}
				});

		((TextView) findViewById(R.id.txt_skip))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AppUtil.onKeyBoardDown(context);
						finish();

					}
				});

		((Button) findViewById(R.id.forgotpwd_submit_bton))
				.setOnClickListener(new OnClickListener() {

					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (((EditText) findViewById(R.id.forgotpwd_emailaddress_text))
								.getText().toString().trim()
								.equalsIgnoreCase("")) {
							AppUtil.showCustomToast(context,
									"please enter your email id");
						} else if (!AppUtil
								.isEmailValid(((EditText) findViewById(R.id.forgotpwd_emailaddress_text))
										.getText().toString().trim())) {
							AppUtil.showCustomToast(context,
									"please enter valid email id");
						} else if (AppUtil.isNetworkAvailable(context)) {
							dialog = ProgressDialog.show(context, "",
									"please wait..");
							ForgotPasswordTask task = new ForgotPasswordTask();
							task.execute(new String[] { ((EditText) findViewById(R.id.forgotpwd_emailaddress_text))
									.getText().toString() });
						} else {
							AppUtil.showCustomToast(context,
									"Please check your internet");
						}
					}
				});
	}

	/** ForgotPassword asyntask */
	private class ForgotPasswordTask extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... urls) {
			String response = "";
			try {
				JsonCall obj = new JsonCall();
				response = obj.forgotPassword(urls[0], context.getResources()
						.getString(R.string.forgot_password_url));
				Log.e("Forgot Password RES", "" + response);
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
						AppUtil.showCustomToast(context, message);
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
