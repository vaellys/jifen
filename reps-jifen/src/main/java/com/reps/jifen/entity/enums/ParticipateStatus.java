package com.reps.jifen.entity.enums;

public enum ParticipateStatus {

	PARTICIPATED((short) 1, "申请中"), CANCEL_PARTICIPATE((short) 0, "取消参与"), AUDIT_PASSED((short) 2, "审核通过"), AUDIT_REJECTED((short) 3, "被驳回"), ACTIVITY_CANCELLED((short) 4, "已取消");

	private Short id;

	private String status;

	ParticipateStatus(Short id, String status) {
		this.id = id;
		this.status = status;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
