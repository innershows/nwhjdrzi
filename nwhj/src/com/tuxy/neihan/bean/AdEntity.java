package com.tuxy.neihan.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 广告实体
 * 
 * @author Innershows
 * 
 */
public class AdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2723839210841251360L;

	/**
	 * 类型， 类型为5
	 */
	private int type;

	/**
	 * 显示时间
	 */
	private long dispalyTime;

	/**
	 * 在线时间
	 */
	private long onlineTime;

	/**
	 * 展示图片高
	 */
	private int displayImageHeight;

	/**
	 * 广告ID
	 */
	private long adId;

	/**
	 * 展示图片宽
	 */
	private int displayImageWidth;

	/**
	 * TODO
	 */
	private String Source;

	private String Package;

	private String title;

	private String openUrl;

	private String downloadUrl;

	private int isAd;

	private String displayInfo;

	private String webUrl;

	private int displayType;

	private String buttonText;

	private String appleid;

	private String trackUrl;

	private String label;

	private String adType;

	private long id;

	private String ipaUrl;

	private String displayImage;

	public void parseJosn(JSONObject json) throws JSONException {
		if (json == null) {
			return;
		}

		type = json.getInt("type");
		dispalyTime = json.getLong("display_time");
		onlineTime = json.getLong("online_time");

		JSONObject ad = json.getJSONObject("ad");

		displayImageHeight = ad.getInt("display_image_height");
		adId = ad.getLong("ad_id");
		displayImageWidth = ad.getInt("display_image_width");
		Source = ad.getString("source");
		Package = ad.getString("package");
		title = ad.getString("title");
		openUrl = ad.getString("open_url");
		downloadUrl = ad.getString("download_url");
		isAd = ad.getInt("is_ad");
		displayInfo = ad.getString("display_info");
		webUrl = ad.getString("web_url");
		displayType = ad.getInt("display_type");
		buttonText = ad.getString("button_text");
		appleid = ad.getString("appleid");
		trackUrl = ad.getString("track_url");
		label = ad.getString("label");
		adType = ad.getString("type");
		id = ad.getLong("id");
		ipaUrl = ad.getString("ipa_url");
		displayImage = ad.getString("display_image");

	}

	public int getType() {
		return type;
	}

	public long getDispalyTime() {
		return dispalyTime;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public int getDisplayImageHeight() {
		return displayImageHeight;
	}

	public long getAdId() {
		return adId;
	}

	public int getDisplayImageWidth() {
		return displayImageWidth;
	}

	public String getSource() {
		return Source;
	}

	public String getPackage() {
		return Package;
	}

	public String getTitle() {
		return title;
	}

	public String getOpenUrl() {
		return openUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public int getIsAd() {
		return isAd;
	}

	public String getDisplayInfo() {
		return displayInfo;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public int getDisplayType() {
		return displayType;
	}

	public String getButtonText() {
		return buttonText;
	}

	public String getAppleid() {
		return appleid;
	}

	public String getTrackUrl() {
		return trackUrl;
	}

	public String getLabel() {
		return label;
	}

	public String getAdType() {
		return adType;
	}

	public long getId() {
		return id;
	}

	public String getIpaUrl() {
		return ipaUrl;
	}

	public String getDisplayImage() {
		return displayImage;
	}

}
