package com.reps.jifen.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.constant.UrlConstant;
import com.reps.jifen.service.IPointsAggregateService;
import com.reps.jifen.util.PointRequestUtil;
import com.reps.jifen.vo.PointsAggregate;

@Transactional
@Service
public class PointsAggregateServiceImpl implements IPointsAggregateService {
	
	public static final Logger logger = LoggerFactory.getLogger(PointsAggregateServiceImpl.class);
	
	@Override
	public ListResult<PointsAggregate> query(int start, int pageSize, String serverPath, PointsAggregate pointsAggregate) throws RepsException {
		//构造请求积分收集参数MAP
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("pageIndex", start);
		paramsMap.put("pageSize", pageSize);
		try {
			String personName = pointsAggregate.getPersonName();
			String schoolName = pointsAggregate.getSchoolName();
			paramsMap.put("personName", URLEncoder.encode(null == personName ? "" : personName, "UTF-8"));
			paramsMap.put("schoolName", URLEncoder.encode(null == schoolName ? "" : schoolName, "UTF-8"));
			return PointRequestUtil.doGets(PointsAggregate.class, paramsMap, serverPath, UrlConstant.O_POINTS_AGGREGATE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e.getMessage(), e);
		}
	}

}
