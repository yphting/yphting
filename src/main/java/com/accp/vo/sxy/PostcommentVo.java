package com.accp.vo.sxy;

import com.accp.pojo.Postcomment;
import com.accp.pojo.User;

public class PostcommentVo extends Postcomment {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}