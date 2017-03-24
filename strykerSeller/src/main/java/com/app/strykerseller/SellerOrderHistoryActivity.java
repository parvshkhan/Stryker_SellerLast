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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.OrderHistoryAdapter;
import com.app.jsoncall.JsonCall;
import com.app.model.OrderHistoryModel;
import com.app.utills.AppUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Inflectica on 10/13/2015.
 */
public class SellerOrderHistoryActivity extends Activity {
    Context context;
    ListView listOrderHistory;
    String userName;
    TextView txtUserName;
    ProgressDialog dialog;
    String storeId = "";
    String buyerId = "";
    ArrayList<OrderHistoryModel> orderItems = new ArrayList<>() ;
    OrderHistoryAdapter adapter;
    ImageView imgBack;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_activity_order_history);
        context = this;
        init();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("user_name")) {
                userName = b.getString("user_name");
                Log.e("User", userName);
                txtUserName.setText(userName);
            }
        }

        if (b.containsKey("buyer_id")) {
            buyerId = b.getString("buyer_id");
            Log.e("BuyerId",""+buyerId);
        }

        if (b.containsKey("store_id")) {
            storeId = b.getString("store_id");
        }

        if (AppUtil.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "Please wait...");
            UserOrderHistoryTask task = new UserOrderHistoryTask();
            task.execute(new String[] {buyerId,storeId});

        } else {
            AppUtil.showCustomToast(context, "please check your internet connection");
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {

                finish();
            }
        });

    }

    public void init() {
        imgBack = (ImageView)findViewById(R.id.img_back);
        txtUserName = (TextView) findViewById(R.id.txt_user_name);
        listOrderHistory = (ListView) findViewById(R.id.lv_order_history);

    }

    private class UserOrderHistoryTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.OrderHistory(urls[0],urls[1],
                        context.getResources().getString(R.string.order_history_url));
                Log.e("OrderHistory RESPONSE", "" + response);
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
                        JSONObject job1 = job.getJSONObject("data");
                        JSONArray jarray = job1.getJSONArray("userorder");

                        for(int i =0; i<jarray.length();i++)
                        {
                            JSONObject jOrder = jarray.getJSONObject(i);
                            String orderId = jOrder.getString("ID");
                            String totalUnit = jOrder.getString("Total_Quantity");
                            String totalPrice = jOrder.getString("TotalAmount");
                            String orderDate = jOrder.getString("OrderDateTime");

                            orderItems.add(new OrderHistoryModel(orderDate,totalUnit,totalPrice,orderId));
                        }
                        adapter = new OrderHistoryAdapter(context,R.layout.seller_row_order_history,orderItems);
                        listOrderHistory.setAdapter(adapter);

                    } else {
                        AppUtil.showCustomToast(context, message);
                    }

                } else {
                    AppUtil.showCustomToast(context, "please check your internet connection");
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
