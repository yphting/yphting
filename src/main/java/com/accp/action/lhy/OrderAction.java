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
import com.accp.vo.lhy.Orders;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/c/lhy")
public class OrderAction {
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private UserBiz userBiz;

	private final static int USER_ID = 1;

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
	public String queryOrderList(HttpSession session, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "") String orderid, @RequestParam(defaultValue = "") Integer status,
			Model model) {
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		PageInfo<Orders> pageInfo = orderBiz.queryOrderList(new Orders(userID, orderid, status), page, 2);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("orderInfo", orderBiz.queryOrderInfo(USER_ID));
		return "grzx-order";
	}

	/**
	 * 查询订单详情
	 * 
	 * @param orderid
	 *            编号
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
	 *            编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/pay")
	public String pay(@RequestParam(required = true) String orderid, Model model,HttpSession session) {
		model.addAttribute("user",(User)session.getAttribute("USER"));
		//model.addAttribute("user", userBiz.queryUserById(USER_ID));
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
	public String payOk(HttpSession session ,@RequestParam(required = true) String orderid, Model model) {
		// 用户扣款，更新订单状态，付款时间
		// 金币记录，站内信商家
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		Orders order = orderBiz.queryOrderById(orderid);
		orderBiz.payOrder(Math.round(order.getSmallplan()), userID, new Orders(orderid, 2, new Date(), null));
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
	public String cancel(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "") String orderid,
			@RequestParam(required = true) String updateid) {
		orderBiz.updateOrder(new Orders(updateid, 6, null, null));
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
		orderBiz.ok(Math.round(order.getSmallplan()), order.getService().getUser().getUserid(),
				new Orders(updateid, 5, null, new Date()));
		return "redirect:/c/lhy/order/query/list?page=" + page + "&orderid=" + orderid;
	}
}