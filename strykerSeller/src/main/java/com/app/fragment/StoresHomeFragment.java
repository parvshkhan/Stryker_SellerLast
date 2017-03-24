package com.app.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.Database.SQLiteHelper;
import com.app.adapter.StoreHomeListAdapter;
import com.app.jsoncall.JsonCall;
import com.app.listener.DeleteStoreListener;
import com.app.model.StoreHomeListModel;
import com.app.strykerseller.AddStoreActivity;
import com.app.strykerseller.InviteShare;
import com.app.strykerseller.R;
import com.app.utills.AppUtil;
import com.app.utills.AppUtils;
import com.app.utills.GPSTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class StoresHomeFragment extends android.support.v4.app.Fragment implements DeleteStoreListener{

    Context context;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    StoreHomeListAdapter adapter;
    ListView listStore;
    ArrayList<StoreHomeListModel> arlistStoreHome = new ArrayList<StoreHomeListModel>();
    //ArrayList<ModelListHomeItem> listfollowing = new ArrayList<ModelListHomeItem>();
    private Double mLat = 0.0, mLong = 0.0;
    ProgressDialog dialog;
    SwipeRefreshLayout swipeLayout;
    TextView txt_share, textView_Promote;
    Dialog uploadOptionDialog;
    String strStore_Id = "";
    EditText editTextMobileNo;
    final int PICK_CONTACT = 1;

    LinearLayout layout_shareApp;
    LinearLayout layout_PhoneContacts;

    ImageView imgAddStore;
    ArrayList<String> storeList = new ArrayList<String>();
    //String storeId = "";
    String finalStrStore_Id;
    String storeName1;


    public interface onUserCountListener {
        public void userCount(String s);
        public void notifCount(String s);
    }

    onUserCountListener someEventListener;


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onUserCountListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.fragment_pager_store_home,
                container, false);

        return rootview;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }

        init();
        //setData();

        if (arlistStoreHome!=null)
            arlistStoreHome.clear();

        SQLiteHelper sqLiteHelper =  SQLiteHelper.getInstance(getActivity());
        arlistStoreHome = sqLiteHelper.getStoreHome();
        sqLiteHelper.close();

        if (AppUtil.isNetworkAvailable(context)) {

            if(arlistStoreHome.isEmpty()) {

                dialog = ProgressDialog.show(context, "", "Please wait...");
                HomeDataTask task = new HomeDataTask();
                task.execute(new String[]{AppUtil.getUserId(context)});
                Log.e("SellerId in Service", AppUtil.getUserId(context));
            }
            else
            {

                adapter = new StoreHomeListAdapter(context,
                        R.layout.seller_row_store_list, arlistStoreHome, StoresHomeFragment.this);
                strStore_Id= arlistStoreHome.get(0).getSrore_id();
                storeName1 = arlistStoreHome.get(0).getStore_name();
                listStore.setAdapter(adapter);

            }

        } else {
            AppUtil.showCustomToast(context,
                    "please check your internet connection");

        }

        if(arlistStoreHome.size()>0)
        {
            listStore.setVisibility(View.VISIBLE);
            imgAddStore.setVisibility(View.GONE);
            layout_shareApp.setVisibility(View.VISIBLE);
            layout_PhoneContacts.setVisibility(View.VISIBLE);
            ((RelativeLayout) getView().findViewById(R.id.rl_message)).setVisibility(View.GONE);

        }else {

            listStore.setVisibility(View.GONE);
            imgAddStore.setVisibility(View.VISIBLE);
            layout_PhoneContacts.setVisibility(View.GONE);
            layout_shareApp.setVisibility(View.GONE);
            String sourceString = "Welcome to Market Business ! <br><br><br> Have you always dreamed of <b> your own mobile app? </b> Do you want to stay ahead of the times and <b> increase your business </b> through new technology? <br><br> Now it's possible! Simply tap on the 'Add' button below to create your online store now and <b> multiply your profits! </b> ";
            ((TextView) getView().findViewById(R.id.txt_nodata)).setText(Html.fromHtml(sourceString));

            ((RelativeLayout) getView().findViewById(R.id.rl_message)).setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        //pb=(com.github.rahatarmanahmed.cpv.CircularProgressView) getView().findViewById(R.id.progressBar1);
        listStore = (ListView) getView().findViewById(R.id.list_home_store);
        //swipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        imgAddStore = (ImageView) getView().findViewById(R.id.img_add2);
        textView_Promote = (TextView)getView().findViewById(R.id.txt_promote);
        txt_share = (TextView) getView().findViewById(R.id.txt_share);
        layout_shareApp = (LinearLayout)getView().findViewById(R.id.layout_shareApp);
        layout_PhoneContacts = (LinearLayout)getView().findViewById(R.id.layout_PhoneContacts);



        listStore.setOnItemClickListener(new OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                /**perform the action on click of store list*/

            }
        });


        txt_share.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker&referrer="+strStore_Id;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Get the Market App on Google play store\n" + appUrl);
                startActivity(Intent.createChooser(sharingIntent,"Share using"));
            }
        });

        textView_Promote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // LayoutInflater inflater = getLayoutInflater();
                // View myView = LayoutInflater.from(con).inflate(R.layout.my_view, parent, false);

                View v = LayoutInflater.from(context).inflate(R.layout.dialog_update, null, false);
                // inflater.inflate(R.layout.dialog_update, null);
                Button dialogButton = (Button) v.findViewById(R.id.dialogButtonSubmit);
                final EditText editTextMobileNo = (EditText) v.findViewById(R.id.edit_no);
                //  final ImageView imgContacts = (ImageView) v.findViewById(R.id.contactList);

                final ImageView imgCross = (ImageView)v.findViewById(R.id.cross);

                //if (dialog!=null)
                //{

                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String strMobileNo = "";
                        String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker&referrer="+strStore_Id;
                        strMobileNo = editTextMobileNo.getText().toString();
                        if(strMobileNo.isEmpty())
                        {
                            editTextMobileNo.setError("Error");
                            return;

                        }

                        int check = strMobileNo.charAt(0);

                        if (check == '0' || check == '1' || check == '2' || check == '3' || check == '4' || check == '5' || check == '6' || strMobileNo.length() < 10) {
                            editTextMobileNo.setError("Check Mobile Number");
                            editTextMobileNo.requestFocus();
                            return;
                        }
                        else {
                            if (AppUtil.isNetworkAvailable(context)) {
                                Request_SendInvetation task = new Request_SendInvetation();
                                task.execute(new String[]{strMobileNo,appUrl});
                                //Toast.makeText(con, "Invitation Send Successully.", Toast.LENGTH_SHORT).show();
                                uploadOptionDialog.dismiss();

                            } else {
                                AppUtils.showCustomToast(getActivity(), "Please check your internet connection");
                            }
                        }



                    }
                });
                //}

                imgCross.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        uploadOptionDialog.dismiss();
                    }
                });

                  /*  imgContacts.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {


                          *//*  Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setData(android.provider.Contacts.People.CONTENT_URI);
                            startActivity(i);
*//*
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, PICK_CONTACT);
                        }
                    });*/

                builder.setView(v);

                uploadOptionDialog = builder.create();
                uploadOptionDialog.show();

            }
        });


        layout_PhoneContacts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),InviteShare.class);
                intent.putExtra("storename",storeName1);
                intent.putExtra("id",strStore_Id);
                startActivity(intent);
            }
        });

        imgAddStore.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(context, AddStoreActivity.class);
                i.putExtra("seller_id", AppUtil.getUserId(context));
                startActivityForResult(i, 10);
            }
        });
		/*swipeLayout.setOnRefreshListener(this);
		 swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
	             android.R.color.holo_green_light,
	             android.R.color.holo_orange_light,
	             android.R.color.holo_red_light);*/

    }









    public void onDeleteStoreClick(int position,String store_id) {
        // TODO Auto-generated method stub
        /** call remove product task */
        if(AppUtil.isNetworkAvailable(context)){
            dialog = ProgressDialog.show(context, "", "please wait..");
            RemoveStoreTask taskRemoveStore = new RemoveStoreTask(position);
            taskRemoveStore.execute(new String[] {"1",AppUtil.getUserId(context),store_id});
        }else{
            AppUtil.showCustomToast(context, "please check your internet");
        }
    }
