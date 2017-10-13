package com.reps.jifen.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reps.core.exception.RepsException;
import com.reps.jifen.constant.UrlConstant;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ActivityUtil {
	
	/**兑换类别 1:物品  2:活动*/
	private static final Integer ACTIVITY_TYPE = 2;
	
	public static final Logger logger = LoggerFactory.getLogger(ActivityUtil.class);
	
	private ActivityUtil() {
	}
	
	public static void doPosts(String studentId, String rewardId, Integer points, String serverPath) throws RepsException{
		try {
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
				logger.error("请求异常,paramsMap {}", JSONSerializer.toJSON(paramsMap).toString());
				throw new RepsException("请求异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e.getMessage(), e);
		}
	}
	
}
