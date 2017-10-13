package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.vo.PointsAggregate;

public interface IPointsAggregateService {
	
	public ListResult<PointsAggregate> query(int start, int pageSize, String serverPath, PointsAggregate pointsAggregate) throws RepsException;
	
}
