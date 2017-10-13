package com.reps.jifen.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reps.core.RepsConstant;
import com.reps.core.commons.Pagination;
import com.reps.core.orm.ListResult;
import com.reps.core.web.AjaxStatus;
import com.reps.core.web.BaseAction;
import com.reps.jifen.service.IPointsAggregateService;
import com.reps.jifen.service.IPointsCollectService;
import com.reps.jifen.vo.ConfigurePath;
import com.reps.jifen.vo.PointsAggregate;
import com.reps.jifen.vo.PointsCollect;

@Controller
@RequestMapping(value = RepsConstant.ACTION_BASE_PATH + "/jifen/pointsaggregate")
public class PointsAggregateAction extends BaseAction{
	
	protected final Logger logger = LoggerFactory.getLogger(PointsAggregateAction.class);
	
	@Autowired
	IPointsAggregateService pointsAggregateService;
	
	@Autowired
	IPointsCollectService pointsCollectService;
	
	@RequestMapping(value = "/list")
	public Object list(Pagination pager, PointsAggregate pointsAggregate){
		ModelAndView mav = getModelAndView("/jifen/pointsaggregate/list");
		try {
			ListResult<PointsAggregate> listResult = pointsAggregateService.query(pager.getCurPageNumber(), pager.getPageSize(), ConfigurePath.MONGO_PATH, pointsAggregate);
			mav.addObject("pointsAggregate", pointsAggregate);
			//分页数据
			mav.addObject("list", listResult.getList());
			//分页参数
			pager.setTotalRecord(listResult.getCount().longValue());
			mav.addObject("pager", pager);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/detailist")
	public Object pointsCollect(Pagination pager, PointsCollect pointsCollect){
		ModelAndView mav = getModelAndView("/jifen/pointsaggregate/detailist");
		try {
			ListResult<PointsCollect> listResult = pointsCollectService.query(pager.getCurPageNumber(), pager.getPageSize(), ConfigurePath.MONGO_PATH, pointsCollect);
			mav.addObject("pointsCollect", pointsCollect);
			//分页数据
			mav.addObject("list", listResult.getList());
			//分页参数
			pager.setTotalRecord(listResult.getCount().longValue());
			mav.addObject("pager", pager);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询参数失败", e);
			return ajax(AjaxStatus.ERROR, e.getMessage());
		}
	}

}
