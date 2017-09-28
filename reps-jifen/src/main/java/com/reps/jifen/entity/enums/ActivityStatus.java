package com.reps.jifen.entity.enums;

/**
 * 活动状态
 * 
 * @author qianguobing
 * @date 2017年8月25日 下午3:32:16
 */
public enum ActivityStatus {

	UN_PUBLISH((short) 0, "未发布"), PUBLISHED((short) 1, "进行中"), CANCELLED((short) 3, "已取消"), COMPLETED((short) 2, "已结束");

	private Short index;
	private String name;

	ActivityStatus(Short index, String name) {
		this.index = index;
		this.name = name;
	}

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
