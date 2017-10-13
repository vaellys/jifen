package com.reps.jifen.service.impl;

import static com.reps.core.util.DateUtil.getDateFromStr;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsCollect;
import com.reps.jifen.repository.PointsCollectRepository;
import com.reps.jifen.service.IPointsCollectService;

@Service
public class PointsCollectServiceImpl implements IPointsCollectService {

	protected final Logger logger = LoggerFactory
			.getLogger(PointsCollectServiceImpl.class);

	@Autowired
	PointsCollectRepository repository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public PointsCollect save(PointsCollect jfPointsCollect)
			throws RepsException {
		try {
			// 设置积分收集时间与获取时间，暂时一样
			jfPointsCollect.setCollectTime(new Date());
			jfPointsCollect.setGetTime(new Date());
			return repository.save(jfPointsCollect);
		} catch (Exception e) {
			logger.error("保存失败", e);
			throw new RepsException("保存失败", e);
		}
	}

	@Override
	public ListResult<PointsCollect> findByPersonId(String personId,
			Integer pageIndex, Integer pageSize) throws RepsException {
		if (StringUtil.isBlank(personId)) {
			throw new RepsException("人员ID不能为空");
		}
		// 设置分页参数
		pageIndex = null == pageIndex ? 1 : pageIndex;
		pageSize = null == pageSize ? 20 : pageSize;
		// 构建分页对象
		PageRequest pageRequest = new PageRequest(pageIndex - 1,
				pageSize);
		Page<PointsCollect> page = repository.findByPersonId(personId,
				pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<PointsCollect> listResult = new ListResult<>(
				page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}

	@Override
	public ListResult<PointsCollect> query(PointsCollect pointsCollect, Integer pageIndex, Integer pageSize) throws RepsException {
		try {
			if(null == pointsCollect) {
				throw new RepsException("参数异常");
			}
			String personId = pointsCollect.getPersonId();
			if (StringUtil.isBlank(personId)) {
				throw new RepsException("人员ID不能为空");
			}
			// 设置分页参数
			pageIndex = null == pageIndex ? 1 : pageIndex;
			pageSize = null == pageSize ? 20 : pageSize;
			Query query = new Query();
			//设置分页
			query.skip((pageIndex - 1) * pageSize);
			query.limit(pageSize);
			//增加查询条件 personId
			Criteria criteria = Criteria.where("personId").is(personId);
			//增加时间查询条件
			String beginTimeDisp = URLDecoder.decode(pointsCollect.getBeginTimeDisp(), "UTF-8");
			String endTimeDisp = URLDecoder.decode(pointsCollect.getEndTimeDisp(), "UTF-8");
			beginTimeDisp = StringUtil.isBlank(beginTimeDisp) ? "" : beginTimeDisp +" 00:00:00";
			endTimeDisp = StringUtil.isBlank(endTimeDisp) ? "" : endTimeDisp + " 23:59:59";
			if(StringUtil.isNotBlank(beginTimeDisp) || StringUtil.isNotBlank(endTimeDisp)) {
				Criteria and = criteria.and("getTime");
				if(StringUtil.isNotBlank(beginTimeDisp)) {
					and.gte(getDateFromStr(beginTimeDisp, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtil.isNotBlank(endTimeDisp)) {
					and.lte(getDateFromStr(endTimeDisp, "yyyy-MM-dd HH:mm:ss"));
				}
			}
			query.addCriteria(criteria);
			//获取总数
			long count = mongoTemplate.count(query, PointsCollect.class);
			List<PointsCollect> pointsCollectList = mongoTemplate.find(query, PointsCollect.class);
			if (null == pointsCollectList) {
				throw new RepsException("mongodb查询失败");
			}
			ListResult<PointsCollect> listResult = new ListResult<>(
					pointsCollectList, count);
			listResult.setPageSize(pageSize);
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e.getMessage(), e);
		}
	}

}
