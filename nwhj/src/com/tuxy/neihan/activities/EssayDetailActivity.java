package com.tuxy.neihan.activities;

import com.tuxy.neihan.R;
import com.tuxy.neihan.adapter.DetailPagerAdapter;
import com.tuxy.neihan.bean.DataStore;
import com.tuxy.neihan.bean.TextEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class EssayDetailActivity extends FragmentActivity {
	private ViewPager pager;
	private DetailPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_essay_detail);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int category = 1;
		int currentEssayPosition = 0;
		// 默认传过来有种类和位置
		if (extras != null) {
			category = extras.getInt("category", 1);
			currentEssayPosition = extras.getInt("currentEssayPosition", 0);
		}
		System.out.println("--currentEssayPosition-->>" + currentEssayPosition);
		adapter = new DetailPagerAdapter(getSupportFragmentManager(),
				category == 1 ? DataStore.getInstance().getTextEntities()
						: DataStore.getInstance().getImageEntities());

		pager = (ViewPager) findViewById(R.id.detail_pager_content);

		// 设置FragmentPagerAdapter
		pager.setAdapter(adapter);

		// 设置位置
		if (currentEssayPosition > 0) {
			pager.setCurrentItem(currentEssayPosition);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.essay_detail, menu);
		return true;
	}

}
