package com.reps.jifen.action;

import static com.reps.jifen.entity.enums.CategoryType.REWARD;
import static com.reps.jifen.entity.enums.RewardStatus.UN_PUBLISH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.PointReward;
import com.reps.jifen.entity.RewardCategory;
import com.reps.jifen.service.IRewardCategoryService;
import com.reps.jifen.service.IRewardService;
import com.reps.jifen.vo.ConfigurePath;

/**
 * 积分物品管理相关操作
 * 
 * @author qianguobing
 * @date 2017年8月16日 上午9:15:32
 */
@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/reward")
public class PointRewardAction extends BaseAction {

	protected final Logger logger = LoggerFactory.getLogger(PointRewardAction.class);
	
	@Autowired
	IRewardService jfRewardService;

	@Autowired
	IRewardCategoryService jfRewardCategoryService;

	/**
	 * 积分物品管理列表
	 * 
	 * @param pager
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/list")
	public Object list(Pagination pager, PointReward jfReward) {
		try {
			ModelAndView mav = getModelAndView("/jifen/reward/list");
			RewardCategory jfRewardCategory = jfReward.getJfRewardCategory();
			if (null == jfRewardCategory) {
				jfRewardCategory = new RewardCategory();
				jfReward.setJfRewardCategory(jfRewardCategory);
			}
			// 设置物品类别
			jfRewardCategory.setType(REWARD.getIndex());
			ListResult<PointReward> listResult = jfRewardService.query(pager.getStartRow(), pager.getPageSize(), jfReward);
			// 查询物品类型
			List<RewardCategory> categoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
			Map<String, String> activityTypeMap = new HashMap<>();
			activityTypeMap.put("", "全部物品");
			for (RewardCategory category : categoryList) {
				activityTypeMap.put(category.getId(), category.getName());
			}
			mav.addObject("rewardTypeMap", activityTypeMap);
			mav.addObject("jfReward", jfReward);
			// 分页数据
			mav.addObject("list", listResult.getList());
			// 分页参数
			pager.setTotalRecord(listResult.getCount().longValue());
			mav.addObject("pager", pager);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 添加物品页面入口
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd() {
		ModelAndView mav = getModelAndView("/jifen/reward/add");
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		return mav;
	}

	/**
	 * 添加物品
	 * 
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(PointReward jfReward){
		try {
			if (jfReward == null) {
				throw new RepsException("数据不完整");
			}
			jfRewardService.save(jfReward);
			return ajax(AjaxStatus.OK, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 物品管理添加页面选择物品分类，展示树形结构
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/choose")
	public Object choose() {
		ModelAndView mav = new ModelAndView("/jifen/reward/choose");
		RewardCategory jfRewardCategory = new RewardCategory();
		// 设置物品类别
		jfRewardCategory.setType(REWARD.getIndex());
		List<RewardCategory> rewardCategoryList;
		try {
			rewardCategoryList = jfRewardCategoryService.getRewardCategory(jfRewardCategory);
			mav.addObject("treelist", rewardCategoryList);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 修改物品页面入口
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping(value = "/toedit")
	public Object toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/reward/edit");
		PointReward jfReward;
		try {
			jfReward = jfRewardService.get(id);
			mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
			mav.addObject("reward", jfReward);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 修改物品
	 * 
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(PointReward jfReward){
		try {
			jfRewardService.update(jfReward);
			return ajax(AjaxStatus.OK, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 删除物品
	 * 
	 * @param jfReward
	 * @return Object
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(PointReward jfReward) {
		try {
			jfRewardService.update(jfReward);
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除活动失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 批量删除物品
	 * 
	 * @param ids
	 *            物品id以逗号分隔拼接
	 * @return Object
	 */
	@RequestMapping(value = "/batchdelete")
	@ResponseBody
	public Object batchDelete(String ids) {
		try {
			if (StringUtil.isBlank(ids)) {
				return ajax(AjaxStatus.ERROR, "删除失败");
			}
			jfRewardService.batchDelete(ids);
			return ajax(AjaxStatus.OK, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量删除活动失败", e);
			return ajax(AjaxStatus.ERROR, "删除失败");
		}
	}

	/**
	 * 批量发布物品
	 * 
	 * @param ids
	 * @return Object
	 */
	@RequestMapping(value = "/batchpublish")
	@ResponseBody
	public Object batchPublish(String ids, Short status) {
		try {
			jfRewardService.batchPublish(ids, status);
			if(UN_PUBLISH.getIndex() == status) {
				return ajax(AjaxStatus.OK, "取消发布成功");
			}else {
				return ajax(AjaxStatus.OK, "发布成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

	/**
	 * 物品详情展示页面
	 * 
	 * @param id
	 * @return Object
	 */
	@RequestMapping({ "/show" })
	public Object show(String id) {
		ModelAndView mav = new ModelAndView("/jifen/reward/show");
		PointReward jfReward;
		try {
			jfReward = jfRewardService.get(id);
			mav.addObject("reward", jfReward);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

}
