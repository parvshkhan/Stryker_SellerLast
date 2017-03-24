package com.app.strykerseller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.LocalNotificationsAdapter;
import com.app.jsoncall.JsonCall;
import com.app.model.NotificationModel;
import com.app.utills.AppUtil;
import com.app.utills.AppUtils;
import com.app.utills.GPSTracker;


import java.util.ArrayList;



/**
 * Created by Inflectica on 10/13/2015.
 */
public class SellerNotificationActivity extends Activity {

    Context context;
    ProgressDialog dialog;
    private Double mLat = 0.0, mLong = 0.0;
    ArrayList<NotificationModel> arlistNotifications = new ArrayList<>();
    ListView listNotification;
    LocalNotificationsAdapter adapter;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_activity_notification);
        context = this;
        init();

        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                finish();
            }
        });

        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }

        if (AppUtils.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "please wait...");
            LocalNotifications task = new LocalNotifications();
            task.execute(new String[]{AppUtil.getUserId(context)});

        } else {
            AppUtils.showCustomToast(context, "Please check your in internet connection");
        }
    }

    public void init() {

        listNotification = (ListView) findViewById(R.id.lv_notifications);
    }


    private class LocalNotifications extends AsyncTask<String, Void, String> {

        
        protected void onPreExecute() {
            super.onPreExecute();
        }

        
        protected String doInBackground(String... urls) {
            String response = "";

            try {
                JsonCall obj = new JsonCall();
                response = obj.localNotifications(urls[0],getString(R.string.notifications_url));
                Log.e("Notifications", "" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        
        protected void onPostExecute(String result) {
            JSONObject jsonObject;

            try {
                if (result != null) {
                    jsonObject = new JSONObject(result);

                    JSONObject commandResult = jsonObject.getJSONObject("commandResult");
                    int status = commandResult.getInt("success");

                    if (status == 1) {

                        Toast.makeText(context, commandResult.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONArray jaData = commandResult.getJSONArray("data");
                        JSONObject joData;
                        for (int i = 0; i < jaData.length(); i++) {
                            joData = jaData.getJSONObject(i);
                            String headerDate = joData.getString("date");

                            JSONArray jaDetails = joData.getJSONArray("datedetail");
                            JSONObject joDetails;
                            for (int j = 0; j < jaDetails.length(); j++) {
                                joDetails = jaDetails.getJSONObject(j);

                                String notItemId = joDetails.getString("NotificationItemID");
                                String notMessage = joDetails.getString("NotificationMessage");
                                String notTypeId = joDetails.getString("NotificationTypeID");
                                String userName = joDetails.getString("FullName");
                                String userImage = joDetails.getString("PofileImage");
                                String totalPrice = joDetails.getString("TotalAmount");
                                String Quantity = joDetails.getString("Quantity");

                                if (j == 0) {
                                    arlistNotifications.add(new NotificationModel(headerDate, notItemId,
                                            notMessage, notTypeId, userName, userImage, totalPrice, Quantity, true));
                                } else {
                                    arlistNotifications.add(new NotificationModel(headerDate, notItemId,
                                            notMessage, notTypeId, userName, userImage, totalPrice, Quantity, false));
                                }
                            }
                        }

                        adapter = new LocalNotificationsAdapter(context, R.layout.seller_row_notifications, arlistNotifications);
                        listNotification.setAdapter(adapter);

                    } else {
                        Toast.makeText(context, commandResult.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
                if (dialog != null) {
                    dialog.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        }
    }

}
