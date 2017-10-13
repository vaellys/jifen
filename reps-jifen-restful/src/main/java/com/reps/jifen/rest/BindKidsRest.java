package com.reps.jifen.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reps.core.restful.AuthInfo;
import com.reps.core.restful.RestBaseController;
import com.reps.core.restful.RestResponse;
import com.reps.core.restful.RestResponseStatus;
import com.reps.rrt.service.IBindKidsService;
import com.reps.school.entity.ParentStudent;

@RestController
@RequestMapping(value = "/uapi/bindkids")
public class BindKidsRest extends RestBaseController {
	
	private final Log logger = LogFactory.getLog(BindKidsRest.class);

	@Autowired
	private IBindKidsService kidService;
	
	@RequestMapping(value = "/childlist")
	public RestResponse<Map<String, List<Map<String, Object>>>>  childList(ParentStudent ps) {
		
		RestResponse<Map<String, List<Map<String, Object>>>> restResponse = new RestResponse<>();
		AuthInfo info = getCurrentLoginInfo();
		if (StringUtils.isBlank(info.getPersonId())) {
			restResponse.setStatus(RestResponseStatus.BAD_REQUEST.code());
			restResponse.setMessage("该账户尚无用户信息,请先录入用户信息");
			return restResponse;
		}
		Map<String, List<Map<String, Object>>> resultData = new HashMap<>();
		List<Map<String, Object>> data = new ArrayList<>();
		try {
			ps.setParentId(info.getPersonId());
			List<ParentStudent> list = kidService.find(ps);
			if (list != null && list.size() > 0) {
				for(ParentStudent pss : list) {
					Map<String, Object> map = new HashMap<>();
					map = kidService.getUserInfo(pss.getStudentId());
					if (map == null) {
						continue;
					}
					map.put("status", pss.getStatus());
					map.put("defaultBind", pss.getDefaultBind());
					data.add(map);
				}
			}
			resultData.put("data", data);
			restResponse.setResult(resultData);
		} catch (Exception e) {
			logger.error("查询列表异常", e);
			restResponse.setStatus(RestResponseStatus.INTERNAL_SERVER_ERROR.code());
			restResponse.setMessage("查询列表异常: " + e);
		}
		return restResponse;
	}
}
