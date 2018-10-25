package com.accp.dao.lhy;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.accp.vo.lhy.User;

@Component("lhyuserdao")
public interface UserDao {
	/**
	 * 根据编号获取用户
	 * 
	 * @param userid
	 *            编号
	 * @return
	 */
	User queryUserById(int userid);

	/**
	 * 相对修改用余额
	 * 
	 * @param usermoney
	 *            相对余额
	 * @param userid
	 *            用户编号
	 * @return
	 */
	boolean updateUserMoney(@Param("usermoney") double usermoney, @Param("userid") int userid);
}