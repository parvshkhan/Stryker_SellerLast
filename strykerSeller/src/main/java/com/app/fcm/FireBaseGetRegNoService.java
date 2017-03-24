package com.app.fcm;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.app.fragment.StoresHomeFragment.MY_PREFS_NAME;

public class FireBaseGetRegNoService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";

    @Override
    public void onCreate() {
        try{
            super.onCreate();
            //Get hold of the registration token
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            //Log the token
            Log.e(TAG, "Reg token: " + refreshedToken);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onTokenRefresh() {
        try{
            //Get hold of the registration token
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            //Log the token
            Log.e(TAG, "Refreshed token: " + refreshedToken);
            Toast.makeText(FireBaseGetRegNoService.this,refreshedToken, Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("name", refreshedToken);
            //editor.putInt("idName", 12);
            editor.commit();
            // new LoginPrefrencesKeys(this).setString(LoginPrefrencesKeys.REG_NO, refreshedToken);
            // SharedPrefrenceUtils.setRegistrationId(this,refreshedToken);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void sendRegistrationToServer(String token) {
        //Implement this method if you want to store the token on your server

    }
}