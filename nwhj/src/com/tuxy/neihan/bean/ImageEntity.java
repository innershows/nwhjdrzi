package com.tuxy.neihan.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageEntity extends TextEntity {

	private ImageUrlList largeList;

	private ImageUrlList middleList;

	public ImageUrlList getLargeList() {
		return largeList;
	}

	public ImageUrlList getMiddleList() {
		return middleList;
	}

	public void parseJson(JSONObject json) throws JSONException {

		super.parseJson(json);
		if (json == null) {
			return;
		}

		JSONObject group = json.getJSONObject("group");
		JSONObject largeImage = group.optJSONObject("large_image");
		JSONObject middleImage = group.optJSONObject("middle_image");

		this.largeList = new ImageUrlList();
		if (largeImage != null) {
			this.largeList.parseJson(largeImage);
		}

		this.middleList = new ImageUrlList();
		if (middleImage != null) {
			this.middleList.parseJson(middleImage);
		}
	}
}
