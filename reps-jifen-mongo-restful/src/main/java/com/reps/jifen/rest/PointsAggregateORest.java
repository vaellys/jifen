package com.reps.jifen.rest;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.jifen.entity.PointsAggregate;
import com.reps.jifen.service.IPointsAggregateService;
import static com.reps.jifen.util.PointsAggregateUtil.*;

@RestController
@RequestMapping(value = "/oapi/pointsaggregate")
public class PointsAggregateORest extends RestBaseController{
	
	private static Log logger = LogFactory.getLog(PointsAggregateORest.class);
	
	@Autowired
	private IPointsAggregateService jfPointsAggregateService;
	
	@RequestMapping(value = "/listcount", method = { RequestMethod.GET })
	public RestResponse<List<PointsAggregate>> list(Integer count) {
		try {
			List<PointsAggregate> result = jfPointsAggregateService.findByCount(count);
			if(null == result) {
				throw new RepsException("查询积分排行榜异常");
			}
			//设置头像地址
			setPictureUrls(result, this.getFileHttpPath());
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public RestResponse<ListResult<PointsAggregate>> list(PointsAggregate pointsAggregate, Integer pageIndex, Integer pageSize) {
		try {
			ListResult<PointsAggregate> result = jfPointsAggregateService.findAll(pointsAggregate, pageIndex, pageSize);
			if(null == result) {
				throw new RepsException("查询积分总排行榜异常");
			}
			//设置头像地址
			setPictureUrls(result.getList(), this.getFileHttpPath());
			return wrap(RestResponseStatus.OK, "查询成功", result);
		} catch (Exception e) {
			logger.error("查询异常", e);
			return wrap(RestResponseStatus.INTERNAL_SERVER_ERROR, "查询异常：" + e.getMessage());
		}
	}
	
}
