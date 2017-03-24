package com.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.Database.SQLiteHelper;
import com.app.adapter.OrderHistoryAdapter;
import com.app.adapter.ReportsAdapter;
import com.app.jsoncall.JsonCall;
import com.app.model.OrderHistoryModel;
import com.app.model.ReportProductModel;
import com.app.strykerseller.ProductListActivity;
import com.app.strykerseller.R;
import com.app.utills.AppUtil;
import com.app.utills.GPSTracker;

import java.util.ArrayList;


public class ReportsFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Context context;
    ListView listOrder;
    ReportsAdapter adapter;
    OrderHistoryAdapter adapterOrder;
    private Double mLat = 0.0, mLong = 0.0;
    ProgressDialog dialog;
    RelativeLayout rl_date, rl_product, rl_user;
    TextView txtDate, txtProduct, txtUser;
    ArrayList<ReportProductModel> arlistReportProduct = new ArrayList<>();
    ArrayList<ReportProductModel> arlistReportUser = new ArrayList<>();
    ArrayList<OrderHistoryModel> arlistReportDate = new ArrayList<>();
    int flag;
    String storeId = "";
    SwipeRefreshLayout swipeRefreshLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.fragment_reports,
                container, false);
        return rootview;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        listOrder = (ListView)getActivity().findViewById(R.id.lv_reports);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.swieeffepe) ;

        GPSTracker mGPS = new GPSTracker(context);
        if (mGPS.canGetLocation) {
            mLat = mGPS.getLatitude();
            mLong = mGPS.getLongitude();
        }

        init();

        ProductListActivity proActivity = (ProductListActivity) getActivity();
        storeId = proActivity.StoreId();

        Log.e("StoreID in Reports", "" + storeId);

        if (AppUtil.isNetworkAvailable(context)) {
            dialog = ProgressDialog.show(context, "", "Please wait...");
            ReportProductTask task = new ReportProductTask();
            task.execute(new String[]{storeId});

        } else {
            AppUtil.showCustomToast(context,
                    "please check your internet connection");
        }
    }



    private void init() {

        //pb=(com.github.rahatarmanahmed.cpv.CircularProgressView) getView().findViewById(R.id.progressBar1);

        //swipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        rl_date = ((RelativeLayout) getView().findViewById(R.id.rl_date));
        rl_product = ((RelativeLayout) getView().findViewById(R.id.rl_product));
        rl_user = ((RelativeLayout) getView().findViewById(R.id.rl_user));

        txtDate = (TextView)getView().findViewById(R.id.txt_date);
        txtProduct = (TextView)getView().findViewById(R.id.txt_pending);
        txtUser = (TextView)getView().findViewById(R.id.txt_user);

        swipeRefreshLayout.setOnRefreshListener(this);

		/*swipeLayout.setOnRefreshListener(this);
         swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
	             android.R.color.holo_green_light,
	             android.R.color.holo_orange_light,
	             android.R.color.holo_red_light);*/


        rl_date.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 0;

                SQLiteHelper myDataBaseHelper = new SQLiteHelper(getActivity());
                arlistReportDate=myDataBaseHelper.getAllRecords();
                myDataBaseHelper.close();

                if (arlistReportDate.isEmpty()) {
                    if (AppUtil.isNetworkAvailable(context)) {
                        dialog = ProgressDialog.show(context, "", "Please wait...");
                        ReportDateTask task = new ReportDateTask();
                        task.execute(new String[]{storeId});

                    } else {
                        AppUtil.showCustomToast(context,
                                "please check your internet connection");
                    }
                }else{

                    try {
                        setData(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



            }
        });

        rl_product.setOnClickListener(new OnClickListener() {


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
        rl_user.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 2;
                try {
                    if(arlistReportUser.size()<= 0) {
                        if (AppUtil.isNetworkAvailable(context)) {
                            dialog = ProgressDialog.show(context, "", "Please wait...");
                            ReportUserTask task = new ReportUserTask();
                            task.execute(new String[]{storeId});

                        } else {
                            AppUtil.showCustomToast(context,
                                    "please check your internet connection");
                        }
                    }
                    setData(2);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    public void setData(int tabPosition) throws JSONException {
        //arlistOrderHome.clear();
        //txtPending, txtDispatched, txtCancelByMe, txtCancelBySeller;
        txtDate.setTextColor(getResources().getColor(R.color.Text_color_black));
        txtProduct.setTextColor(getResources().getColor(R.color.Text_color_black));
        txtUser.setTextColor(getResources().getColor(R.color.Text_color_black));

        if (tabPosition == 0) {
            txtDate.setTextColor(getResources().getColor(R.color.orange1));
            adapterOrder = new OrderHistoryAdapter(context,
                    R.layout.seller_row_order_history, arlistReportDate);
            listOrder.setAdapter(adapterOrder);

        } else if (tabPosition == 1) {
            txtProduct.setTextColor(getResources().getColor(R.color.orange1));
            Log.e("List in SetData", "" + arlistReportProduct);
            adapter = new ReportsAdapter(context,
                    R.layout.seller_row_reports_products, arlistReportProduct,tabPosition);
            listOrder.setAdapter(adapter);

        } else if (tabPosition == 2) {
            txtUser.setTextColor(getResources().getColor(R.color.orange1));
            adapter = new ReportsAdapter(context,
                    R.layout.seller_row_reports_products, arlistReportUser,tabPosition);
            listOrder.setAdapter(adapter);
        }

    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        refreshData();


    }

    private void refreshData() {
        arlistReportProduct.clear();
        arlistReportUser.clear();

        SQLiteHelper sqLiteHelper = SQLiteHelper.getInstance(getActivity());
        sqLiteHelper.deleteCategory();
        long size=sqLiteHelper.size();
        Log.e("SIZE",""+size);
       // Toast.makeText(context, "SIZE "+size, Toast.LENGTH_SHORT).show();

        ReportDateTask task = new ReportDateTask();
        task.execute(new String[]{storeId});



        ReportProductTask task1 = new ReportProductTask();
        task1.execute(new String[]{storeId});


        ReportUserTask taskuser = new ReportUserTask();
        taskuser.execute(new String[]{storeId});


    }

    private class ReportProductTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.productWiseReport(urls[0],
                        context.getResources().getString(R.string.report_productwise_url));
                Log.e("ProductWise RRESPONSE", "" + response);
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

                        arlistReportProduct.clear();

                        JSONObject jo = job.getJSONObject("data");
                        JSONArray jaPending = jo.getJSONArray("products");


                        for (int i = 0; i < jaPending.length(); i++) {

                            JSONObject jobj = jaPending.getJSONObject(i);
                            String categoryName = jobj.getString("categoryName");
                            String proName = jobj.getString("Name");

                            String totalPrice = jobj.getString("SUM( TotalAmount )");
                            String Quantity = jobj.getString("SUM( Quantity )");

                            arlistReportProduct.add(new ReportProductModel
                                    (categoryName, proName, totalPrice, Quantity));
                        }
                        setData(1);

                    } else {
                        //AppUtil.showCustomToast(context, message);
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

    private class ReportUserTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.userWiseReport(urls[0],
                        context.getResources().getString(R.string.report_userwise_url));
                Log.e("UserWise RRESPONSE", "" + response);
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

                        arlistReportUser.clear();

                        JSONArray jaPending = job.getJSONArray("data");

                        for (int i = 0; i < jaPending.length(); i++) {

                            JSONObject jobj = jaPending.getJSONObject(i);

                            String userId = jobj.getString("ID");
                            String userName = jobj.getString("FullName");
                            String userImage = jobj.getString("PofileImage");
                            String totalPrice = jobj.getString("SUM(TotalAmount)");
                            String Quantity = jobj.getString("SUM(Quantity)");

                            arlistReportUser.add(new ReportProductModel
                                    (userName, totalPrice, Quantity));
                        }
                        setData(2);

                    } else {
                        // AppUtil.showCustomToast(context, message);
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

    private class ReportDateTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                JsonCall obj = new JsonCall();
                response = obj.dateWiseReport(urls[0],
                        context.getResources().getString(R.string.report_datewaise_url));
                Log.e("Datewise RRESPONSE", "" + response);
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

                        arlistReportDate.clear();

                        JSONArray jaPending = job.getJSONArray("data");

                        //	JSONArray jaDispatched = jo1.getJSONArray("dipatched");
                        //	JSONArray jaCancelByMe = jo1.getJSONArray("cancelledbyme");

                        for (int i = 0; i < jaPending.length(); i++) {

                            JSONObject jobj = jaPending.getJSONObject(i);

                            String orderDate = jobj.getString("OrderDate");
                            String totalPrice = jobj.getString("SUM(TotalAmount)");
                            String Quantity = jobj.getString("SUM(Quantity)");

                            SQLiteHelper sQLiteHelper = new SQLiteHelper(getActivity());
                            sQLiteHelper.inserReportDate(new OrderHistoryModel
                                    (orderDate, totalPrice, Quantity));

                            arlistReportDate.add(new OrderHistoryModel
                                    (orderDate, totalPrice, Quantity));
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        setData(0);

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
