package com.accp.action.zg;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.accp.biz.zg.MerchantBiz;
import com.accp.pojo.Services;
import com.accp.pojo.User;
import com.accp.util.file.Upload;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/zg/c")
public class MerchantAction {
	
	@Autowired
	private  MerchantBiz  merchantBiz;
	
	@GetMapping("getUserinfo")
	public String queryUserinfo(HttpSession session,String username) {
		User user=merchantBiz.queryUserinfo(username);
		if(user!= null) {
			session.setAttribute("USER",user);
			return "sjzx-index";	
		}else {
			return "redirect:/sjzx-index";
		}	
	}
	
	@GetMapping("getServices")
	public String  queryService(Model model,Integer pageNum,Integer pageSize) {
		System.out.println(pageNum);
		PageInfo<Services> pageInfo=merchantBiz.queryServices(pageNum, pageSize);
		model.addAttribute("PAGE_INFO", pageInfo);
		return "sjzx-services";
	}
	
	@GetMapping("getServicesByTitle")
	public String  queryServiceByTitle(Model model,Integer pageNum,Integer pageSize,String serviceTitle) {
		PageInfo<Services> pageInfo=merchantBiz.queryServicesBytitle(pageNum, pageSize, serviceTitle);
		model.addAttribute("PAGE_INFO", pageInfo);
		return "sjzx-services";
	}
	
	
	@PostMapping("addServices")
	public String  addServices(HttpSession session,Model model,int stid,String servicetitle,String servicefutitle,int serviceprice, MultipartFile file1,MultipartFile file2,MultipartFile file3,MultipartFile file4,MultipartFile file5,String serviceintro,String[] servicecity,int country,String[] servicecostinclude,String servicecosttypeid,String uploadtitle,String uploaddataurl) {
		String fmturl = null;
		String xjturl1=null;
		String xjturl2=null;
		String xjturl3=null;
		String xjturl4=null;
		if(!file1.isEmpty()) {
			try {
				fmturl=Upload.uploadImg(file1);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
		}
		if(!file2.isEmpty()) {
			try {
				xjturl1=Upload.uploadImg(file2);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		if(!file3.isEmpty()) {
			try {
				xjturl2=Upload.uploadImg(file3);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		if(!file4.isEmpty()) {
			try {
				xjturl3=Upload.uploadImg(file4);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		if(!file5.isEmpty()) {
			try {
				xjturl4=Upload.uploadImg(file5);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		String cs = null;
		for(int i=1;i<servicecity.length;i++) {
			cs+=servicecity[i]+",".substring(0,servicecity.length-1);
		}
		
		String fysm = null;
		for(int i=1;i<servicecostinclude.length;i++) {
			fysm+=servicecostinclude[i]+",".substring(0,servicecostinclude.length-1);
		}
		
		Services service=new Services(stid,null,null,servicetitle,servicefutitle,serviceprice,fmturl,xjturl1,xjturl2,xjturl3,xjturl4,servicecosttypeid,serviceintro,cs,country,fysm,null, null,null,null,null,null,null,null,null,null,uploadtitle, uploaddataurl,null,null,null,null);	
		merchantBiz.addService(service);
		return "redirect:/zg/c/getServices?pageNum=1&pageSize=3";
	}
	
	@PostMapping("removeServices")
	public String  removeService(int serviceID) {
		merchantBiz.removeService(serviceID);
		return "redirect:/zg/c/getServices?pageNum=1&pageSize=3";
	}
	
	@PostMapping("updateServices")
	public String  modifyServices(HttpSession session,Model model,int serviceid,int stid,String servicetitle,String servicefutitle,int serviceprice, MultipartFile file1,MultipartFile file2,MultipartFile file3,MultipartFile file4,MultipartFile file5,String serviceintro,String[] servicecity,int country,String[] servicecostinclude,String servicecosttypeid,String uploadtitle,String uploaddataurl) {
		String fmturl = null;
		String xjturl1=null;
		String xjturl2=null;
		String xjturl3=null;
		String xjturl4=null;
		if(!file1.isEmpty()) {
			try {
				fmturl=Upload.uploadImg(file1);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
		}
		if(!file2.isEmpty()) {
			try {
				xjturl1=Upload.uploadImg(file2);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		if(!file3.isEmpty()) {
			try {
				xjturl2=Upload.uploadImg(file3);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		if(!file4.isEmpty()) {
			try {
				xjturl3=Upload.uploadImg(file4);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		if(!file5.isEmpty()) {
			try {
				xjturl4=Upload.uploadImg(file5);
				model.addAttribute("message","图片路径正确");
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("message","图片路径错误");
			}
			
		}
		String cs = null;
		for(int i=1;i<servicecity.length;i++) {
			cs+=servicecity[i]+",".substring(0,servicecity.length-1);
		}
		
		String fysm = null;
		for(int i=1;i<servicecostinclude.length;i++) {
			fysm+=servicecostinclude[i]+",".substring(0,servicecostinclude.length-1);
		}
		
		Services service=new Services(serviceid,stid,null,null,servicetitle,servicefutitle,serviceprice,fmturl,xjturl1,xjturl2,xjturl3,xjturl4,servicecosttypeid,serviceintro,cs,country,fysm,null, null,null,null,null,null,null,null,null,null,uploadtitle, uploaddataurl,null,null,null,null,null,null);	
		merchantBiz.modifyService(service);
		return "redirect:/zg/c/getServices?pageNum=1&pageSize=3";
	}
	
}
