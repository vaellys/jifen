package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 个人积分获取记录
 * 
 * @author qianguobing
 * @date 2017年9月6日 下午3:24:29
 */
@Document(collection = "reps_jf_points_collect")
public class PointsCollect implements Serializable {

	private static final long serialVersionUID = 5845280450059451585L;

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
	private Date getTime;

	/** 收集时间 */
	private Date collectTime;

	/** 本次总积分 */
	private Integer totalPoints;

	/** 本次可用积分 */
	private Integer totalPointsUsable;

	@JsonIgnore
	@Transient
	private Date beginTime;

	@JsonIgnore
	@Transient
	private Date endTime;

	@JsonIgnore
	@Transient
	private String beginTimeDisp;

	@JsonIgnore
	@Transient
	private String endTimeDisp;

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

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	@Override
	public String toString() {
		return "JfPointsCollect [personId=" + personId + ", getFrom=" + getFrom
				+ ", ruleName=" + ruleName + ", ruleCode=" + ruleCode
				+ ", points=" + points + ", recordId=" + recordId
				+ ", getTime=" + getTime + ", collectTime=" + collectTime
				+ ", totalPoints=" + totalPoints + ", totalPointsUsable="
				+ totalPointsUsable + "]";
	}

}
