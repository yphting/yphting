package com.accp.dao.zjw;

import java.util.List;

import com.accp.pojo.Post;
import com.accp.pojo.Postcomment;

public interface IZjwDao {
	/**
	 * 根据用户id查询帖子
	 */
	public List<Post> query(Integer userID);
	/**
	 * 根据用户id查询帖子回复表
	 */
	public List<Postcomment> queryhuifu(Integer userID);
	/**
	 * 
	 */
	
}
