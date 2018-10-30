package com.accp.biz.sxy;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.sxy.IForumDao;
import com.accp.pojo.Forummanagement;
import com.accp.pojo.Post;
import com.accp.pojo.Postcollection;
import com.accp.pojo.Postcomment;
import com.accp.pojo.Postfabulous;
import com.accp.vo.sxy.PostVo;
import com.accp.vo.sxy.PostcommentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class ForumBiz {
	
	@Autowired 
	private IForumDao dao;
	
	/**
	 * 查询所有版块（左侧栏）
	 * @return
	 */
	public List<Forummanagement> queryBlock(){
		return dao.queryBlock();
	}
	/**
	 * 查询版块对应的所有栏目
	 * @param pid
	 * @return
	 */
	public List<Forummanagement> queryCount(Integer pid){
		return dao.queryColumn(pid);
	}
	
	/**
	 * 查询版块对应版块名称
	 * @param fmid
	 * @return
	 */
	public Forummanagement queryTitleName(Integer fmid) {
		return dao.queryTitleName(fmid);
	}
	
	/**
	 * 分页查询帖子列表
	 * @param page
	 * @param size
	 * @param fmid
	 * @return
	 */
	public PageInfo<PostVo> queryPostList(Integer pid,Integer page,Integer size,Integer fmid,String title,Integer forumId,Integer orderId,Integer essence){
		PageHelper.startPage(page, size);
		return new PageInfo<PostVo>(dao.queryPostList(pid,fmid,title,forumId,orderId,essence));
	}
	
	/**
	 * 显示热门帖子
	 * @param page
	 * @param size
	 * @param title
	 * @return
	 */
	public PageInfo<PostVo> queryHotPost(Integer page,Integer size,String title){
		PageHelper.startPage(page, size);
		return new PageInfo<PostVo>(dao.queryHotPost(title));
	}
	
	/**
	 * 发布帖子
	 * @param forum
	 * @return
	 */
	public int savePost(Post post) {
		return dao.savePost(post);
	}
	
	/**
	 * 查询用户当天发帖数
	 * @param userId
	 * @return
	 */
	public int checkPostCount(Integer userId) {
		return dao.checkPostCount(userId);
	}
	
	/**
	 * 发帖加积分
	 * @param userId
	 */
	public void updateJIntegral(Integer userId) {
		dao.updateJIntegral(userId);
	}
	
	/**
	 * 查询帖子详情
	 * @param postId
	 * @return
	 */
	public PostVo queryPostDetail(Integer postId) {
		return dao.queryPostDetail(postId);
	}
	
	/**
	 * 查询帖子详情内评论列表
	 * @param page
	 * @param size
	 * @param postId
	 * @return
	 */
	public PageInfo<PostcommentVo> queryComment(Integer page,Integer size,Integer postId){
		PageHelper.startPage(page, size);
		return new PageInfo<PostcommentVo>(dao.queryComment(postId));
	}
	
	/**
	 * 显示详情页面右侧对应版块对应栏目的最新话题列表
	 * @param fmid
	 * @return
	 */
	public List<PostVo> queryNewPost(Integer fmid){
		return dao.queryNewPost(fmid);
	}
	
	/**
	 * 查询我的论坛中我的发帖列表
	 * @param page
	 * @param size
	 * @param userId
	 * @return
	 */
	public PageInfo<PostVo> queryMyForum(Integer page,Integer size,Integer userId,String title){
		PageHelper.startPage(page, size);
		return new PageInfo<PostVo>(dao.queryMyPost(userId,title));
	}
	
	/**
	 * 查询我的韩汀论坛中我回复的帖子列表
	 * @param page
	 * @param size
	 * @param userId
	 * @param title
	 * @return
	 */
	public PageInfo<PostVo> queryMyComment(Integer page,Integer size,Integer userId,String title){
		PageHelper.startPage(page, size);
		return new PageInfo<PostVo>(dao.queryMyComment(userId,title));
	}
	
	/**
	 * 查询我的韩汀论坛中我收藏的帖子列表
	 * @param page
	 * @param size
	 * @param userId
	 * @param title
	 * @return
	 */
	public PageInfo<PostVo> queryMyCollection(Integer page,Integer size,Integer userId,String title){
		PageHelper.startPage(page, size);
		return new PageInfo<PostVo>(dao.queryMyCollection(userId, title));
	}
	
	/**
	 * 验证是否重复收藏
	 * @param postId
	 * @param userId
	 * @return
	 */
	public int checkHasCollection(Integer postId,Integer userId) {
		return dao.checkHasCollection(postId, userId);
	}
	/**
	 * 验证是否收藏或点赞自己帖子
	 * @param postId
	 * @param userId
	 * @return
	 */
	public int checkIsSelf(Integer postId,Integer userId) {
		return dao.checkIsSelf(postId, userId);
	}
	
	/**
	 * 收藏
	 * @param collec
	 */
	public void saveCollection(Postcollection collec) {
		dao.saveCollection(collec);
	}
	
	/**
	 * 验证是否重复点赞
	 * @param postId
	 * @param userId
	 * @return
	 */
	public int checkHasFabulous(Integer postId,Integer userId) {
		return dao.checkHasFabulous(postId, userId);
	}
	
	/**
	 * 点赞
	 * @param fabu
	 */
	public void saveFabulous(Postfabulous fabu) {
		dao.saveFabulous(fabu);
	}
	
	/**
	 * 评论
	 * @param comm
	 */
	public void saveComment(Postcomment comm) {
		dao.saveComment(comm);
	}
	
	/**
	 * 查询用户最新评论
	 * @param userId
	 * @return
	 */
	public Postcomment queryLastComment(Integer userId) {
		return dao.queryLastComment(userId);
	}
	
	/**
	 * 查询当天 评论次数
	 * @param userId
	 * @return
	 */
	public int queryCommentCount(Integer userId) {
		return dao.queryCommentCount(userId);
	}
	
	/**
	 * 回复评论加积分
	 * @param comm
	 */
	public void updateCommentJIntegral(Postcomment comm) {
		dao.updateCommentJIntegral(comm);
	}
	
	/**
	 * 删除评论
	 * @param pcid
	 */
	public void removeComment(Integer pcid) {
		dao.removeComment(pcid);
	}
	
} 
