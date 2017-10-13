package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.TeacherPointsAssign;

public interface ITeacherPointsAssignService {

	void save(TeacherPointsAssign data);
	
	/**
	 * 通过教师ID查询分配给所有学生的记录
	 * @param teacherId
	 * @param pageIndex
	 * @param pageSize
	 * @return ListResult<JfTeacherPointsAssign>
	 * @throws RepsException
	 */
	ListResult<TeacherPointsAssign> findByTeacherId(String teacherId, Integer pageIndex, Integer pageSize) throws RepsException;
	
	List<TeacherPointsAssign> findByStudentIdAndMark(String studentId, int mark); 
}
