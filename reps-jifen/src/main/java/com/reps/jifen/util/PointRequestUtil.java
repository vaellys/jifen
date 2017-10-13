package com.reps.jifen.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class PointRequestUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(PointRequestUtil.class);
	
	private PointRequestUtil() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ListResult<T> doGets(Class<T> clazz, Map<String, Object> paramsMap, String serverPath, String requestUri) throws RepsException{
		try {
			ListResult<T> listResult = new ListResult<>();
			String params = ConvertUrlUtil.convertByMap(paramsMap);
			JSONObject jsonObject = HttpRequstUtil.getGetUrlResponse(serverPath + requestUri + "?" + params);
			if (null != jsonObject) {
				if (200 != jsonObject.getInt("status")) {
					throw new RepsException(jsonObject.getString("message"));
				}else {
					JSONObject result = jsonObject.getJSONObject("result");
					JSONArray jsonArray = result.getJSONArray("list");
					long count = result.getLong("count");
					JsonConfig config = new JsonConfig();
					config.addIgnoreFieldAnnotation(JsonIgnore.class);
					List<T> list = (List<T>) JSONArray.toList(jsonArray, clazz.newInstance(), config);
					listResult.setList(list);
					listResult.setCount(count);
				}
			}else {
				logger.error("请求异常,paramsMap {}", JSONSerializer.toJSON(paramsMap).toString());
				throw new RepsException("请求异常");
			}
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e.getMessage(), e);
		}
	}

}
