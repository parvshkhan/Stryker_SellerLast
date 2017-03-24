package com.app.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.app.model.PendingModel;
import com.app.strykerseller.R;
import com.app.utills.AppUtils;
import com.app.utills.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by Inflac on 16-10-2015.
 */
public class Hot_ProductList_Adapter extends ArrayAdapter<PendingModel> {

    ArrayList<PendingModel> list;// = new ArrayList<>();
    Context context;
    ProgressDialog dialog;
    int number;

    public Hot_ProductList_Adapter(Context context, int resource, ArrayList<PendingModel> list) {
        super(context, resource, list);

        this.context = context;
        this.list = list;

    }

    class Dataholder {

        TextView txt_productName,txt_desc,txt_price,txt_initial,txt_header_add;
        ImageView imgRemove, imgSelec,imgProduct;
        View view;
    }

    
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Dataholder holder = new Dataholder();

        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =inflater.inflate(R.layout.seller_row_hot_product, parent, false);

            holder.txt_header_add = (TextView) row.findViewById(R.id.txt_header_add);
            holder.txt_productName = (TextView)row.findViewById(R.id.txt_product_name);
            holder.txt_desc= (TextView)row.findViewById(R.id.txt_desc);
            holder.txt_price = (TextView)row.findViewById(R.id.txt_price);
            holder.txt_initial = (TextView)row.findViewById(R.id.txt_initial);
            holder.imgRemove = (ImageView)row.findViewById(R.id.img_cancel);
           // holder.imgSelect = (ImageView)row.findViewById(R.id.img_select);
            holder.imgProduct = (ImageView) row.findViewById(R.id.img_prod_img);
            row.setTag(holder);

        }
        else {

            holder = (Dataholder)row.getTag();
        }

         final PendingModel model = (PendingModel)this.getItem(position);

        if (model.getIsShowHeader()) {
            holder.txt_header_add.setVisibility(View.VISIBLE);
            holder.txt_header_add.setText(model.getProCategory());

        }else {
            holder.txt_header_add.setVisibility(View.GONE);
        }

        holder.txt_productName.setText(model.getProName());
        holder.txt_desc.setText(model.getProDescription());
        holder.txt_price.setText(""+model.getTotalCost());
        holder.txt_initial.setText("" + (model.getProName()).toUpperCase().charAt(0)+"");

        /**add image path validation here*/
    	
		if(!model.getProductImageUrl().equalsIgnoreCase("")){
			try {				
				holder.txt_initial.setVisibility(View.GONE);
				holder.imgProduct.setVisibility(View.VISIBLE);
				
				Picasso.with(context).load(context.getString(R.string.image_base_url) + model.getProductImageUrl())
				.placeholder(R.drawable.new_placeholder)				
				.transform(new CircleTransform())
				.into(holder.imgProduct);				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{			
            holder.txt_initial.setText("" + (model.getProName()).toUpperCase().charAt(0)+"");

            holder.txt_initial.setVisibility(View.VISIBLE);
			holder.imgProduct.setVisibility(View.GONE);
		}

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Successfully Delete", Toast.LENGTH_SHORT).show();
            	
            	/**remove product task*/		
        		//final String orderId = arlistOrderPending.get(posi).getOrderId();
        		  AlertDialog.Builder builder = new AlertDialog.Builder(context);
        		    builder.setMessage("Are you sure want to remove the product from Hot List?")
        		            .setCancelable(false)
        		            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
        		                public void onClick(DialogInterface dialog1, int id) {
        		                	/**call asynTask */        		                
        		                    if (AppUtils.isNetworkAvailable(context)) {
        		                        dialog = ProgressDialog.show(context, "", "please wait...");
        		                        DeleteProduct deleteTask = new DeleteProduct(position);
        		                        deleteTask.execute(new String[]{model.getProductId()});
        		                       // number = position;
        		                    } else {
        		                        AppUtils.showCustomToast(context, "Please check your in internet connection");

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

        return row;
    }

    private class DeleteProduct extends AsyncTask<String, Void, String> {

        int position ;
        public DeleteProduct(int position){

            this.position = position;
        }

        
        protected void onPreExecute() {
            super.onPreExecute();
        }

        
        protected String doInBackground(String... urls) {
            String response="";

            try {
                JsonCall jsonClass = new JsonCall();
                response = jsonClass.deleteProduct(urls[0], context.getString(R.string.deleteproduct_url));
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
                    int status = commandResult.getInt("success");

                    if (status == 1)  {
                        Toast.makeText(context, "Hot product removed successfully. ", Toast.LENGTH_SHORT).show(); 
                        
                        try {
                            if(list.get(position).getIsShowHeader()){
                                list.get(position+1).setIsShowHeader(true);       }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        list.remove(position);
                        notifyDataSetChanged();

                    }else{
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
