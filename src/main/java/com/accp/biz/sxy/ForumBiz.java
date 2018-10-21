package com.accp.biz.sxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.sxy.IForumDao;
import com.accp.pojo.Forummanagement;
import com.accp.vo.sxy.PostVo;
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
}
