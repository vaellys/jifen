package com.reps.jifen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reps.core.orm.IdEntity;
import com.reps.system.entity.Person;

@Entity
@Table(name = "reps_jf_study_pjfzsz_right")
public class StudyPjfzszRight extends IdEntity {

	private static final long serialVersionUID = -3888862558487812026L;
	
	/**指标id*/
	@Column(name = "behavior_id")
	private String behaviorId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "behavior_id", insertable = false, updatable = false)
    private StudyAssessPoints study;
	
	@Column(name = "teacher_id")
	private String teacherId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id", insertable = false, updatable = false)
	private Person person;

	public String getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(String behaviorId) {
		this.behaviorId = behaviorId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public StudyAssessPoints getStudy() {
		return study;
	}

	public void setStudy(StudyAssessPoints study) {
		this.study = study;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
