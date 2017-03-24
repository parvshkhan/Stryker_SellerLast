package com.app.adapter;

import com.app.fragment.ProductListFragment;
import com.app.fragment.PushNotificationFragment;
import com.app.fragment.ReportsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerProductListAdapter extends FragmentPagerAdapter {

	public TabsPagerProductListAdapter(FragmentManager fm) {
		super(fm);
	}

	
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// store fragment fragment activity
			return new PushNotificationFragment();
		case 1:
			// product list fragment activity
			return new ProductListFragment();
		
		case 2:
			//
			return new ReportsFragment();
		}
		return null;
	}

	
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
