package com.reps.jifen.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reps.core.orm.IGenericDao;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.StudyPjfzszRight;

@Repository
public class StudyPjfzszRightDao {

	@Autowired
	IGenericDao<StudyPjfzszRight> dao;
	
	public void save(StudyPjfzszRight data) {
		dao.save(data);
	}
	
	public void update(StudyPjfzszRight data) {
		dao.update(data);
	}
	
	public StudyPjfzszRight get(String id) {
		return dao.get(StudyPjfzszRight.class, id);
	}
	
	public void delete(String id) {
		StudyPjfzszRight data = get(id);
		if (data != null) {
			dao.delete(data);
		}
	}
	
	public List<StudyPjfzszRight> find(StudyPjfzszRight query) {
		DetachedCriteria dc = DetachedCriteria.forClass(StudyPjfzszRight.class);
		dc.createAlias("person", "person");
		dc.createAlias("study", "study");
		if (query != null) {
			if (StringUtils.isNotBlank(query.getTeacherId())) {
				dc.add(Restrictions.eq("teacherId", query.getTeacherId()));
			}
			if (StringUtils.isNotBlank(query.getBehaviorId())) {
				dc.add(Restrictions.eq("behaviorId", query.getBehaviorId()));
			}
		}
		return dao.findByCriteria(dc);
	}
	
	public ListResult<StudyPjfzszRight> query(int start, int pageSize, StudyPjfzszRight query) {
		DetachedCriteria dc = DetachedCriteria.forClass(StudyPjfzszRight.class);
		dc.createAlias("person", "person");
		dc.createAlias("study", "study");
		if (query != null) {
			if (StringUtils.isNotBlank(query.getTeacherId())) {
				dc.add(Restrictions.eq("teacherId", query.getTeacherId()));
			}
			if (StringUtils.isNotBlank(query.getBehaviorId())) {
				dc.add(Restrictions.eq("behaviorId", query.getBehaviorId()));
			}
		}
		return dao.query(dc, start, pageSize);
	}
}
