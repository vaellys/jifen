package com.reps.jifen.util;

import java.util.List;

import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsExchange;

public class PointsExchangeUtil {
	
	private PointsExchangeUtil() {
	}
	
	public static void transformList(List<PointsExchange> list) {
		if (null != list && list.size() > 0) {
			for (PointsExchange bean : list) {
				String name = bean.getName();
				if(StringUtil.isNotBlank(name)) {
					bean.setName(name.replace(name.charAt(1), '*'));
				}
			}
		}
	}
	
}
