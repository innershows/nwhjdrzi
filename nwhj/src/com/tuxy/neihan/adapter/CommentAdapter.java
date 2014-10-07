package com.tuxy.neihan.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tuxy.neihan.R;
import com.tuxy.neihan.bean.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private Context context;
	private List<Comment> comments;

	public CommentAdapter(Context context, List<Comment> comments) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.comments = comments;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return comments.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View ret = convertView;
		if (convertView == null) {
			ret = LayoutInflater.from(context).inflate(R.layout.item_comment,
					parent, false);
		}
		if (ret != null) {

			ViewHolder holder = (ViewHolder) ret.getTag();
			if (holder == null) {
				holder = new ViewHolder();

				holder.chbDigg = (CheckBox) ret
						.findViewById(R.id.comment_digg_count);
				holder.profileImage = (ImageView) ret
						.findViewById(R.id.comment_profile_image);
				holder.txtContent = (TextView) ret
						.findViewById(R.id.comment_content);
				holder.txtCreateTime = (TextView) ret
						.findViewById(R.id.comment_create_time);
				holder.txtNick = (TextView) ret
						.findViewById(R.id.comment_profile_nick);

				ret.setTag(holder);
			}
			Comment comment = comments.get(position);

			holder.txtContent.setText(comment.getText());

			Date dt = new Date(comment.getCreateTime() * 1000);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String format = df.format(dt);

			holder.txtCreateTime.setText(format);

			holder.txtNick.setText(comment.getUserName());

			int diggCount = comment.getDiggCount();
			holder.chbDigg.setText(Integer.toString(diggCount));

			int userBury = comment.getUserBury();
			holder.chbDigg.setEnabled(userBury == 0); // 设置可选 ， 如果是1 ， 不可选

		}

		return ret;
	}

	private static class ViewHolder {
		ImageView profileImage;
		TextView txtNick;
		TextView txtCreateTime;
		CheckBox chbDigg;
		TextView txtContent;

	}

}
