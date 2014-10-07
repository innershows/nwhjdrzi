package com.tuxy.neihan.adapter;

import java.util.List;

import com.tuxy.neihan.bean.TextEntity;
import com.tuxy.neihan.fragments.DetailContentFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailPagerAdapter extends FragmentPagerAdapter {
	private List<TextEntity> entities;

	public DetailPagerAdapter(FragmentManager fm, List<TextEntity> entities) {
		super(fm);
		this.entities = entities;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		DetailContentFragment fragment = new DetailContentFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("entity", entities.get(arg0));

		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

}
