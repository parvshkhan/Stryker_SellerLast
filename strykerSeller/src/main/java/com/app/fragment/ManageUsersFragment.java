package com.app.fragment;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.ManageUserListAdapter;
import com.app.jsoncall.JsonCall;
import com.app.model.ChatUserModel;
import com.app.strykerseller.R;
import com.app.strykerseller.SellerChatActivity;
import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;



public class ManageUsersFragment extends android.support.v4.app.Fragment {

    Context context;

    ManageUserListAdapter adapter;
    ListView listUsers;
    ArrayList<ChatUserModel> arlistUsers = new ArrayList<ChatUserModel>();
    private Double mLat = 0.0, mLong = 0.0;
    ProgressDialog dialog;
    ImageView imgAddStore;
    String storeId = "";
    
    public interface onSomeEventListener {
    public void someEvent(String s);
   }

   onSomeEventListener someEventListener;

   
   public void onAttach(Activity activity) {
     super.onAttach(activity);
         try {
           someEventListener = (onSomeEventListener) activity;
         } catch (ClassCastException e) {
             throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
         }
   }

    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.fragment_chat_manage_users,
                container, false);

        return rootview;
    }
    
    
    

    
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        SellerChatActivity chatActivity = (SellerChatActivity)getActivity();
        storeId = chatActivity.StoreId();

        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }

        init();

        if (AppUtil.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "Please wait...");
            ManageChatUserTask task = new ManageChatUserTask();
            Log.e("StoreId in Fragment",storeId);
            task.execute(new String[] {AppUtil.getUserId(context), storeId});
        } else {
            AppUtil.showCustomToast(context,
                    "please check your internet connection");
        }
    }

    public void init()
    {
        listUsers = (ListView)getView().findViewById(R.id.list_manege_users);

        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**perform the action on click of user list*/
            }
        });

    }

    private class ManageChatUserTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.manageUsers(urls[0], urls[1], context.getResources().getString(R.string.manage_user_url));
                Log.e("User List", "" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            JSONObject jObject;
            try {
                if (result != null) {

                    arlistUsers.clear();
                    jObject = new JSONObject(result);

                    JSONObject job = jObject.getJSONObject("commandResult");
                    int success = job.getInt("success");
                    String message = job.getString("message");

                    if (success == 1) {
                        String imageUrl = "";
                        JSONArray ja = job.getJSONArray("data");

                        for (int i = 0; i < ja.length(); i++)
                        {
                            JSONObject jo = ja.getJSONObject(i);
                            String buyerId = jo.getString("ID");
                            String buyerChatId = jo.getString("ChatID");
                            String userName = jo.getString("Username");
                                    imageUrl = jo.getString("PofileImage");

                            arlistUsers.add(new ChatUserModel(buyerId,imageUrl,userName,buyerChatId));
                        }
                        
                        adapter = new ManageUserListAdapter(context, R.layout.seller_row_manage_user, arlistUsers,storeId,ManageUsersFragment.this);
                        
                       /* adapter = new ManageUserListAdapter(context,
                                R.layout.seller_row_manage_user, arlistUsers,ManageUsersFragment.this,storeId);*/
                        listUsers.setAdapter(adapter);
                        SellerChatActivity.userCount = arlistUsers.size();
                        someEventListener.someEvent(arlistUsers.size()+"");
                    } else {
                        AppUtil.showCustomToast(context, message);
                    }

                } else {
                    AppUtil.showCustomToast(context,
                            "Please check your internet connection");
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


