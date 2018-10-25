package com.accp.biz.zg;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.zg.IMerchantDao;

import com.accp.pojo.Services;

import com.accp.pojo.User;
import com.accp.vo.zg.ServicesVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MerchantBiz {
	
	@Autowired
	private IMerchantDao merchantDao;
	
	public User queryUserinfo(String username) {
			return merchantDao.queryUserinfo(username);		
	}
	
	public PageInfo<ServicesVo> queryServicesBytitle(Integer pageNum,Integer pageSize,String serviceTitle){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<ServicesVo>(merchantDao.queryServiceByTitle(serviceTitle));
	}
	
	
	
	public PageInfo<ServicesVo> queryServices(Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<ServicesVo>(merchantDao.queryServiceByTitle(null));
	}
	
	public Services queryServices(int serviceid) {
			return merchantDao.queryService(serviceid);
		
		
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void addService(Services service) {
		
		merchantDao.addService(service);
		
	}
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void removeService(int serviceID) {
		
		merchantDao.deleteService(serviceID);
	}
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void modifyService(Services  service) {
		
		merchantDao.updateService(service);
	}
	
	
	
	
	
}
