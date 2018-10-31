package com.accp.vo.lsm;

import com.accp.pojo.Advertisement;

public class AdvertisementVO extends Advertisement{
	private ImageWidthAndHeightVO whObj;	//广告大小对象

	public ImageWidthAndHeightVO getWhObj() {
		return whObj;
	}

	public void setWhObj(ImageWidthAndHeightVO whObj) {
		this.whObj = whObj;
	}
}
