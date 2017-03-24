package com.app.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.model.ReportProductModel;
import com.app.strykerseller.R;
import com.squareup.picasso.Picasso;

import java.util.List;




public class ReportsAdapter extends ArrayAdapter<ReportProductModel> {

	int layoutId;
	Context con;
	List<ReportProductModel> listStore;
	int tab;
	//DeleteStoreListener listener;
	public ReportsAdapter(Context context, int resource,
						  List<ReportProductModel> objects, int tabPosition) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		con = context;
		layoutId = resource;
		listStore = objects;
		tab = tabPosition;
		//this.listener = (DeleteStoreListener) context;
		Log.e("ListHistory", "" + listStore);
	}

	class ViewHolder {
		TextView txtProName, txtPrice, txtUnits, txtUserName, txtInitial;
		ImageView userImage;
		int posi;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View row = convertView;
		final ReportProductModel hgetter = getItem(position);

		if (row == null) {
			row = inflater.inflate(layoutId, null);
			ViewHolder holder = new ViewHolder();

			holder.txtUserName = (TextView) row.findViewById(R.id.txt_name);
			holder.txtProName = (TextView) row.findViewById(R.id.txt_name);
			holder.txtPrice = (TextView) row.findViewById(R.id.txt_price_2);
			holder.txtUnits = (TextView) row.findViewById(R.id.txt_unit_count_2);
			holder.txtInitial = (TextView) row.findViewById(R.id.txt_initname_r);
			row.setTag(holder);

		}
		final ViewHolder holder = (ViewHolder) row.getTag();

		holder.posi = position;

		holder.txtPrice.setText(hgetter.getTotalPrice());
		holder.txtUnits.setText(hgetter.getTotalQuantity());
		if(tab == 1)
			holder.txtProName.setText(hgetter.getProName());
		else if(tab == 2)
			holder.txtUserName.setText(hgetter.getUserName());
		
		try {
			//holder.txtInitial.setText(hgetter.getUserName().charAt(0) + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//holder.orderID.setText(hgetter.getOrderId());

		/*if(!hgetter.getUserImage().equalsIgnoreCase("")){
			Picasso.with(con).load(hgetter.getUserImage())
					.placeholder(R.drawable.placeholder)
							.transform(new CircleTransform())
					.into(holder.userImage);
		}*/

		return row;
	}


}
