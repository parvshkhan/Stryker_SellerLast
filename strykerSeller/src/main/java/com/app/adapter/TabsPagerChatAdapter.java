package com.app.adapter;

import com.app.fragment.ChatFragment;
import com.app.fragment.ManageUsersFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class TabsPagerChatAdapter extends FragmentPagerAdapter {

	public TabsPagerChatAdapter(FragmentManager fm) {
		super(fm);
	}

	
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Account Setting fragment activity
			return new ManageUsersFragment();
		case 1:
			// General Setting fragment activity
			return new ChatFragment();
		}
		return null;
	}

	
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
