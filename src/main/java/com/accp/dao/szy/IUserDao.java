package com.accp.dao.szy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.News;
import com.accp.pojo.Sharea;
import com.accp.pojo.User;
import com.accp.vo.szy.TimeOutEmailDateVo;

public interface IUserDao {
	/**
	 * 验证邮箱是否存在
	 * @param email
	 * @return
	 */
	public Integer queryEmail(@Param("email")String email);
	/**
	 * 使用邮箱注册
	 * @return
	 */
	public int saveEmailUser(@Param("emailDate")TimeOutEmailDateVo emailDate);
	/**
	 * 使用邮箱登陆
	 * @param emailDate
	 * @return
	 */
	public User login(@Param("email")String email,@Param("password")String password);
	/**
	 * 修改邮件登陆密码
	 * @param email
	 * @param password
	 * @return
	 */
	public int updatePwd(@Param("email")String email,@Param("password")String password);
	/**
	 * 查看中韩行政地区
	 * @return
	 */
	public List<Sharea> querySharea();
	/**
	 * 修改个人资料
	 * @param u
	 * @return
	 */
	public int updateUserInfo(@Param("u")User u);
	/**
	 * 查询当前用户
	 * @param userID
	 * @return
	 */
	public User queryUser(@Param("userID")Integer  userID);
	/**
	 * 修改用户图片
	 * @param userImgPath
	 * @param userID
	 * @return
	 */
	public int updateUserImg(@Param("userImg")String userImgPath,@Param("userID")Integer  userID);
	/**
	 * 查询服务类别
	 * @param id
	 * @return
	 */
	public String querySerType(@Param("id")Integer id);
	/**
	 * 修改用户店铺信息
	 * @param u
	 * @return
	 */
	public int updateUserDpxx(@Param("u")User u);
	/**
	 * 查询用户系统信息
	 * @param userID
	 * @return
	 */
	public List<News> queryXtNews(@Param("userid")Integer userID,@Param("newsType")Integer newsType);
	/**
	 * 修改已读状态
	 * @param newsID
	 * @return
	 */
	public int updateXtNews(@Param("newsID")String newsID);
}
