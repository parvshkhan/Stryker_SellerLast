package com.app.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fragment.OrderHomeFragment;
import com.app.listener.CancelOrderListener;
import com.app.model.CircleTransform;
import com.app.model.OrderHomeModel;
import com.app.strykerseller.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;



public class OrderHomeListAdapter extends ArrayAdapter<OrderHomeModel> {

	int layoutId;
	Context con;
	ProgressDialog dialog;
	List<OrderHomeModel> listStore;
	CancelOrderListener listener;
	int tabPosition;
	public OrderHomeListAdapter(Context context, int resource,
								List<OrderHomeModel> objects, OrderHomeFragment frag, int tabPosition) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		con = context;
		layoutId = resource;
		listStore = objects;
		listener = (CancelOrderListener) frag;
		this.tabPosition = tabPosition;
	}

	class ViewHolder {
		TextView userName, orderID, txtDate;
		ImageView userImage,imgCancelOrder;
		int posi;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View row = convertView;
		final OrderHomeModel hgetter = getItem(position);

		if (row == null) {
			row = inflater.inflate(layoutId, null);
			ViewHolder holder = new ViewHolder();

			holder.userName = (TextView) row.findViewById(R.id.txt_name);
			holder.orderID = (TextView) row.findViewById(R.id.txt_order_id);
			holder.txtDate = (TextView) row.findViewById(R.id.txt_time);
			holder.userImage = (ImageView)row.findViewById(R.id.img_user);
			holder.imgCancelOrder = (ImageView)row.findViewById(R.id.img_cancel);

			row.setTag(holder);

		}
		final ViewHolder holder = (ViewHolder) row.getTag();

		holder.posi = position;

		if(tabPosition == 0)
		{
			holder.imgCancelOrder.setVisibility(View.VISIBLE);
			holder.userName.setText(hgetter.getUserName());
			holder.orderID.setText("Order Number" + " " +hgetter.getOrderId());
			holder.txtDate.setText("Placed On" + " " +hgetter.getOrderTime());
			//holder.orderID.setText(hgetter.getOrderId());

			if(!hgetter.getUserImage().equalsIgnoreCase("")){
				Picasso.with(con).load(hgetter.getUserImage())
						.placeholder(R.drawable.new_placeholder)
						.transform(new CircleTransform())
						.into(holder.userImage);
			}

			holder.imgCancelOrder.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					listener.onCancelOrderClick(holder.posi,hgetter.getOrderId());
				}
			});

		}else{

			holder.imgCancelOrder.setVisibility(View.INVISIBLE);
			holder.userName.setText(hgetter.getUserName());
			holder.orderID.setText("Order Number" + " " +hgetter.getOrderId());
			holder.txtDate.setText("Placed On" + " " +hgetter.getOrderTime());
			//holder.orderID.setText(hgetter.getOrderId());

			if(!hgetter.getUserImage().equalsIgnoreCase("")){
				Picasso.with(con).load(hgetter.getUserImage())
						.placeholder(R.drawable.new_placeholder)
						.transform(new CircleTransform())
						.into(holder.userImage);
			}
		}

		return row;
	}

}
