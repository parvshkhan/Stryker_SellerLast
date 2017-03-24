package com.quickblox.sample.chat.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.utills.MyExceptionHandler;
import com.app.strykerseller.R;

public class SplashActivity extends Activity {

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(SplashActivity.this, DialogsActivity.class);
        startActivity(intent);
        finish();


     /*   final QBUser user = new QBUser();
        user.setLogin(AppUtil.getUserId(c));
        user.setPassword("123456");

        ChatService.initIfNeed(this);

        ChatService.getInstance().login(user, new QBEntityCallbackImpl() {

            
            public void onSuccess() {
                // Go to Dialogs screen
                //
                Intent intent = new Intent(SplashActivity.this, DialogsActivity.class);
                startActivity(intent);
                finish();
            }

            
            public void onError(List errors) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
                dialog.setMessage("chat login errors: " + errors).create().show();
            }
        });*/
    }
}