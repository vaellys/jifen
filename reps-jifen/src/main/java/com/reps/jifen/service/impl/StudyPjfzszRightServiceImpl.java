package com.reps.jifen.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reps.core.orm.ListResult;
import com.reps.jifen.dao.StudyPjfzszRightDao;
import com.reps.jifen.entity.StudyPjfzszRight;
import com.reps.jifen.service.IStudyPjfzszRightService;

@Transactional
@Service("com.reps.jifen.service.impl.AllocationIndexServiceImpl")
public class StudyPjfzszRightServiceImpl implements IStudyPjfzszRightService {
	
	@Autowired
	StudyPjfzszRightDao allocationDao;

	@Override
	public void save(StudyPjfzszRight data) {
		allocationDao.save(data);
	}

	@Override
	public void update(StudyPjfzszRight data) {
		allocationDao.update(data);
	}

	@Override
	public void delete(String id) {
		allocationDao.delete(id);
	}

	@Override
	public StudyPjfzszRight get(String id, boolean eager) {
		StudyPjfzszRight data = allocationDao.get(id);
		if (data != null && eager) {
			Hibernate.initialize(data.getPerson());
			Hibernate.initialize(data.getStudy());
		}
		return data;
	}

	@Override
	public List<StudyPjfzszRight> find(StudyPjfzszRight query) {
		return allocationDao.find(query);
	}

	@Override
	public ListResult<StudyPjfzszRight> query(int start, int pageSize,
			StudyPjfzszRight query) {
		return allocationDao.query(start, pageSize, query);
	}

}
