package com.app.strykerseller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import com.app.adapter.AddStoreAdapter;
import com.app.model.StoreModel;



/**
 * Created by Inflectica on 10/15/2015.
 */
public class StoresActivity extends Activity {

    Context context;
    ImageView addStore;
    ListView listView;
    ArrayList<StoreModel> storelist = new ArrayList<>();
    AddStoreAdapter adapter;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.seller_activity_home_stores);

        init();

        AddStoreAdapter adapter = new AddStoreAdapter(context, R.layout.seller_row_store_list, storelist);
        listView.setAdapter(adapter);


        addStore.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View v) {

                Intent in = new Intent(StoresActivity.this, AddStoreActivity.class);
                startActivityForResult(in, 90);
            }
        });

    }

    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 90 && resultCode == 100) {

            storelist.add(new StoreModel(data.getStringExtra("storeCode"), data.getStringExtra("storeName")));
            adapter.notifyDataSetChanged();
        }
    }

    public void  init()
    {
        addStore = (ImageView)findViewById(R.id.img_add2);
        listView = (ListView)findViewById(R.id.lv_store_details);
    }
}
