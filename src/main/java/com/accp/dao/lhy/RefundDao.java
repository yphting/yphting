package com.accp.dao.lhy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.vo.lhy.Refund;

public interface RefundDao {
	/**
	 * 新增退款
	 * 
	 * @param refund
	 *            退款
	 * @return
	 */
	boolean saveRefund(@Param("refund") Refund refund);

	/**
	 * 获取退款箱单
	 * 
	 * @param orderId
	 *            订单编号
	 * @return
	 */
	List<Refund> queryRefundByOrderId(@Param("orderid") String orderId);
}