package com.accp.vo.sxy;

import java.util.List;

import com.accp.pojo.Postcomment;
import com.accp.pojo.User;

public class PostcommentVo extends Postcomment {
	private User user;
	private List<Postcomment> pc;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Postcomment> getPc() {
		return pc;
	}

	public void setPc(List<Postcomment> pc) {
		this.pc = pc;
	}

	
}