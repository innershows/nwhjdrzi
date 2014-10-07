package com.tuxy.neihan.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文本段子实体
 * 
 * @author Innershows
 */

public class TextEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6948303332454248821L;

	private int type;

	private long createTime;

	public long getCreateTime() {
		return createTime;
	}

	/**
	 * 代表赞的个数
	 */
	private int favoriteCount;

	public int getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * 代表当前用户是否踩了， 0代表没有 ， 1 代表踩了。
	 */
	private int userBury;

	/**
	 * 代表当前用户是否赞了， 0代表没有， 1 代表赞了。
	 */
	private int userFavorite;

	/**
	 * 代表踩的个数
	 */
	private int buryCount;

	/**
	 * 用于第三方分享，提交的网站参数
	 */
	private String shareUrl;

	/**
	 * TODO 分析这个字段的含义
	 */
	private int label;

	/**
	 * 文本段子的内容（完整的内容）
	 */
	private String content;

	/**
	 * 评论的个数
	 */
	private int commentCount;

	/**
	 * 状态 ， 其中的可选值3需要分析是什么类型
	 */
	private int status;

	/**
	 * 状态的信息 <br/>
	 * 可选值 <br/>
	 * <ul>
	 * <li>"已发布到热门列表"</li>
	 * </ul>
	 */
	private String statusDesc;

	public int getType() {
		return type;
	}

	public int getUserBury() {
		return userBury;
	}

	public int getUserFavorite() {
		return userFavorite;
	}

	public int getBuryCount() {
		return buryCount;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public int getLabel() {
		return label;
	}

	public String getContent() {
		return content;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public int getHasComments() {
		return hasComments;
	}

	public int getGoDetailCount() {
		return goDetailCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public long getGroupId() {
		return groupId;
	}

	public int getLevel() {
		return level;
	}

	public int getRepinCount() {
		return repinCount;
	}

	public int getUserRepin() {
		return userRepin;
	}

	public int getHasHotComments() {
		return hasHotComments;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public UserEntity getUser() {
		return user;
	}

	/**
	 * 当前用户是否已经评论 0代表没有评论
	 */
	private int hasComments;

	/**
	 * TODO 需要复分析这个字段的含义
	 */
	private int goDetailCount;

	/**
	 * TODO 需要去了解Digg是个什么意思
	 */
	private int userDigg;

	/**
	 * Digg的个数
	 */
	private int diggCount;

	/**
	 * 段子的ID ， 访问详情和评论时，用这个作为接口的参数
	 */
	private long groupId;

	/**
	 * TODO 这个需要分析一下是什么含义，现在有两处地方出现。 1. 获取列表接口有一个 level = 6 2. 文本段子实体中，level = 4
	 */
	private int level;

	/**
	 * TODO 分析含义
	 */
	private int repinCount;

	/**
	 * 代表用户是否repin了，0代表没有
	 */
	private int userRepin;

	/**
	 * 是否含有热门评论<br/>
	 */
	private int hasHotComments;

	/**
	 * 内容分类类型 ， 1 文本 ， 2 图片
	 */
	private int categoryId;

	// TODO 需要去分析 comments 这个json数组中的内容是什么？

	/**
	 * 上线时间
	 */
	private long onlineTime;

	/**
	 * 显示时间
	 */
	private long displayTime;

	/**
	 * 用户
	 */
	private UserEntity user;

	public void parseJson(JSONObject json) throws JSONException {

		if (json == null) {
			return;
		}
		this.onlineTime = json.getLong("online_time");
		this.displayTime = json.getLong("display_time");

		JSONObject group = json.getJSONObject("group");
		this.createTime = group.getLong("create_time");
		this.favoriteCount = group.getInt("favorite_count");
		this.userBury = group.getInt("user_bury");
		this.buryCount = group.getInt("bury_count");
		this.shareUrl = group.getString("share_url");
		this.label = group.optInt("label", 0);
		this.content = group.getString("content");
		this.commentCount = group.getInt("comment_count");
		this.status = group.getInt("status");
		this.goDetailCount = group.getInt("go_detail_count");
		this.statusDesc = group.getString("status_desc");

		this.user = new UserEntity();
		this.user.parseJson(group.getJSONObject("user"));

		this.userDigg = group.getInt("user_digg");
		this.groupId = group.getLong("group_id");
		this.level = group.getInt("level");
		this.repinCount = group.getInt("repin_count");
		this.diggCount = group.getInt("digg_count");
		this.hasHotComments = group.optInt("has_hot_comments", 0);
		this.userRepin = group.getInt("user_repin");
		this.categoryId = group.getInt("category_id");

	}
}
