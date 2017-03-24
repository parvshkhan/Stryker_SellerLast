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

import com.app.model.PendingModel;
import com.app.strykerseller.R;


/**
 * Created by Inflac on 13-10-2015.
 */
public class HotProductListAdapter extends ArrayAdapter {

    List header = new ArrayList();
    List product_list = new ArrayList();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    public HotProductListAdapter(Context context, int resource) {
        super(context, resource);

    }

    
    public void add(Object object) {
        super.add(object);
        product_list.add(object);
    }

    public int getItemViewType(int position) {
        return header.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return product_list.size();
    }

    public Object getItem(int position) {

        return this.product_list.get(position);
    }

    class Datahandler {

        TextView txt_productName,txt_desc,txt_price,txt_initial;
        TextView header;
        ImageView imgRemove, imgSelect;

    }

    public void addSectionHeaderItem(String item) {

        product_list.add(item);
        header.add(product_list.size()-1);
        notifyDataSetChanged();
    }

    
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Datahandler handler = new Datahandler();
        int rowType = getItemViewType(position);


        if(convertView == null) {

            switch (rowType) {

                case TYPE_ITEM :

                    row= inflater.inflate(R.layout.seller_row_hot_product,parent, false);
                    handler.txt_productName = (TextView)row.findViewById(R.id.txt_product_name);
                    handler.txt_desc= (TextView)row.findViewById(R.id.txt_desc);
                    handler.txt_price = (TextView)row.findViewById(R.id.txt_price);
                    handler.txt_initial = (TextView)row.findViewById(R.id.txt_initial);
                    handler.imgRemove = (ImageView)row.findViewById(R.id.img_cancel);

                    break;

                case TYPE_SEPARATOR :

                    row = inflater.inflate(R.layout.custom_pending_header,parent,false);
                    handler.header = (TextView)row.findViewById(R.id.txt_header_list);
                    break;
            }

            row.setTag(handler);
        }
        else {

            handler = (Datahandler)row.getTag();
        }

        switch (rowType) {

            case TYPE_ITEM :

            PendingModel pending_model;
            pending_model = (PendingModel)this.getItem(position);

            handler.txt_productName.setText(pending_model.getProName());
            handler.txt_desc.setText(pending_model.getProDescription());
            handler.txt_price.setText(""+pending_model.getTotalCost());
            handler.txt_initial.setText(""+(pending_model.getProName()).toUpperCase().charAt(0)+"");

                break;

            case TYPE_SEPARATOR :

                handler.header.setText((String)product_list.get(position));

                break;
        }

        return row;
    }
}