/*

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        //	if(requestCode == 10 && resultCode == 100){

        *//** update data here*//*
        try {

            String storeId = data.getStringExtra("storeId");
            String storeCode = data.getStringExtra("storeCode");
            String storeName = data.getStringExtra("storeName");
            String imageUrl = data.getStringExtra("imageUrl");
            //String srore_id, String store_image,String store_name
            arlistStoreHome.add(new StoreHomeListModel(storeId,imageUrl,storeName));
            adapter.notifyDataSetChanged();

           finalStrStore_Id = strStore_Id;
            String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker & referrer ="+finalStrStore_Id;

            switch (requestCode) {
                case (PICK_CONTACT):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
                            String number = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            Log.d("HELLO : ", " "+number);
                            Toast.makeText(context, "Number"+number, Toast.LENGTH_SHORT).show();
                            Request_SendInvetation task = new Request_SendInvetation();
                            task.execute(new String[]{name, appUrl});

                            // TODO Whatever you want to do with the selected contact name.
                        }
                    }
                    break;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //}
    }*/



    /** home data asyntask */
    private class HomeDataTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.sellerHomeData(urls[0],
                        context.getResources().getString(R.string.seller_home_url));
                Log.e("Seller Home Data RE", "" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            JSONObject jObject;
            try {
                if (result != null) {

                    arlistStoreHome.clear();
                    jObject = new JSONObject(result);
                    JSONObject job = jObject.getJSONObject("commandResult");
                    int success = job.getInt("success");
                    String message = job.getString("message");
                    if (success == 1) {
                        JSONObject job1 = job.getJSONObject("data");
                        //UserProfile.PopulateAddress(job1.toString());
                        JSONArray ja = job1.getJSONArray("stores");

                        JSONArray jaStoreInfo = job1.getJSONArray("storeinfo");

                        String imageUrl = "";
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);

                            String storeId = jo.getString("Id");
                            String storeName = jo.getString("Name");
                            String storeDescription = jo.getString("Description");
                            String storeCode = jo.getString("StoreCode");
                            String sddress = jo.getString("Address");
                            //String strImgUrl = jo.getString("Image_Url");
                            JSONArray jar = jo.getJSONArray("StoreGallery");
                            finalStrStore_Id= storeId;
                            strStore_Id=storeId;
                            storeName1= storeName;
                            //sendi bhb()




                            if (jar.length() > 0) {
                                JSONObject j = jar.getJSONObject(0);
                                imageUrl = j.getString("ImageUrl");

                                Log.e("Image---->",imageUrl);
                            }

                            arlistStoreHome.add(new StoreHomeListModel(storeId, imageUrl, storeName));

                            strStore_Id = storeId;
                            SQLiteHelper sQLiteHelper = new SQLiteHelper(getActivity());
                            sQLiteHelper.insertStoreHome(new StoreHomeListModel
                                    (storeId, imageUrl, storeName));
                        }
                        adapter = new StoreHomeListAdapter(context,
                                R.layout.seller_row_store_list, arlistStoreHome, StoresHomeFragment.this);
                        listStore.setAdapter(adapter);

                        if(jaStoreInfo.length() > 0){
                            JSONObject jobINfo = jaStoreInfo.getJSONObject(0);
                            someEventListener.userCount(jobINfo.getString("TotalUsers"));
                        }

                        someEventListener.notifCount(job1.getString("NotificationCount"));
                        Log.e("arlistStoreHome Size--->",""+arlistStoreHome.size());

                        if(arlistStoreHome.size()>0)
                        {
                            listStore.setVisibility(View.VISIBLE);
                            imgAddStore.setVisibility(View.GONE);
                            layout_shareApp.setVisibility(View.VISIBLE);
                            layout_PhoneContacts.setVisibility(View.VISIBLE);
                            ((RelativeLayout) getView().findViewById(R.id.rl_message)).setVisibility(View.GONE);

                        }else {

                            listStore.setVisibility(View.GONE);
                            layout_shareApp.setVisibility(View.GONE);
                            imgAddStore.setVisibility(View.VISIBLE);
                            layout_PhoneContacts.setVisibility(View.GONE);
                            String sourceString = "Welcome to Market Business ! <br><br><br> Have you always dreamed of <b> your own mobile app? </b> Do you want to stay ahead of the times and <b> increase your business </b> through new technology? <br><br> Now it's possible! Simply tap on the 'Add' button below to create your online store now and <b> multiply your profits! </b> ";
                            ((TextView) getView().findViewById(R.id.txt_nodata)).setText(Html.fromHtml(sourceString));

                            ((RelativeLayout) getView().findViewById(R.id.rl_message)).setVisibility(View.VISIBLE);
                        }



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
    /**remove store asyntask */
    private class RemoveStoreTask extends AsyncTask<String, Void, String> {
        int position ;
        public RemoveStoreTask(int position){
            this.position = position;
        }
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.sellerRemoveStore(urls[0], urls[1],urls[2],
                        context.getResources().getString(R.string.remove_store_url));
                Log.e("remove store RRESPONSE", "" + response);
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
                        AppUtil.showCustomToast(context, message);
                        //JSONObject job1 = job.getJSONObject("data");
                        arlistStoreHome.remove(position);
                        adapter.notifyDataSetChanged();

                        if(arlistStoreHome.size()>0)
                        {
                            listStore.setVisibility(View.VISIBLE);
                            imgAddStore.setVisibility(View.GONE);
                            ((RelativeLayout) getView().findViewById(R.id.rl_message)).setVisibility(View.GONE);

                        }else {

                            listStore.setVisibility(View.GONE);
                            imgAddStore.setVisibility(View.VISIBLE);
                            ((RelativeLayout) getView().findViewById(R.id.rl_message)).setVisibility(View.VISIBLE);
                        }


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


    public class Request_SendInvetation extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            String response = "";

            try {

                JsonCall obj = new JsonCall();
                response = obj.promoteApp(params[0], params[1],context.getString(R.string.promote_app_url));

                Log.e("Response", "" + response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null) {

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject jObject = jsonObject.getJSONObject("commandResult");
                    Integer status = jObject.getInt("success");
                    String message = jObject.getString("message");
                    Log.e("Success", status.toString());

                    if (status == 1) {

                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                if (dialog != null)
                    dialog.cancel();
            }

        }
    }

}
