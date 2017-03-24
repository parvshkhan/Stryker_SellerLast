package com.app.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.adapter.OrderHomeListAdapter;
import com.app.jsoncall.JsonCall;
import com.app.listener.CancelOrderListener;
import com.app.model.OrderHomeModel;
import com.app.strykerseller.R;
import com.app.strykerseller.SellerPendingActivity;
import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;

import java.util.ArrayList;




public class OrderHomeFragment extends android.support.v4.app.Fragment implements OnRefreshListener, CancelOrderListener {

    Context context;

    OrderHomeListAdapter adapter;
    ListView listOrder;
    ArrayList<OrderHomeModel> arlistOrderPending = new ArrayList<OrderHomeModel>();
    ArrayList<OrderHomeModel> arlistOrderDispatched = new ArrayList<OrderHomeModel>();
    ArrayList<OrderHomeModel> arlistOrderCancelBySeller = new ArrayList<OrderHomeModel>();
    ArrayList<OrderHomeModel> arlistOrderCancelByMe = new ArrayList<OrderHomeModel>();
    //ArrayList<ModelListHomeItem> listfollowing = new ArrayList<ModelListHomeItem>();
    private Double mLat = 0.0, mLong = 0.0;
    ProgressDialog dialog;
    //SwipeRefreshLayout swipeLayout;
    int flag = 0;
    RelativeLayout rl_pending, rl_dispatched, rl_cancelByMe, rl_cancelBySeller;
    TextView txtPending, txtDispatched, txtCancelByMe, txtCancelBySeller;
    String storeName;
    String orderId = "";
    
    boolean isActiveHandler = false;

    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.fragment_home_orders,
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

        Log.e("Order Home Fragment", "True");
        isActiveHandler = true;
        init();
        //setData();

