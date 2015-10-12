package com.example.view;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class PagerAdapter extends FragmentPagerAdapter{
	
	private ArrayList<Fragment> framents;
	
	public PagerAdapter(FragmentManager fm, ArrayList<Fragment> framents) {
		super(fm);
		this.framents = framents;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return framents.size();
	}
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return framents.get(arg0);
	}
}
