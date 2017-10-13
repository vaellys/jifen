package com.reps.jifen.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PointsCollect {
	
	/** 人员ID */
	private String personId;
	
	private String personName;
	
	/** 积分来源 */
	private String getFrom;
	
	/** 规则名称 */
	private String ruleName;
	
	/** 规则标识码 */
	private String ruleCode;
	
	/** 积分值 */
	private Integer points;
	
	/** 来源记录ID */
	private String recordId;
	
	/** 获取时间 */
	private String getTime;
	
	/** 收集时间 */
	private String collectTime;
	
	/** 本次总积分 */
	private Integer totalPoints;
	
	/** 本次可用积分 */
	private Integer totalPointsUsable;
	
	@JsonIgnore
	private String beginTimeDisp = "";
	
	@JsonIgnore
	private String endTimeDisp = "";
	
	@JsonIgnore
	private String beginTime;
	
	@JsonIgnore
	private String endTime;
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getGetFrom() {
		return getFrom;
	}

	public void setGetFrom(String getFrom) {
		this.getFrom = getFrom;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getBeginTimeDisp() {
		return beginTimeDisp;
	}

	public void setBeginTimeDisp(String beginTimeDisp) {
		this.beginTimeDisp = beginTimeDisp;
	}

	public String getEndTimeDisp() {
		return endTimeDisp;
	}

	public void setEndTimeDisp(String endTimeDisp) {
		this.endTimeDisp = endTimeDisp;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
