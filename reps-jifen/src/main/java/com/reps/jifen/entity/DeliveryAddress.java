package com.reps.jifen.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.reps.core.orm.IdEntity;

/**
 * 收货地址
 * @author Lanxumit
 *
 */
@Entity
@Table(name = "reps_jf_delivery_address")
public class DeliveryAddress extends IdEntity {

	private static final long serialVersionUID = 1028287676736172280L;

	/**收货地址创建人id*/
	@Column(name = "person_id")
	private String personId;
	
	/**收货人*/
	@Column(name = "consignee_name")
	private String consigneeName;
	
	/**详细地址*/
	@Column(name = "detail_address")
	private String detailAddress;
	
	/**邮政编码*/
	@Column(name = "postal_code")
	private String postalcode;
	
	/**联系电话*/
	@Column(name = "phone")
	private String phone;
	
	/** 0否 1是*/
	@Column(name = "is_default")
	private Short isDefault;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Transient
	private String ids;

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
