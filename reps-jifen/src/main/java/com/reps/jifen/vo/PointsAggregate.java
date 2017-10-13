package com.reps.jifen.vo;

import javax.persistence.Transient;

public class PointsAggregate {
	
	/** 人员ID */
	private String personId;
	
	/** 总积分 */
	private Integer totalPoints;
	
	/** 可用积分 */
	private Integer totalPointsUsable;
	
	/** 积分累计级别 */
	private Short level;
	
	/**排名占比*/
	@Transient
	private String ranking;
	
	/**距离下一级所需积分*/
	@Transient
	private Integer needPoints;
	
	/** 学生姓名 */
	private String personName;
	
	/** 学校名字 */
	private String schoolName;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getTotalPointsUsable() {
		return totalPointsUsable;
	}

	public void setTotalPointsUsable(Integer totalPointsUsable) {
		this.totalPointsUsable = totalPointsUsable;
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public Integer getNeedPoints() {
		return needPoints;
	}

	public void setNeedPoints(Integer needPoints) {
		this.needPoints = needPoints;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}
