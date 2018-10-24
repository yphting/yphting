package com.accp.vo.lhy;

public class Refund extends com.accp.pojo.Refund {
	// 订单
	private Orders order;
	// 买家
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}
}