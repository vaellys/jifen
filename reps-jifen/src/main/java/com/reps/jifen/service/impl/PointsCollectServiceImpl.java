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
import com.reps.core.util.StringUtil;
import com.reps.jifen.constant.UrlConstant;
import com.reps.jifen.service.IPointsCollectService;
import com.reps.jifen.util.PointRequestUtil;
import com.reps.jifen.vo.PointsCollect;

@Transactional
@Service
public class PointsCollectServiceImpl implements IPointsCollectService {
	
	public static final Logger logger = LoggerFactory.getLogger(PointsCollectServiceImpl.class);
	
	@Override
	public ListResult<PointsCollect> query(int start, int pageSize, String serverPath, PointsCollect pointsCollect) throws RepsException {
		if(null == pointsCollect) {
			throw new RepsException("参数异常");
		}
		String personId = pointsCollect.getPersonId();
		if(StringUtil.isBlank(personId)) {
			throw new RepsException("查询异常:人员ID不能为空");
		}
		//构造请求积分收集参数MAP
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("personId", pointsCollect.getPersonId());
		paramsMap.put("pageIndex", start);
		paramsMap.put("pageSize", pageSize);
		try {
			paramsMap.put("beginTimeDisp", URLEncoder.encode(pointsCollect.getBeginTimeDisp(), "UTF-8"));
			paramsMap.put("endTimeDisp", URLEncoder.encode(pointsCollect.getEndTimeDisp(), "UTF-8"));
			return PointRequestUtil.doGets(PointsCollect.class, paramsMap, serverPath, UrlConstant.O_POINTS_COLLECT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e.getMessage(), e);
		}
	}

}
