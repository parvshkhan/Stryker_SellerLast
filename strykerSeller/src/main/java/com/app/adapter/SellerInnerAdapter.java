package com.app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import com.app.jsoncall.JsonCall;
import com.app.model.ModelSellerproductList;
import com.app.model.SellerProductListModel;
import com.app.strykerseller.R;
import com.app.utills.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inflac on 16-10-2015.
 */
public class SellerInnerAdapter extends ArrayAdapter {
    List<ModelSellerproductList> templist = new ArrayList<ModelSellerproductList>();
    Context context;
    int number;
    ProgressDialog dialog;

    public SellerInnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;

    }

    class Dataholder {

        TextView txt_product_name,txt_product_desc,txt_price_product,txt_icon,txt_header_add;
        ImageView img_cancel;
        View view;
    }

    
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Dataholder holder = new Dataholder();

        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row =inflater.inflate(R.layout.row_add_product_list, parent, false);
            holder.txt_header_add = (TextView)row.findViewById(R.id.txt_header_add);
            holder.txt_product_name = (TextView)row.findViewById(R.id.txt_product_name);
            holder.txt_product_desc = (TextView)row.findViewById(R.id.txt_product_desc);
            holder.txt_price_product = (TextView)row.findViewById(R.id.txt_price_product);
            holder.txt_icon = (TextView)row.findViewById(R.id.txt_icon);
            holder.img_cancel = (ImageView)row.findViewById(R.id.img_cancel);
            holder.view = (View)row.findViewById(R.id.view_base);

            row.setTag(holder);

        }
        else {
            holder = (Dataholder)row.getTag();
        }

         final SellerProductListModel model = (SellerProductListModel)this.getItem(position);

        if(model.getIsShowHeader()&& model.getIsLastItem()) {
            holder.view.setVisibility(View.GONE);

        }
        else if (model.getIsShowHeader()) {
            holder.txt_header_add.setVisibility(View.VISIBLE);
            holder.txt_header_add.setText(model.getCategoryname());

        }else if(model.getIsLastItem()) {
            holder.view.setVisibility(View.GONE);
        }
        holder.txt_product_name.setText(model.getProductname());
        holder.txt_product_desc.setText(model.getProduct_desc());
        holder.txt_price_product.setText(model.getPrice());
        holder.txt_icon.setText(""+model.getProductname().toUpperCase().charAt(0)+"");

        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Successfully Delete", Toast.LENGTH_SHORT).show();
                if (AppUtils.isNetworkAvailable(context)) {
                    dialog = ProgressDialog.show(context,"", "please wait...");
                    DeleteProduct delteDeleteProduct = new DeleteProduct();
                    delteDeleteProduct.execute(new String[] {model.getProductId()});
                    number = position;
                } else {
                    AppUtils.showCustomToast(context, "Please check your in internet connection");

                }
                notifyDataSetChanged();

            }
        });

        return row;
    }
    
    private class DeleteProduct extends AsyncTask<String, Void, String> {


        
        protected void onPreExecute() {
            super.onPreExecute();
        }

        
        protected String doInBackground(String... urls) {
            String response="";

            try {
                JsonCall jsonClass = new JsonCall();
                response = jsonClass.deleteProduct(urls[0],context.getString(R.string.deleteproduct_url));
                Log.e("DeleteProduct",""+response);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        
        protected void onPostExecute(String result) {
            JSONObject jsonObject;

            try {
                if (result !=null) {
                    jsonObject = new JSONObject(result);
                    JSONObject commandResult = jsonObject.getJSONObject("commandResult");
                    boolean status = commandResult.getBoolean("success");
                    if (status)  {
                        Toast.makeText(context, commandResult.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
                if (dialog != null ){
                    dialog.cancel();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                if (dialog != null ){
                    dialog.cancel();
                }
            }
        }
    }
}
