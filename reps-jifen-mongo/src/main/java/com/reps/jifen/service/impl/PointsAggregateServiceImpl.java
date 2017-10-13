package com.reps.jifen.service.impl;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.PointsAggregate;
import com.reps.jifen.repository.IPointsAggregateRepository;
import com.reps.jifen.service.IPointsAggregateService;

@Service
public class PointsAggregateServiceImpl implements IPointsAggregateService {
	
	private static Log logger = LogFactory.getLog(PointsAggregateServiceImpl.class);
	
	@Autowired
	private IPointsAggregateRepository repository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<PointsAggregate> findByPersonId(String personId) {
		return repository.findByPersonId(personId);
	}
	
	@Override
	public List<PointsAggregate> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public void save(PointsAggregate data) {
		repository.save(data);
	}

	@Override
	public void update(PointsAggregate data) {
		repository.save(data);
	}

	@Override
	public PointsAggregate getByPersonId(String personId) {
		return repository.getByPersonId(personId);
	}

	@Override
	public ListResult<PointsAggregate> findAll(PointsAggregate pointsAggregate, Integer pageIndex, Integer pageSize) throws RepsException {
		try {
			// 设置分页参数
			pageIndex = null == pageIndex ? 1 : pageIndex;
			pageSize = null == pageSize ? 20 : pageSize;
			Query query = new Query();
			//设置分页
			query.skip((pageIndex - 1) * pageSize);
			query.limit(pageSize);
			String personName = pointsAggregate.getPersonName();
			personName = URLDecoder.decode(StringUtil.isBlank(personName) ? "" : personName, "UTF-8");
			String schoolName = pointsAggregate.getSchoolName();
			schoolName = URLDecoder.decode(StringUtil.isBlank(schoolName) ? "" : schoolName, "UTF-8");
			Criteria criteria = null;
			if(StringUtil.isNotBlank(personName)) {
				//增加查询条件 personName
				criteria = Criteria.where("personName").regex(".*?\\" + personName + ".*");  
			}
			if(StringUtil.isNotBlank(schoolName)) {
				if(null != criteria) {
					criteria.and("schoolName").is(schoolName);
				}else {
					criteria = Criteria.where("schoolName").regex(".*?\\" + schoolName + ".*");  
				}
			}
			if(null != criteria) {
				query.addCriteria(criteria);
			}
			//获取总数
			long count = mongoTemplate.count(query, PointsAggregate.class);
			List<PointsAggregate> pointsCollectList = mongoTemplate.find(query, PointsAggregate.class);
			if (null == pointsCollectList) {
				throw new RepsException("mongodb查询失败");
			}
			ListResult<PointsAggregate> listResult = new ListResult<>(
					pointsCollectList, count);
			listResult.setPageSize(pageSize);
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求异常", e);
			throw new RepsException(e.getMessage(), e);
		}
	}

	@Override
	public List<PointsAggregate> findByCount(Integer count) throws RepsException {
		if (null == count) {
			throw new RepsException("查询异常:count不能为空");
		}
		// 构建分页对象
		PageRequest pageRequest = new PageRequest(0,
				count, new Sort(new Order(Direction.DESC, "totalPoints")));
		Page<PointsAggregate> page = repository.findAll(pageRequest);
		if (null == page) {
			throw new RepsException("mongodb查询失败");
		}
		return page.getContent();
	}
	
}
