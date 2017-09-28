package com.reps.jifen.util;

import java.util.HashMap;
import java.util.Map;

import com.reps.core.exception.RepsException;
import com.reps.jifen.constant.UrlConstant;

import net.sf.json.JSONObject;

public class ActivityUtil {
	
	/**兑换类别 1:物品  2:活动*/
	private static final Integer ACTIVITY_TYPE = 2;
	
	private ActivityUtil() {
	}
	
	public static void doPosts(String studentId, String rewardId, Integer points, String serverPath) throws Exception{
		//构造请求积分收集参数MAP
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("personId", studentId);
		paramsMap.put("rewardId", rewardId);
		paramsMap.put("points", points);
		//设置活动类型
		paramsMap.put("type", ACTIVITY_TYPE);
		String params = ConvertUrlUtil.convertByMap(paramsMap);
		JSONObject jsonObject = HttpRequstUtil.getPostUrlResponse(serverPath + UrlConstant.O_CANCEL_EXCHANGE, params);
		if (null != jsonObject) {
			if (200 != jsonObject.getInt("status")) {
				throw new RepsException(jsonObject.getString("message"));
			} 
		}else {
			throw new RepsException("网络异常");
		}
	}
	
}
