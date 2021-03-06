package com.accp.dao.zg;

import java.util.List;




import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Appraisalapply;
import com.accp.pojo.Services;
import com.accp.pojo.User;
import com.accp.vo.zg.EvaluationVo;
import com.accp.vo.zg.ServicesVo;
import com.accp.vo.zg.UserAppVo;
public interface IMerchantDao {
	
	
	  public User  queryUserinfo(@Param("username")String username);
	  
	  
	  public User queryUserByid(@Param("userid")Integer userid);
	  
	  public List<ServicesVo> queryServiceByTitle(@Param("serviceTitle") String serviceTitle,@Param("userid")Integer userid);
	  
	  public Services  queryService(@Param("serviceid")int serviceid);
	  
	  public int addService(@Param("services")Services service);
	  
	  public int updateService(@Param("services")Services service);
	  
	  public int deleteService(@Param("serviceID")int serviceID);
	  
	  //public UserAppVo queryAppraisalapply(@Param("userid") int userid);
	   	
	  public List<UserAppVo>  queryAppraisalapply(@Param("userid") int userid ,@Param("oneid") int oneid,@Param("twoid") int twoid);
	  
	  public int addAppraisalapply(@Param("pojo") Appraisalapply pojo);
	  
	  public List<EvaluationVo>  queryEvaluation(@Param("userid") int userid);
	  
}
