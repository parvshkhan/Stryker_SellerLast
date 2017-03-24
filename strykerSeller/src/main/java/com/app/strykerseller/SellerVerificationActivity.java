package com.app.strykerseller;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import com.app.jsoncall.JsonCall;
import com.app.listener.SmsReceivedListner;
import com.app.receiver.IncomingSms;
import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.sample.chat.core.ChatService;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

/**
 * Created by Inflectica on 10/13/2015.
 */
public class SellerVerificationActivity extends Activity implements SmsReceivedListner{

    Context context;
    
    static EditText edtNumber;
    Button btnSubmit;
    ProgressDialog dialog;
    RelativeLayout rl_resend;
    ImageView imgBack;
    private Double mLat = 0.0, mLong = 0.0;
    
    String email = "", userName = "";
    CountDownTimer ctimer;
    IncomingSms receiveSms;
    
    String chatuserid  = "";
    
    boolean isRegOnChat = false;
    
    String messageCode;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.seller_activity_verify_number);
        context = this;
        init();
        setListener();
        startCounter();
        
        
    }

    public void init() {
    	
    	Bundle b = getIntent().getExtras();
    	if(b != null){
    		if(b.containsKey("email_id")){
    			email = b.getString("email_id");    			
    		}
    		if(b.containsKey("user_name")){
    			userName = b.getString("user_name");    			
    		}    		
    	}

        GPSTracker mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }

        edtNumber = (EditText)findViewById(R.id.edt_verify_code);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        rl_resend = (RelativeLayout)findViewById(R.id.rl_resend);
        imgBack = (ImageView)findViewById(R.id.img_back);
        
       
		try {
			chatuserid = email.substring(0,email.indexOf("@" ));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// CREATE CHAT USER
        try {
            ChatService.initIfNeed(SellerVerificationActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        QBAuth.createSession(new QBEntityCallbackImpl<QBSession>() {
			
			public void onSuccess(QBSession session,
					Bundle params) {
				// success

				final QBUser user1 = new QBUser(chatuserid,
						"12345678");
				// user1.setExternalId("453415");
				// user1.setFacebookId("100233453457767");
				// user1.setTwitterId("182334635457");
				user1.setEmail(email);
				user1.setFullName(userName);
				// user1.setPhone("+189045678121");
				StringifyArrayList<String> tags = new StringifyArrayList<String>();

				// user1.setWebsite("www.mysite.com");

				QBUsers.signUp(user1,
						new QBEntityCallbackImpl<QBUser>() {
							
							public void onSuccess(QBUser user,
									Bundle args) {
								Log.e("response------------->",
										" user created");
								AppUtil.setChatUserLoginId(context, chatuserid);
								AppUtil.setChatUserId(context, user.getId().toString());
								  SavechatidTask task = new SavechatidTask();						 	   	    
						 	   	  task.execute(new String[] {AppUtil.getUserId(context), user.getId().toString(),user.getLogin().toString() });
	
							}

							
							public void onError(
									List<String> errors) {
								Log.e("response------------>",
										" user creation failed");
							}
						});

			}

			
			public void onError(List<String> errors) {
				// errors
			}
		});

		// END CHAT USER
		
		
		 receiveSms = new IncomingSms();
	     receiveSms.setOnSmsReceivedListener(this);
	     registerReceiver(receiveSms, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
	
        
    }
    
    
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    }
    
    
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	try {
			unregisterReceiver(receiveSms);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void setListener()
    {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
            	AppUtil.onKeyBoardDown(context);
                if (AppUtil.isNetworkAvailable(context)) {
                    dialog = ProgressDialog.show(context, "",
                            "please wait..");
                    VerifyCodeTask task = new VerifyCodeTask();
                    task.execute(new String[] {AppUtil.getUserId(context), edtNumber.getText().toString()});
                } else {
                    AppUtil.showCustomToast(context,
                            "Please check your internet");
                }

            }
        });


        rl_resend.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {

                if (AppUtil.isNetworkAvailable(context)) {
                	if(ctimer != null)
                		ctimer.cancel();
                		startCounter();
                    dialog = ProgressDialog.show(context, "",
                            "please wait..");
                    ResendCodeTask task = new ResendCodeTask();
                    task.execute(new String[] {AppUtil.getUserId(context)});
                } else {
                    AppUtil.showCustomToast(context,
                            "Please check your internet");
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class VerifyCodeTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.verifyCode(urls[0], urls[1], context.getResources().getString(R.string.verify_mobile_url));
                Log.e("VerifyCode RRESPONSE", "" + response);
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
                        //Boolean status = job.getBoolean("data");
                    	AppUtil.setLogin(context, true);
                        Intent in = new Intent(SellerVerificationActivity.this,SellerHomeActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(in);
                        finish();
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

    private class ResendCodeTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.resendCode(urls[0], context.getResources().getString(R.string.resend_code_url));
                Log.e("ResendCode RRESPONSE", "" + response);
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
                       // Boolean status = job.getBoolean("data");
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
    private class SavechatidTask extends AsyncTask<String, Void, String> {
		
		 
	       protected void onPreExecute() {
	           super.onPreExecute();
	       }
	       protected String doInBackground(String... urls) {
	           String response = "";
	           try {
	               JsonCall obj = new JsonCall();
	               response = obj.updatechat(urls[0], urls[1], urls[2],context.getResources().getString(R.string.update_chat));
	               Log.e("GetDialog", "" + response);
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
	                   	 
	                	   isRegOnChat = true;	 
	                	   
	                	   if (messageCode != null) {
							edtNumber.setText(messageCode);
							ctimer.cancel();
							if (message != null) {
								btnSubmit.performClick();
							}
							/*   Intent i = new Intent(SignUpActivity.this,
										SellerVerificationActivity.class);
								startActivity(i);
								finish();*/
						}
	                   	
	                   }
	                  

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
  /*  public void recivedSms(String message, Context context) 
    {
   try 
     {	 
	   edtNumber.setText(message);	    
	   ctimer.cancel();

     } 
     catch (Exception e) 
         {         
    	 e.printStackTrace();
               }
   }*/
    
    
	 public void startCounter(){
		/*  ((ProgressBar) findViewById(R.id.pb_counter)).getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"),
	                 android.graphics.PorterDuff.Mode.MULTIPLY);*/
		 ((ProgressBar) findViewById(R.id.pb_counter)).setVisibility(View.VISIBLE);
		ctimer =  new CountDownTimer(30000, 1000) {

		     public void onTick(long millisUntilFinished) {
		         ((TextView) findViewById(R.id.txt_counter))
		         .setText("" + millisUntilFinished / 1000);
		       
		     }

		     public void onFinish() {
		    	 ((ProgressBar) findViewById(R.id.pb_counter)).setVisibility(View.GONE);		    	
		    	 ((TextView) findViewById(R.id.txt_counter)).
		    	 setText("We are unable to fetch code,please type below manually.");
		     }
		  }.start();
		 
		 
	
		}

	
	public void onSmsReceived(String message) {
		// TODO Auto-generated method stub
		
		 
			 messageCode = message;
			 if(isRegOnChat){
			edtNumber.setText(messageCode);
			ctimer.cancel();
			if (messageCode != null) {
				btnSubmit.performClick();
			}
			 }
		
	}
    
	 
/*	 public class IncomingSms extends BroadcastReceiver{	
			
			
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				final Bundle bundle = intent.getExtras();
				
				 try {
					  if (bundle != null) 
					  {
					   final Object[] pdusObj = (Object[]) bundle.get("pdus");
					   for (int i = 0; i < pdusObj .length; i++) 
					   {
					    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])                                                                                                    pdusObj[i]);
					    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					    String senderNum = phoneNumber ;
					    String message = currentMessage .getDisplayMessageBody();
					    try
					    { 
					     if (senderNum .equals("MD-STRYKR") || senderNum.equals("BW-STRYKR")) 
					     {
					    	 message = message.replaceAll("\\D+","");
					    	 SellerVerificationActivity Sms = new SellerVerificationActivity();
					         Sms.recivedSms(message,context );
					    	 edtNumber.setText(message);
					    	 btnSubmit.performClick();
					     }
					  }
					  catch(Exception e){
						  e.printStackTrace();
					  }
					  
					  }
					   }

					   } catch (Exception e) 
					  {
					      e.printStackTrace();          
					 }
			}
	 }*/
}
