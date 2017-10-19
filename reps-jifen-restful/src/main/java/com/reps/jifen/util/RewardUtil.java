package com.reps.jifen.util;

import static com.reps.core.commons.OrderCondition.OrderDirection.ASC;
import static com.reps.core.commons.OrderCondition.OrderDirection.DESC;
import static com.reps.jifen.entity.enums.RewardStatus.PUBLISHED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.ActivityReward;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;

/**
 * 物品和活动工具类
 * @author qianguobing
 * @date 2017年9月12日 下午3:57:50
 */
public class RewardUtil {

	private RewardUtil() {
	}

	/**
	 * 排序字段map
	 */
	private static final Map<String, String> SORT_FIELD_MAP = new HashMap<>();

	static {
		SORT_FIELD_MAP.put("record", "exchangedCount");
		SORT_FIELD_MAP.put("point", "points");
		SORT_FIELD_MAP.put("time", "createTime");
		SORT_FIELD_MAP.put("participate", "participatedCount");
	}

	/**
	 * 设置奖品属性
	 * 
	 * @param jfReward
	 * @param type
	 */
	public static void setReward(PointReward jfReward) {
		jfReward.setIsShown(PUBLISHED.getIndex());
		String sortField = jfReward.getSortField();
		// 获取排序字段，默认按发布时间进行排序
		if (StringUtil.isBlank(sortField) || !SORT_FIELD_MAP.containsKey(sortField)) {
			jfReward.setSortField("createTime");
		} else {
			jfReward.setSortField(SORT_FIELD_MAP.get(sortField));
		}
		String sortOrder = jfReward.getSortOrder();
		if (!ASC.name().equalsIgnoreCase(sortOrder) && !DESC.name().equalsIgnoreCase(sortOrder)) {
			jfReward.setSortOrder(DESC.name());
		}
	}
	
	/**
	 * 设置活动属性
	 * 
	 * @param jfReward
	 */
	public static void setReward(ActivityReward jfReward) {
		jfReward.setIsShown(PUBLISHED.getIndex());
		String sortField = jfReward.getSortField();
		// 获取排序字段，默认按发布时间进行排序
		if (StringUtil.isBlank(sortField) || !SORT_FIELD_MAP.containsKey(sortField)) {
			jfReward.setSortField("createTime");
		} else {
			jfReward.setSortField(SORT_FIELD_MAP.get(sortField));
		}
		String sortOrder = jfReward.getSortOrder();
		if (!ASC.name().equalsIgnoreCase(sortOrder) && !DESC.name().equalsIgnoreCase(sortOrder)) {
			jfReward.setSortOrder(DESC.name());
		}
	}
	
	public static void setRewardType(PointReward jfReward, String type) {
		RewardCategory jfRewardCategory = new RewardCategory();
		jfRewardCategory.setType(type);
		jfReward.setJfRewardCategory(jfRewardCategory);
		jfReward.setIsShown(PUBLISHED.getIndex());
	}
	
	/**
	 * 设置图片地址
	 * @param list
	 * @param imageServerPath
	 * @throws RepsException
	 */
	public static void setPictureUrls(List<PointReward> list, String imageServerPath) throws RepsException{
		for (PointReward jfReward : list) {
			String picture = jfReward.getPicture();
			if(StringUtil.isNotBlank(picture)) {
				String uri = picture.split(",", -1)[0];
				if(StringUtil.isNotBlank(uri)) {
					jfReward.setPicture(imageServerPath + uri);
				}else {
					jfReward.setPicture(uri);
				}
			}
		}
	}
	
	public static void setActivityPictureUrls(List<ActivityReward> list, String imageServerPath) throws RepsException{
		for (ActivityReward jfReward : list) {
			String picture = jfReward.getPicture();
			if(StringUtil.isNotBlank(picture)) {
				jfReward.setPicture(imageServerPath + picture);
			}else {
				jfReward.setPicture(picture);
			}
		}
	}
	
	public static void setActivityInfo(List<PointActivityInfo> list, String imageServerPath) throws RepsException{
		for (PointActivityInfo jfReward : list) {
			ActivityReward pointReward = jfReward.getPointReward();
			if(null != pointReward) {
				jfReward.setActivityName(pointReward.getName());
				jfReward.setParticipatedCount(pointReward.getParticipatedCount());
				jfReward.setIsShown(pointReward.getIsShown());
				String picture = pointReward.getPicture();
				if(StringUtil.isNotBlank(picture)) {
					jfReward.setPicture(imageServerPath + picture);
				}else {
					jfReward.setPicture(picture);
				}
			}else {
				throw new RepsException("活动信息异常");
			}
			Short auditStatus = jfReward.getAuditStatus();
			jfReward.setAuditStatus(null == auditStatus ? 0 : auditStatus);
		}
	}
	
}
