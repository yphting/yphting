package com.accp.action.lhy;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accp.biz.lhy.OrderBiz;
import com.accp.pojo.User;
import com.accp.vo.lhy.Orders;

@Controller
@RequestMapping("/c/lhy")
public class CenterAction {
	@Autowired
	private OrderBiz orderBiz;

	/**
	 * 个人中心主页
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/center/home")
	public String centerHome(Model model, HttpSession session) {
		User user = (User) session.getAttribute("USER");
		Orders order = new Orders();
		order.setUserid(user.getUserid());
		model.addAttribute("list", orderBiz.queryOrderList(order, 1, 10).getList());
		model.addAttribute("orderInfo", orderBiz.queryOrderInfo(user.getUserid()));
		model.addAttribute("user", user);
		return "grzx-index";
	}
}