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

	List<Refund> queryRefundByOrderId(@Param("orderid") Integer orderId);

	List<Refund> queryRefundByOrderId(String orderId);
}