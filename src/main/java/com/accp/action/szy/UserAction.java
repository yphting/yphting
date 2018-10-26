package com.accp.action.szy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.accp.biz.szy.UserBiz;
import com.accp.pojo.News;
import com.accp.pojo.Sharea;
import com.accp.pojo.User;
import com.accp.util.code.VerifyCode;
import com.accp.util.email.Email;
import com.accp.util.email.EmailBoard;
import com.accp.util.file.Upload;
import com.accp.util.rsaKey.RSAUtils;
import com.accp.vo.szy.ListVo;
import com.accp.vo.szy.NewsVo;
import com.accp.vo.szy.TimeOutEmailDateVo;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/c/szy")
public class UserAction {
	
	@Autowired
	private UserBiz biz;
	/**
	 * 验证账号
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/user/yzEmail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,String> queryEmail(String email){
		Map<String,String> map=new HashMap<>();
		System.out.println("执行邮箱验证");
		try {
			if(biz.queryEmail(email)) {
				map.put("code", "200");
			}else {
				map.put("code", "500");
			}
		} catch (Exception e) {
			map.put("msg", e.getMessage());
		}
		return map;
	}
	/**
	 * 邮箱注册
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/user/gotoLogin",method=RequestMethod.POST)
	public String gotoLogin(Model model,String email,String username) {
		System.out.println(email+"-----"+username+"----");
		try {
			//生成安全码
			String codeId=VerifyCode.createVerifyCode(8);
			if(ListVo.emailList.get(email)==null) {
				ListVo.emailList.put(email,new TimeOutEmailDateVo(email, codeId, new Date()));
			}else {
				ListVo.emailList.get(email).setTime(new Date());
				ListVo.emailList.get(email).setCodeId(codeId);
			}
			
			System.out.println("执行Email新增=========");
			Email.sendSimpleMail(email, "用户注册", EmailBoard.register(username, "http://127.0.0.1/c/szy/user/emailYanz?email="+email+"&nickName="+username+"&codeId="+codeId));
			System.out.println("====================\n发送成功\n====================\n");
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			System.out.println("====================\n发送失败\n====================\n");
		}
		return "redirect:/szy-login.html";
	}
	/**
	 * 激活邮箱
	 * @param model
	 * @param email
	 * @param nickName
	 * @param codeId
	 * @return
	 */
	@RequestMapping(value="/user/emailYanz",method=RequestMethod.GET)
	public String emailYanz(Model model,String email,String nickName,String codeId) {
		if(ListVo.emailList.get(email)==null) {
			return "/szy-yz-no.html";
		}else {
			if(ListVo.emailList.get(email).getCodeId().trim().equals(codeId.trim())) {
				model.addAttribute("user", new TimeOutEmailDateVo(email,nickName));
				return "/szy-zuce-yz.html";
			}else {
				return "/szy-yz-no.html";
			}
		}
	}
	/**
	 * 新增邮箱登陆用户
	 * @param tqedv
	 * @return
	 */
	@RequestMapping(value="/user/saveEmail",method=RequestMethod.POST)
	public String saveEmail(TimeOutEmailDateVo tqedv) {
		if(biz.saveEmailUser(tqedv)) {
			return "redirect:/szy-login.html";
		}else{
			return "/szy-zuce-yz.html";
		}
	}
	/**
	 * 获取公钥
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/user/rsaKey",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> generateRSAKey(String email){
				// 将公钥传到前端
	            Map<String,String> map = new HashMap<String,String>();
		 try {
				// 获取公钥和私钥
				HashMap<String, Object> keys = RSAUtils.getKeys();
	            RSAPublicKey publicKey = (RSAPublicKey) keys.get("public");
	            RSAPrivateKey privateKey = (RSAPrivateKey) keys.get("private");
	            // 保存私钥到 redis，也可以保存到数据库
	            try {
					ListVo.emailService.put(email, privateKey);
				} catch (Exception e) {
					System.out.println("私钥存储失败");
				}
	            // 注意返回modulus和exponent以16为基数的BigInteger的字符串表示形式
	            map.put("modulus", publicKey.getModulus().toString(16));
	            map.put("exponent", publicKey.getPublicExponent().toString(16));
	        } catch (Exception e) {
	        	map.put("msg", e.getMessage());
	        } 
		 return map;
	}
	/**
	 * 解密 登陆方法
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/user/checkRSAKey",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> checkRSAKey(HttpSession session,String email,String password) {
	        Object object = ListVo.emailService.get(email);
	        Map<String,String> map = new HashMap<String,String>();
	        try {
	            // 解密
	        	System.out.println(password);
	            String decryptByPrivateKey = RSAUtils.decryptByPrivateKey(password, (RSAPrivateKey) object);
	            System.out.println(decryptByPrivateKey);
	            User u=biz.login(email, decryptByPrivateKey);
	    		if(u!=null) {
	    			session.setAttribute("USER", u);
	    			session.setAttribute("Email", email);
	    			map.put("code", "200");
	    		}else {
	    			map.put("code", "500");
	    		}
	        } catch (Exception e) {
	            map.put("msg", e.getMessage());
	        }
	        return map;
	 }

	/**
	 * 站外修改密码
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/user/updatePwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updatePwd(String email){
		Map<String,String> map=new HashMap<>();
		try {
			String pwd=VerifyCode.createVerifyCode(6);
			Email.sendSimpleMail(email, "重置密码", EmailBoard.verifyCode(email, "您的密码已重置,新密码为:", pwd));
			System.out.println("====================\n修改密码发送成功\n====================\n");
			if(biz.updatePwd(email, pwd)) {
				map.put("code", "200");
			}else {
				map.put("code", "500");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("====================\n修改密码发送失败\n====================\n");
			map.put("msg", e.getMessage());
		}
		return map;
	}
	/**
	 * 退出登陆方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/close",method=RequestMethod.GET)
	public String close(HttpSession session) {
		session.removeAttribute("USER");
		session.removeAttribute("Email");
		return "/szy-login.html";
	}
	/**
	 * 获取用户信息方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/queryGrzxInfo",method=RequestMethod.GET)
	public String queryGrzxInfo(){
		return "zhsz-grzl.html";
	}
	/**
	 * 获取用户信息方法
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/queryHeaderUser",method=RequestMethod.GET)
	@ResponseBody
	public User queryHeaderUser(Integer userid) {
		return biz.queryUser(userid);
	}
	/**
	 * 查询地址
	 * @return
	 */
	@RequestMapping(value="/user/querySharea",method=RequestMethod.GET)
	@ResponseBody
	public List<Sharea> querySharea(){
		return biz.querySharea();
	}
	/**
	 * 修改账户信息
	 * @param session
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/user/updateUserInfo",method=RequestMethod.POST)
	public String updateUserInfo(HttpSession session,User u){
		if(biz.updateUserInfo(u)) {
			Integer userID=((User)session.getAttribute("USER")).getUserid();
			session.setAttribute("USER", biz.queryUser(userID));
		}
		return "zhsz-grzl.html";
	}
	/**
	 * 修改图片
	 * @param session
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/user/updateUserImg",method=RequestMethod.POST)
	public String updateUserImg(HttpSession session,@RequestParam("img") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				Integer userID=((User)session.getAttribute("USER")).getUserid();
				String url = Upload.uploadFile(file);
				biz.updateUserImg(url, userID);
				session.setAttribute("USER", biz.queryUser(userID));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		return "zhsz-grzl.html";
	}
	/**
	 * 修改密码
	 * @param session
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/user/updateEmailPwd",method=RequestMethod.POST)
	public String updateEmailPwd(HttpSession session,String password) {
		String email=session.getAttribute("Email").toString();
		biz.updatePwd(email, password);
		session.removeAttribute("USER");
		session.removeAttribute("Email");
		return "szy-login.html";
	}
	/**
	 * 店铺设置
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/getdpszInfo",method=RequestMethod.GET)
	public String getdpszInfo(Model model,HttpSession session) {
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		model.addAttribute("User", biz.queryUser(userID));
		return "/zhsz-dpxx.html";
	}
	/**
	 * 查询服务类别
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user/getSerType",method=RequestMethod.GET)
	@ResponseBody
	public String getSerType(Integer id) {
		return biz.querySerType(id);
	}
	/**
	 * 修改店铺信息
	 * @param u
	 * @param file1
	 * @param file2
	 * @param file3
	 * @param file4
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value="/user/updateUserDpxx",method=RequestMethod.POST)
	public String updateUserDpxx(HttpSession session,User u,@RequestParam("thumb") MultipartFile file1,@RequestParam("idcardpic1") MultipartFile file2,@RequestParam("idcardpic2") MultipartFile file3,@RequestParam("vippic") MultipartFile file4) throws IllegalStateException, IOException {
		System.out.println("1");
		if(file1.getSize()!= 0) {
			u.setShopimg(Upload.uploadFile(file1));
		}
		if(file2.getSize()!= 0) {
			u.setIdentitypositiveimg(Upload.uploadFile(file2));
		}
		if(file3.getSize()!= 0) {
			u.setIdentitynegativeimg(Upload.uploadFile(file3));
		}
		if(file4.getSize()!= 0) {
			u.setIdentityhandimg(Upload.uploadFile(file4));
		}
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		u.setUserid(userID);
		biz.updateUserDpxx(u);
		session.setAttribute("USER", biz.queryUser(userID));
		return "redirect:/c/szy/user/getdpszInfo";
	}
	/**
	 * 分页查询用户系统信息
	 * @param session
	 * @param newsType
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/user/queryNewPageInfo",method=RequestMethod.GET)
	@ResponseBody
	public PageInfo<News> queryNewPageInfo(HttpSession session,Integer newsType,Integer pageNum,Integer pageSize){
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		return biz.queryNewPageInfo(userID, newsType, pageNum, pageSize);
	}
	
	/**
	 * 修改已读状态
	 * @param newsID
	 * @return
	 */
	@RequestMapping(value="/user/updateXtNews",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateXtNews(String newsID){
		Map<String,String> m=new HashMap<>();
		System.out.println(newsID);
		newsID=newsID.substring(1, newsID.length());
		System.out.println(newsID);
		String[] Ids=newsID.split(",");
		try {
			for (String id : Ids) {
				biz.updateXtNews(id);
			}
			m.put("code", "200");
		} catch (Exception e) {
			m.put("code", "500");
			m.put("msg", e.getMessage());
		}
		return m;
	}
	
	/**
	 * 删除系统消息
	 * @param newsID
	 * @return
	 */
	@RequestMapping(value="/user/deleteNews",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> deleteNews(String newsID){
		Map<String,String> m=new HashMap<>();
		newsID=newsID.substring(1, newsID.length());
		String[] Ids=newsID.split(",");
		try {
			for (String id : Ids) {
				biz.deleteNews(id);
			}
			m.put("code", "200");
		} catch (Exception e) {
			m.put("code", "500");
			m.put("msg", e.getMessage());
		}
		return m;
	}
	/**
	 * 获取当前用户session
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/queryAUser")
	@ResponseBody
	public User queryAUser(HttpSession session) {
		User u=new User();
		u=(User)session.getAttribute("USER");
		return u;
	}
	/**
	 * 查询用户站内信
	 * @param session
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/user/queryZnxNewsPageInfo",method=RequestMethod.GET)
	@ResponseBody
	public PageInfo<NewsVo>  queryZnxNewsPageInfo(HttpSession session,Integer pageNum,Integer pageSize){
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		return biz.queryZnxNewsPageInfo(userID, pageNum, pageSize);
	}
	/**
	 * 修改站内信状态
	 * @param groupID
	 * @return
	 */
	@RequestMapping(value="/user/updateZnxNews",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateZnxNews(String groupID) {
		Map<String,String> m=new HashMap<>();
		System.out.println(groupID);
		groupID=groupID.substring(1, groupID.length());
		System.out.println(groupID);
		String[] Ids=groupID.split(",");
		try {
			for (String id : Ids) {
				biz.updateZnxNews(id);
			}
			m.put("code", "200");
		} catch (Exception e) {
			m.put("code", "500");
			m.put("msg", e.getMessage());
		}
		return m;
	}
	

	/**
	 * 删除系统消息
	 * @param newsID
	 * @return
	 */
	@RequestMapping(value="/user/deleteZnxNews",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> deleteZnxNews(String groupID){
		Map<String,String> m=new HashMap<>();
		groupID=groupID.substring(1, groupID.length());
		String[] Ids=groupID.split(",");
		try {
			for (String id : Ids) {
				biz.deleteZnxNews(id);
			}
			m.put("code", "200");
		} catch (Exception e) {
			m.put("code", "500");
			m.put("msg", e.getMessage());
		}
		return m; 
	}
	/**
	 * 查询站内信详情
	 * @param model
	 * @param groupID
	 * @return
	 */
	@RequestMapping(value="/user/queryZnxXq",method=RequestMethod.GET)
	public String queryZnxXq(Model model ,String groupID) {
		model.addAttribute("news", biz.queryZnxXq(groupID));
		return "/xx-znx-xq.html";
	}
	/**
	 * 新增站内信
	 * @param session
	 * @param thesender
	 * @param content
	 * @param newstype
	 * @param messagegroup
	 * @return
	 */
	@RequestMapping(value="/user/saveZnx",method=RequestMethod.GET)
	public String saveZnx(HttpSession session,Integer thesender,String content,Integer newstype,Integer messagegroup) {
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		News n=new News();
		n.setThesender(userID);
		n.setContent(content);
		n.setNewstype(newstype);
		n.setMessagegroup(messagegroup);
		n.setAddressee(thesender);
		biz.saveZnx(n);
		return "redirect:/c/szy/user/queryZnxXq?groupID="+messagegroup+"&thesender="+thesender;
	}
	
	/**
	 * 查询所有信息
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="/user/queryAllNews",method=RequestMethod.GET)
	@ResponseBody
	public List<News> queryAllNews(HttpSession session){
		Integer userID=((User)session.getAttribute("USER")).getUserid();
		return biz.queryAllNews(userID);
	}
}
