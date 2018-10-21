package com.accp.vo.zjw;

import java.util.Date;

public class HuitieOv {
	    private String userName;
	    
	    private String title;
	    
	    private Integer pcid;

	    private Integer postid;

	    private Integer commentator;

	    private Integer pid;

	    private Date reviewtime;

	    private Integer operatingstate;

	    private String content;

		public HuitieOv(String userName, String title, Integer pcid, Integer postid, Integer commentator, Integer pid,
				Date reviewtime, Integer operatingstate, String content) {
			super();
			this.userName = userName;
			this.title = title;
			this.pcid = pcid;
			this.postid = postid;
			this.commentator = commentator;
			this.pid = pid;
			this.reviewtime = reviewtime;
			this.operatingstate = operatingstate;
			this.content = content;
		}

		public HuitieOv() {
			super();
			// TODO 自动生成的构造函数存根
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getPcid() {
			return pcid;
		}

		public void setPcid(Integer pcid) {
			this.pcid = pcid;
		}

		public Integer getPostid() {
			return postid;
		}

		public void setPostid(Integer postid) {
			this.postid = postid;
		}

		public Integer getCommentator() {
			return commentator;
		}

		public void setCommentator(Integer commentator) {
			this.commentator = commentator;
		}

		public Integer getPid() {
			return pid;
		}

		public void setPid(Integer pid) {
			this.pid = pid;
		}

		public Date getReviewtime() {
			return reviewtime;
		}

		public void setReviewtime(Date reviewtime) {
			this.reviewtime = reviewtime;
		}

		public Integer getOperatingstate() {
			return operatingstate;
		}

		public void setOperatingstate(Integer operatingstate) {
			this.operatingstate = operatingstate;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

}
