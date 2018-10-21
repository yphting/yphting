package com.accp.biz.zg;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.accp.dao.zg.IMerchantDao;
import com.accp.pojo.Services;
import com.accp.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MerchantBiz {
	
	@Autowired
	private IMerchantDao merchantDao;
	
	public User queryUserinfo(String username) {
			return merchantDao.queryUserinfo(username);		
	}
	
	public PageInfo<Services> queryServicesBytitle(Integer pageNum,Integer pageSize,String serviceTitle){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<Services>(merchantDao.queryServiceByTitle(serviceTitle));
	}
	
	
	
	public PageInfo<Services> queryServices(Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<Services>(merchantDao.queryServiceByTitle(null));
	}
	
	
	public void addService(Services service) {
		
		merchantDao.addService(service);
		
	}
	
	public void removeService(int serviceID) {
		
		merchantDao.deleteService(serviceID);
	}
	
	public void modifyService(Services service) {
		
		merchantDao.updateService(service);
	}
	
	
	
}
