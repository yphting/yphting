package com.accp.action.lhy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.accp.biz.lhy.OrderBiz;
import com.accp.biz.lhy.RefundBiz;
import com.accp.vo.lhy.Orders;
import com.accp.vo.lhy.Refund;

@Controller
@RequestMapping("/c/lhy")
public class RefundAction {
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private RefundBiz refundBiz;

	/**
	 * 查看退款详情
	 * 
	 * @param orderid
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/refund/detail")
	public String refundDetail(@RequestParam(required = true) String orderid, Model model) {
		model.addAttribute("order", orderBiz.queryOrderById(orderid));
		model.addAttribute("refund", refundBiz.queryRefundByOrderId(orderid));
		return "grzx-refund-detail";
	}

	/**
	 * 申请管理员介入
	 * 
	 * @param orderid
	 *            订单编号
	 * @return
	 */
	@RequestMapping("/refund/applyadmin")
	public String applyAdmin(@RequestParam(required = true) String orderid) {
		Orders order = new Orders();
		order.setOrderid(orderid);
		order.setRefundstatus(3);
		Refund refund = new Refund();
		refund.setOrderid(orderid);
		refund.setAdminstatus(1);
		refundBiz.applyAdmin(order, refund);
		return "redirect:/c/lhy/refund/detail";
	}
}