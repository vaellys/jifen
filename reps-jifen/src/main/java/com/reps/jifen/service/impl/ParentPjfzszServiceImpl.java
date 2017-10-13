package com.reps.jifen.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.ParentPjfzszDao;
import com.reps.jifen.entity.ParentPjfzsz;
import com.reps.jifen.service.IParentPjfzszService;

/**
 * 积分家庭行为评分设置service实现
 * @author qianguobing
 * @date 2017年8月28日 下午5:17:49
 */
@Service("com.reps.jifen.service.impl.JfParentPjfzszServiceImpl")
@Transactional
public class ParentPjfzszServiceImpl implements IParentPjfzszService {
	
	@Autowired
	ParentPjfzszDao dao;

	@Override
	public void save(ParentPjfzsz jfParentPjfzsz) {
		dao.save(jfParentPjfzsz);
	}

	@Override
	public void delete(ParentPjfzsz jfParentPjfzsz) {
		dao.delete(jfParentPjfzsz);
	}

	@Override
	public void update(ParentPjfzsz jfParentPjfzsz) throws RepsException {
		if(null == jfParentPjfzsz) {
			throw new RepsException("参数异常");
		}
		ParentPjfzsz parentPjfzsz = this.get(jfParentPjfzsz.getId());
		String item = jfParentPjfzsz.getItem();
		if(StringUtil.isNotBlank(item)) {
			parentPjfzsz.setItem(item);
		}
		String content = jfParentPjfzsz.getContent();
		if(StringUtil.isNotBlank(content)) {
			parentPjfzsz.setContent(content);
		}
		Short pointsScope = jfParentPjfzsz.getPointsScope();
		if(null != pointsScope) {
			parentPjfzsz.setPointsScope(pointsScope);
		}
		String applyGrade = jfParentPjfzsz.getApplyGrade();
		if(StringUtil.isNotBlank(applyGrade)) {
			parentPjfzsz.setApplyGrade(applyGrade);
		}
		Short isEnabled = jfParentPjfzsz.getIsEnabled();
		if(null != isEnabled) {
			parentPjfzsz.setIsEnabled(isEnabled);
		}
		dao.update(parentPjfzsz);
	}

	@Override
	public ParentPjfzsz get(String id) throws RepsException {
		if(StringUtil.isBlank(id)) {
			throw new RepsException("查询异常:id不能为空");
		}
		ParentPjfzsz parentPjfzsz = dao.get(id);
		if(null == parentPjfzsz) {
			throw new RepsException("查询异常:该家庭行为不存在");
		}
		return parentPjfzsz;
	}

	@Override
	public ListResult<ParentPjfzsz> query(int start, int pagesize, ParentPjfzsz jfParentPjfzsz) {
		ListResult<ParentPjfzsz> parentPjfzszList = dao.query(start, pagesize, jfParentPjfzsz);
		for (ParentPjfzsz bean : parentPjfzszList.getList()) {
			Short pointsScope = bean.getPointsScope();
			StringBuilder sb = new StringBuilder();
			while((short)pointsScope > 0) {
				sb.insert(0, pointsScope--);
				sb.insert(0, "+");
				sb.insert(0, ", ");
			}
			bean.setPointsScopeDisp(sb.deleteCharAt(sb.toString().indexOf(",")).toString());
		}
		return parentPjfzszList;
	}

	@Override
	public void batchDelete(String ids) {
		dao.batchDelete(ids);
	}

	@Override
	public List<ParentPjfzsz> find(ParentPjfzsz query) {
		return dao.find(query);
	}

}
