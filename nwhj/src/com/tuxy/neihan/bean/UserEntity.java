package com.tuxy.neihan.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户实体
 * 
 * @author Innershows
 */
public class UserEntity {
	/**
	 * 头像网址
	 */
	private String avatarUrl;

	/**
	 * 用户ID
	 */
	private long userId;

	/**
	 * 用户名称 （昵称）
	 */
	private String name;

	/**
	 * 是否是认证用户
	 */
	private boolean userVerifed;

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public boolean isUserVerifed() {
		return userVerifed;
	}

	public void parseJson(JSONObject json) throws JSONException {
		if (json == null) {
			return;
		}
		this.avatarUrl = json.getString("avatar_url");
		this.userId = json.getLong("user_id");
		this.name = json.getString("name");
		this.userVerifed = json.getBoolean("user_verified");
	}

	/**
	 * "user": { "avatar_url": "http://p1.pstatp.com/thumb/1367/2213311454",
	 * "user_id": 3080520868, "name": "请叫我梓安哥", "user_verified": false },
	 */
}
