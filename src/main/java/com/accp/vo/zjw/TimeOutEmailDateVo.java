package com.accp.vo.zjw;

import java.util.Date;

public class TimeOutEmailDateVo {
	private String email;
	private String codeId;
	private Date time;

	public TimeOutEmailDateVo(String email, String codeId, Date time) {
		super();
		this.email = email;
		this.codeId = codeId;
		this.time = time;
	}

	public TimeOutEmailDateVo() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
