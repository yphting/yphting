package com.accp.vo.zsp;

import java.util.ArrayList;
import java.util.List;

public class userVo {
	    private String userName;
	    private String shopName;
	    private String signature;
	    private String shopImg;
	    private List<ServicesVo>servicesVo=new ArrayList<ServicesVo>(0);
	    
		public userVo() {
			super();
			// TODO Auto-generated constructor stub
		}
		public userVo(String userName, String shopName, String signature, String shopImg, List<ServicesVo> servicesVo) {
			super();
			this.userName = userName;
			this.shopName = shopName;
			this.signature = signature;
			this.shopImg = shopImg;
			this.servicesVo = servicesVo;
		}
		public List<ServicesVo> getServicesVo() {
			return servicesVo;
		}
		public void setServicesVo(List<ServicesVo> servicesVo) {
			this.servicesVo = servicesVo;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getShopName() {
			return shopName;
		}
		public void setShopName(String shopName) {
			this.shopName = shopName;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
		public String getShopImg() {
			return shopImg;
		}
		public void setShopImg(String shopImg) {
			this.shopImg = shopImg;
		}
	    
}
