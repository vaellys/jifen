package com.reps.jifen.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reps.core.orm.IdEntity;

/**
 * 活动信息
 * @author qianguobing
 * @date 2017年8月17日 下午5:19:54
 */
@Entity
@Table(name = "reps_jf_activity")
public class ActivityReward extends IdEntity implements Serializable {

	private static final long serialVersionUID = 5942323150277253519L;

	/** 所属分类ID */
	@Column(name = "category_id", nullable = false, length = 32, insertable=false, updatable=false)
	private String categoryId;
	
	/** 所属分类 */
	@JsonIgnore
    @ManyToOne(cascade = {})
    @JoinColumn(name = "category_id")
    private RewardCategory jfRewardCategory;

	/** 名称 */
	@Column(name = "name", nullable = false, length = 30)
	private String name;

	/** 扣除积分 */
	@Column(name = "points")
	private Integer points;

	/** 描述 */
	@Column(name = "description", length = 500)
	private String description;
	
	/** 图片 */
	@Column(name = "picture", length = 180)
	private String picture;
	
	/** 是否上架 */
	@Column(name = "is_shown")
	private Short isShown;
	
	/** 登记时间 */
	@Column(name = "create_time")
	private Date createTime;
	
	/** 活动开始时间 */
	@Column(name = "begin_time")
	private Date beginTime;
	
	/** 结束时间 */
	@Column(name = "begin_end")
	private Date endTime;
	
	/** 截止时间 */
	@Column(name = "finish_time")
	private Date finishTime;
	
	/** 参与人数 */
	@Column(name = "participated_count")
	private Integer participatedCount;
	
	/** 删除标识 1：有效（默认值），9：删除 */
	@Column(name = "valid_record")
	private Short validRecord;
	
	@Transient
	@JsonIgnore
	private String finishTimeDisp;
	
	@Transient
	@JsonIgnore
	private String beginTimeDisp;
	
	@Transient
	@JsonIgnore
	private String endTimeDisp;
	
	/** 积分开始 */
	@Transient
	@JsonIgnore
	private Integer pointBegin;
	
	/** 积分结束 */
	@Transient
	@JsonIgnore
	private Integer pointEnd;
		
	/** 排序字段 participatedCount（参与人数），points（积分值），createTime（新品）*/
	@Transient
	@JsonIgnore
	private String sortField;
	
	/** 分类名称 */
	@Transient
	private String categoryName;
	
	/** 排序顺序 asc（升序）、desc（降序）*/
	@Transient
	@JsonIgnore
	private String sortOrder;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public RewardCategory getJfRewardCategory() {
		return jfRewardCategory;
	}

	public void setJfRewardCategory(RewardCategory jfRewardCategory) {
		this.jfRewardCategory = jfRewardCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Short getIsShown() {
		return isShown;
	}

	public void setIsShown(Short isShown) {
		this.isShown = isShown;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFinishTimeDisp() {
		return finishTimeDisp;
	}

	public void setFinishTimeDisp(String finishTimeDisp) {
		this.finishTimeDisp = finishTimeDisp;
	}

	public Integer getPointBegin() {
		return pointBegin;
	}

	public void setPointBegin(Integer pointBegin) {
		this.pointBegin = pointBegin;
	}

	public Integer getPointEnd() {
		return pointEnd;
	}

	public void setPointEnd(Integer pointEnd) {
		this.pointEnd = pointEnd;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getParticipatedCount() {
		return participatedCount;
	}

	public void setParticipatedCount(Integer participatedCount) {
		this.participatedCount = participatedCount;
	}

	public Short getValidRecord() {
		return validRecord;
	}

	public void setValidRecord(Short validRecord) {
		this.validRecord = validRecord;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
}
