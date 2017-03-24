package com.app.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.strykerseller.R;
import com.app.strykerseller.SellerHomeActivity;
import com.application.SellerApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qb.gson.Gson;

import org.json.JSONObject;

import java.util.Map;


public class FireBaseNotificationService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";
    public static final int NOTIFICATION_ID = 1111; // any integer constant
    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try{
            //Log data to Log Cat
            Log.d(TAG, "From: " + remoteMessage.getFrom());

            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("Notification", object.toString());

            //create notification
            sendNotification(object);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendNotification(JSONObject messagebody) {
        try{
            String json = messagebody.toString();
            NotificationModel model = new Gson().fromJson(json, NotificationModel.class);
            //	NotificationModel model = getNotificationsModel(notificationBundle);
            if (model.getIsVStatus().equalsIgnoreCase("Y")) {
                mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                boolean isAppVisible = SellerApplication.isActivityVisible();
                boolean isNotificationSound = true;//SharedPrefrenceUtils.isNotificationSoundOn(this);

                //TODO: update count if app is open.
                if (isAppVisible) {
                    if (isNotificationSound) {
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        } catch (Exception e) {
                        }
                    }
                    // ExpenseFragment.loadExpenseList();

                } else {
                    try{
                        Intent intent = new Intent(this, SellerHomeActivity.class);
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);

                        String notificationText = model.getHeading();

                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(getNotificationIcon())
                                .setContentTitle("QCI App")
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(model.getMessage()))
                                .setContentText(notificationText)
                                .setWhen(System.currentTimeMillis());
                        //.setNumber(notificationTable.getUnViewdMessageNotificationsCount());

                        // Play default notification sound and vibration
                        if (isNotificationSound) {
                            mBuilder.setDefaults(Notification.DEFAULT_ALL);
                        } else {
                            mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBuilder.setColor(getResources().getColor(R.color.app_background_color));
                        }
                        mBuilder.setContentIntent(contentIntent);
                        mBuilder.setAutoCancel(true);
                        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int getNotificationIcon() {
        try{
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ?
                    R.drawable.stryker_icon : R.drawable.stryker_icon;
        }catch (Exception e){
            e.printStackTrace();
            return  0;
        }
    }
}

