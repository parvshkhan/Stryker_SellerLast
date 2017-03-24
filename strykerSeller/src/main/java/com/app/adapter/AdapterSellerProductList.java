package com.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.app.strykerseller.R;
import com.app.strykerseller.SellerAddProductActivity;
import com.app.utills.AppUtil;


/**
 * Created by Inflac on 15-10-2015.
 */
public class AdapterSellerProductList extends ArrayAdapter {

    List hotproductlist = new ArrayList();
    List addproductlist= new ArrayList();
    Context context;


    public AdapterSellerProductList(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;

    }

    public void add(Object object, int selector) {
        super.add(object);
        if (selector == 0) {

            hotproductlist.add(object);
        }
        else if (selector==1){
            addproductlist.add(object);
        }

    }

    
    public long getItemId(int position) {
        return position;
    }

    
    public int getCount() {
        return 2;
    }


    class DataHandler {
        ListView listView;
        ImageView img_add_product,img_add2,img_onoff;
        TextView txt_preview;

    }



    
    public int getViewTypeCount() {
        return 2;
    }

    
    public int getItemViewType(int position) {

        if(position==0) {
            return 0;
        }
        else if (position==1)
            return 1;

        else
            return 0;


        }



    
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DataHandler handler = new DataHandler();
        LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        int type = getItemViewType(position);

        if (convertView == null) {



            switch(type) {

                case 0:

                    row = inflater.inflate(R.layout.hotaddlist, parent, false);
                    handler.listView = (ListView)row.findViewById(R.id.listview_hotproduct);
                    View header = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.seller_header_productlist, null, false);
                    handler.listView.addHeaderView(header);
                    handler.img_add_product = (ImageView)row.findViewById(R.id.img_add_product);
                    handler.txt_preview = (TextView)row.findViewById(R.id.txt_preview);
                    handler.img_onoff = (ImageView)row.findViewById(R.id.img_onoff);
                    Log.e("hotproductlistsize", "" + hotproductlist.size());
                    break;

                case 1:
                    row = inflater.inflate(R.layout.hotaddlist, parent, false);
                    handler.listView =(ListView)row.findViewById(R.id.listview_addproduct);
                    View add_header = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_add_product, null,false);
                    handler.listView.addHeaderView(add_header);
                    handler.img_add2 = (ImageView)row.findViewById(R.id.img_add2);
                    Log.e("addproductlistsize", "" + addproductlist.size());

                    break;

            }

            row.setTag(handler);
        }
        else {

          handler = (DataHandler)row.getTag();
        }

        switch (type) {

            case 0:
                SellerInnerAdapter innerAdapter = new SellerInnerAdapter(getContext(),R.layout.row_add_product_list, hotproductlist);
                handler.listView.setAdapter(innerAdapter);
                Log.e("sethot", "" + hotproductlist.size());
                handler.listView.setVisibility(View.VISIBLE);
    //                AppUtil.setListViewHeightBasedOnChildren(handler.listView);
                handler.img_add_product.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Add New Hot Product", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context,SellerAddProductActivity.class);
                        i.putExtra("is_hot","1");
                        context.startActivity(i);


                    }
                });
                handler.txt_preview.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"PreView", Toast.LENGTH_SHORT).show();
                    }
                });

                handler.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "HotSelected" + position, Toast.LENGTH_SHORT).show();
                    }
                });

                handler.img_onoff.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Publish", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case 1:

                SellerInnerAdapter innerAdapter1 = new SellerInnerAdapter(getContext(), R.layout.row_add_product_list, addproductlist);


                handler.listView.setAdapter(innerAdapter1);

                Log.e("setadd", "" + addproductlist.size());
                handler.listView.setVisibility(View.VISIBLE);
                AppUtil.setListViewHeightBasedOnChildren(handler.listView);

                handler.img_add2.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "ADD Products", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, SellerAddProductActivity.class);
                        i.putExtra("is_hot","0");
                        context.startActivity(i);
                    }
                });


                handler.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "Add Selected "+position, Toast.LENGTH_SHORT).show();
                    }
                });

                break;

        }

        return row;
    }
}
