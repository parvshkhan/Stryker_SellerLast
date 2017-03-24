package com.app.strykerseller;

import com.app.adapter.TabsPagerChatAdapter;
import com.app.fragment.ManageUsersFragment.onSomeEventListener;
import com.app.utills.AppUtil;
import com.quickblox.sample.chat.ui.activities.BaseActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by Inflectica on 10/13/2015.
 */
public class SellerChatActivity extends BaseActivity implements onSomeEventListener {
    Context context;
    RelativeLayout tab1Users, tab2Chat;
    TextView txtTabUsers,txtTab2Chat, txtCount, storeName;
    ImageView imgBack;
    private Double mLat = 0.0, mLong = 0.0;
    private ViewPager viewPager;
    private TabsPagerChatAdapter mAdapter;
    ProgressDialog dialog;
    String store_id = "",store_name = "";
    public static int userCount = 0;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_activity_chat);
        context = this;

        Bundle b = getIntent().getExtras();

        if(b != null){
            if (b.containsKey("store_id")){
                store_id = b.getString("store_id");
                Log.e("StoreId in Activity", store_id);
            }            
            if(b.containsKey("store_name")){
            	
            	store_name = b.getString("store_name");
            	 Log.e("StoreName in Activity", store_name);
            }
            
            ((TextView)findViewById(R.id.txt_store_name)).setText(store_name);
            
            
			/*if(b.containsKey("from")){
				from = b.getString("from");
			}*/
        }

      //  AppUtil.setStoreId(context,store_id);


    }

    
    protected void onStart() {
        super.onStart();
       /* init();
        setListener();*/
        initView();
        setListener();
        
    }
    
    
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	 init();
         
    }
    
    
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	AppUtil.onKeyBoardDown(context);
    }

    public String StoreId() {
        return store_id;
    }
    
    public void initView(){
    	  viewPager = (ViewPager)findViewById(R.id.pager_chat);
          tab1Users = (RelativeLayout) findViewById(R.id.rl_manage_users);
          tab2Chat = (RelativeLayout)findViewById(R.id.rl_chat);
          txtTabUsers = (TextView)findViewById(R.id.txt_manage_user);
          txtTab2Chat = (TextView)findViewById(R.id.txt_chat);

          txtCount = (TextView)findViewById(R.id.txt_count);

          imgBack = (ImageView)findViewById(R.id.img_back);
          
          storeName = (TextView)findViewById(R.id.txt_store_name);

    }

    public void init()
    {

      
        mAdapter = new TabsPagerChatAdapter(this.getSupportFragmentManager());
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
                tab1Users.setBackgroundResource(R.drawable.select2);
                tab2Chat.setBackgroundResource(R.drawable.select1);
                break;
            case 1:
                tab1Users.setBackgroundResource(R.drawable.select1);
                tab2Chat.setBackgroundResource(R.drawable.select2);
                break;
        }
    }

    private void setListener() {

        tab1Users.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(0);
            }
        });
        tab2Chat.setOnClickListener(new View.OnClickListener() {

            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(1);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {
            	AppUtil.onKeyBoardDown(context);
                finish();
            }
        });
    }

	
	public void someEvent(String s) {
		// TODO Auto-generated method stub
		txtCount.setText(s);
	}

}
