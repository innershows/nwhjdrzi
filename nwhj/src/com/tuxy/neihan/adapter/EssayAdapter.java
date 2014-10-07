package com.tuxy.neihan.adapter;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuxy.neihan.R;
import com.tuxy.neihan.activities.EssayDetailActivity;
import com.tuxy.neihan.bean.DataStore;
import com.tuxy.neihan.bean.TextEntity;
import com.tuxy.neihan.bean.UserEntity;

public class EssayAdapter extends BaseAdapter {

	private OnClickListener listener;
	private List<TextEntity> entities;
	private Context context;

	public EssayAdapter(Context context, List<TextEntity> entities) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entities = entities;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View ret = convertView;
		if (convertView == null) {
			ret = LayoutInflater.from(context).inflate(R.layout.item_essay,
					parent, false);
		}

		if (ret != null) {
			ViewHolder holder = (ViewHolder) ret.getTag();

			if (holder == null) {
				holder = new ViewHolder();

				holder.btnGifPlay = (TextView) ret
						.findViewById(R.id.btnGifPlay);
				holder.btnShare = (ImageButton) ret
						.findViewById(R.id.item_share);
				holder.chbBuryCount = (CheckBox) ret
						.findViewById(R.id.item_bury_count);
				holder.chbDiggCount = (CheckBox) ret
						.findViewById(R.id.item_digg_count);
				holder.gifImageView = (GifImageView) ret
						.findViewById(R.id.gifImageView);
				holder.imgProfileImage = (ImageView) ret
						.findViewById(R.id.item_profile_image);
				holder.pbDownloadProgress = (ProgressBar) ret
						.findViewById(R.id.item_image_download_progress);
				holder.txtCommetnCount = (TextView) ret
						.findViewById(R.id.item_comment_count);

				holder.txtContent = (TextView) ret
						.findViewById(R.id.item_content);

				// 点击事件 。
				 holder.txtContent.setTag(position + "");
				 holder.txtContent.setOnClickListener(listener);

				holder.txtProfilenick = (TextView) ret
						.findViewById(R.id.item_profile_nick);

				ret.setTag(holder);
			}
			TextEntity entity = entities.get(position);
			if (entity.getCategoryId() != 2) {
				holder.gifImageView.setVisibility(View.GONE);
				holder.pbDownloadProgress.setVisibility(View.GONE);
				holder.btnGifPlay.setVisibility(View.GONE);
			}
			// 1 设置文本
			UserEntity user = entity.getUser();
			String nick = "";
			if (user != null) {
				nick = user.getName();
			}
			holder.txtProfilenick.setText(nick);

			String content = entity.getContent();
			holder.txtContent.setText(content);

			int diggCount = entity.getDiggCount();

			holder.chbDiggCount.setText(Integer.toString(diggCount));

			int userDigg = entity.getUserDigg(); // 当前用户是否赞过

			holder.chbDiggCount.setEnabled(userDigg == 0);// 如果为1
															// ，表示已经赞过，那么没法再赞了。

			int buryCount = entity.getBuryCount();
			holder.chbBuryCount.setText(Integer.toString(buryCount));

			int userBury = entity.getUserBury();
			holder.chbBuryCount.setEnabled(userBury == 0); // 同赞

			int commentCount = entity.getCommentCount();
			holder.txtCommetnCount.setText(Integer.toString(commentCount));

			// 设置checkbox 和imagebutton 没有焦点
			// holder.chbBuryCount.setFocusable(false);
			// holder.chbDiggCount.setFocusable(false);
			//
			// holder.btnGifPlay.setFocusable(false);
			// holder.btnShare.setFocusable(false);

			// 2 设置图片
			// TODO 需要加载各种图片资源数据
		}

		return ret;
	}

	private static class ViewHolder {

		/**
		 * 头像
		 */
		ImageView imgProfileImage;
		/**
		 * 昵称
		 */
		TextView txtProfilenick;
		/**
		 * 文本内容
		 */
		TextView txtContent;
		/**
		 * 图片下载进度
		 */
		ProgressBar pbDownloadProgress;
		/**
		 * 图片显示
		 */
		GifImageView gifImageView;
		/**
		 * 图片显示部分， 点击显示
		 */
		TextView btnGifPlay;
		/**
		 * 赞，包含个数 ， 赞过， 不可用
		 */
		CheckBox chbDiggCount;
		/**
		 * 踩， 包含个数， 踩过，不可用
		 */
		CheckBox chbBuryCount;
		/**
		 * 评论个数，点击可以查看评论
		 */
		TextView txtCommetnCount;
		/**
		 * 分享操作
		 */
		ImageButton btnShare;
	}

}
