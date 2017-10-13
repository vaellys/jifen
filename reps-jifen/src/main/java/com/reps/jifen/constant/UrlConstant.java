package com.reps.jifen.constant;

/**
 * url常量()
 * @author Lanxumit
 *
 */
public class UrlConstant {

	/**
	 * 保存积分兑换记录post
	 */
	public static final String SAVE_EXCHANGE = "/uapi/pointsexchange/save";
	
	/**
	 * 取消订单/取消参与活动
	 */
	public static final String CANCEL_EXCHANGE = "/uapi/pointsexchange/cancel";
	
	public static final String O_CANCEL_EXCHANGE = "/oapi/pointsexchange/cancel";
	
	public static final String IS_ALLOW_OPTION = "/uapi/pointsaggregate/allowoption";
	
	/**
	 *个人积分查询列表
	 */
	public static final String O_POINTS_AGGREGATE = "/oapi/pointsaggregate/list";
	
	/**
	 * 积分获取列表
	 */
	public static final String O_POINTS_COLLECT = "/oapi/pointscollect/list";
	
	
}
