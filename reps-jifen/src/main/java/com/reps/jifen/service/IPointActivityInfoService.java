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
	
	public Long count(String rewardId, List<Short> isParticipates) throws RepsException;

	public PointActivityInfo get(PointActivityInfo activityInfo) throws RepsException;

	public void cancelParticipate(PointActivityInfo activityInfo) throws RepsException;
	
	public void updateAudit(PointActivityInfo activityInfo, String serverPath) throws Exception;
	
	public List<PointActivityInfo> find(PointActivityInfo activityInfo) throws RepsException;
	
	public List<PointActivityInfo> findNotAudit(PointActivityInfo activityInfo) throws RepsException;

	/**
	 * 活动参与超过截止时间时，管理员还没审核，则系统自动审核通过
	 * 定时任务，每天凌晨2点执行一次
	 */
	public void updateScheduleAudit();
	
}
