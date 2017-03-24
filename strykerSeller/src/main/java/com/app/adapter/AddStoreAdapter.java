package com.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.app.model.StoreModel;
import com.app.strykerseller.R;




/**
 * Created by Inflectica on 10/15/2015.
 */
public class AddStoreAdapter extends ArrayAdapter<StoreModel> {

    Context context;
    LayoutInflater mLayoutInflater;
    ArrayList<StoreModel> storeList;

    public AddStoreAdapter(Context context, int resource, List objects) {
        super(context, resource);
    }

    
    public View getView(int position, View convertView, ViewGroup container) {

        if (convertView == null) {

            mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate(R.layout.seller_row_store_list, container, false);

            ImageView imgv = (ImageView) convertView.findViewById(R.id.img_store);
            TextView storeName = (TextView) convertView.findViewById(R.id.txt_store_name);

            storeName.setText(storeList.get(position).getBusName());
            container.addView(convertView);

        }
        notifyDataSetChanged();
        return convertView;
    }
}
