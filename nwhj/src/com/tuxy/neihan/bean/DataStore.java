package com.tuxy.neihan.bean;

import java.util.LinkedList;
import java.util.List;

/**
 * 存储段子的单例
 * 
 * @author Innershows
 * 
 */
public class DataStore {
	private DataStore() {
		// TODO Auto-generated constructor stub
		textEntities = new LinkedList<TextEntity>();
		imageEntities = new LinkedList<TextEntity>();

	}

	private static DataStore outInstance;

	public static DataStore getInstance() {
		if (outInstance == null) {
			outInstance = new DataStore();
		}
		return outInstance;
	}

	private List<TextEntity> textEntities;
	private List<TextEntity> imageEntities;

	/**
	 * 把获取到的文本段子放在最前面，这个方法针对的是下拉刷新的操作
	 * 
	 * @param entities
	 */
	public void addTextEntities(List<TextEntity> entities) {
		if (entities != null) {
			this.textEntities.addAll(0, entities);
		}
	}

	/**
	 * 把获取到的文本段子放在最后面，这个方法针对的是上拉加载（查看旧数据）的操作
	 * 
	 * @param entities
	 */
	public void appendTextEntities(List<TextEntity> entities) {
		if (entities != null) {
			this.textEntities.addAll(entities);
		}
	}

	// //////////////////////////////////////////////////////////////////////
	/**
	 * 把获取到的图片段子放在最前面，这个方法针对的是下拉刷新的操作
	 * 
	 * @param entities
	 */
	public void addImageEntities(List<TextEntity> entities) {
		if (entities != null) {
			this.imageEntities.addAll(0, entities);
		}
	}

	/**
	 * 把获取到的图片段子放在最后面，这个方法针对的是上拉加载（查看旧数据）的操作
	 * 
	 * @param entities
	 */
	public void appendImageEntities(List<TextEntity> entities) {
		if (entities != null) {
			this.imageEntities.addAll(entities);
		}
	}

	/**
	 * 获取文本段子列表
	 * 
	 * @return
	 */
	public List<TextEntity> getTextEntities() {
		return textEntities;
	}

	/**
	 * 获取图片段子列表
	 * 
	 * @return
	 */
	public List<TextEntity> getImageEntities() {
		return imageEntities;
	}

}
