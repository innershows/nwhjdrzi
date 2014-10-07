package com.tuxy.neihan.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.tuxy.neihan.R;
import com.tuxy.neihan.activities.EssayDetailActivity;
import com.tuxy.neihan.adapter.EssayAdapter;
import com.tuxy.neihan.bean.DataStore;
import com.tuxy.neihan.bean.EntityList;
import com.tuxy.neihan.bean.TextEntity;
import com.tuxy.neihan.client.ClientAPI;

/**
 * *1. 列表界面第一次启动 ， 并且数据为空的时候，自动加载数据 。 *2. 只要列表没有数据， 进入这个界面的时候就尝试加载数据 。 *3. 只要有数据
 * ， 就不进行数据的加载 *4. 进入这个界面，并且有数据的情况下， 尝试检查新信息的个数。 * @author Innershows
 * 
 */
public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener, OnRefreshListener2<ListView>, OnItemClickListener {

	private RequestQueue queue;
	/*
	 * 分类ID ， 1 代表文本
	 */
	public static final int CATEGORY_TEXT = 1;
	/*
	 * 分类ID ， 2 代表图片
	 */
	public static final int CATEGORY_IMAGE = 2;

	/**
	 * 请求定义的分类
	 */
	private int requestCategory = CATEGORY_TEXT;
	private boolean hasMore = true;

	private long maxTime;
	private long minTime;
	private View quickTools;
	private TextView textNotify;
	private EssayAdapter adapter;

	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (queue == null) {
			queue = Volley.newRequestQueue(getActivity());
		}
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

		List<TextEntity> textEntities = DataStore.getInstance()
				.getTextEntities();

		adapter = new EssayAdapter(getActivity(), textEntities);
		listView.setAdapter(adapter);
		adapter.setListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v instanceof TextView) {
					String string = (String) v.getTag();
					if (string != null) {
						int position = Integer.parseInt(string);
						System.out.println("--position-->>" + position);
						Intent intent = new Intent(getActivity(),
								EssayDetailActivity.class);

						TextEntity textEntity = DataStore.getInstance()
								.getTextEntities().get(position);
						intent.putExtra("category", textEntity.getCategoryId());
						intent.putExtra("currentEssayPosition", position);
						startActivity(intent);
					}
				}
			}
		});
		listView.setOnItemClickListener(this);

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
		if (DataStore.getInstance().getTextEntities().isEmpty()) {
			ClientAPI.getList(queue, requestCategory, 30, minTime, 0,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String arg0) {
							// TODO 加载新数据
							listOnResponse(arg0, 1);
						}
					});
		}
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

		this.adapter = null;
		this.quickTools = null;
		this.textNotify = null;
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
	public void onPullDownToRefresh(
			final PullToRefreshBase<ListView> refreshView) {
		// TODO 实现下拉刷新 ,那么就要进行加载新数据的操作 。 上往下
		ClientAPI.getList(queue, requestCategory, 30, minTime, 0,
				new Response.Listener<String>() {
					// TODO 加载新数据
					@Override
					public void onResponse(String arg0) {
						refreshView.onRefreshComplete();
						listOnResponse(arg0, 1);
					}
				});
	}

	@Override
	public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
		// TODO 实现上拉刷新，考虑加载旧的数据 。 下往上
		ClientAPI.getList(queue, requestCategory, 30, 0, maxTime,
				new Response.Listener<String>() {
					// TODO 加载新数据
					@Override
					public void onResponse(String arg0) {
						refreshView.onRefreshComplete();
						listOnResponse(arg0, 2);
					}
				});

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 获取段子列表 这个方法， 是在Volly联网响应返回的时候， 在主线程被调用 它是在主线程执行的， 因此可以直接更新UI
	 * 
	 * @param arg0
	 * @param type
	 *            这个参数 有1 和2 1 表示下拉， 2 表示上拉
	 */
	public void listOnResponse(String arg0, int type) {
		// TODO Auto-generated method stub
		try {
			if (arg0 == null || arg0.trim().equals("")) {
				return;
			}

			JSONObject json = new JSONObject(arg0);
			if (!json.getString("message").equals("success")) {
				return;
			}
			// 获取根节点下的data对象
			JSONObject obj = json.getJSONObject("data");

			EntityList entityList = new EntityList();
			// 解析JSON的方法，包含图片，文本，广告的解析
			entityList.parseJson(obj);// 相当于获取数据内容

			minTime = entityList.getMinTime();
			maxTime = entityList.getMaxTime();

			if (type == 1) {
				DataStore.getInstance().addTextEntities(
						entityList.getEntities());

			} else if (type == 2) {

				DataStore.getInstance().appendTextEntities(
						entityList.getEntities());
			}

			adapter.notifyDataSetChanged();

			if (!(hasMore = entityList.isHasMore())) { // 没有更多的数据了。
				// TODO 提示用户没有数据了。
				Toast.makeText(getActivity(), "没有数据了...", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * listview的item点击事件
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "this is click" + position,
				Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getActivity(), EssayDetailActivity.class);

		TextEntity textEntity = DataStore.getInstance().getTextEntities()
				.get(position);
		intent.putExtra("category", textEntity.getCategoryId());
		intent.putExtra("currentEssayPosition", position);

		startActivity(intent);
	}

}
