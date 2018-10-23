package com.accp.dao.lhy;

import org.apache.ibatis.annotations.Param;

import com.accp.vo.lhy.Evaluate;

public interface EvaluateDao {
	/**
	 * 保存评价
	 * 
	 * @param evaluate
	 *            评价
	 * @return
	 */
	boolean saveEvaluate(@Param("evaluate") Evaluate evaluate);
}