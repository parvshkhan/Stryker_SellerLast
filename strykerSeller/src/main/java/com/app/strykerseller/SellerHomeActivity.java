package com.app.strykerseller;

import com.app.adapter.TabsPagerAdapter;
import com.app.fragment.ManageUsersFragment.onSomeEventListener;
import com.app.fragment.StoresHomeFragment.onUserCountListener;
import com.app.utills.AppUtil;
import com.quickblox.sample.chat.ui.activities.BaseActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SellerHomeActivity extends BaseActivity implements onUserCountListener{
	Context context;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	RelativeLayout tab1Order, tab2Store, tab3Chat;
	private Double mLat = 0.0, mLong = 0.0;
	TextView txtTaborder,txtTabStore, txtTabChat;
	ImageView imgSetting, imgSearch, imgAddStore;
	ProgressDialog dialog;
	// Request code for READ_CONTACTS. It can be any number > 0.
	private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller_activity_home_stores_1);
		context = this;

	}


	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		/*init();
		setListener();	*/
	}


	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		init();
		setListener();
	}


	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			AppUtil.onKeyBoardDown(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() {

		viewPager = (ViewPager) findViewById(R.id.pager_home_item);

		tab1Order = (RelativeLayout) findViewById(R.id.rl_orders);
		tab2Store = (RelativeLayout) findViewById(R.id.rl_stores);
		tab3Chat  = (RelativeLayout) findViewById(R.id.rl_chat);
		txtTaborder = (TextView) findViewById(R.id.txt_order);
		txtTabStore = (TextView) findViewById(R.id.txt_store);
		txtTabChat  = (TextView) findViewById(R.id.txt_chat);

		imgSetting = (ImageView) findViewById(R.id.img_settings);
		imgSearch = (ImageView) findViewById(R.id.img_search);
		imgAddStore = (ImageView) findViewById(R.id.img_add1);

		mAdapter = new TabsPagerAdapter(this.getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				setTabSelection(position);
			}


			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}


			public void onPageScrollStateChanged(int arg0) {
			}
		});

		viewPager.setCurrentItem(1);
		setTabSelection(1);
	}

	private void setTabSelection(int posi) {
		switch (posi) {
			case 0:
				tab1Order.setBackgroundResource(R.drawable.select2);
				tab2Store.setBackgroundResource(R.drawable.select1);
				tab3Chat.setBackgroundResource(R.drawable.select1);
				break;
			case 1:
				tab1Order.setBackgroundResource(R.drawable.select1);
				tab2Store.setBackgroundResource(R.drawable.select2);
				tab3Chat.setBackgroundResource(R.drawable.select1);
				break;
			case 2:
				tab1Order.setBackgroundResource(R.drawable.select1);
				tab2Store.setBackgroundResource(R.drawable.select1);
				tab3Chat.setBackgroundResource(R.drawable.select2);
		}
	}

	private void setListener() {

		tab1Order.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});
		tab2Store.setOnClickListener(new OnClickListener() {


			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});
		tab3Chat.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				viewPager.setCurrentItem(2);

			}
		});

		imgAddStore.setOnClickListener(new OnClickListener() {


			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SellerHomeActivity.this,AddStoreActivity.class);
				startActivity(i);
			}
		});



		imgSetting.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent in = new Intent(SellerHomeActivity.this,SettingActivity.class);
				startActivity(in);
			}
		});
		((RelativeLayout) findViewById(R.id.rl_notif1)).setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(SellerHomeActivity.this,NotificationActivity.class);
				startActivity(in);
			}
		});
	}


	public void userCount(String s) {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.txt_notif)).setText(s);



		if(s.equalsIgnoreCase("")){
			((RelativeLayout) findViewById(R.id.rl_notif)).setVisibility(View.INVISIBLE);
		}else{
			((RelativeLayout) findViewById(R.id.rl_notif)).setVisibility(View.VISIBLE);
		}
	}


	public void notifCount(String s) {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.txt_notif1)).setText(s);

		if(s.equalsIgnoreCase("0") || s.equalsIgnoreCase("")){
			((TextView) findViewById(R.id.txt_notif1)).setVisibility(View.INVISIBLE);
		}else{
			((TextView) findViewById(R.id.txt_notif1)).setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

       /* int RQS_PICK_CONTACT = 1;

        if(requestCode == RQS_PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //contactName.setText(name);
               // contactNumber.setText(number);
                //contactEmail.setText(email);
            }
        }*/
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,
										   int[] grantResults) {
		if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission is granted
				//showContacts();
			} else {
				Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
			}
		}
	}



}
