package com.app.adapter;

import com.app.fragment.ChatHomeFragment;
import com.app.fragment.OrderHomeFragment;
import com.app.fragment.StoresHomeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;




public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Account Setting fragment activity
			return new OrderHomeFragment();
		case 1:
			// General Setting fragment activity
			return new StoresHomeFragment();

		case 2:
		// Chat fragment activity
		return new ChatHomeFragment();
	}
		return null;
	}

	
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
