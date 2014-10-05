package com.tuxy.test;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tuxy.neihan.R;
import com.tuxy.neihan.bean.CommentList;
import com.tuxy.neihan.bean.EntityList;
import com.tuxy.neihan.client.ClientAPI;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 这个文件就是一个测试入口，所有的测试内容，都可以放在这里
 * 
 * @author Innershows
 * 
 */
public class TestActivity extends Activity implements Response.Listener<String> {
	private RequestQueue queue;
	private Button button;
	private int offset;
	/*
	 * 分类ID ， 1 代表文本
	 */
	public static final int CATEGORY_TEXT = 1;
	/*
	 * 分类ID ， 2 代表图片
	 */
	public static final int CATEGORY_IMAGE = 2;

	private EntityList entityList;
	private CommentList commentList;

	/*
	 * 数据
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		entityList = new EntityList();
		button = (Button) findViewById(R.id.activity_test_button);
		queue = Volley.newRequestQueue(this);
		// final int itemCount = 30;
		// TODO 定义adapter
		// TODO adapter设置集合 setList（entityList.getEntities）;

		// button.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// if (entityList == null) {
		// ClientAPI.getList(queue, CATEGORY_IMAGE, itemCount, 0,
		// TestActivity.this);
		// return;
		// }
		//
		// ClientAPI.getList(queue, CATEGORY_IMAGE, itemCount,
		// entityList.getMinTime(), TestActivity.this);
		// }
		// });

		// ClientAPI.getList(queue, CATEGORY_IMAGE, itemCount, this);
		commentList = new CommentList();

		// 可以newadapter 了， 可以添加 数据了。虽然size 为0 ， 但是会慢慢加的。
		final long groupId = 3551461874L; // 这个Id 是对于文本段子的id
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!commentList.isHasMore()) { // 有才加载 ， 没有就算了
					System.out.println("--没有数据了-->>");
					return;
				}
				ClientAPI
						.getComments(queue, TestActivity.this, groupId, offset);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	/**
	 * 获取段子列表
	 * 
	 * @param arg0
	 */
	public void listOnResponse(String arg0) {
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

			// 解析JSON的方法，包含图片，文本，广告的解析
			entityList.parseJson(obj);// 相当于获取数据内容

			if (!entityList.isHasMore()) { // 没有更多的数据了。
				// TODO 提示用户没有数据了。
				System.out.println("--" + entityList.getTip());
				return;
			}
			// 获取段子内容列表

			// TODO adapter.notified...
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Response.Listener#onResponse(java.lang.Object)
	 */
	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			if (arg0 == null || arg0.trim().equals("")) {
				return;
			}

			JSONObject object = new JSONObject(arg0);
			// Iterator<String> keys = object.keys(); // 获取 JSON 数据的key
			// while (keys.hasNext()) {
			// String key = (String) keys.next();
			// System.out.println("--json key-->>" + key);
			// }

			commentList.parseJson(object);
			if (commentList.isHasMore()) { // 如果还有， 就偏移20个，找下一个
				offset += 20;
			}

			// TODO 直接将adapternotify...

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
