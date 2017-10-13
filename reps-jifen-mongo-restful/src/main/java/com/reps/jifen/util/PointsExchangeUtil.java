package com.reps.jifen.util;

import java.util.List;

import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsExchange;

public class PointsExchangeUtil {

	private PointsExchangeUtil() {
	}

	public static <T> void transformList(List<T> list) {
		if (null != list && list.size() > 0) {
			for (T bean : list) {
				if (bean instanceof PointsExchange) {
					PointsExchange pointsExchange = (PointsExchange) bean;
					String name = pointsExchange.getName();
					if (StringUtil.isNotBlank(name)) {
						pointsExchange.setName(name.replace(name.charAt(1), '*'));
					}
				}
			}
		}
	}

}
