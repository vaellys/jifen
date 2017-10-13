package com.reps.jifen.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.LoginToken;
import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.entity.StudyPjfzszRight;
import com.reps.jifen.entity.StudyAssessPoints;
import com.reps.jifen.entity.enums.AccessCategory;
import com.reps.jifen.service.IStudyPjfzszRightService;
import com.reps.jifen.service.IAssessPointsService;
import com.reps.jifen.vo.ConfigurePath;
import com.reps.system.entity.Organize;
import com.reps.system.entity.User;
import com.reps.system.service.IUserService;

/**
 * 校园/学习评价分值设置
 * @author zf
 * 
 */
@Controller("com.reps.jifen.action.AssessPointsAction")
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/pjfzsz")
public class AssessPointsAction extends BaseAction {
	
	private final Log logger = LogFactory.getLog(AssessPointsAction.class);
	
	private final String teacherIdentity = "20";
	
	@Autowired
	IAssessPointsService assessPointsService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IStudyPjfzszRightService allocationService;

	@RequestMapping(value = "/xxlist")
	public ModelAndView xxlist(Pagination pager, StudyAssessPoints query) {

		ModelAndView mav = getModelAndView("/jifen/pjfzsz/xxlist");
		// 学习评价指标设置
		query.setCategory(AccessCategory.XXHD.getCode());
		ListResult<StudyAssessPoints> result = assessPointsService.query(
				pager.getStartRow(), pager.getPageSize(), query);
		pager.setTotalRecord(result.getCount());
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/xylist")
	public ModelAndView xylist(Pagination pager, StudyAssessPoints query) {

		ModelAndView mav = getModelAndView("/jifen/pjfzsz/xylist");
		// 校园评价指标设置
		query.setCategory(AccessCategory.XYXW.getCode());
		ListResult<StudyAssessPoints> result = assessPointsService.query(
				pager.getStartRow(), pager.getPageSize(), query);
		pager.setTotalRecord(result.getCount());
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/xyxzlist")
	public ModelAndView xyxzlist(Pagination pager, StudyAssessPoints query) {

		ModelAndView mav = getModelAndView("/jifen/pjfzsz/xyxzlist");
		// 校园评价指标设置
		query.setCategory(AccessCategory.XYXW.getCode());
		ListResult<StudyAssessPoints> result = assessPointsService.query(
				pager.getStartRow(), pager.getPageSize(), query);
		pager.setTotalRecord(result.getCount());
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd(String category) {
		ModelAndView mav = getModelAndView("/jifen/pjfzsz/add");
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		mav.addObject("category", category);
		return mav;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(StudyAssessPoints info) throws RepsException {
		try {
			StudyAssessPoints data = new StudyAssessPoints();
			data.setCategory(info.getCategory());
			data.setDescription(info.getDescription());
			data.setIcon(info.getIcon());
			data.setIsEnable(info.getIsEnable());
			data.setItem(info.getItem());
			data.setMark(info.getMark());
			data.setPointsScope(Math.abs(info.getPointsScope()));
			assessPointsService.save(data);
			return ajax(AjaxStatus.OK, "评价指标设置成功");
		} catch (Exception e) {
			logger.error("评价分值设置异常", e);
			return ajax(AjaxStatus.ERROR, "评价指标设置异常");
		}
	}
	
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(String id) {
		ModelAndView mav = getModelAndView("/jifen/pjfzsz/edit");
		StudyAssessPoints data = assessPointsService.get(id);
		mav.addObject("data", data);
		mav.addObject("imageUploadPath", ConfigurePath.IMG_UPLOAD_PATH);
		mav.addObject("imagePath", ConfigurePath.IMG_FILE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(StudyAssessPoints info) throws RepsException {
		try {
			StudyAssessPoints old = assessPointsService.get(info.getId());
			if (old == null) {
				return ajax(AjaxStatus.ERROR, "该评价指标设置不存在");
			}
			if (StringUtils.isNotBlank(info.getItem())) {
				old.setItem(info.getItem());
			}
			if (info.getMark() != null) {
				old.setMark(info.getMark());
			}
			if (info.getPointsScope() != null) {
				old.setPointsScope(info.getPointsScope());
			}
			if (info.getIsEnable() != null) {
				old.setIsEnable(info.getIsEnable());
			}
			if (StringUtils.isNotBlank(info.getDescription())) {
				old.setDescription(info.getDescription());
			}
			old.setIcon(info.getIcon());
			assessPointsService.update(old);
			return ajax(AjaxStatus.OK, "修改评价指标成功");
		} catch (Exception e) {
			logger.error("评价分值设置异常", e);
			return ajax(AjaxStatus.ERROR, "修改评价指标异常");
		}
	}
	
	@RequestMapping(value = "/showdetail")
	public ModelAndView showDetail(String id) {
		ModelAndView mav = new ModelAndView("/jifen/pjfzsz/detail");
		StudyAssessPoints data = assessPointsService.get(id);
		mav.addObject("data", data);
		return mav;
	}
	
	@RequestMapping(value = "/isenable")
	@ResponseBody
	public Object updateIsEnable(StudyAssessPoints info) throws RepsException {
		StudyAssessPoints data = assessPointsService.get(info.getId());
		if (data != null) {
			data.setIsEnable(info.getIsEnable());
			assessPointsService.update(data);
			return ajax(AjaxStatus.OK, "修改成功");
		} else {
			return ajax(AjaxStatus.ERROR, "该评价指标设置不存在");
		}
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String ids) throws RepsException {
		if (StringUtils.isBlank(ids)) {
			return ajax(AjaxStatus.ERROR, "请求参数错误");
		}
		String[] sIds = ids.split(",");
		for (String id : sIds) {
			assessPointsService.delete(id);
		}
		return ajax(AjaxStatus.OK, "删除成功");
	}
	
	@RequestMapping(value = "/fplist")
	public ModelAndView fpList(Pagination pager, StudyPjfzszRight query) {
		ModelAndView mav = getModelAndView("/jifen/pjfzsz/fplist");
		ListResult<StudyPjfzszRight> result = allocationService
				.query(pager.getStartRow(), pager.getPageSize(), query);
		pager.setTotalRecord(result.getCount());
		mav.addObject("list", result.getList());
		mav.addObject("pager", pager);
		mav.addObject("query", query);
		mav.addObject("behaviorId", query.getBehaviorId());
		mav.addObject("actionBasePath", RepsConstant.ACTION_BASE_PATH);
		return mav;
	}
	
	@RequestMapping(value = "/saverule")
	@ResponseBody
	public Object saveQuota(StudyPjfzszRight query) {
		try {
			List<StudyPjfzszRight> list = allocationService.find(query);
			if (list == null || list.isEmpty()) {
				StudyPjfzszRight data = new StudyPjfzszRight();
				BeanUtils.copyProperties(query, data);
				allocationService.save(data);
			}
			return ajax(AjaxStatus.OK, "保存成功");
		} catch (Exception e) {
			logger.error("保存失败", e);
			return ajax(AjaxStatus.ERROR, "保存异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/delrule")
	@ResponseBody
	public Object deleteQuota(String id) throws RepsException {
		allocationService.delete(id);
		return ajax(AjaxStatus.OK, "删除成功");
	}
	
	@RequestMapping(value = "/teachers")
	public ModelAndView teachers(Pagination pager, User user, String dialogId, String showName, String hideName, String hideNameValue, String callBack, boolean filterSelected) {
		ModelAndView mav = getModelAndView("/jifen/pjfzsz/teachers");
		String[] selectedUserIds = null;
	    if (StringUtils.isNotBlank(hideNameValue)) {
	      selectedUserIds = hideNameValue.split(",");
	    }
	    LoginToken token = getCurrentToken();
	    Organize organize = new Organize();
	    organize.setParentXpath(token.getParentIdsXpath() + "/" + token.getOrganizeId());
	    //organize.setParentXpath("-1/43312620160501org000000000000000");
	    user.setOrganize(organize);
	    user.setIdentity(teacherIdentity);
	    ListResult<User> listResult = this.userService.query(pager.getStartRow(), pager.getPageSize(), user, null, filterSelected, selectedUserIds);

	    pager.setTotalRecord(listResult.getCount().longValue());

	    mav.addObject("list", listResult.getList());

	    mav.addObject("pager", pager);

	    mav.addObject("user", user);
	    mav.addObject("dialogId", dialogId);
	    mav.addObject("showName", showName);
	    mav.addObject("hideName", hideName);
	    mav.addObject("hideNameValue", hideNameValue);
	    mav.addObject("callBack", callBack);
	    mav.addObject("actionBasePath", "/reps");
	    mav.addObject("admins", RepsConstant.getAdmins());
		return mav;
	}
	
}
