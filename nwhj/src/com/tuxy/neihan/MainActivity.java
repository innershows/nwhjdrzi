package com.tuxy.neihan;

import java.util.ArrayList;
import java.util.List;

import com.tuxy.neihan.fragments.HoDsFragment;
import com.tuxy.neihan.fragments.ImageListFragment;
import com.tuxy.neihan.fragments.MineFragment;
import com.tuxy.neihan.fragments.ReviewFragment;
import com.tuxy.neihan.fragments.TextListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.main_tab_bar);
		radioGroup.setOnCheckedChangeListener(this);

		fragments = new ArrayList<Fragment>();
		fragments.add(new TextListFragment());
		fragments.add(new ImageListFragment());
		fragments.add(new ReviewFragment());
		fragments.add(new HoDsFragment());
		fragments.add(new MineFragment());

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment_container, fragments.get(0))
				.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		int childCount = group.getChildCount();// 获取当前有多少个
		int checkIndex = 0;
		RadioButton btn = null;
		for (int index = 0; index < childCount; index++) {
			btn = (RadioButton) group.getChildAt(index);
			if (btn.isChecked()) { // 获取状态
				checkIndex = index;
				break;
			}
		}
		Fragment fragment = fragments.get(checkIndex);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment_container, fragment).commit();

		// switch (checkedIndex) {
		// case 0:
		//
		// break;
		// case 1:
		//
		// break;
		// case 2:
		//
		// break;
		// case 3:
		//
		// break;
		// case 4:
		//
		// break;
		//
		// default:
		// break;
		// }

	}

}
