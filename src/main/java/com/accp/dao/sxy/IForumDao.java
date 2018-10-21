package com.accp.dao.sxy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Forummanagement;
import com.accp.vo.sxy.PostVo;

public interface IForumDao {
	
	/**
	 * 查询论坛所有版块
	 * @return
	 */
	public List<Forummanagement> queryBlock();
	
	/**
	 * 查询版块对应的栏目
	 * @return
	 */
	public List<Forummanagement> queryColumn(@Param("pid") Integer pid);
	
	/**
	 * 显示版块标题
	 * @param fmid
	 * @return
	 */
	public Forummanagement queryTitleName(@Param("fmid") Integer fmid);
	
	/**
	 * 显示帖子列表
	 * @param fmid
	 * @return
	 */
	public List<PostVo> queryPostList(@Param("pid")Integer pid,@Param("fmid") Integer fmid,@Param("title")String title,@Param("forumid")Integer forumid,@Param("orderId")Integer orderId,@Param("essence")Integer essence);
}
