package com.reps.jifen.service.impl;

import static com.reps.core.util.DateUtil.format;
import static com.reps.core.util.DateUtil.getDateFromStr;
import static com.reps.jifen.entity.enums.AuditStatus.CHECK_PENDING;
import static com.reps.jifen.entity.enums.AuditStatus.PASSED;
import static com.reps.jifen.entity.enums.AuditStatus.REJECTED;
import static com.reps.jifen.entity.enums.ParticipateStatus.AUDIT_PASSED;
import static com.reps.jifen.entity.enums.ParticipateStatus.AUDIT_REJECTED;
import static com.reps.jifen.entity.enums.ParticipateStatus.CANCEL_PARTICIPATE;
import static com.reps.jifen.entity.enums.ParticipateStatus.PARTICIPATED;
import static com.reps.jifen.util.ActivityUtil.doPosts;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.ActivityInfoDao;
import com.reps.jifen.entity.ActivityReward;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.jifen.service.IActivityRewardService;
import com.reps.jifen.service.IPointActivityInfoService;

/**
 * 活动统计管理
 * @author qianguobing
 * @date 2017年9月19日 上午9:40:07
 */
@Service
@Transactional
public class PointActivityInfoServiceImpl implements IPointActivityInfoService {
	
	private Logger logger = LoggerFactory.getLogger(PointActivityInfoServiceImpl.class);
	
	@Autowired
	ActivityInfoDao dao;
	
	@Autowired
	IActivityRewardService activityRewardService;
	
	@Override
	public void save(PointActivityInfo activityInfo) throws RepsException{
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		String studentId = activityInfo.getStudentId();
		String rewardId = activityInfo.getRewardId();
		PointActivityInfo info = new PointActivityInfo();
		info.setStudentId(studentId);
		info.setRewardId(rewardId);
		PointActivityInfo pointActivityInfo = get(activityInfo);
		//第一次参与
		if(null == pointActivityInfo) {
			activityInfo.setCreateTime(new Date());
			activityInfo.setIsParticipate(PARTICIPATED.getId());
			//参与活动记录保存
			dao.save(activityInfo);
		//第二次参与,更新状态
		}else {
			Short isParticipate = pointActivityInfo.getIsParticipate();
			if(null == isParticipate) {
				throw new RepsException("该参与活动记录数据异常");
			}
			if(PARTICIPATED.getId().shortValue() == isParticipate.shortValue()) {
				throw new RepsException("参与活动异常:您已经参与了该活动");
			}
			pointActivityInfo.setIsParticipate(PARTICIPATED.getId());
			update(pointActivityInfo);
		}
		//更新参与人数
		ActivityReward pointReward = activityRewardService.get(rewardId);
		Integer participatedCount = pointReward.getParticipatedCount();
		pointReward.setParticipatedCount((null == participatedCount ? 0 : participatedCount) + 1);
		activityRewardService.update(pointReward);
	}

	@Override
	public ListResult<PointActivityInfo> query(int start, int pagesize, PointActivityInfo activityInfo) throws RepsException{
		return dao.query(start, pagesize, activityInfo);
	}

	@Override
	public Long count(String rewardId, Short isParticipate) throws RepsException {
		if(StringUtil.isBlank(rewardId)) {
			throw new RepsException("统计异常:活动ID不能为空");
		}
		if(null == isParticipate) {
			throw new RepsException("统计异常:参与状态不能为空");
		}
		return dao.count(rewardId, isParticipate);
	}

	@Override
	public void update(PointActivityInfo activityInfo) throws RepsException {
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		PointActivityInfo pointActivityInfo = this.get(activityInfo);
		if(null == pointActivityInfo) {
			throw new RepsException("该活动记录不存在");
		}
		Short isParticipate = activityInfo.getIsParticipate();
		if(null != isParticipate) {
			pointActivityInfo.setIsParticipate(isParticipate);
		}
		Short auditStatus = activityInfo.getAuditStatus();
		if(null != auditStatus) {
			pointActivityInfo.setAuditStatus(auditStatus);
		}
		String auditOpinion = activityInfo.getAuditOpinion();
		if(StringUtil.isNotBlank(auditOpinion)) {
			pointActivityInfo.setAuditOpinion(auditOpinion);
		}
		Date createTime = activityInfo.getCreateTime();
		if(null != createTime) {
			pointActivityInfo.setCreateTime(createTime);
		}
		dao.update(pointActivityInfo);
	}
	
