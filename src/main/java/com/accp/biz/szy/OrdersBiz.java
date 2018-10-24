package com.accp.biz.szy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.szy.IOrdersDao;
import com.accp.pojo.Servicetype;
import com.accp.vo.szy.OrderInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class OrdersBiz {
	@Autowired
	private IOrdersDao dao;
	/**
	 * 查询当前登陆用户预定信息
	 * @return
	 */
	public  PageInfo<OrderInfoVo>  queryUserOrder(Integer userID,Integer orderStatus,Integer refundstatus,String orderID,Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(dao.queryUserOrder(userID, orderStatus, refundstatus,orderID));
	}
	/**
	 * 查询服务类型
	 * @param stid
	 * @return
	 */
	public Servicetype querySerType(Integer stid) {
		return dao.querySerType(stid);
	}
	/**
	 * 修改订单
	 * @param orderStatus
	 * @param orderID
	 * @return
	 */
	public int updateOrders(Integer orderStatus,String orderID) {
		return dao.updateOrders(orderStatus, orderID);
	}
}
