package com.accp.dao.zg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Services;
import com.accp.pojo.User;

public interface IMerchantDao {
	
	
	  public User  queryUserinfo(@Param("username")String username);
	  
	  
	  
	  public List<Services> queryServiceByTitle(@Param("serviceTitle") String serviceTitle);
	  
	  
	  public int addService(@Param("services")Services service);
	  
	  public int updateService(@Param("services")Services service);
	  
	  
	  
	  
	  public int deleteService(@Param("serviceID")int serviceID);
	  
	 

	 
	  
}
