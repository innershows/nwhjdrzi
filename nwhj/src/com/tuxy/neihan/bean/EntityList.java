package com.tuxy.neihan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 所有实体列表（包括TextEntity ， ImageEntity）
 * 
 * @author Innershows
 * 
 */
public class EntityList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4492275584710685320L;
	private boolean hasMore;
	private long minTime;
	private String tip;
	private long maxTime;
	private List<TextEntity> entities;

	public EntityList() {
		// TODO Auto-generated constructor stub
		entities = new ArrayList<TextEntity>();// 得实例化 ，
												// 免得多次创建EntityList对象,减少对象的创建
	}

	public List<TextEntity> getEntities() {
		return entities;
	}

	public void parseJson(JSONObject json) throws JSONException {
		if (json == null) {
			return;
		}

		hasMore = json.getBoolean("has_more");
		tip = json.getString("tip");
		if (hasMore) {
			minTime = json.getLong("min_time");
		}

		maxTime = json.optLong("max_time", 0L);

		// 从data对象中获取名称为data数组，代表的是段子列表的数据
		JSONArray jsonArray = json.getJSONArray("data");
		int len = jsonArray.length();
		// 遍历数组中的每一条段子信息
		for (int i = 0; i < len; i++) {

			JSONObject item = jsonArray.getJSONObject(i);
			// ImageEntity imageEntity = new ImageEntity();
			// imageEntity.parseJson(item);
			int type = item.getInt("type");// 获取类型，1 是段子， 5是广告
			if (type == 5) {
				// TODO 处理广告内容
				AdEntity adEntity = new AdEntity();
				adEntity.parseJosn(item);
				System.out.println("--received ad id" + adEntity.getId());
			} else if (type == 1) {
				// TODO 处理段子内容
				int categoryId = item.getJSONObject("group").getInt(
						"category_id");
				TextEntity entity = null;

				if (categoryId == 1) {
					// TODO 解析文本段子
					entity = new TextEntity();
				} else if (categoryId == 2) {
					// TODO 解析图片段子
					entity = new ImageEntity();
				} else {
					break;
				}
				entity.parseJson(item);
				entities.add(entity);
			}
		}

	}

	public boolean isHasMore() {
		return hasMore;
	}

	public long getMinTime() {
		return minTime;
	}

	public String getTip() {
		return tip;
	}

	public long getMaxTime() {
		return maxTime;
	}

}
