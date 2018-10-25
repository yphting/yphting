package com.accp.dao.zg;

import java.util.List;




import org.apache.ibatis.annotations.Param;
import com.accp.pojo.Services;
import com.accp.pojo.User;
import com.accp.vo.zg.ServicesVo;
public interface IMerchantDao {
	
	
	  public User  queryUserinfo(@Param("username")String username);
	  
	  public List<ServicesVo> queryServiceByTitle(@Param("serviceTitle") String serviceTitle);
	  
	  public Services  queryService(@Param("serviceid")int serviceid);
	  
	  public int addService(@Param("services")Services service);
	  
	  public int updateService(@Param("services")Services service);
	  
	  
	  public int deleteService(@Param("serviceID")int serviceID);
	  
	  

	 
	  
}
