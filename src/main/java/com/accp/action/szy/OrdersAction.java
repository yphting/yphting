package com.accp.action.szy;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.biz.szy.OrdersBiz;
import com.accp.pojo.Orders;
import com.accp.pojo.Servicetype;
import com.accp.pojo.User;

@Controller
@RequestMapping("/c/szy")
public class OrdersAction {
	@Autowired
	private OrdersBiz biz;
	/**
	 * 查询当前用户所有订单信息
	 * @param session
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/order/queryAllOrder",method=RequestMethod.GET)
	public String queryAllOrdes(HttpSession session,Model model,Integer orderStatus,Integer refundstatus,String orderID,Integer pageNum,Integer pageSize) {
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		model.addAttribute("pageInfo", biz.queryUserOrder(userID, orderStatus, refundstatus, orderID, pageNum, pageSize));
		return "/sjzx-order.html";
	}
	/**
	 * 查询类型
	 * @param stid
	 * @return
	 */
	@RequestMapping(value="/order/querySerType",method=RequestMethod.GET)
	@ResponseBody
	public Servicetype querySerType(Integer stid) {
		return biz.querySerType(stid);
	}
	/**
	 * 修改状态
	 * @param orderStatus
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value="/order/updateOrders",method=RequestMethod.GET)
	public String updateOrders(Integer orderStatus,String orderID) {
		biz.updateOrders(orderStatus, orderID);
		return "redirect:/c/szy/order/queryAllOrder?orderStatus=0&refundstatus=0&pageNum=1&pageSize=5&orderID=";
	}
	/**
	 * 查询所有订单信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/order/queryCountOrder",method=RequestMethod.GET)
	@ResponseBody
	public List<Orders> queryCountOrder(HttpSession session) {
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		return biz.queryCountOrder(userID);
	}
}
