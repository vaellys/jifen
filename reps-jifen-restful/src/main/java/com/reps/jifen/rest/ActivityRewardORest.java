package com.reps.jifen.rest;

import static com.reps.jifen.entity.enums.ActivityStatus.*;
import static com.reps.jifen.util.PageUtil.cps;
import static com.reps.jifen.util.PageUtil.getStartIndex;
import static com.reps.jifen.util.RewardUtil.setActivityPictureUrls;
import static com.reps.jifen.util.RewardUtil.setReward;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.ActivityReward;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IActivityRewardService;

@RestController
@RequestMapping(value = "/oapi/activity")
public class ActivityRewardORest extends RestBaseController {

	private final Log logger = LogFactory.getLog(ActivityRewardORest.class);

	@Autowired
	private IActivityRewardService jfActivityRewardService;
	
	/**
	 * 查询已经发布的活动列表
	 * 
	 * @param jfReward
	 * @param pageIndex
	 * @param pageSize
	 * @return RestResponse<ListResult<JfReward>>
	 */
	@RequestMapping(value = "/list")
	public RestResponse<ListResult<ActivityReward>> list(ActivityReward jfReward, Integer pageIndex, Integer pageSize) {
		try {
			setReward(jfReward);
			pageSize = cps(pageSize);
			ListResult<ActivityReward> result = jfActivityRewardService.query(getStartIndex(pageIndex, pageSize), pageSize, jfReward);
			if(null == result) {
				throw new RepsException("查询活动列表异常");
			}
			// 设置页大小
			result.setPageSize(pageSize);
			setActivityPictureUrls(result.getList(), this.getFileHttpPath());
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常:" + e.getMessage());
		}
	}

	/**
	 * 根据参数数量返回活动列表数
	 * 
	 * @param count
	 * @return RestResponse<List<JfReward>>
	 */
	@RequestMapping(value = "/listcount")
	public RestResponse<List<ActivityReward>> findByCount(Integer count) {
		try {
			if (null == count) {
				throw new RepsException("查询异常:参数count不能为空");
			}
			ActivityReward jfReward = new ActivityReward();
			jfReward.setIsShown(PUBLISHED.getIndex());
			ListResult<ActivityReward> resultList = jfActivityRewardService.query(0, count, jfReward);
			if (null == resultList) {
				throw new RepsException("查询活动数量异常");
			}
			List<ActivityReward> result = resultList.getList();
			//设置图片地址
			setActivityPictureUrls(result, this.getFileHttpPath());
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * 活动详情
	 * 
	 * @param id
	 *            活动ID
	 * @return RestResponse<JfReward>
	 */
	@RequestMapping(value = "/detail")
	public RestResponse<ActivityReward> detail(String id) {
		try {
			ActivityReward jfReward = jfActivityRewardService.get(id);
			if(null == jfReward) {
				throw new RepsException("查询活动异常:该活动不存在");
			}
			RewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
			if(null == jfRewardCategory) {
				throw new RepsException("查询活动异常:该活动所在分类不存在");
			}
			//设置分类名称
			jfReward.setCategoryName(jfRewardCategory.getName());
			jfReward.setPicture(getFileFullUrl(jfReward.getPicture(), null));
			return wrap(RestResponseStatus.OK, "查询成功", jfReward);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
