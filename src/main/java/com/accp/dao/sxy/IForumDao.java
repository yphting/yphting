package com.accp.dao.sxy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Forummanagement;
import com.accp.pojo.Post;
import com.accp.vo.sxy.PostVo;
import com.accp.vo.sxy.PostcommentVo;

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
	
	/**
	 * 显示热门帖子
	 * @param title
	 * @return
	 */
	public List<PostVo> queryHotPost(@Param("title")String title);
	
	
	/**
	 * 发帖增加积分
	 * @param userId
	 */
	public void updateJIntegral(@Param("userId")Integer userId);
	
	/**
	 * 发布帖子
	 * @param forum
	 * @return
	 */
	public int savePost(@Param("post")Post post);
	
	/**
	 * 查询帖子详情
	 * @param postId
	 * @return
	 */
	public PostVo queryPostDetail(@Param("postId")Integer postId);
	
	/**
	 * 帖子详情内的评论内容列表
	 * @param postId
	 * @return
	 */
	public List<PostcommentVo> queryComment(@Param("postId")Integer postId);
	
	/**
	 * 帖子详情右侧显示对应版块对应栏目最新话题
	 * @param fmid
	 * @return
	 */
	public List<PostVo> queryNewPost(@Param("fmid")Integer fmid);
	
	/**
	 * 查询我的韩汀论坛中我的发帖列表
	 * @param userId
	 * @return
	 */
	public List<PostVo> queryMyPost(@Param("userId")Integer userId,@Param("title")String title);
	
	
}
