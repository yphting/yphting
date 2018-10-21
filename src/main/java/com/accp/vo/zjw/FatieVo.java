package com.accp.vo.zjw;

import java.util.Date;

public class FatieVo {
	private String userName;
	
	private Integer postid;

	private Integer userid;

	private Integer fmid;

	private String title;

	private Date releasetime;

	private Integer operatingstate;

	private Integer recommend;

	private Integer auditstatus;

	private String content;

	private String adminopinion;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getPostid() {
		return postid;
	}

	public void setPostid(Integer postid) {
		this.postid = postid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getFmid() {
		return fmid;
	}

	public void setFmid(Integer fmid) {
		this.fmid = fmid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleasetime() {
		return releasetime;
	}

	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
	}

	public Integer getOperatingstate() {
		return operatingstate;
	}

	public void setOperatingstate(Integer operatingstate) {
		this.operatingstate = operatingstate;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getAuditstatus() {
		return auditstatus;
	}

	public void setAuditstatus(Integer auditstatus) {
		this.auditstatus = auditstatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAdminopinion() {
		return adminopinion;
	}

	public void setAdminopinion(String adminopinion) {
		this.adminopinion = adminopinion;
	}

	public FatieVo(String userName, Integer postid, Integer userid, Integer fmid, String title, Date releasetime,
			Integer operatingstate, Integer recommend, Integer auditstatus, String content, String adminopinion) {
		super();
		this.userName = userName;
		this.postid = postid;
		this.userid = userid;
		this.fmid = fmid;
		this.title = title;
		this.releasetime = releasetime;
		this.operatingstate = operatingstate;
		this.recommend = recommend;
		this.auditstatus = auditstatus;
		this.content = content;
		this.adminopinion = adminopinion;
	}
	public FatieVo() {
		// TODO Auto-generated constructor stub
	}
}
