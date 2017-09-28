package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.ActivityReward;

/**
 * 活动奖品相关操作
 * 
 * @author qianguobing
 * @date 2017年8月18日 上午10:39:31
 */
public interface IActivityRewardService {

	/**
	 * 添加活动
	 * 
	 * @param jfReward
	 */
	public void save(ActivityReward jfReward) throws RepsException;

	/**
	 * 删除活动
	 * 
	 * @param jfReward
	 */
	public void delete(ActivityReward jfReward) throws RepsException;

	/**
	 * 修改活动
	 * 
	 * @param jfReward
	 * @throws RepsException 
	 */
	public void update(ActivityReward jfReward) throws RepsException;
	
	/**
	 * 活动延期
	 * @param jfReward
	 */
	public void delay(ActivityReward jfReward) throws RepsException;

	/**
	 * 通过指定id获得对象
	 * 
	 * @param id
	 * @return JfReward
	 */
	public ActivityReward get(String id) throws RepsException;

	/**
	 * 分页查询
	 * 
	 * @param start
	 * @param pagesize
	 * @param jfReward
	 * @return ListResult<JfReward>
	 */
	ListResult<ActivityReward> query(int start, int pagesize, ActivityReward jfReward);

	/**
	 * 根据奖品分类id，得到该分类下的所有活动
	 * 
	 * @param cid 分类id
	 * @return JfReward
	 */
	public List<ActivityReward> getActivityRewardOfCategory(String cid);

	/**
	 * 批量删除活动
	 * @param ids
	 */
	public void batchDelete(String ids);

	/**
	 * 批量发布活动
	 * @param ids
	 * @param status
	 */
	public void batchPublish(String ids, Short status) throws RepsException;
	
	/**
	 * 根据分类类别查询活动
	 * @param jfReward
	 * @return List<JfReward>
	 */
	public List<ActivityReward> getActivityRewardByCategoryType(ActivityReward jfReward);

	/**
	 * 根据兑截止换时间来判断活动是否下架,设置为已下架 2
	 */
	public void activityExpired();

	/**
	 * 取消活动
	 * @param id
	 * @param status
	 * @param serverPath
	 * @throws Exception
	 */
	public void updatePublish(String id, Short status, String serverPath) throws Exception;

}
