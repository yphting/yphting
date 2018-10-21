package com.accp.vo.sxy;

import com.accp.pojo.Forummanagement;
import com.accp.pojo.Post;
import com.accp.pojo.User;

public class PostVo extends Post {
	private User user;
	private Forummanagement forum;
	private PostcommentVo lastcomment;
	private Integer count;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Forummanagement getForum() {
		return forum;
	}

	public void setForum(Forummanagement forum) {
		this.forum = forum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public PostcommentVo getLastcomment() {
		return lastcomment;
	}

	public void setLastcomment(PostcommentVo lastcomment) {
		this.lastcomment = lastcomment;
	}
}
