package com.reps.jifen.service;

import java.util.List;

import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.StudyPjfzszRight;

public interface IStudyPjfzszRightService {

	
	void save(StudyPjfzszRight data);
	
	void update(StudyPjfzszRight data);
	
	void delete(String id);
	
	StudyPjfzszRight get(String id, boolean eager);
	
	List<StudyPjfzszRight> find(StudyPjfzszRight query);
	
	ListResult<StudyPjfzszRight> query(int start, int pageSize, StudyPjfzszRight query);
	
}
