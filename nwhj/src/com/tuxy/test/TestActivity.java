package com.tuxy.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tuxy.neihan.R;
import com.tuxy.neihan.bean.ImageEntity;
import com.tuxy.neihan.bean.ImageUrlList;
import com.tuxy.neihan.client.ClientAPI;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

/**
 * 这个文件就是一个测试入口，所有的测试内容，都可以放在这里
 * 
 * @author Innershows
 * 
 */
public class TestActivity extends Activity implements Response.Listener<String> {
	private RequestQueue queue;
	/*
	 * 分类ID ， 1 代表文本
	 */
	public static final int CATEGORY_TEXT = 1;
	/*
	 * 分类ID ， 2 代表图片
	 */
	public static final int CATEGORY_IMAGE = 2;

	/*
	 * 数据
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		queue = Volley.newRequestQueue(this);
		int itemCount = 1;

		ClientAPI.getList(queue, CATEGORY_IMAGE, itemCount, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Response.Listener#onResponse(java.lang.Object)
	 */
	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);

			// 获取根节点下的data对象
			JSONObject obj = json.getJSONObject("data");
			// 从data对象中获取名称为data数组，代表的是段子列表的数据
			JSONArray jsonArray = obj.getJSONArray("data");
			int len = jsonArray.length();
			// 遍历数组中的每一条图片段子信息
			for (int i = 0; i < len; i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				ImageEntity imageEntity = new ImageEntity();
				imageEntity.parseJson(item);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
