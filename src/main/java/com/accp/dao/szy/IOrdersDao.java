package com.accp.dao.szy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Orders;
import com.accp.pojo.Servicetype;
import com.accp.vo.szy.OrderInfoVo;

public interface IOrdersDao {
	/**
	 * 查询当前用户收到的预定信息
	 * @param userID 用户ID
	 * @param orderStatus 订单状态
	 * @param refundstatus 退款状态
	 * @return
	 */
	public List<OrderInfoVo> queryUserOrder(@Param("userid")Integer userID,@Param("orderStatus")Integer orderStatus,@Param("refundstatus")Integer refundstatus,@Param("orderID")String orderID);
	/**
	 * 查询服务类别
	 * @param stid
	 * @return
	 */
	public Servicetype querySerType(@Param("stid")Integer stid);
	/**
	 * 修改订单状态
	 * @param orderStatus
	 * @param orderID
	 * @return
	 */
	public int updateOrders(@Param("orderStatus")Integer orderStatus,@Param("orderID")String orderID);
	/**
	 * 查询商家所有订单
	 * @return
	 */
	public List<Orders> queryCountOrder(@Param("userid")Integer userID);
	/**
	 * 查询单个订单
	 * @return
	 */
	public OrderInfoVo queryAOrder(@Param("orderID")String orderID);
}
