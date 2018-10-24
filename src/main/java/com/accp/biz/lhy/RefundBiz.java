package com.accp.biz.lhy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.lhy.RefundDao;
import com.accp.vo.lhy.Refund;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class RefundBiz {
	@Autowired
	private RefundDao dao;

	public List<Refund> queryRefundByOrderId(String orderId) {
		return dao.queryRefundByOrderId(orderId);
	}
}