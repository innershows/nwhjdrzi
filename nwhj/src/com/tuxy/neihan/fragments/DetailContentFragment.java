package com.tuxy.neihan.fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.tuxy.neihan.R;
import com.tuxy.neihan.adapter.CommentAdapter;
import com.tuxy.neihan.bean.Comment;
import com.tuxy.neihan.bean.CommentList;
import com.tuxy.neihan.bean.TextEntity;
import com.tuxy.neihan.bean.UserEntity;
import com.tuxy.neihan.client.ClientAPI;
import com.tuxy.neihan.view.MyListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailContentFragment extends Fragment implements OnTouchListener,
		Listener<String> {

	private TextEntity entity;
	private ScrollView scrollView;
	private CommentAdapter hotAdapter;
	private RequestQueue queue;
	private CommentAdapter recentAdapter;
	private LinearLayout layout;
	private int offset;

	private List<Comment> hotComments;
	private List<Comment> recentComments;

	private TextView txtHotCommentCount;
	private TextView txtRecentCommentCount;
	private boolean hasMore;

	public DetailContentFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		queue = Volley.newRequestQueue(getActivity());
		if (entity == null) {
			Bundle arguments = getArguments();
			Serializable serializable = arguments.getSerializable("entity");
			if (serializable instanceof TextEntity) {
				entity = (TextEntity) serializable;
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View ret = inflater.inflate(R.layout.fragment_detail_content,
				container, false);

		layout = (LinearLayout) ret.findViewById(R.id.scrollview_content);

		hotComments = new ArrayList<Comment>();
		recentComments = new ArrayList<Comment>();

		hotAdapter = new CommentAdapter(getActivity(), hotComments);
		recentAdapter = new CommentAdapter(getActivity(), recentComments);
		scrollView = (ScrollView) ret.findViewById(R.id.detail_scroll);
		scrollView.setOnTouchListener(this);

		// 1 . 设置主体内容

		setEssayContent(ret);

		txtHotCommentCount = (TextView) ret
				.findViewById(R.id.txt_hot_comments_count);

		MyListView hotCommentListView = (MyListView) ret
				.findViewById(R.id.hot_comments_list);

		hotCommentListView.setAdapter(hotAdapter);

		txtRecentCommentCount = (TextView) ret
				.findViewById(R.id.txt_recent_comments_count);

		MyListView recentCommentListView = (MyListView) ret
				.findViewById(R.id.recent_comments_list);
		recentCommentListView.setAdapter(recentAdapter);
		return ret;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		ClientAPI.getComments(queue, this, entity.getGroupId(), 0);

	}

	/**
	 * 设置段子主体内容 （详情）
	 * 
	 * @param ret
	 */
	private void setEssayContent(View ret) {
		TextView btnGifPlay = (TextView) ret.findViewById(R.id.btnGifPlay);
		ImageButton btnShare = (ImageButton) ret.findViewById(R.id.item_share);
		CheckBox chbBuryCount = (CheckBox) ret
				.findViewById(R.id.item_bury_count);
		CheckBox chbDiggCount = (CheckBox) ret
				.findViewById(R.id.item_digg_count);
		GifImageView gifImageView = (GifImageView) ret
				.findViewById(R.id.gifImageView);
		ImageView imgProfileImage = (ImageView) ret
				.findViewById(R.id.item_profile_image);
		ProgressBar pbDownloadProgress = (ProgressBar) ret
				.findViewById(R.id.item_image_download_progress);
		TextView txtCommetnCount = (TextView) ret
				.findViewById(R.id.item_comment_count);
		TextView txtContent = (TextView) ret.findViewById(R.id.item_content);
		TextView txtProfilenick = (TextView) ret
				.findViewById(R.id.item_profile_nick);

		if (entity.getCategoryId() != 2) {
			gifImageView.setVisibility(View.GONE);
			pbDownloadProgress.setVisibility(View.GONE);
			btnGifPlay.setVisibility(View.GONE);
		}
		// 1 设置文本
		UserEntity user = entity.getUser();
		String nick = "";
		if (user != null) {
			nick = user.getName();
		}
		txtProfilenick.setText(nick);

		String content = entity.getContent();
		txtContent.setText(content);

		int diggCount = entity.getDiggCount();

		chbDiggCount.setText(Integer.toString(diggCount));

		int userDigg = entity.getUserDigg(); // 当前用户是否赞过

		chbDiggCount.setEnabled(userDigg != 1);// 如果为1
												// ，表示已经赞过，那么没法再赞了。

		int buryCount = entity.getBuryCount();
		chbBuryCount.setText(Integer.toString(buryCount));

		int userBury = entity.getUserBury();
		chbBuryCount.setEnabled(userBury != 1); // 同赞

		int commentCount = entity.getCommentCount();
		txtCommetnCount.setText(Integer.toString(commentCount));

		// 2 设置图片
		// TODO 需要加载各种图片资源数据
	}

	// /////////////////////////////////////////////////
	// 实现onTouch的方法

	private boolean hasMove = false;

	/*
	 * (non-Javadoc) 处理ScrollView触摸事件 ， 用于在ScrollView滚动到最下面的时候，自动加载数据
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		boolean bret = false;
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			bret = true;
			hasMore = false;
		} else if (action == MotionEvent.ACTION_MOVE) {
			hasMore = true;
		} else if (action == MotionEvent.ACTION_UP) {
			if (hasMore) {
				int scrollY = scrollView.getScrollY(); // scrollView 在窗体上面的高度
				int measuredHeight = scrollView.getMeasuredHeight(); // 窗体的高度
				int totalHeight = layout.getHeight(); // scrollview总共的高度 .
														// 这个layout是scrollview里面内部的布局
														// ， 它拥有整个高度

				if (scrollY + measuredHeight >= totalHeight) {
					// TODO 进行评论分页加载
					ClientAPI.getComments(queue, this, entity.getGroupId(),
							offset);

				}

			}
		}
		return bret;
	}

	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			if (arg0 == null || arg0.trim().equals("")) {
				return;
			}

			JSONObject object = new JSONObject(arg0);
			CommentList commentList = new CommentList();
			commentList.parseJson(object);
			hasMore = commentList.isHasMore();
			if (hasMore) { // 如果还有， 就偏移20个，找下一个
				offset += 20;
			}
			List<Comment> topComments = commentList.getTopComments();
			if (topComments != null && !topComments.isEmpty()) {
				hotComments.addAll(topComments);
				hotAdapter.notifyDataSetChanged();
			}

			recentComments.addAll(commentList.getRecentComments());
			// TODO 直接将adapternotify...
			recentAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
