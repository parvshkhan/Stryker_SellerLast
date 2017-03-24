package com.app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.app.model.OrderHistoryModel;
import com.app.strykerseller.R;


/**
 * Created by Inflac on 16-10-2015.
 */
public class OrderHistoryAdapter extends ArrayAdapter<OrderHistoryModel> {

    List<OrderHistoryModel> orderlist;
    Context context;
    int layoutId;

    public OrderHistoryAdapter(Context context, int resource, List<OrderHistoryModel> objects) {
        super(context, resource, objects);
        this.context = context;
        orderlist = objects;
        layoutId = resource;
        Log.e("ListHistory","" +orderlist);

    }

    class Dataholder {

        TextView txtDate, txtPrice, txtUnits;
        int posi;
    }

    
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        final OrderHistoryModel hgetter = getItem(position);

        if (row == null) {

            row = inflater.inflate(layoutId, null);
            Dataholder holder = new Dataholder();

            holder.txtDate = (TextView) row.findViewById(R.id.txt_date_header);
            holder.txtPrice = (TextView) row.findViewById(R.id.txt_price_2);
            holder.txtUnits = (TextView) row.findViewById(R.id.txt_unit_count_2);

            row.setTag(holder);
        }


        final Dataholder holder = (Dataholder) row.getTag();

        holder.posi = position;

        holder.txtDate.setText(hgetter.getOrderDate());
        holder.txtPrice.setText(hgetter.getTotalPrice());
        holder.txtUnits.setText(hgetter.getTotalUnit());

      /*  holder.txtDate.setText(orderlist.get(position).getOrderDate());
        holder.txtPrice.setText(orderlist.get(position).getTotalPrice());
        holder.txtUnits.setText(orderlist.get(position).getTotalUnit());*/

        return row;
    }
}
