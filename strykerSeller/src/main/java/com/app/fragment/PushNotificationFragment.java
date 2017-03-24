package com.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.NotificationsAdapter;
import com.app.jsoncall.JsonCall;
import com.app.model.NotificationModel;
import com.app.strykerseller.ProductListActivity;
import com.app.strykerseller.R;
import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;

import java.util.ArrayList;


/**
 * Created by Inflectica on 10/23/2015.
 */
public class PushNotificationFragment extends android.support.v4.app.Fragment {

    Context context;
    private Double mLat = 0.0, mLong = 0.0;
    ProgressDialog dialog;
    String storeId = "";
    ListView notificationList;
    NotificationsAdapter adapter;
    ArrayList<NotificationModel> arlistNotifications = new ArrayList<NotificationModel>();
    Button btnSend;
    EditText edt_desc;
    String promoMessage = "";

    @Nullable
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_push_notification, container, false);
        return rootview;
    }

    
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }

        init();
        setListener();

        ProductListActivity proActivity = (ProductListActivity) getActivity();
        storeId = proActivity.StoreId();

        Log.e("StoreId",storeId);

        if (AppUtil.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "Please wait...");
            NotificationTask task = new NotificationTask();
            task.execute(new String[]{AppUtil.getUserId(context), storeId});

        } else {
            AppUtil.showCustomToast(context,
                    "please check your internet connection");
        }
    }

    public void init()
    {
        notificationList = (ListView)getView().findViewById(R.id.lv_notifications);
        btnSend = (Button)getView().findViewById(R.id.btn_send);
        edt_desc = (EditText)getView().findViewById(R.id.edt_desc);

    }

    public void setListener()
    {
        btnSend.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
            	AppUtil.onKeyBoardDown(context);
                if (AppUtil.isNetworkAvailable(context)) {
                    dialog = ProgressDialog.show(context, "", "Please wait...");
                    SendNotificationTask task = new SendNotificationTask();
                    task.execute(new String[]{storeId, edt_desc.getText().toString()});

                } else {
                    AppUtil.showCustomToast(context,
                            "please check your internet connection");
                }
            }

        });

        edt_desc.setText("");

    }

    private class NotificationTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.showNotifications(urls[0], urls[1],
                        context.getResources().getString(R.string.notification_list_url));
                Log.e("Notification RRESPONSE", "" + response);
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

                        arlistNotifications.clear();

                        JSONObject job1 = job.getJSONObject("data");
                        JSONArray ja_notification = job1.getJSONArray("notification");

                        for (int i = 0; i < ja_notification.length(); i++) {

                            JSONObject jobj = ja_notification.getJSONObject(i);

                            String notDesc = jobj.getString("NotificationMessage");
                            String notId = jobj.getString("NotificationItemID");
                            String notDate = jobj.getString("DateCreated");

                            arlistNotifications.add(new NotificationModel
                                    (notId, notDate, notDesc));
                        }

                        adapter = new NotificationsAdapter(context,
                                R.layout.seller_row_push_notification, arlistNotifications);
                        notificationList.setAdapter(adapter);

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


    private class SendNotificationTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.sendNotifications(urls[0], urls[1],
                        context.getResources().getString(R.string.send_notification_url));
                Log.e("Notification RRESPONSE", "" + response);
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
                    	 edt_desc.setText("");
                        JSONObject job1 = job.getJSONObject("data");                 
                        
                        
                        JSONArray ja_notification = job1.getJSONArray("notification");
                        arlistNotifications.clear();
                        for (int i = 0; i < ja_notification.length(); i++) {

                            JSONObject jobj = ja_notification.getJSONObject(i);

                            String notDesc = jobj.getString("NotificationMessage");
                            String notId = jobj.getString("NotificationItemID");
                            String notDate = jobj.getString("DateCreated");

                            arlistNotifications.add(new NotificationModel
                                    (notId, notDate, notDesc));
                        }
                        //JSONObject ja_notification = job1.getJSONObject("notification");
                        //String notMessage = ja_notification.getString("NotificationMessage");
                        //String notId = ja_notification.getString("ID");
                       // String notDate = ja_notification.getString("DateCreated");

                         //   arlistNotifications.add(new NotificationModel
                          //          (notId, notDate, notMessage));

                        //adapter = new NotificationsAdapter(context,R.layout.seller_row_push_notification, arlistNotifications);
                        adapter.notifyDataSetChanged();

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
