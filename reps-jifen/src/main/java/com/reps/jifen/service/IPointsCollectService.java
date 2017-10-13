package com.reps.jifen.service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.vo.PointsCollect;

public interface IPointsCollectService {
	
	public ListResult<PointsCollect> query(int start, int pageSize, String serverPath, PointsCollect pointsCollect) throws RepsException;
	
}
