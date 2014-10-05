package com.tuxy.neihan.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 评论接口返回的 data ： ｛｝数据部分的实体定义 包含了 top_comment 和 recent_comments 两个数组 JSON 格式如下<br/>
 * 
 * <pre>
 * ｛
 * 		“data”:{
 * 					"top_comments" : [],
 * 					"recent_comments":[]
 * 				}
 * ｝
 * </pre>
 * 
 * 
 * @author Innershows
 */
public class CommentList {
	private List<Comment> topComments;
	private List<Comment> recentComments;
	private long groupId;
	private int totalNumber;
	/**
	 * 第一次默认有多余的。
	 */
	private boolean hasMore = true;

	public CommentList() {
		// TODO Auto-generated constructor stub
		topComments = new ArrayList<Comment>();
		recentComments = new ArrayList<Comment>();

	}

	public void parseJson(JSONObject data) throws JSONException {
		if (data == null) {
			return;
		}

		groupId = data.getLong("group_id");
		totalNumber = data.getInt("total_number");
		hasMore = data.getBoolean("has_more");

		JSONObject json = data.getJSONObject("data");
		JSONArray tArray = json.optJSONArray("top_comments");
		JSONArray rArray = json.optJSONArray("recent_comments");
		getComments(tArray, topComments);
		getComments(rArray, recentComments);
	}

	public List<Comment> getTopComments() {
		return topComments;
	}

	public List<Comment> getRecentComments() {
		return recentComments;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	private void getComments(JSONArray array, List<Comment> list)
			throws JSONException {
		if (array != null) {
			for (int index = 0; index < array.length(); index++) {
				JSONObject obj = array.getJSONObject(index);
				Comment comment = new Comment();
				comment.parseJson(obj);
				list.add(comment);
			}
		}
	}

}
