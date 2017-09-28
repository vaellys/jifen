package com.reps.jifen.service.impl;

import static com.reps.core.util.DateUtil.format;
import static com.reps.core.util.DateUtil.getDateFromStr;
import static com.reps.jifen.entity.enums.AuditStatus.REJECTED;
import static com.reps.jifen.entity.enums.CategoryType.ACTIVITY;
import static com.reps.jifen.entity.enums.ParticipateStatus.PARTICIPATED;
import static com.reps.jifen.entity.enums.RewardStatus.PUBLISHED;
import static com.reps.jifen.entity.enums.RewardStatus.SOLD_OUT;
import static com.reps.jifen.entity.enums.RewardStatus.UN_PUBLISH;
import static com.reps.jifen.entity.enums.ValidRecord.VALID;
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
import com.reps.jifen.dao.PointRewardDao;
import com.reps.jifen.entity.PointActivityInfo;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IActivityRewardService;
import com.reps.jifen.service.IPointActivityInfoService;

/**
 * 积分活动业务实现
 * @author qianguobing
 * @date 2017年8月18日 上午10:48:17
 */
@Service
@Transactional
public class ActivityRewardServiceImpl implements IActivityRewardService {
	
	protected final Logger logger = LoggerFactory.getLogger(ActivityRewardServiceImpl.class);
	
	private static final Short CANCEL_ACTIVITY = 0;

	@Autowired
	PointRewardDao dao;
	
	@Autowired
	IPointActivityInfoService pointActivityInfoService;
	
	@Override
	public void save(PointReward jfReward) throws RepsException{
		//设置活动开始时间
		Date beginTime = getBeginTimeDisp(jfReward);
		//设置活动结束时间
		Date endTime = getEndTimeDisp(jfReward);
		jfReward.setBeginTime(beginTime);
		jfReward.setEndTime(endTime);
		//设置截止时间
		Date finishTime = getFinishTimeDisp(jfReward);
		jfReward.setFinishTime(finishTime);
		if(null != beginTime && null != finishTime) {
			if(finishTime.getTime() >= beginTime.getTime()) {
				throw new RepsException("保存异常:报名截止日期大于活动开始时间");
			}
		}else {
			throw new RepsException("活动开始日期或报名截止日期为空");
		}
		jfReward.setCreateTime(new Date());
		//设置是否发布，默认未发布
		jfReward.setIsShown(UN_PUBLISH.getIndex());
		//有效，默认有效
		jfReward.setValidRecord(VALID.getId());
		dao.save(jfReward);
	}

	@Override
	public void delete(PointReward jfReward) throws RepsException{
		if(null == jfReward) {
			throw new RepsException("删除异常");
		}
		Integer exchangedCount = jfReward.getExchangedCount();
		if(null != exchangedCount && exchangedCount.intValue() > 0) {
			throw new RepsException("删除异常:该活动有人参与过不能删除");
		}
		dao.delete(jfReward);
	}

	@Override
	public void update(PointReward jfReward) throws RepsException{
		if (jfReward == null) {
			throw new RepsException("参数异常");
		}
		PointReward pointReward = this.get(jfReward.getId());
		RewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
		if(null != jfRewardCategory) {
			pointReward.setJfRewardCategory(jfRewardCategory);
		}
		String name = jfReward.getName();
		if (StringUtil.isNotBlank(name)) {
			pointReward.setName(name);
		}
		Integer points = jfReward.getPoints();
		if (null != points) {
			pointReward.setPoints(points);
		}
		String description = jfReward.getDescription();
		if (StringUtil.isNotBlank(description)) {
			pointReward.setDescription(description);
		}
		String pictureUrl = jfReward.getPicture();
		if(StringUtil.isNotBlank(pictureUrl)) {
			pointReward.setPicture(pictureUrl);
		}
		//设置上线时间
		Date beginTime = getBeginTimeDisp(jfReward);
		if(null != beginTime) {
			pointReward.setBeginTime(beginTime);
		}
		//设置上线时间
		Date endTime = getEndTimeDisp(jfReward);
		if(null != endTime) {
			pointReward.setEndTime(endTime);
		}
		//设置截止时间
		Date finishTime = getFinishTimeDisp(jfReward);
		if(null != finishTime) {
			pointReward.setFinishTime(finishTime);;
		}
		Integer participatedCount = jfReward.getParticipatedCount();
		if(null != participatedCount) {
			pointReward.setParticipatedCount(participatedCount);
		}
		Integer exchangedCount = jfReward.getExchangedCount();
		if(null != exchangedCount) {
			pointReward.setExchangedCount(exchangedCount);
		}
		Short validRecord = jfReward.getValidRecord();
	    if(null != validRecord) {
	    	pointReward.setValidRecord(validRecord);
	    }
	    Short isShown = jfReward.getIsShown();
	    if(null != isShown) {
	    	pointReward.setIsShown(isShown);
	    }
		dao.update(pointReward);
	}

	@Override
	public PointReward get(String id) throws RepsException{
		if(StringUtil.isBlank(id)) {
			throw new RepsException("查询异常:活动ID不能为空");
		}
		PointReward pointReward = dao.get(id);
		if(null == pointReward) {
			throw new RepsException("查询异常:该活动不存在");
		}
		return pointReward;
	}

	@Override
	public ListResult<PointReward> query(int start, int pagesize, PointReward jfReward) {
		//设置截止时间
		jfReward.setFinishTime(getFinishTimeDisp(jfReward));
		return dao.query(start, pagesize, jfReward);
	}

