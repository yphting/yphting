package com.accp.action.lhy;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.accp.biz.lhy.OrderBiz;
import com.accp.biz.lhy.UserBiz;
import com.accp.pojo.User;
import com.accp.vo.lhy.Evaluate;
import com.accp.vo.lhy.Orders;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/c/lhy")
public class OrderAction {
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private UserBiz userBiz;

	/**
	 * 查询订单列表
	 * 
	 * @param page
	 *            页数
	 * @param orderid
	 *            模糊订单号
	 * @param status
	 *            订单类型
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/query/list")
	public String queryOrderList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "") String orderid, @RequestParam(defaultValue = "") Integer status,
			Model model, HttpSession session) {
		Integer userId = ((User) session.getAttribute("USER")).getUserid();
		Orders order = new Orders();
		order.setUserid(userId);
		order.setOrderid(orderid);
		order.setOrderstatus(status);
		PageInfo<Orders> pageInfo = orderBiz.queryOrderList(order, page, 2);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("orderInfo", orderBiz.queryOrderInfo(userId));
		return "grzx-order";
	}

	/**
	 * 查询订单详情
	 * 
	 * @param orderid
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/query/detail")
	public String queryOrderDetail(@RequestParam(required = true) String orderid, Model model) {
		model.addAttribute("order", orderBiz.queryOrderById(orderid));
		return "grzx-order-detail";
	}

	/**
	 * 支付订单
	 * 
	 * @param orderid
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/pay")
	public String pay(@RequestParam(required = true) String orderid, Model model, HttpSession session) {
		Integer userId = ((User) session.getAttribute("USER")).getUserid();
		model.addAttribute("user", userBiz.queryUserById(userId));
		model.addAttribute("order", orderBiz.queryOrderById(orderid));
		return "grzx-order-pay";
	}

	/**
	 * 确认支付订单
	 * 
	 * @param orderid
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/pay/ok")
	public String payOk(@RequestParam(required = true) String orderid, HttpSession session) {
		// 用户扣款，更新订单状态，付款时间
		// 交易额，金币记录，站内信商家
		Integer userId = ((User) session.getAttribute("USER")).getUserid();
		Orders order = orderBiz.queryOrderById(orderid);
		Orders updateOrder = new Orders();
		updateOrder.setOrderid(orderid);
		updateOrder.setOrderstatus(2);
		updateOrder.setPaymenttime(new Date());
		orderBiz.payOrder(Math.round(order.getSmallplan()), userId, updateOrder);
		return "redirect:/c/lhy/order/query/list";
	}

	/**
	 * 取消订单
	 * 
	 * @param page
	 *            页数
	 * @param orderid
	 *            模糊订单号
	 * @param updateid
	 *            订单编号
	 * @return
	 */
	@RequestMapping("/order/cancel")
	public String cancel(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "") String orderid, @RequestParam(required = true) String updateid) {
		Orders order = new Orders();
		order.setOrderid(updateid);
		order.setOrderstatus(6);
		orderBiz.updateOrder(order);
		return "redirect:/c/lhy/order/query/list?page=" + page + "&orderid=" + orderid;
	}

	/**
	 * 确认完成
	 * 
	 * @param page
	 *            页数
	 * @param orderid
	 *            模糊订单号
	 * @param updateid
	 *            订单编号
	 * @return
	 */
	@RequestMapping("/order/ok")
	public String ok(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "") String orderid,
			@RequestParam(required = true) String updateid) {
		// 商家入款，更新订单状态，完成时间
		// 商家金币记录，站内信商家
		Orders order = orderBiz.queryOrderById(updateid);
		Orders updateOrder = new Orders();
		updateOrder.setOrderid(updateid);
		updateOrder.setOrderstatus(5);
		updateOrder.setCompletetime(new Date());
		orderBiz.ok(Math.round(order.getSmallplan()), order.getService().getUser().getUserid(), updateOrder);
		return "redirect:/c/lhy/order/query/list?page=" + page + "&orderid=" + orderid;
	}

	/**
	 * 评价订单
	 * 
	 * @param orderid
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/evaluate")
	public String evaluate(@RequestParam(required = true) String orderid, Model model) {
		model.addAttribute("order", orderBiz.queryOrderById(orderid));
		return "grzx-order-evaluate";
	}

	/**
	 * 确认评价
	 * 
	 * @param evaluate
	 *            评价
	 * @param orderid
	 *            订单编号
	 * @return
	 */
	@RequestMapping("/order/evaluate/ok")
	public String evaluateOk(Evaluate evaluate, String orderid, HttpSession session) {
		// 新增评价信息，修改订单评价状态
		Integer userId = ((User) session.getAttribute("USER")).getUserid();
		Orders order = orderBiz.queryOrderById(orderid);
		evaluate.setServiceid(order.getService().getServiceid());
		evaluate.setUserid(userId);
		evaluate.setServiceappraisedate(new Date());
		Orders updateOrder = new Orders();
		updateOrder.setOrderid(orderid);
		updateOrder.setCommentstatus(2);
		orderBiz.evaluateOk(evaluate, updateOrder);
		return "redirect:/c/lhy/evaluate/query/list";
	}
}