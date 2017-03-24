package com.app.strykerseller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.AdapterSellerProductList;
import com.app.jsoncall.JsonCall;
import com.app.model.SellerProductListModel;
import com.app.utills.AppUtils;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Inflac on 15-10-2015.
 */
public class SellerActivityProductList extends Activity {

    ListView listview_outer,listview_hotproduct,listview_addproduct;
    ImageView img_add1,img_search,img_onoff,img_add_product;
    TextView txt_pushnotification,txt_product_list,txt_reports,txt_preview;
    ProgressDialog dialog;
    Context context;
    List part = new ArrayList();

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product_list);
        context = this;
        init();
        if(AppUtils.isNetworkAvailable(context)) {

            dialog = ProgressDialog.show(context,"","Please wait....");
            ProductList pro = new ProductList();
            pro.execute(new String[]{"5"});
        }
        else {
            AppUtils.showCustomToast(context,"Please check your internet connection");
        }


       /* for (int i =0; i<2;i++) {

            if (i==0) {

                for (int j = 0; j < 5; j++)
                    adapter.add(new ModelSellerproductList("Pepsodent"+j, "120", "Cool and Clear"),i);

            }
            if ( i==1) {

                for (int j=0; j<10; j++)

                    adapter.add(new ModelSellerproductList("Colgate"+j, "130", "Cool and Clear"),i);

            }


        }
*/


        img_add1.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show();
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show();
            }
        });

        txt_pushnotification.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                Toast.makeText(context, "Push Notification", Toast.LENGTH_SHORT).show();
            }
        });

        txt_reports.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                Toast.makeText(context, "Reports", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void init() {
       // listview_hotproduct = (ListView)findViewById(R.id.listview_hotproduct);

      //  listview_addproduct = (ListView)findViewById(R.id.listview_addproduct);

        listview_outer = (ListView)findViewById(R.id.listview_outer);
        img_add1 = (ImageView)findViewById(R.id.img_add1);
        img_search = (ImageView)findViewById(R.id.img_search);
        img_onoff = (ImageView)findViewById(R.id.img_onoff);
        img_add_product = (ImageView)findViewById(R.id.img_add_product);

        txt_pushnotification = (TextView)findViewById(R.id.txt_pushnotification);
        txt_product_list = (TextView)findViewById(R.id.txt_product_list);
        txt_reports = (TextView)findViewById(R.id.txt_reports);
        txt_preview = (TextView)findViewById(R.id.txt_preview);



      /*  listview_addproduct.setVisibility(View.VISIBLE);
        View add_header = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_add_product, null,false);
        listview_addproduct.addHeaderView(add_header);

        listview_hotproduct.setVisibility(View.VISIBLE);
        View header = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.seller_header_productlist, null,false);
        listview_hotproduct.addHeaderView(header);*/

    }

    private class ProductList extends AsyncTask<String,Void,String> {

        
        protected void onPreExecute() {
            super.onPreExecute();
        }

        
        protected String doInBackground(String... url) {
            String response="";
            try {
                JsonCall jsonClass = new JsonCall();
                response = jsonClass.getProductlist(url[0],getString(R.string.productlist_url));
                Log.e("Productlist Response",""+response);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        
        protected void onPostExecute(String result) {
            JSONObject jsonObject;

            AdapterSellerProductList adapter = new AdapterSellerProductList(context,R.layout.row_hot_product_list, part );
            listview_outer.setAdapter(adapter);

            try {
                if (result != null) {
                    jsonObject = new JSONObject(result);

                    JSONObject categoryobject = jsonObject.getJSONObject("commandResult");
                    int status = categoryobject.getInt("success");
                    Log.e("status",""+status);

                    JSONObject dataobject = categoryobject.getJSONObject("data");

                    JSONArray hotarray = dataobject.getJSONArray("hotproducts");

                    for( int i =0; i<hotarray.length(); i++) {
                        JSONObject object = hotarray.getJSONObject(i);
                        if(i==hotarray.length()-1) {
                            adapter.add(new SellerProductListModel(object.getString("Id"),object.getString("Name"),object.getString("Description")
                                    ,object.getString("ImageUrl"),"none",object.getString("Price"),false, true),0);
                        }
                        else
                            adapter.add(new SellerProductListModel(object.getString("Id"),object.getString("Name"),object.getString("Description")
                                    ,object.getString("ImageUrl"),"none",object.getString("Price"),false, false),0);
                   }

                    JSONObject productobject = dataobject.getJSONObject("products");
                    JSONArray jsonArray = productobject.getJSONArray("category");


                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        JSONArray jarray = jobject.getJSONArray("items");

                        for (int j = 0;j<jarray.length(); j++) {
                            JSONObject object = jarray.getJSONObject(j);
                            if(j==0)

                            adapter.add(new SellerProductListModel(object.getString("ProID"),object.getString("ProName"),object.getString("ProDescription")
                                    ,object.getString("ProImageUrl"),jobject.getString("name"),object.getString("ProPrice"),true, false),1);
                            else if(j== jarray.length()-1)
                                adapter.add(new SellerProductListModel(object.getString("ProID"),object.getString("ProName"),object.getString("ProDescription")
                                        ,object.getString("ProImageUrl"),jobject.getString("name"),object.getString("ProPrice"),false, true),1);


                            else
                                adapter.add(new SellerProductListModel(object.getString("ProID"),object.getString("ProName"),object.getString("ProDescription")
                                        ,object.getString("ProImageUrl"),jobject.getString("name"),object.getString("ProPrice"),true, false),1);


                        }
                    }
                }
                if (dialog != null ){
                    dialog.cancel();
                }
            }

            catch (Exception e) {
                e.printStackTrace();
                if (dialog !=null) {
                    dialog.cancel();
                }
            }
        }


    }

    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
