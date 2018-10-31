package com.accp.biz.zsp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.zsp.GoldnotesDao;
import com.accp.pojo.Banktype;
import com.accp.pojo.Evaluationservice;
import com.accp.pojo.Goldnotes;
import com.accp.pojo.Integralrecord;
import com.accp.pojo.Logistics;
import com.accp.pojo.News;
import com.accp.pojo.Putforward;
import com.accp.pojo.Services;
import com.accp.pojo.Sharea;
import com.accp.pojo.User;
import com.accp.vo.zsp.EvaluationserviceToservicesVo;
import com.accp.vo.zsp.ServicesVo;
import com.accp.vo.zsp.UserToServicesVo;
import com.accp.vo.zsp.userVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class GoldnotesBiz {
	
	@Autowired
    private GoldnotesDao dao;
	/**
	 * 金币流向表
	 * @param pageNum  
	 * @param pageSize
	 * @param userId用户id
	 * @return
	 */
    public  PageInfo<Goldnotes> goldnotesQueryAll(Integer pageNum,Integer pageSize,Integer userId,Integer acquisitionMode) {
    	 PageHelper.startPage(pageNum,pageSize);
  	   return new PageInfo<Goldnotes>(dao.goldnotesQueryAll(userId,acquisitionMode));
    }
    /**
     * 查询积分流向
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    public PageInfo<Integralrecord>IntegralRecordQueryAll(Integer pageNum,Integer pageSize,Integer userId){
   	    PageHelper.startPage(pageNum,pageSize);
    	return new PageInfo<Integralrecord>(dao.IntegralRecordQueryAll(userId));
    }
    /**
     * 添加金币提现记录表
     * @param Goldnotes 
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void addPutforWard(Putforward  putforward) {
    	User user=dao.getUser(putforward.getUserid());
    	Float money=user.getUsermoney()-putforward.getMoney();
    	dao.updUser(money, null,putforward.getUserid());
		dao.addPutforWard(putforward);
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		
		News news=new News();
    	news.setAddressee(putforward.getUserid());
    	news.setContent("您于"+dateString+"进行金币提现");
    	news.setMessagegroup(2);
    	news.setNewstype(1);
    	news.setReadstate(false);
    	news.setSendingtime(new Date());
    	news.setThesender(putforward.getUserid());
    	dao.addNews(news);
	}
    /**
     * 添加金币充值记录表
     * @param goldnotes
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void addGoldnotes(Goldnotes  goldnotes) {
    	Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
    	News news=new News();
    	news.setAddressee(goldnotes.getUserid());
    	news.setContent("您于"+dateString+"进行了充值");
    	news.setMessagegroup(1);
    	news.setNewstype(1);
    	news.setReadstate(false);
    	news.setSendingtime(new Date());
    	news.setThesender(null);
    	dao.addNews(news);
    	dao.addGoldnotes(goldnotes);
	}
    /**
     * 查询物流记录
     * @param pageNum
     * @param pageSize
     * @param logistics
     * @return
     */
    public PageInfo<Logistics>getListLogistics(Integer pageNum,Integer pageSize,Logistics logistics){
    	PageHelper.startPage(pageNum, pageSize);
    	return new PageInfo<Logistics>(dao.getListlogistics(logistics));
    }
    /**
     * 查询商品服务评价记录
     * @param pageNum
     * @param pageSize
     * @param evaluationservice
     * @return
     */
  public PageInfo<EvaluationserviceToservicesVo>getListEvaluationService(Integer pageNum,Integer pageSize,Evaluationservice evaluationservice){
	  PageHelper.startPage(pageNum, pageSize);
	  return new PageInfo<EvaluationserviceToservicesVo>(dao.getListEvaluationService(evaluationservice));
  }
  /**
   * 查询银行类别
   * @return
   */
  public List<Banktype>getListBankType(){
	  return dao.getListBankType();
  }
  /**
   * 添加物流 
   * @param goldnotes
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
 public void addLogistics(Logistics logistics) {
	 dao.addLogistics(logistics);
 }
  /**
   * 查询物流详情
   * @param id
   * @return
   */
  public Logistics getLogistics(Integer userId,Integer id) {
	  return dao.getLogistics(userId, id);
  }
  public List<Sharea>getShAreaById(Integer id) {
	  return dao.getShAreaById(id);
  }
  /**
   * 查询用户余额
   * @param userId
   * @return
   */
  public User getUser(Integer userId) {
	  return dao.getUser(userId);
  }
  /**
   * 修改金币记录状态
   * @param recordId
   * @param auditStatus
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
  public void updGoldnotes(Integer recordId,Integer auditStatus) {
	  dao.updGoldnotes(recordId, auditStatus);
  }
  /**
   * 查询当前金币记录是否存在
   * @param recordId
   * @return
   */
  public Goldnotes getGoldnotesById(Integer recordId) {
	  return dao.getGoldnotesById(recordId);
  }
  /**
   * 查询收藏的服务
   * @param pageNum
   * @param pageSize
   * @param userId
   * @return
   */
  public PageInfo<Services>getServicesByUserId(Integer pageNum,Integer pageSize,Integer userId){
	  PageHelper.startPage(pageNum, pageSize);
	  return new PageInfo<Services>(dao.getServicesByUserId(userId));
  }
  /**
   * 查询收藏的服务及商家
   * @param pageNum
   * @param pageSize
   * @param userId
   * @return
   */
  public PageInfo<userVo>getMerchantCollectionById(Integer pageNum,Integer pageSize,Integer userId){
	  PageHelper.startPage(pageNum, pageSize);
	  return new PageInfo<userVo>(dao.getMerchantCollectionById(userId));
  }
  /**
   * 修改用户余额合物流状态
   * @param moery
   * @param userId
   * @param logisticsid
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
  public void updUser(Float moery,Integer userId,Integer logisticsid) {
	  Logistics logistics=new Logistics();
	  logistics.setAuditstatus(2);
	  logistics.setOrdertime(new Date());
	  logistics.setLogisticsid(logisticsid);
	    Logistics logisticss = dao.getLogistics(userId,logisticsid);
	    Goldnotes goldnotes=new Goldnotes();
		goldnotes.setUserid(userId);
		goldnotes.setAcquisitionmode(2);
		goldnotes.setRecorddate(new Date());
		goldnotes.setRecorddescribe("物流支付订单号"+logisticss.getLogisticsid());
		goldnotes.setRecordinandout(-+(float)logisticss.getPrice());
		goldnotes.setAuditstatus(2);
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
    
		News news=new News();
    	news.setAddressee(goldnotes.getUserid());
    	news.setContent("您于"+dateString+"进行了物流支付");
    	news.setMessagegroup(1);
    	news.setNewstype(1);
    	news.setReadstate(false);
    	news.setSendingtime(new Date());
    	news.setThesender(null);
    	dao.addNews(news);
		
		dao.addGoldnotes(goldnotes);
	    dao.updatedLogistics(logistics);
	    dao.updUser(moery,null,userId);
  }
  /**
   * 修改物流状态
   * @param logistics
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
  public void updatedLogistics(Logistics logistics) {
	  dao.updatedLogistics(logistics);
  }
  public List<UserToServicesVo>getUserToServicesVo(){
	  return dao.getUserToServicesVo();
  }
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
  public void updateUserToGoldnotes(Float total_amount,String out_trade_no,Integer userId,Integer getAuditstatus) {
	  User user=dao.getUser(userId);
  	if(user.getUsermoney()==null) {
  		user.setUsermoney((float) 0);
  	}
  	Float money=user.getUsermoney()+total_amount;
  	  Integer userIntegra2=(int) (total_amount/10);
	  user.setUserintegral(user.getUserintegral()+userIntegra2);
  	 // user.setuserIntegra1();
	  Integralrecord Integralrecord=new Integralrecord();
	  Integralrecord.setUserid(userId);
	  Integralrecord.setIrdate(new Date());
	  Integralrecord.setIrdescribe("充值送积分");
	  Integralrecord.setAuditstatus(4);
	  Integralrecord.setRecordinandout(user.getUserintegral());
      dao.addIntegralRecord(Integralrecord);
	  dao.updGoldnotes(Integer.parseInt(out_trade_no),getAuditstatus);
	  dao.updUser(money, user.getUserintegral(),userId);
  }
}





