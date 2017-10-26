package com.reps.jifen.util;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsAggregate;

public class PointsAggregateUtil {
	
	private PointsAggregateUtil() {
		
	}
	
	/**
	 * 设置图片地址
	 * @param list
	 * @param imageServerPath
	 * @throws RepsException
	 */
	public static <T> void setPictureUrls(List<T> list, String imageServerPath) throws RepsException{
		for (T t : list) {
			if(t instanceof PointsAggregate) {
				PointsAggregate pointsAggregate = (PointsAggregate) t;
				String avatarUrl = pointsAggregate.getAvatarUrl();
				if(StringUtil.isNotBlank(avatarUrl)) {
					pointsAggregate.setAvatarUrl(imageServerPath + avatarUrl);
				}else {
					pointsAggregate.setAvatarUrl(avatarUrl);
				}
			}
		}
	}
	
}
