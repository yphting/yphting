package com.accp.dao.zg;

import java.util.List;




import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Appraisalapply;
import com.accp.pojo.Services;
import com.accp.pojo.User;
import com.accp.vo.zg.ServicesVo;
public interface IMerchantDao {
	
	
	  public User  queryUserinfo(@Param("username")String username);
	  
	  
	  public User queryUserByid(@Param("userid")Integer userid);
	  
	  public List<ServicesVo> queryServiceByTitle(@Param("serviceTitle") String serviceTitle,@Param("userid")Integer userid);
	  
	  public Services  queryService(@Param("serviceid")int serviceid);
	  
	  public int addService(@Param("services")Services service);
	  
	  public int updateService(@Param("services")Services service);
	  
	  public int deleteService(@Param("serviceID")int serviceID);
	  
	  public List<Appraisalapply>  queryAppraisalapplyByUserId(@Param("userid") int  userid);
	  	
	  public int updateAppraisalapply(@Param("app") Appraisalapply app);
	  
}
