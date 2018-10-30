package com.accp.vo.zg;

import java.util.Date;

public class UserAppVo {
		private int firstServiceID;
		private int secondServiceID;
		private Date submitTime;
		private Date auditTime;
		private int auditStatus;
		public int getFirstServiceID() {
			return firstServiceID;
		}
		public void setFirstServiceID(int firstServiceID) {
			this.firstServiceID = firstServiceID;
		}
		public int getSecondServiceID() {
			return secondServiceID;
		}
		public void setSecondServiceID(int secondServiceID) {
			this.secondServiceID = secondServiceID;
		}
		public Date getSubmitTime() {
			return submitTime;
		}
		public void setSubmitTime(Date submitTime) {
			this.submitTime = submitTime;
		}
		public Date getAuditTime() {
			return auditTime;
		}
		public void setAuditTime(Date auditTime) {
			this.auditTime = auditTime;
		}
		public int getAuditStatus() {
			return auditStatus;
		}
		public void setAuditStatus(int auditStatus) {
			this.auditStatus = auditStatus;
		}
		
		
		
}
