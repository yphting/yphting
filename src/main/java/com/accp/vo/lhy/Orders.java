package com.accp.vo.lhy;

import java.util.Date;

public class Orders extends com.accp.pojo.Orders {
	// 服务
	private Services service;

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

	public Orders() {
		super();
	}

	public Orders(String orderid, Integer orderstatus, Date paymenttime, Date completetime) {
		super();
		this.setOrderid(orderid);
		this.setOrderstatus(orderstatus);
		this.setPaymenttime(paymenttime);
		this.setCompletetime(completetime);
	}

	public Orders(Integer userid, String orderid, Integer orderstatus) {
		super();
		this.setUserid(userid);
		this.setOrderid(orderid);
		this.setOrderstatus(orderstatus);
	}
}