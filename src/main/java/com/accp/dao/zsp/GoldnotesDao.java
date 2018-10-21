package com.accp.dao.zsp;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.Banktype;
import com.accp.pojo.Evaluationservice;
import com.accp.pojo.Goldnotes;
import com.accp.pojo.Integralrecord;
import com.accp.pojo.Logistics;
import com.accp.pojo.Putforward;
import com.accp.pojo.Sharea;


public interface GoldnotesDao {
	/**
	 * 根据 用户id查询金币流向表
	 * @param userId
	 * @return
	 */
    public List<Goldnotes>goldnotesQueryAll(@Param("userId")Integer userId);
    /**
     * 查询积分流向
     * @param userId
     * @return
     */
    public List<Integralrecord>IntegralRecordQueryAll(@Param("userId")Integer userId);
    /**
     *添加用户积分流向表
     * @param putforWard
     * @return
     */
    public int addPutforWard(@Param("putforWard") Putforward putforWard);
   /**
    *  添加用户金币支入支出记录
    * @param goldnotes
    * @return
    */
    public int  addGoldnotes(Goldnotes goldnotes);
    /**
     * 查询我的物流
     * @param logistics
     * @return
     */
    public List<Logistics> getListlogistics(@Param("logistics")Logistics logistics);
    /**
     * 查询我的服务商品评价记录
     * @param evaluationservice
     * @return
     */
    public List<Evaluationservice>getListEvaluationService(@Param("evaluationservice")Evaluationservice evaluationservice);
    /**
     * 查询银行列表
     * @return 
     */
    public List<Banktype>getListBankType();
    /**
     * 添加物流记录 
     * @param logistics
     * @return
     */
    public int addLogistics(@Param("logistics")Logistics logistics);
    /**
     * 查询物流详情
     * @param id
     * @return
     */
    public Logistics getLogistics(@Param("userId")Integer  userId,@Param("id")Integer id);
    /**
     * 查询地址 
     * @param id
     * @return
     */
    public List<Sharea> getShAreaById(@Param("id")Integer id);
}