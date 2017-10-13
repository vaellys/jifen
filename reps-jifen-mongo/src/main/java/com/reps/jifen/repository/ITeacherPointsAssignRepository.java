package com.reps.jifen.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reps.jifen.entity.TeacherPointsAssign;

public interface ITeacherPointsAssignRepository extends MongoRepository<TeacherPointsAssign, String>{
	
	public Page<TeacherPointsAssign> findByTeacherId(String teacherId, Pageable pageable);
	
	public List<TeacherPointsAssign> findByStudentIdAndMark(String studentId, int mark);
}
