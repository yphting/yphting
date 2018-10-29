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
import com.accp.biz.szy.UserBiz;
import com.accp.pojo.Orders;
import com.accp.pojo.Servicetype;
import com.accp.pojo.User;

@Controller
@RequestMapping("/c/szy")
public class OrdersAction {
	@Autowired
	private OrdersBiz biz;
	@Autowired
	private UserBiz userBiz;
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
	public String updateOrders(HttpSession session,Integer orderStatus,String orderID) {
		biz.updateOrders(orderStatus, orderID);
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		String content="";
		switch (orderStatus) {
		case 3:
			content="您的订单\"+orderID+\",商家已接单,请进入个人中心查看";
			break;
		case 4:
			content="您的订单\"+orderID+\",商家已提供服务,请进入个人中心查看";
			break;
		case 7:
			content="您的订单\"+orderID+\",商家已取消,请进入个人中心查看";
			break;
		default:
			System.out.println("多余状态");
			break;
		}
		userBiz.saveXtxx(userID, content);
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
	/**
	 * 查询订单详情
	 * @param model
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value="/order/queryAOrder",method=RequestMethod.GET)
	public String queryAOrder(Model model,String orderID) {
		model.addAttribute("order", biz.queryAOrder(orderID));
		return "/sjrz-order-deatil.html";
	}
}