        if (AppUtil.isNetworkAvailable(context)) {
           // dialog = ProgressDialog.show(context, "", "Please wait...");
            HomeDataTask task = new HomeDataTask();
            task.execute(new String[]{AppUtil.getUserId(context)});
        } else {
            AppUtil.showCustomToast(context,
                    "please check your internet connection");
        }
    }
    
    
    public void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	isActiveHandler = false;
    }
    
    
    public void onDestroyView() {
    	// TODO Auto-generated method stub
    	super.onDestroyView();
    	isActiveHandler = false;
    }
    
    public void refreshOrder(){
    	if (isActiveHandler) {
			new Handler().postDelayed(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					 if (AppUtil.isNetworkAvailable(context)) {
				           // dialog = ProgressDialog.show(context, "", "Please wait...");
				            HomeDataTask task = new HomeDataTask();
				            task.execute(new String[]{AppUtil.getUserId(context)});
				        }
				}
			}, 3*60*1000);
		}
    }

    private void init() {
        //pb=(com.github.rahatarmanahmed.cpv.CircularProgressView) getView().findViewById(R.id.progressBar1);
        listOrder = (ListView) getView().findViewById(R.id.lv_order_details);
        //swipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        rl_pending = ((RelativeLayout) getView().findViewById(R.id.rl_pending));
        rl_dispatched = ((RelativeLayout) getView().findViewById(R.id.rl_dispatched));
        rl_cancelByMe = ((RelativeLayout) getView().findViewById(R.id.rl_cancelled));
        rl_cancelBySeller = ((RelativeLayout) getView().findViewById(R.id.rl_cancelled_2));

        txtPending = (TextView) getView().findViewById(R.id.txt_pending);
        txtDispatched = (TextView) getView().findViewById(R.id.txt_dispatch);
        txtCancelByMe = (TextView) getView().findViewById(R.id.txt_cancel);
        txtCancelBySeller = (TextView) getView().findViewById(R.id.txt_cancel_2);

        listOrder.setOnItemClickListener(new OnItemClickListener() {

            
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                /**perform the action on click of order list*/
                Intent i = new Intent(context, SellerPendingActivity.class);
                if (flag == 0) {
                    i.putExtra("data", arlistOrderPending.get(position).getCategoryJosnArray().toString());
                    i.putExtra("order_date", arlistOrderPending.get(position).getOrderTime());
                    i.putExtra("user_name", arlistOrderPending.get(position).getUserName());
                    i.putExtra("user_detail", arlistOrderPending.get(position).getUserDetails());
                    i.putExtra("order_id", arlistOrderPending.get(position).getOrderId());
                    i.putExtra("order_type", "pending");
                } else if (flag == 1) {
                    i.putExtra("data", arlistOrderDispatched.get(position).getCategoryJosnArray().toString());
                    i.putExtra("order_date", arlistOrderDispatched.get(position).getOrderTime());
                    i.putExtra("user_name", arlistOrderDispatched.get(position).getUserName());
                    i.putExtra("order_id",  arlistOrderDispatched.get(position).getOrderId());
                    i.putExtra("user_detail", arlistOrderDispatched.get(position).getUserDetails());
                    i.putExtra("order_type", "dispatched");
                } else if (flag == 2) {
                    i.putExtra("data", arlistOrderCancelByMe.get(position).getCategoryJosnArray().toString());
                    i.putExtra("order_date", arlistOrderCancelByMe.get(position).getOrderTime());
                    i.putExtra("user_name", arlistOrderCancelByMe.get(position).getUserName());
                    i.putExtra("order_id",  arlistOrderCancelByMe.get(position).getOrderId());
                    i.putExtra("user_detail", arlistOrderCancelByMe.get(position).getUserDetails());
                    i.putExtra("order_type", "cancelbyme");
                } else if (flag == 3) {
                    i.putExtra("data", arlistOrderCancelBySeller.get(position).getCategoryJosnArray().toString());
                    i.putExtra("order_date", arlistOrderCancelBySeller.get(position).getOrderTime());
                    i.putExtra("user_name", arlistOrderCancelBySeller.get(position).getUserName());
                    i.putExtra("order_id",  arlistOrderCancelBySeller.get(position).getOrderId());
                    i.putExtra("user_detail", arlistOrderCancelBySeller.get(position).getUserDetails());
                    i.putExtra("order_type", "cencelbyseller");
                }
                startActivity(i);
            }
        });
        rl_pending.setOnClickListener(new OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 0;
                try {
                    setData(0);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        rl_dispatched.setOnClickListener(new OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 1;
                try {
                    setData(1);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        rl_cancelByMe.setOnClickListener(new OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 2;
                try {
                    setData(2);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        rl_cancelBySeller.setOnClickListener(new OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
               flag = 3;
                try {
                    setData(3);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

		/*swipeLayout.setOnRefreshListener(this);
		 swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
	             android.R.color.holo_green_light,
	             android.R.color.holo_orange_light,
	             android.R.color.holo_red_light);*/

    }

    public void setData(int tabPosition) throws JSONException {
        //arlistOrderHome.clear();
        //txtPending, txtDispatched, txtCancelByMe, txtCancelBySeller;
        txtPending.setTextColor(getResources().getColor(R.color.Text_color_black));
        txtDispatched.setTextColor(getResources().getColor(R.color.Text_color_black));
        txtCancelByMe.setTextColor(getResources().getColor(R.color.Text_color_black));
        txtCancelBySeller.setTextColor(getResources().getColor(R.color.Text_color_black));

        if (tabPosition == 0) {
            txtPending.setTextColor(getResources().getColor(R.color.orange1));
            adapter = new OrderHomeListAdapter(context,
                    R.layout.seller_row_order_list_1, arlistOrderPending, OrderHomeFragment.this,tabPosition);
        } else if (tabPosition == 1) {
            txtDispatched.setTextColor(getResources().getColor(R.color.orange1));
            adapter = new OrderHomeListAdapter(context,
                    R.layout.seller_row_order_list_1, arlistOrderDispatched, OrderHomeFragment.this,tabPosition);
        } else if (tabPosition == 2) {
            txtCancelByMe.setTextColor(getResources().getColor(R.color.orange1));
            adapter = new OrderHomeListAdapter(context,
                    R.layout.seller_row_order_list_1, arlistOrderCancelByMe, OrderHomeFragment.this,tabPosition);
        } else if (tabPosition == 3) {
            txtCancelBySeller.setTextColor(getResources().getColor(R.color.orange1));
            adapter = new OrderHomeListAdapter(context,
                    R.layout.seller_row_order_list_1, arlistOrderCancelBySeller, OrderHomeFragment.this,tabPosition);
        }
        listOrder.setAdapter(adapter);

    }

    
    public void onRefresh() {
        // TODO Auto-generated method stub

    }

    /**
     * home data asyntask
     */
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
                Log.e("Home orderrrr Data RRESPONSE", "" + response);
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

                        arlistOrderCancelByMe.clear();
                        arlistOrderCancelBySeller.clear();
                        arlistOrderDispatched.clear();
                        arlistOrderPending.clear();

                        JSONObject jo = job.getJSONObject("data");
                        JSONObject jo1 = jo.getJSONObject("orderdetail");

                        JSONArray jaPending = jo1.getJSONArray("pending");
                        	JSONArray jaDispatched = jo1.getJSONArray("dispatched");
                        	JSONArray jaCancelByMe = jo1.getJSONArray("cancellByBuyer");
                        JSONArray jaCancelBySeller = jo1.getJSONArray("cancellBySeller");
                       
                        for (int i = 0; i < jaPending.length(); i++) {
                            JSONObject jobj = jaPending.getJSONObject(i);
                            String storeID = jobj.getString("StoreID");
                            storeName = jobj.getString("StoreName");
                            JSONObject jOrder = jobj.getJSONObject("order");
                            String userDetails = jobj.getJSONObject("Address").toString();
                            String userName = jOrder.getString("user_name");
                            String userId = jOrder.getString("user_id");
                            String userImage = jOrder.getString("user_image");
                            String orderTime = jOrder.getString("order_datetime");
                            JSONArray ja11 = jOrder.getJSONArray("category");

                            for (int j = 0; j < ja11.length(); j++) {

                                JSONObject jcat = ja11.getJSONObject(j);
                                JSONArray jitemArr = jcat.getJSONArray("items");

                                for (int k = 0; k < jitemArr.length(); k++) {
                                    JSONObject jItem = jitemArr.getJSONObject(k);
                                    orderId = jItem.getString("OrderID");
                                }
                            }

                            arlistOrderPending.add(new OrderHomeModel
                                    (storeID, orderId, userId, userName, userImage, orderTime, ja11,userDetails,""));
                        }
						
                        /***/
                        /**List for Dispatched Order**/

                        for (int i = 0; i < jaDispatched.length(); i++) {
                            JSONObject jobj = jaDispatched.getJSONObject(i);
                            String storeID = jobj.getString("StoreID");
                            storeName = jobj.getString("StoreName");
                            JSONObject jOrder = jobj.getJSONObject("order");
                            String userDetails = jobj.getJSONObject("Address").toString();
                            String userName = jOrder.getString("user_name");
                            String userId = jOrder.getString("user_id");
                            String userImage = jOrder.getString("user_image");
                            String orderTime = jOrder.getString("order_datetime");
                            JSONArray ja11 = jOrder.getJSONArray("category");

                            for (int j = 0; j < ja11.length(); j++) {

                                JSONObject jcat = ja11.getJSONObject(j);
                                JSONArray jitemArr = jcat.getJSONArray("items");

                                for (int k = 0; k < jitemArr.length(); k++) {
                                    JSONObject jItem = jitemArr.getJSONObject(k);
                                    orderId = jItem.getString("OrderID");
                                }
                            }

                            arlistOrderDispatched.add(new OrderHomeModel
                                    (storeID, orderId, userId, userName, userImage, orderTime, ja11, userDetails,""));
                        }

                        /**List for CancelledbyMe Order**/

                        for (int i = 0; i < jaCancelByMe.length(); i++) {
                            JSONObject jobj = jaCancelByMe.getJSONObject(i);
                            String storeID = jobj.getString("StoreID");
                            storeName = jobj.getString("StoreName");
                            JSONObject jOrder = jobj.getJSONObject("order");
                            String userDetails = jobj.getJSONObject("Address").toString();
                            String userName = jOrder.getString("user_name");
                            String userId = jOrder.getString("user_id");
                            String userImage = jOrder.getString("user_image");
                            String orderTime = jOrder.getString("order_datetime");
                            JSONArray ja11 = jOrder.getJSONArray("category");

                            for (int j = 0; j < ja11.length(); j++) {

                                JSONObject jcat = ja11.getJSONObject(j);
                                JSONArray jitemArr = jcat.getJSONArray("items");

                                for (int k = 0; k < jitemArr.length(); k++) {
                                    JSONObject jItem = jitemArr.getJSONObject(k);
                                    orderId = jItem.getString("OrderID");
                                }
                            }

                            arlistOrderCancelByMe.add(new OrderHomeModel
                                    (storeID, orderId, userId, userName, userImage, orderTime, ja11, userDetails,""));
                        }
				

                        for (int i = 0; i < jaCancelBySeller.length(); i++) {
                            JSONObject jobj = jaCancelBySeller.getJSONObject(i);
                            String storeID = jobj.getString("StoreID");
                            storeName = jobj.getString("StoreName");
                            JSONObject jOrder = jobj.getJSONObject("order");
                            String userDetails = jobj.getJSONObject("Address").toString();
                            String userName = jOrder.getString("user_name");
                            String userId = jOrder.getString("user_id");
                            String userImage = jOrder.getString("user_image");
                            String orderTime = jOrder.getString("order_datetime");
                            JSONArray ja11 = jOrder.getJSONArray("category");
                            for (int j = 0; j < ja11.length(); j++) {

                                JSONObject jcat = ja11.getJSONObject(j);
                                JSONArray jitemArr = jcat.getJSONArray("items");

                                for (int k = 0; k < jitemArr.length(); k++) {
                                    JSONObject jItem = jitemArr.getJSONObject(k);
                                    orderId = jItem.getString("OrderID");
                                }
                            }

                            arlistOrderCancelBySeller.add(new OrderHomeModel
                                    (storeID, orderId, userId, userName, userImage, orderTime, ja11, userDetails,""));
                        }

                        setData(flag);

                    } else {
                        //AppUtil.showCustomToast(context, message);
                    }

                } else {
                   // AppUtil.showCustomToast(context,"please check your internet connection");
                }
                refreshOrder();
                if (dialog != null)
                    dialog.cancel();
            } catch (Exception e) {
                e.printStackTrace();
                if (dialog != null)
                    dialog.cancel();
                
                refreshOrder();

            }
        }
    }

    
    public void onCancelOrderClick(final int posi, final String orderId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure, want to cancel this order?")
                .setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogCancel, int id) {

                        if (AppUtil.isNetworkAvailable(context)) {
                            dialog = ProgressDialog.show(context, "", "please wait..");
                            CancelOrderTask task = new CancelOrderTask(posi);
                            task.execute(new String[]{orderId, "4", AppUtil.getUserId(context), AppUtil.getDeviceId(context)});
                        } else {
                            AppUtil.showCustomToast(context, "please check your internet");
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogCancel, int id) {
                    	dialogCancel.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class CancelOrderTask extends AsyncTask<String, Void, String> {
        int position;

        public CancelOrderTask(int position) {
            this.position = position;
        }

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

                        arlistOrderPending.remove(position);
                        listOrder.setAdapter(adapter);
                        //finish();

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
