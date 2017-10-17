package com.reps.jifen.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 个人积分汇总
 * @author qianguobing
 * @date 2017年9月7日 上午9:01:49
 */
@Document(collection = "reps_jf_points_aggregate")
public class PointsAggregate implements Serializable{
	
	private static final long serialVersionUID = 879865376296672774L;

	@Id
	private String id;
	
	/** 人员ID */
	private String personId;
	
	/** 总积分 */
	private Integer totalPoints;
	
	/** 可用积分 */
	private Integer totalPointsUsable;
	
	/** 积分累计级别 */
	private Short level;
	
	/**排名占比*/
	private Float beat;
	
	/**排名*/
	@Transient
	private int top;
	
	/**距离下一级所需积分*/
	@Transient
	private Integer needPoints;
	
	/** 学生姓名 */
	private String personName;
	
	/** 学校名字 */
	private String schoolName;
	
	/** 头像地址 */
	private String url;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public Float getBeat() {
		return beat;
	}

	public void setBeat(Float beat) {
		this.beat = beat;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "JfPointsAggregate [personId=" + personId + ", totalPoints=" + totalPoints + ", totalPointsUsable=" + totalPointsUsable + ", level=" + level + "]";
	} 
	
}
