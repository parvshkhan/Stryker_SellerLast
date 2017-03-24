package com.quickblox.sample.chat;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.quickblox.core.QBSettings;

//import vc908.stickerfactory.StickersManager;

public class SellerApplicationSingleton_Seller extends Application {
    private static final String TAG = SellerApplicationSingleton_Seller.class.getSimpleName();

    public static final String APP_ID = "29540";
    public static final String AUTH_KEY = "euCZqPezkQ2mcvY";
    public static final String AUTH_SECRET = "y2vr6gjvBzWESSa";
    public static final String STICKER_API_KEY = "72921666b5ff8651f374747bfefaf7b2";

   // public static final String USER_LOGIN = "vishal";
  //  public static final String USER_PASSWORD = "12345678";

    private static SellerApplicationSingleton_Seller instance;
    public static SellerApplicationSingleton_Seller getInstance() {
        return instance;
    }

    
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        instance = this;
        
        // Initialise QuickBlox SDK
        //
        QBSettings.getInstance().fastConfigInit(APP_ID, AUTH_KEY, AUTH_SECRET);
        //StickersManager.initialize(STICKER_API_KEY, this);
    }

    public int getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}
