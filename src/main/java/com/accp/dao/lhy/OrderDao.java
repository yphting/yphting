package com.accp.dao.lhy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.vo.lhy.Orders;

public interface OrderDao {
	/**
	 * 查询订单列表
	 * 
	 * @param order
	 *            订单
	 * @return
	 */
	List<Orders> queryOrderList(@Param("order") Orders order);

	/**
	 * 根据编号查询订单
	 * 
	 * @param orderid
	 *            编号
	 * @return
	 */
	Orders queryOrderById(@Param("orderid") String orderid);

	/**
	 * 修改订单
	 * 
	 * @param order
	 *            订单
	 * @return
	 */
	boolean updateOrder(@Param("order") Orders order);
}