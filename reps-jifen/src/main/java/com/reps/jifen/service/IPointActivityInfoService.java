package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.PointActivityInfo;

public interface IPointActivityInfoService {
	
	public void save(PointActivityInfo activityInfo) throws RepsException;
	
	public void update(PointActivityInfo activityInfo) throws RepsException;
	
	public PointActivityInfo get(String id) throws RepsException;
	
	public ListResult<PointActivityInfo> query(int start, int pagesize, PointActivityInfo activityInfo) throws RepsException;
	
	public Long count(String rewardId, Short isParticipate) throws RepsException;

	public PointActivityInfo get(PointActivityInfo activityInfo) throws RepsException;

	public void cancelParticipate(PointActivityInfo activityInfo) throws RepsException;
	
	public void updateAudit(PointActivityInfo activityInfo, String serverPath) throws Exception;
	
	public List<PointActivityInfo> find(PointActivityInfo activityInfo) throws RepsException;
	
	public List<PointActivityInfo> findNotAudit(PointActivityInfo activityInfo) throws RepsException;
	
}
