package com.app.strykerseller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.TabsPagerProductListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class ProductListActivity extends FragmentActivity{

	Context context ;
	private ViewPager viewPager;
	private TabsPagerProductListAdapter mAdapter;
	RelativeLayout tab1Push, tab2ProductList, tab3Reports;
	private Double mLat = 0.0, mLong = 0.0;
	//TextView txtTabstore,txtTabProductList, txtChatList;
	ImageView imgSetting, imgSearch, imgAddStore;
	ProgressDialog dialog;
	TextView txtBack;
	String store_id = "", from = "";
	String ProductListData = "",storeName = "";


	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seller_activity_product_list);
		context = this;

		Bundle b = getIntent().getExtras();

		if(b != null){
			if (b.containsKey("store_id")){
				store_id = b.getString("store_id");
				//Log.e("StoreId in Activity",store_id);
			}
			if(b.containsKey("store")){
				storeName  = b.getString("store");
			}
		}
	}

	
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		init();
		setListener();

	}

	public String StoreId() {
		return store_id;
	}


	public String productListData() {
		return ProductListData;
	}

	public void init(){

		txtBack = (TextView)findViewById(R.id.txt_back_storename);

		tab1Push = (RelativeLayout) findViewById(R.id.rl_pushnotification);
		tab2ProductList = (RelativeLayout) findViewById(R.id.rl_productlist);
		tab3Reports = (RelativeLayout) findViewById(R.id.rl_reports);

		viewPager = (ViewPager) findViewById(R.id.pager_home_item);

		imgSearch = (ImageView) findViewById(R.id.img_search);
		imgAddStore = (ImageView) findViewById(R.id.img_add1);

		mAdapter = new TabsPagerProductListAdapter(this.getSupportFragmentManager());
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

		if(from.equalsIgnoreCase("chat")){
			viewPager.setCurrentItem(2);
			setTabSelection(2);
		}else{
		viewPager.setCurrentItem(1);
		setTabSelection(1);
		}
		
		txtBack.setText(storeName);
	}

	private void setTabSelection(int posi) {
		switch (posi) {
		case 0:
			tab1Push.setBackgroundResource(R.drawable.select_03);
			tab2ProductList.setBackgroundResource(R.drawable.deselect);
			tab3Reports.setBackgroundResource(R.drawable.deselect);
			break;
		case 1:
			tab1Push.setBackgroundResource(R.drawable.deselect);
			tab2ProductList.setBackgroundResource(R.drawable.select_03);
			tab3Reports.setBackgroundResource(R.drawable.deselect);
			break;

		case 2:

			tab1Push.setBackgroundResource(R.drawable.deselect);
			tab2ProductList.setBackgroundResource(R.drawable.deselect);
			tab3Reports.setBackgroundResource(R.drawable.select_03);
			break;

		}
	}

	public void setListener(){
		tab1Push.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});
		tab2ProductList.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});
		tab3Reports.setOnClickListener(new OnClickListener() {

			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(2);
			}
		});

		

/*		imgSearch.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ProductListActivity.this,SearchStoreToAddActivity.class);
				startActivity(i);
			}
		});*/


		txtBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
	}

}
