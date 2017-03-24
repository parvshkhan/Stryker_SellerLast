package com.app.strykerseller;

import com.app.utills.AppUtil;
import com.app.utills.AppUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;



/**
 * Created by Inflectica on 11/6/2015.
 */
public class SettingActivity extends Activity {

    ProgressDialog dialog;
    Context context;
    RelativeLayout relativeLayout_EditProfile;
    RelativeLayout relativeLayout_ChangePassw;



    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        context = this;

        ((ImageView)findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {

                finish();
            }
        });

        ((RelativeLayout)findViewById(R.id.rl_logout)).setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
     
                if (AppUtils.isNetworkAvailable(context)) {
                    AppUtil.setLogin(context, false);
                    AppUtil.setUserId(context, "");
                    AppUtil.setChatUserId(context, "");
					AppUtil.setChatUserLoginId(context, "");
                    Intent in = new Intent(SettingActivity.this,LoginActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();

                } else {
                    AppUtils.showCustomToast(context, "Please check your internet connection");
                }
            }
        });


      // relativeLayout_EditProfile = (RelativeLayout)findViewById(R.id.layout_profile);

       /* relativeLayout_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), EditProfile_Activity.class);
                startActivity(intent);

            }
        });*/

      /*  relativeLayout_ChangePassw = (RelativeLayout)findViewById(R.id.rl_change_passw);
        relativeLayout_ChangePassw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });
*/
    }
}
