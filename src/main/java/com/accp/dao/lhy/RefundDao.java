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
	 * 获取退款详单
	 * 
	 * @param orderId
	 *            订单编号
	 * @return
	 */
	Refund queryRefundByOrderId(@Param("orderid") String orderId);

	/**
	 * 修改退款
	 * 
	 * @param refund
	 *            退款
	 * @return
	 */
	boolean updateRefund(@Param("refund") Refund refund);

	/**
	 * 查询收到的退款列表
	 * 
	 * @param userid
	 *            用户编号
	 * @return
	 */
	List<Refund> queryRefundList(@Param("userid") Integer userid);

	/**
	 * 查询我的退款列表
	 * 
	 * @param userid
	 * @return
	 */
	List<Refund> queryMyRefundList(@Param("userid") Integer userid);
}