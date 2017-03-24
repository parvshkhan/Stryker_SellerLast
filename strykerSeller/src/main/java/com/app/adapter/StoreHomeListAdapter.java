package com.app.adapter;

import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.sax.StartElementListener;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.app.fragment.StoresHomeFragment;
import com.app.jsoncall.JsonCall;
import com.app.listener.DeleteStoreListener;
import com.app.model.StoreHomeListModel;
import com.app.strykerseller.AddStoreActivity;
import com.app.strykerseller.ProductListActivity;
import com.app.strykerseller.R;
import com.app.strykerseller.SellerChatActivity;
import com.app.utills.AppUtil;
import com.app.utills.AppUtils;
import com.quickblox.sample.chat.utils.Blur;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class StoreHomeListAdapter extends ArrayAdapter<StoreHomeListModel> {

    int layoutId;
    Context con;
    String blank = "";
    ProgressDialog dialog;
    List<StoreHomeListModel> listStore;
    Dialog uploadOptionDialog;
    DeleteStoreListener listener;
    StoresHomeFragment frag = new StoresHomeFragment();
    public StoreHomeListAdapter(Context context, int resource,
                                List<StoreHomeListModel> objects, StoresHomeFragment frag) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        con = context;
        layoutId = resource;
        listStore = objects;
        listener =  (DeleteStoreListener) frag;
    }

    class ViewHolder {

        TextView txtStoreName, txt_manage_products, txt_Chat;
      //  TextView txt_share, textView_Promote;
        ImageView imgStore, imgDelete;
        RelativeLayout rl_manage_products, rl_chat;
        int posi;


    }


    public View getView(int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) con
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        final StoreHomeListModel hgetter = getItem(position);
        if (row == null) {
            row = inflater.inflate(layoutId, null);
            ViewHolder holder = new ViewHolder();
            holder.txtStoreName = (TextView) row.findViewById(R.id.txt_store_name);
            holder.txt_manage_products = (TextView) row.findViewById(R.id.txt_manage_products);
            holder.txt_Chat = (TextView) row.findViewById(R.id.txt_chat);
            holder.imgStore = (ImageView) row.findViewById(R.id.img_store);
            holder.imgDelete = (ImageView) row.findViewById(R.id.img_delete);
            holder.rl_manage_products = (RelativeLayout) row.findViewById(R.id.rl_manage_products);
          //  holder.textView_Promote = (TextView)row.findViewById(R.id.txt_promote);
            holder.rl_chat = (RelativeLayout) row.findViewById(R.id.rl_chat);
           // holder.txt_share = (TextView) row.findViewById(R.id.txt_share);
            row.setTag(holder);

        }
        final ViewHolder holder = (ViewHolder) row.getTag();
        holder.posi = position;

        String strStore_Id = "";
        holder.txtStoreName.setText(hgetter.getStore_name());
        strStore_Id = hgetter.getSrore_id();


        //.....SHIVRAJ....//
        Transformation blurTransformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap blurred = Blur.fastblur(con, source, 10);
                source.recycle();
                return blurred;
            }

            @Override
            public String key() {
                return "blur()";
            }
        };


        final  int imageViewHeight = 500;
        final int imageViewWidth = 500;

        Picasso.with(con)
                .load(con.getString(R.string.image_base_url)+hgetter.getStore_image()) // thumbnail url goes here
                .placeholder(R.drawable.placeholder)
                .resize(imageViewWidth, imageViewHeight)
                .transform(blurTransformation)
                .into(holder.imgStore, new Callback() {
                    @Override
                    public void onSuccess() {

                        Picasso.with(con)
                                .load(con.getString(R.string.image_base_url)+hgetter.getStore_image()) // image url goes here
                                .resize(imageViewWidth, imageViewHeight)
                                .placeholder(holder.imgStore.getDrawable())
                                .into(holder.imgStore);
                        //progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                    }
                });

       /* Picasso.with(con).load(con.getString(R.string.image_base_url)+hgetter.getStore_image())
                .placeholder(R.drawable.placeholder)
                //.resize(900, 600)
                //.transform(new CircleTransform())
                .into(holder.imgStore);
		*/



	/*	if(!hgetter.getStore_image().equalsIgnoreCase("")){
			Picasso.with(con).load(hgetter.getStore_image())
			.placeholder(R.drawable.placeholder)
			//.transform(new CircleTransform())
					.into(holder.imgStore);
		}	*/

     /*   final String finalStrStore_Id = strStore_Id;
        holder.txt_share.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker&referrer="+finalStrStore_Id;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Get the Market App on Google play store\n" + appUrl);
                con.startActivity(Intent.createChooser(sharingIntent,"Share using"));
            }
        });*/
/*

        holder.textView_Promote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                // LayoutInflater inflater = getLayoutInflater();
                // View myView = LayoutInflater.from(con).inflate(R.layout.my_view, parent, false);

                View v = LayoutInflater.from(con).inflate(R.layout.dialog_update, parent, false);
                // inflater.inflate(R.layout.dialog_update, null);
                Button dialogButton = (Button) v.findViewById(R.id.dialogButtonSubmit);
                final EditText editTextMobileNo = (EditText) v.findViewById(R.id.edit_no);
               //final ImageView imgContacts = (ImageView) v.findViewById(R.id.contactList);

                final ImageView imgCross = (ImageView)v.findViewById(R.id.cross);

                //if (dialog!=null)
                //{

                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String strMobileNo = "";
                        String appUrl = "https://play.google.com/store/apps/details?id=com.app.stryker&referrer="+finalStrStore_Id;
                        strMobileNo = editTextMobileNo.getText().toString();
                        int check = strMobileNo.charAt(0);

                        if (check == '0' || check == '1' || check == '2' || check == '3' || check == '4' || check == '5' || check == '6' || strMobileNo.length() < 10) {
                            editTextMobileNo.setError("Check Mobile Number");
                            editTextMobileNo.requestFocus();
                            return;
                        }
                        else {
                            if (AppUtil.isNetworkAvailable(con)) {
                                Request_SendInvetation task = new Request_SendInvetation();
                                task.execute(new String[]{strMobileNo, appUrl});
                                //Toast.makeText(con, "Invitation Send Successully.", Toast.LENGTH_SHORT).show();
                                uploadOptionDialog.dismiss();

                            } else {
                                AppUtils.showCustomToast(con, "Please check your internet connection");

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

                 */
/*   imgContacts.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent i = new Intent();
                            i.setAction(Intent.ACTION_VIEW);
                            i.setData(android.provider.Contacts.People.CONTENT_URI);
                            con.startActivity(i);
                        }
                    });*//*

               */
/* ContentResolver resolver = con.getContentResolver();
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

                String id;
                String name;
                String number;
                String image;

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        id   = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                        if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                        {
                            Cursor pCur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
                            while (pCur.moveToNext())
                            {
                                number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                image = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                                //SingleRow s = new SingleRow(name,number,image);
                                //listItem.add(s);
                                editTextMobileNo.setText(number);
                            }
                            pCur.close();

                        }
                    }
                }
                cursor.close();
               *//*
// list.setAdapter(new PranzAdapter(this, listItem));


            builder.setView(v);

                uploadOptionDialog = builder.create();
                uploadOptionDialog.show();

            }
        });
*/






        holder.imgDelete.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                /** call delete store service*/

                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                builder.setMessage("Are you sure want to remove store?")
                        .setCancelable(false)
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /**call asynTask */
                                listener.onDeleteStoreClick(holder.posi,hgetter.getSrore_id());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                //listStore.remove(holder.posi);
                //notifyDataSetChanged();
            }
        });

        holder.rl_manage_products.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                /** switch to product list activity*/
                Intent in = new Intent(con,ProductListActivity.class);
                Log.e("StoreId in Adapter",hgetter.getSrore_id());
                in.putExtra("store_id",hgetter.getSrore_id());
                in.putExtra("store", hgetter.getStore_name());
                con.startActivity(in);
            }
        });
        holder.imgStore.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                /** switch to product list activity*/
                Intent in = new Intent(con,ProductListActivity.class);
                Log.e("StoreId in Adapter",hgetter.getSrore_id());
                in.putExtra("store_id",hgetter.getSrore_id());
                in.putExtra("store", hgetter.getStore_name());
                con.startActivity(in);
            }
        });

        holder.rl_chat.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                /** switch to chat list activity*/
                Intent i = new Intent(con, SellerChatActivity.class);
                i.putExtra("from", "chat");
                i.putExtra("store_id", hgetter.getSrore_id());
                i.putExtra("store_name", hgetter.getStore_name());
                con.startActivity(i);
            }
        });

        return row;
    }



    public class Request_SendInvetation extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {

            String response = "";

            try {

                JsonCall obj = new JsonCall();
                response = obj.promoteApp(params[0], params[1],con.getString(R.string.promote_app_url));

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

                        Toast.makeText(con, ""+message, Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(con, ""+message, Toast.LENGTH_SHORT).show();
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
