package com.accp.biz.lhy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.lhy.UserDao;
import com.accp.vo.lhy.User;

@Service("lhyusrbiz")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class UserBiz {
	@Autowired
	private UserDao dao;

	/**
	 * 根据编号获取用户
	 * 
	 * @param userid
	 *            用户编号
	 * @return
	 */
	public User queryUserById(int userid) {
		return dao.queryUserById(userid);
	}
}