	private Date getFinishTimeDisp(PointReward jfReward) {
		String finishTimeDisp = jfReward.getFinishTimeDisp();
		if(StringUtil.isNotBlank(finishTimeDisp)) {
			Date finishTime = getDateFromStr(finishTimeDisp, "yyyy-MM-dd");
			return finishTime;
		}else {
			return null;
		}
	}

	private Date getBeginTimeDisp(PointReward jfReward) {
		String beginTimeDisp = jfReward.getBeginTimeDisp();
		if(StringUtil.isNotBlank(beginTimeDisp)) {
			Date beginTime = getDateFromStr(beginTimeDisp, "yyyy-MM-dd");
			return beginTime;
		}else {
			return null;
		}
	}
	
	private Date getEndTimeDisp(PointReward jfReward) {
		String endTimeDisp = jfReward.getEndTimeDisp();
		if(StringUtil.isNotBlank(endTimeDisp)) {
			Date endTime = getDateFromStr(endTimeDisp, "yyyy-MM-dd");
			return endTime;
		}else {
			return null;
		}
	}
	
	@Override
	public List<PointReward> getActivityRewardOfCategory(String cid) {
		return dao.getRewardOfCategory(cid);
	}

	@Override
	public void batchDelete(String ids) {
		dao.batchDelete(ids);
	}
	
	@Override
	public void updatePublish(String id, Short status, String serverPath) throws Exception{
		if(StringUtil.isBlank(id)) {
			throw new RepsException("活动ID不能为空");
		}
		if(null == status) {
			throw new RepsException("活动状态不能为空");
		}
		PointReward pointReward = get(id);
		//检查截止时间
		checkFinishTime(pointReward);
		//取消活动
		if(CANCEL_ACTIVITY.shortValue() == status.shortValue()) {
			PointActivityInfo activityInfo = new PointActivityInfo();
			activityInfo.setRewardId(id);
			activityInfo.setAuditStatus(REJECTED.getId());
			//取消活动时，此人为参与中，审核状态为未审核和审核通过，这些人返还积分
			activityInfo.setIsParticipate(PARTICIPATED.getId());
			List<PointActivityInfo> list = pointActivityInfoService.find(activityInfo);
			for (PointActivityInfo pointActivityInfo : list) {
				this.cancelActivity(pointActivityInfo, pointReward.getPoints(), serverPath);
			}
		}
		//修改活动状态
		PointReward jfReward = new PointReward();
		jfReward.setId(id);
		jfReward.setIsShown(status);
		this.update(jfReward );
	}
	
	private void cancelActivity(PointActivityInfo pointActivityInfo, Integer points, String serverPath) throws Exception {
		String studentId = pointActivityInfo.getStudentId();
		String rewardId = pointActivityInfo.getRewardId();
		if(StringUtil.isBlank(studentId) || StringUtil.isBlank(rewardId) || null == points) {
			throw new RepsException("活动数据异常");
		}
		//请求mongodb 修改个人积分，保存积分日志
		doPosts(studentId, rewardId, points, serverPath);
	}
	
	@Override
	public void batchPublish(String ids, Short status) throws RepsException{
		if (StringUtil.isBlank(ids)) {
			throw new RepsException("发布异常:活动ID不能为空");
		}
		if (null == status) {
			throw new RepsException("发布异常:活动状态不能为空");
		}
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			PointReward pointReward = this.get(id);
			checkFinishTime(pointReward);
		}
		dao.batchUpdate(ids, status);
	}

	private void checkFinishTime(PointReward pointReward) throws RepsException {
		Date finishTime = pointReward.getFinishTime();
		if(null == finishTime) {
			throw new RepsException("截止时间为空");
		}
		if(finishTime.getTime() < getDateFromStr(format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime()) {
			throw new RepsException("活动截止时间小于当前时间,请修改截止时间后再发布！");
		}
	}
	
	@Scheduled(cron = "0 0 2 * * ?")
//	@Scheduled(cron = "*/20 * * * * ?")
	public void activityExpired() {
		try {
			PointReward jfReward = new PointReward();
			RewardCategory category = new RewardCategory();
			category.setType(ACTIVITY.getIndex());
			jfReward.setIsShown(PUBLISHED.getIndex());
			jfReward.setJfRewardCategory(category);
			List<PointReward> activityList = getActivityRewardByCategoryType(jfReward);
			//获取当前系统时间
			long currentTime = getDateFromStr(format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			for (PointReward activity : activityList) {
				//获取活动过期时间
				long expireTime = activity.getFinishTime().getTime();
				if(expireTime - currentTime < 0) {
					try {
						dao.batchUpdate(activity.getId(), SOLD_OUT.getIndex());
					} catch (Exception e) {
						logger.error("活动过期状态更新失败,该活动信息为:活动ID " + jfReward.getId() + ", 活动状态  " + jfReward.getIsShown() + ", 活动截止时间 " + jfReward.getFinishTime());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活动过期异常", e);
		}
	}

	@Override
	public List<PointReward> getActivityRewardByCategoryType(PointReward jfReward) {
		return dao.getRewardByCategoryType(jfReward);
	}
	
	@Override
	public void delay(PointReward jfReward) throws RepsException {
		if(null == jfReward) {
			throw new RepsException("活动数据异常");
		}
		String id = jfReward.getId();
		if(StringUtil.isBlank(id)) {
			throw new RepsException("数据异常:活动ID不能为空");
		}
		if(null == jfReward.getFinishTimeDisp()) {
			throw new RepsException("数据异常:活动截止时间不能为空");
		}
		this.update(jfReward);
		batchPublish(id, PUBLISHED.getIndex());
	}

}
