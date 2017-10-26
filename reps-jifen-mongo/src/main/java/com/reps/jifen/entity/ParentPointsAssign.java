package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 家长积分分配记录
 * @author qianguobing
 * @date 2017年9月6日 下午3:00:02
 */
@Document(collection = "reps_jf_parent_points_assign")
public class ParentPointsAssign implements Serializable{
	
	private static final long serialVersionUID = 6931689340059192641L;

	/** 家长ID */
	private String parentId;
	
	/** 学生ID */
	private String studentId;
	
	/** 学校ID */
	private String schoolId;
	
	/** 家长姓名 */
	private String parentName;
	
	/** 学生姓名 */
	private String studentName;
	
	/** 学校名称 */
	private String schoolName;
	
	/** 规则名称 */
	private String ruleName;
	
	/** 积分 */
	private Integer points;
	
	/** 奖惩 0 - 扣除, 1 - 奖励 */
	private Short mark;
	
	/** 分配时间 */
	private Date createTime;
	
	/**行为时间*/
	private Date behaviorTime;
	
	/**上周/本周  0/1*/
	@Transient
	private String type;
	
	/**规则id*/
	@Transient
	private String ruleId;
	
	/** 头像地址 */
	@Transient
	private String avatarUrl;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Short getMark() {
		return mark;
	}

	public void setMark(Short mark) {
		this.mark = mark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Date getBehaviorTime() {
		return behaviorTime;
	}

	public void setBehaviorTime(Date behaviorTime) {
		this.behaviorTime = behaviorTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Override
	public String toString() {
		return "JfParentPointsAssign [parentId=" + parentId + ", studentId=" + studentId + ", schoolId=" + schoolId + ", parentName=" + parentName + ", studentName=" + studentName + ", schoolName="
				+ schoolName + ", ruleName=" + ruleName + ", points=" + points + ", mark=" + mark + ", createTime=" + createTime + "]";
	}
	
}
