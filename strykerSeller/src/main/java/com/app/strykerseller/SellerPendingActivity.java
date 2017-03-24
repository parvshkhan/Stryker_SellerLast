package com.app.strykerseller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.adapter.Pending_Adapter;
import com.app.jsoncall.JsonCall;
import com.app.model.PendingModel;
import com.app.utills.AppUtil;


/**
 * Created by Inflectica on 10/13/2015.
 */
public class SellerPendingActivity extends Activity {

    String jsonArray = "";
    Context context;
    TextView txt_header_pending, txt_user_name, txt_price, txtOrderStatus, txt_user_detail;
    ImageView imgCancelOrder;
    ListView pending_listview;
    ProgressDialog dialog;
    Typeface tfbold;
    Typeface tfregular;
    Typeface tfthin;
    TextView txtTime;
    int totalCost = 0;
    String order_id = "", orderDate = "", userName = "", userDetails = "";
    ImageView img_back;
    Pending_Adapter pending_adapter;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_activity_pending);
        context = this;
        init();
        setListener();

        Bundle b = getIntent().getExtras();

        if (b != null) {
            if (b.containsKey("data")) {
                try {
                    JSONArray jaData = new JSONArray(b.getString("data"));
                    setData(jaData);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (b.containsKey("order_date")) {
                orderDate = b.getString("order_date");
                Log.e("Date in Pending Act",orderDate);
                ((TextView)findViewById(R.id.txt_dateTime)).setText(orderDate);
            }
            if (b.containsKey("user_name")) {
                userName = b.getString("user_name");
                Log.e("User in Pending Act",userName);
                txt_user_name.setText(userName);
            }

            if (b.containsKey("order_id")) {
                order_id = b.getString("order_id");
            }

            if (b.containsKey("order_type")) {
                txtOrderStatus.setText("Order " + b.getString("order_type") + "..");
                if (b.getString("order_type").equalsIgnoreCase("Pending")) {
                    imgCancelOrder.setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.txt_dispatch)).setVisibility(View.VISIBLE);
                } else {
                    imgCancelOrder.setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.txt_dispatch)).setVisibility(View.GONE);
                }
            }
            
            if(b.containsKey("user_detail")){
            	userDetails = b.getString("user_detail");            	
            	setUserDetails(userDetails);
            }
        }
    }
    
    public void setUserDetails(String userData){    	
    	try {
			JSONObject jo = new JSONObject(userData);
			 txt_user_detail.setText("Name : " + jo.getString("FullName")
					 + "\n" + "Mobile No : " + jo.getString("Mobile")+ "\n" + 
					 "Address : " + jo.getString("Address1") + " " + jo.getString("Address2")
					 +"\n" + "City : " + jo.getString("City") + "\n" + "State : " + 
					 jo.getString("State") + "\n" + "Country : " + jo.getString("Country")
					 +"\n" + "ZipCode : " + jo.getString("ZipCode"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public void setData(JSONArray data) {

       pending_adapter = new Pending_Adapter(context, R.layout.row_pending);

        for (int i = 0; i < data.length(); i++) {
            try {

                JSONObject jo = data.getJSONObject(i);
                pending_adapter.addSectionHeaderItem(jo.getString("name"));
                String ProductCatId = jo.getString("ProductCatId");

                JSONArray jaItem = jo.getJSONArray("items");
                for (int j = 0; j < jaItem.length(); j++) {
                    JSONObject jo1 = jaItem.getJSONObject(j);

                    pending_adapter.add(new PendingModel(jo1.getString("ProName"), jo1.getString("ProDescription"), jo1.getString("Quantity"),
                            jo1.getString("TotalCost"),""));
                    try {
                        totalCost += Integer.parseInt(jo1.getString("TotalCost"));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        txt_price.setText(totalCost + "");        
        
        pending_listview.setAdapter(pending_adapter);
        
       
    }

    public void init() {
        /*tfbold = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");
        tfregular = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
        tfthin = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");*/

        img_back = (ImageView)findViewById(R.id.img_back);
        imgCancelOrder = (ImageView) findViewById(R.id.img_cancel_order);

        txt_user_name = (TextView)findViewById(R.id.txt_user_name);

        txtOrderStatus = (TextView)findViewById(R.id.txt_order_status);
       // txt_header_pending.setTypeface(tfregular);

        pending_listview = (ListView)findViewById(R.id.lv_items);
        pending_listview.setVisibility(View.VISIBLE);

        View footerView =  ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_pending_list, null, false);
        txt_price = (TextView) footerView.findViewById(R.id.txt_price);
       // txt_price.setTypeface(tfbold);
        txt_user_detail = (TextView) footerView.findViewById(R.id.txt_user_detail);
        pending_listview.addFooterView(footerView);

    }

    public void setListener(){
    	
    	((TextView) findViewById(R.id.txt_dispatch)).
    	setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   AlertDialog.Builder builder = new AlertDialog.Builder(context);
	                builder.setMessage("Are you sure, want to dispatch this order?")
	                        .setCancelable(false)
	                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog1, int id) {

	                                if(AppUtil.isNetworkAvailable(context)){
	                                    dialog = ProgressDialog.show(context, "", "please wait..");
	                                    CancelOrderTask task = new CancelOrderTask();
	                                    task.execute(new String[] {order_id,"2",AppUtil.getUserId(context),AppUtil.getDeviceId(context)});
	                                }else{
	                                    AppUtil.showCustomToast(context, "please check your internet");
	                                }
	                            }
	                        })
	                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog1, int id) {
	                                dialog1.cancel();
	                            }
	                        });
	                AlertDialog alert = builder.create();
	                alert.show();
			}
		});

        imgCancelOrder.setOnClickListener(new View.OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // status id = 3 for buyer and 4 for seller
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure, want to cancel this order?")
                        .setCancelable(false)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog1, int id) {

                                if(AppUtil.isNetworkAvailable(context)){
                                    dialog = ProgressDialog.show(context, "", "please wait..");
                                    CancelOrderTask task = new CancelOrderTask();
                                    task.execute(new String[] {order_id,"4",AppUtil.getUserId(context),AppUtil.getDeviceId(context)});
                                }else{
                                    AppUtil.showCustomToast(context, "please check your internet");
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog1, int id) {
                                dialog1.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    private class CancelOrderTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.cancelOrder(urls[0], urls[1], urls[2], urls[3],
                        context.getResources().getString(R.string.cancel_order_url));
                Log.e("cancel order RRESPONSE", "" + response);
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
                        finish();
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