	@Override
	public void cancelParticipate(PointActivityInfo activityInfo) throws RepsException{
		PointActivityInfo pointActivityInfo = this.get(activityInfo);
		if(null == pointActivityInfo) {
			throw new RepsException("活动异常:该活动记录不存在");
		}
		Short isParticipate = pointActivityInfo.getIsParticipate();
		Short auditStatus = pointActivityInfo.getAuditStatus();
		if(null != auditStatus) {
			if(PASSED.getId().shortValue() == auditStatus.shortValue()) {
				throw new RepsException("活动异常:该参与活动已经审核通过，不能取消");
			}
		}
		if(null == isParticipate) {
			throw new RepsException("该参与活动记录数据异常");
		}
		if(CANCEL_PARTICIPATE.getId().shortValue() == isParticipate.shortValue()) {
			throw new RepsException("取消活动异常:该参与活动记录已经被取消了");
		}
		//取消活动
		activityInfo.setIsParticipate(CANCEL_PARTICIPATE.getId());
		this.update(activityInfo);
		//更新活动表，已参与人数
		ActivityReward jfReward = activityRewardService.get(activityInfo.getRewardId());
		Integer participatedCount = jfReward.getParticipatedCount();
		jfReward.setParticipatedCount(null == participatedCount ? 0 : participatedCount - 1);
		activityRewardService.update(jfReward);
	}

	@Override
	public PointActivityInfo get(String id) throws RepsException {
		if(StringUtil.isBlank(id)) {
			throw new RepsException("查询异常:活动参与ID不能为空");
		}
		PointActivityInfo activityInfo = dao.get(id);
		if(null == activityInfo) {
			throw new RepsException("查询异常:该活动参与记录不存在");
		}
		return activityInfo;
	}
	
	@Override
	public PointActivityInfo get(PointActivityInfo activityInfo) throws RepsException{
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		List<PointActivityInfo> infoList = dao.find(activityInfo);
		if(null != infoList && infoList.size() > 0) {
			return infoList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void updateAudit(PointActivityInfo activityInfo, String serverPath) throws Exception {
		if(null == activityInfo) {
			throw new RepsException("参数异常");
		}
		Short auditStatus = activityInfo.getAuditStatus();
		if(null == auditStatus) {
			throw new RepsException("审核异常:审核状态不能为空");
		}
		String id = activityInfo.getId();
		if(StringUtil.isBlank(id)) {
			throw new RepsException("审核异常:活动参与记录ID不能为空");
		}
		PointActivityInfo pointActivityInfo = this.get(id);
		String studentId = pointActivityInfo.getStudentId();
		String rewardId = pointActivityInfo.getRewardId();
		if(StringUtil.isBlank(id) || StringUtil.isBlank(rewardId)) {
			throw new RepsException("活动参与记录数据异常");
		}
		ActivityReward pointReward = activityRewardService.get(rewardId);
		Integer points = pointReward.getPoints();
		if(null == points) {
			throw new RepsException("该活动数据异常");
		}
		if(REJECTED.getId().shortValue() == auditStatus.shortValue()) {
			//请求mongodb 修改个人积分，保存积分日志
			doPosts(studentId, rewardId, points, serverPath);
			//修改活动记录表和活动信息表状态
			//cancelParticipate(buildParam(activityInfo, auditStatus, studentId, rewardId));
			//同时更新参与状态
			activityInfo.setIsParticipate(AUDIT_REJECTED.getId());
		}
		//同时更新参与状态
		activityInfo.setIsParticipate(AUDIT_PASSED.getId());
		update(buildParam(activityInfo, auditStatus, studentId, rewardId));
	}

	private PointActivityInfo buildParam(PointActivityInfo activityInfo, Short auditStatus, String studentId, String rewardId) {
		PointActivityInfo info = new PointActivityInfo();
		info.setAuditStatus(auditStatus);
		info.setAuditOpinion(activityInfo.getAuditOpinion());
		info.setStudentId(studentId);
		info.setRewardId(rewardId);
		info.setIsParticipate(activityInfo.getIsParticipate());
		return info;
	}
	
	@Override
	public List<PointActivityInfo> find(PointActivityInfo activityInfo) throws RepsException {
		return dao.find(activityInfo);
	}

	@Override
	public List<PointActivityInfo> findNotAudit(PointActivityInfo activityInfo) throws RepsException {
		return dao.findNotAudit(activityInfo);
	}
	
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(cron = "*/20 * * * * ?")
	@Override
	public void scheduleAudit() {
		try {
			PointActivityInfo activityInfo = new PointActivityInfo();
			activityInfo.setAuditStatus(CHECK_PENDING.getId());
			List<PointActivityInfo> findNotAuditList = dao.findNotAudit(activityInfo);
			//获取当前系统时间
			long currentTime = getDateFromStr(format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			for (PointActivityInfo info : findNotAuditList) {
				ActivityReward pointReward = info.getPointReward();
				if(null != pointReward) {
					//获取活动过期时间
					long expireTime = pointReward.getFinishTime().getTime();
					if(expireTime - currentTime < 0) {
						try {
							info.setAuditStatus(PASSED.getId());
							this.updateAudit(info, null);
						} catch (Exception e) {
							logger.error("活动过期审核状态更新失败,该活动信息为:活动记录ID " + info.getId() + ", 审核状态  " + info.getAuditStatus() + ", 报名截止时间 " + pointReward.getFinishTime());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活动审核异常", e);
		}
	}

}
