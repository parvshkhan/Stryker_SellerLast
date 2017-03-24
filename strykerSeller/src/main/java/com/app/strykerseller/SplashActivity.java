package com.app.strykerseller;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.app.fcm.FireBaseGetRegNoService;
import com.app.service.OnClearFromRecentService;
import com.app.utills.AppUtil;
import com.app.utills.MyExceptionHandler;

import static com.app.fragment.StoresHomeFragment.MY_PREFS_NAME;


public class SplashActivity extends Activity {
	Context context;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;



        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        //String restoredText = prefs.getString("text", null);
       // if (restoredText != null) {
            String name = prefs.getString("name", "");//"No name defined" is the default value.
      //  }
        // Handle Error //
        String exception = getIntent().getStringExtra("uncaughtException");
          if(exception!=null)
          Toast.makeText(this,"Please try again", Toast.LENGTH_SHORT).show();



        new Handler().postDelayed(new Runnable() {
            
            public void run() {
            	if(AppUtil.getLogin(context)){
            	AppUtil.setFirstLogin(context, false);
                Intent in = new Intent(SplashActivity.this,SellerHomeActivity.class);
                startActivity(in);

                finish();

                }else{
                	Intent i = new Intent(SplashActivity.this,LoginActivity.class);
					startActivity(i);
					finish();
                }

            }

        }, 3000);




        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));

       Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(getApplicationContext(), com.quickblox.sample.chat.ui.activities.SplashActivity.class));

    }


}
