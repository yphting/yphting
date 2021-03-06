package com.accp.biz.lhy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.lhy.EvaluateDao;
import com.accp.dao.lhy.OrderDao;
import com.accp.dao.lhy.RefundDao;
import com.accp.dao.lhy.UserDao;
import com.accp.dao.szy.IUserDao;
import com.accp.dao.zsp.GoldnotesDao;
import com.accp.pojo.Goldnotes;
import com.accp.pojo.User;
import com.accp.vo.lhy.Evaluate;
import com.accp.vo.lhy.OrderInfo;
import com.accp.vo.lhy.Orders;
import com.accp.vo.lhy.Refund;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class OrderBiz {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private EvaluateDao evaluateDao;
	@Autowired
	private RefundDao refundDao;
	@Autowired
	private IUserDao iuserDao;
	@Autowired
	private GoldnotesDao goldnotesDao;

	/**
	 * 分页查询订单列表
	 * 
	 * @param order
	 *            订单
	 * @param page
	 *            页数
	 * @param size
	 *            行数
	 * @return
	 */
	public PageInfo<Orders> queryOrderList(Orders order, int page, int size) {
		PageHelper.startPage(page, size);
		return new PageInfo<Orders>(orderDao.queryOrderList(order));
	}

	/**
	 * 获取用户订单信息
	 * 
	 * @param userid
	 *            用户编号
	 * @return
	 */
	public OrderInfo queryOrderInfo(int userid) {
		int topay = 0, receipt = 0, tbconfirmed = 0, tbevaluated = 0;
		Orders order = new Orders();
		order.setUserid(userid);
		for (Orders o : orderDao.queryOrderList(order)) {
			switch (o.getOrderstatus()) {
			case 1:
				topay++;
				break;
			case 3:
				receipt++;
				break;
			case 4:
				tbconfirmed++;
				break;
			case 5:
				if (o.getCommentstatus() == 1)
					tbevaluated++;
				break;
			}
		}
		return new OrderInfo(topay, receipt, tbconfirmed, tbevaluated);
	}

	/**
	 * 根据编号获取订单
	 * 
	 * @param orderid
	 *            订单编号
	 * @return
	 */
	public Orders queryOrderById(String orderid) {
		return orderDao.queryOrderById(orderid);
	}

	/**
	 * 支付订单
	 * 
	 * @param usermoney
	 *            相对用户余额
	 * @param userid
	 *            用户编号
	 * @param order
	 *            订单
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean payOrder(double usermoney, int userid, Orders order) {
		User user = userDao.queryUserById(userid);
		if (user.getUsermoney() < usermoney) {
			return false;
		}
		iuserDao.saveXtxx(orderDao.queryOrderById(order.getOrderid()).getService().getUser().getUserid(),
				"用户已支付订单：" + order.getOrderid());
		Goldnotes goldnotes = new Goldnotes();
		goldnotes.setUserid(user.getUserid());
		goldnotes.setAcquisitionmode(1);
		goldnotes.setRecorddate(new Date());
		goldnotes.setRecorddescribe("支付订单：" + order.getOrderid());
		goldnotes.setRecordinandout((float) -usermoney);
		goldnotes.setAuditstatus(2);
		goldnotesDao.addGoldnotes(goldnotes);
		userDao.updateUserMoney((float) -usermoney, userid);
		return orderDao.updateOrder(order);
	}

	/**
	 * 取消订单
	 * 
	 * @param order
	 *            订单
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean cancelOrder(Orders order) {
		iuserDao.saveXtxx(orderDao.queryOrderById(order.getOrderid()).getService().getUser().getUserid(),
				"用户已取消订单：" + order.getOrderid());
		return orderDao.updateOrder(order);
	}

	/**
	 * 完成订单
	 * 
	 * @param usermoney
	 *            金额
	 * @param userid
	 *            商户编号
	 * @param order
	 *            订单
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean ok(double usermoney, int userid, Orders order) {
		User user = orderDao.queryOrderById(order.getOrderid()).getService().getUser();
		iuserDao.saveXtxx(user.getUserid(), "用户已确认完成订单：" + order.getOrderid());
		Goldnotes goldnotes = new Goldnotes();
		goldnotes.setUserid(user.getUserid());
		goldnotes.setAcquisitionmode(2);
		goldnotes.setRecorddate(new Date());
		goldnotes.setRecorddescribe("订单收益：" + order.getOrderid());
		goldnotes.setRecordinandout((float) (usermoney * 0.9));
		goldnotes.setAuditstatus(2);
		goldnotesDao.addGoldnotes(goldnotes);
		userDao.updateUserMoney((float) (usermoney * 0.9), userid);
		return orderDao.updateOrder(order);
	}

	/**
	 * 评价订单
	 * 
	 * @param evaluate
	 *            评价
	 * @param order
	 *            订单
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean evaluateOk(Evaluate evaluate, Orders order) {
		iuserDao.saveXtxx(orderDao.queryOrderById(order.getOrderid()).getService().getUser().getUserid(),
				"用户已评价订单：" + order.getOrderid());
		evaluateDao.saveEvaluate(evaluate);
		return orderDao.updateOrder(order);
	}

	/**
	 * 申请退款
	 * 
	 * @param refund
	 *            退款
	 * @param order
	 *            订单
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public boolean refundok(Refund refund, Orders order) {
		iuserDao.saveXtxx(orderDao.queryOrderById(order.getOrderid()).getService().getUser().getUserid(),
				"用户申请退款，订单：" + order.getOrderid());
		refundDao.saveRefund(refund);
		return orderDao.updateOrder(order);
	}
}