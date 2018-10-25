package com.accp.biz.lhy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.lhy.OrderDao;
import com.accp.dao.lhy.RefundDao;
import com.accp.vo.lhy.Orders;
import com.accp.vo.lhy.Refund;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class RefundBiz {
	@Autowired
	private RefundDao refundDao;
	@Autowired
	private OrderDao orderDao;

	/**
	 * 获取退款详单
	 * 
	 * @param orderId
	 *            订单编号
	 * @return
	 */
	public Refund queryRefundByOrderId(String orderId) {
		return refundDao.queryRefundByOrderId(orderId);
	}

	/**
	 * 申请管理员介入
	 * 
	 * @param order
	 *            订单
	 * @param refund
	 *            退款
	 * @return
	 */
	public boolean applyAdmin(Orders order, Refund refund) {
		orderDao.updateOrder(order);
		return refundDao.updateRefund(refund);
	}
}