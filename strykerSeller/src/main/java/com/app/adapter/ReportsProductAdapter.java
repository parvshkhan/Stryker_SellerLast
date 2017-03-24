package com.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.app.model.ReportProductModel;
import com.app.strykerseller.R;


/**
 * Created by Inflac on 16-10-2015.
 */
public class ReportsProductAdapter extends ArrayAdapter {

    Context context;
    int layoutId;
    List header = new ArrayList();
    List product_list = new ArrayList();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public ReportsProductAdapter(Context context, int resource, List<ReportProductModel> orderlist) {
        super(context, resource);
        this.context = context;
        layoutId = resource;
        Log.e("ListHistory","" +orderlist);
    }

    LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    public ReportsProductAdapter(Context context, int resource) {
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

        TextView txtProName, txtPrice, txtUnits, txtUserName;
        TextView header;

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

                    row= inflater.inflate(R.layout.row_pending,parent, false);
                    handler.txtUserName = (TextView) row.findViewById(R.id.txt_name);
                    handler.txtProName = (TextView) row.findViewById(R.id.txt_name);
                    handler.txtPrice = (TextView) row.findViewById(R.id.txt_price_2);
                    handler.txtUnits = (TextView) row.findViewById(R.id.txt_unit_count_2);
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

                ReportProductModel model;
                model = (ReportProductModel) this.getItem(position);

                handler.txtProName.setText(model.getProName());
                handler.txtUserName.setText(model.getUserName());
                handler.txtPrice.setText(model.getTotalPrice());
                handler.txtUnits.setText(model.getTotalQuantity());
               // handler.txt_initial.setText(""+(pending_model.getProName()).toUpperCase().charAt(0)+"");

                break;

            case TYPE_SEPARATOR :

                handler.header.setText((String)product_list.get(position));

                break;
        }

        return row;
    }

}
