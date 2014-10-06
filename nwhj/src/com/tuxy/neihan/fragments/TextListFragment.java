package com.tuxy.neihan.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.tuxy.neihan.R;

public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener, OnRefreshListener2<ListView> {
	public static final List<String> STRINGS = new ArrayList<String>();
	private View quickTools;
	private TextView textNotify;
	static {
		for (int index = 0; index < 20; index++) {
			STRINGS.add("JAVA" + index);
		}
	}

	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_text_list, container,
				false);

		// TODO 获取标题
		View title = view.findViewById(R.id.textlist_title);
		title.setOnClickListener(this);

		PullToRefreshListView refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.textlist_listview);
		ListView listView = refreshListView.getRefreshableView();

		refreshListView.setMode(Mode.BOTH);

		// 设置上拉刷新 与下拉的事件监听

		refreshListView.setOnRefreshListener(this);

		View header = inflater.inflate(R.layout.textlist_header_tools,
				listView, false);

		// 设置header 两个按钮的的点击事件
		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		listView.addHeaderView(header);

		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, STRINGS));

		listView.setOnScrollListener(this);

		// TODO 获取 快速的工具条（发布和审核） ， 用于列表滚动的显示和隐藏
		quickTools = view.findViewById(R.id.textlist_quick_tools);
		quickTools.setVisibility(View.INVISIBLE);
		// 设置两个浮动的点击事件
		quickPublish = quickTools.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		quickReview = quickTools.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		// TODO 获取 新条目通知控件，每次有新数据显示，过一段时间隐藏

		textNotify = (TextView) view.findViewById(R.id.textlist_new_nofity);
		textNotify.setVisibility(View.INVISIBLE);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	// header 和quickTools 的点击事件

	private Handler handler = new Handler();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.textlist_title:
			textNotify.setVisibility(View.VISIBLE); // 显示提示框
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					textNotify.setVisibility(View.INVISIBLE);
				}
			}, 3000);

			break;
		case R.id.quick_tools_publish:
			Toast.makeText(getActivity(), "publish", Toast.LENGTH_SHORT).show();
			break;
		case R.id.quick_tools_review:
			Toast.makeText(getActivity(), "review", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	// ///////////////////////////////////////////////////////////////////

	// 列表滚动显示工具条
	private int lastVisibleIndex;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (quickTools == null) {
			return;
		}
		if (lastVisibleIndex - firstVisibleItem < 0 || firstVisibleItem == 0
				|| firstVisibleItem == 1) {
			// 现在是向上移动
			quickTools.setVisibility(View.INVISIBLE);
		} else if (lastVisibleIndex - firstVisibleItem > 0) {
			quickTools.setVisibility(View.VISIBLE);
		}

		lastVisibleIndex = firstVisibleItem;
	}

	// //////////////////////////////////////////////////////////////////

	// 列表下拉刷新与上拉刷新
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2
	 * #onPullDownToRefresh
	 * (com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 实现下拉刷新 ,那么就要进行加载新数据的操作 。 上往下

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO 实现上拉刷新，考虑加载旧的数据 。 下往上
	}
	// ///////////////////////////////////////////////////////////////////
}
