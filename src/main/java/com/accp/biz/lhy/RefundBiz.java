package com.accp.biz.lhy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.lhy.OrderDao;
import com.accp.dao.lhy.RefundDao;
import com.accp.dao.lhy.UserDao;
import com.accp.vo.lhy.Orders;
import com.accp.vo.lhy.Refund;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class RefundBiz {
	@Autowired
	private RefundDao refundDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserDao userDao;

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
	 * 修改退款
	 * 
	 * @param order
	 *            订单
	 * @param refund
	 *            退款
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean updateRefund(Orders order, Refund refund) {
		orderDao.updateOrder(order);
		return refundDao.updateRefund(refund);
	}

	/**
	 * 查询收到的退款列表
	 * 
	 * @param userid
	 *            用户编号
	 * @param page
	 *            页
	 * @param size
	 *            行
	 * @return
	 */
	public PageInfo<Refund> queryRefundList(Integer userid, int page, int size) {
		PageHelper.startPage(page, size);
		return new PageInfo<Refund>(refundDao.queryRefundList(userid));
	}

	/**
	 * 同意退款
	 * 
	 * @param order
	 *            订单
	 * @param refund
	 *            退款
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean refundOk(Orders orderInfo, Orders order, Refund refund) {
		Integer buyer = orderInfo.getUserid();
		Integer seller = orderInfo.getService().getUser().getUserid();
		Float money = orderInfo.getSmallplan();
		refund.setActualrefundmoney(money);
		userDao.updateUserMoney(money, buyer);
		userDao.updateUserMoney(-money * 0.9, seller);
		orderDao.updateOrder(order);
		return refundDao.updateRefund(refund);
	}
}