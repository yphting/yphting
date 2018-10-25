package com.accp.action.zg;

import org.springframework.beans.factory.annotation.Autowired;



import java.io.IOException;
import java.util.Date;

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

import com.accp.vo.zg.ServicesVo;
import com.alibaba.fastjson.JSON;
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
		PageInfo<ServicesVo> pageInfo=merchantBiz.queryServices(pageNum, pageSize);
		model.addAttribute("PAGE_INFO", pageInfo);
		return "sjzx-services";
	}
	
	@GetMapping("getServicesByTitle")
	public String  queryServiceByTitle(Model model,Integer pageNum,Integer pageSize,String serviceTitle) {
		PageInfo<ServicesVo> pageInfo=merchantBiz.queryServicesBytitle(pageNum, pageSize, serviceTitle);
		model.addAttribute("PAGE_INFO", pageInfo);
		return "sjzx-services";
	}
	
	@GetMapping("queryServices")
	public String queryServices(Model model,int serviceid,int stid) {
			Services service=merchantBiz.queryServices(serviceid);
			model.addAttribute("Services",service);
			if(stid==1) {
				return "sjzx-xgzjy";
			}else if(stid==2) {
				return "sjzx-xgwzx";
			}else if(stid==3) {
				return "sjzx-xglxzj";
			}else if(stid==4) {
				return "sjzx-xghyfy";
			}else if(stid==5){
				return "sjzx-xgxxzy";
			}
			return null;
		}
	@PostMapping("addServiceslxzj")
	public String  addServiceslxzj(HttpSession session,Model model,int stid,int resourceID,String servicetitle,String servicefutitle,String downloadtitle,int serviceprice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,String serviceintro,String[] areaids,int countryid,String[] servicecostinclude,String servicecosttypeid,String uploaddataurl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cs="";
				for(String i:areaids) {
					cs+=i+",".substring(0,areaids.length-1);
				}
				String baohao="";
				for(String val:servicecostinclude) {
					baohao+=val+",".substring(0,servicecostinclude.length-1);
				}
				
				service.setStid(stid);
				service.setResourceid(resourceID);
				service.setServicetitle(servicetitle);
				service.setServicefutitle(servicefutitle);
				service.setServicecity(cs);
				service.setServicecostinclude(baohao);
				service.setDownloadtitle(downloadtitle);
				service.setServiceprice(serviceprice);
				service.setServicecosttypeid(servicecosttypeid);
				service.setServiceintro(serviceintro);
				service.setCountry(countryid);
				service.setUploaddataurl(uploaddataurl);
				merchantBiz.addService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	
	@PostMapping("updateServiceslxzj")
	public String  updateServiceslxzj(HttpSession session,Model model,int serviceID,int stid,String servicetitle,String servicefutitle,String downloadtitle,int serviceprice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,String serviceintro,String[] areaids,int countryid,String[] servicecostinclude,String servicecosttypeid,String uploaddataurl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cs="";
				for(String i:areaids) {
					cs+=i+",".substring(0,areaids.length-1);
				}
				String baohao="";
				for(String val:servicecostinclude) {
					baohao+=val+",".substring(0,servicecostinclude.length-1);
				}
				service.setServiceid(serviceID);
				service.setStid(stid);
				service.setServicetitle(servicetitle);
				service.setServicefutitle(servicefutitle);
				service.setServicecity(cs);
				service.setServicecostinclude(baohao);
				service.setDownloadtitle(downloadtitle);
				service.setServiceprice(serviceprice);
				service.setServicecosttypeid(servicecosttypeid);
				service.setServiceintro(serviceintro);
				service.setCountry(countryid);
				service.setUploaddataurl(uploaddataurl);
				merchantBiz.modifyService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	
	@PostMapping("addServiceszjy")
	public String  addServiceszjy(HttpSession session,Model model,int stid,int resourceID,String serviceTitle,String serviceFuTitle,String downloadTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,Date serviceStartDate,Date serviceEndDate,int serviceHour,String serviceIntro,String[] areaids,int countryid,String[] serviceCostInclude,String serviceCostTypeID,String uploadDataUrl) {
				Services service=new Services();
				System.out.println(JSON.toJSON(service));
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cs="";
				for(String i:areaids) {
					cs+=i+",".substring(0,areaids.length-1);
				}
				String baohao="";
				for(String val:serviceCostInclude) {
					baohao+=val+",".substring(0,serviceCostInclude.length-1);
				}
			
				service.setStid(stid);
				service.setResourceid(resourceID);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setServicecity(cs);
				service.setServicehour(serviceHour);
				service.setServicestartdate(serviceStartDate);
				service.setServiceenddate(serviceEndDate);
				service.setServicecostinclude(baohao);
				service.setDownloadtitle(downloadTitle);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
				service.setCountry(countryid);
				service.setUploaddataurl(uploadDataUrl);
				merchantBiz.addService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	@PostMapping("updateServiceszjy")
	public String  updateServiceszjy(HttpSession session,Model model,int serviceID,int stid,String serviceTitle,String serviceFuTitle,String downloadTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,Date serviceStartDate,Date serviceEndDate,int serviceHour,String serviceIntro,String[] areaids,int countryid,String[] serviceCostInclude,String serviceCostTypeID,String uploadDataUrl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cs="";
				for(String i:areaids) {
					cs+=i+",".substring(0,areaids.length-1);
				}
				String baohao="";
				for(String val:serviceCostInclude) {
					baohao+=val+",".substring(0,serviceCostInclude.length-1);
				}
				service.setServiceid(serviceID);
				service.setStid(stid);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setServicecity(cs);
				service.setServicehour(serviceHour);
				service.setServicestartdate(serviceStartDate);
				service.setServiceenddate(serviceEndDate);
				service.setServicecostinclude(baohao);
				service.setDownloadtitle(downloadTitle);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
				service.setCountry(countryid);
				service.setUploaddataurl(uploadDataUrl);
				merchantBiz.modifyService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	
	
	
	@PostMapping("addServiceswzx")
	public String  addServiceswzx(HttpSession session,Model model,int stid,int resourceID,String serviceTitle,String hospitalName,String serviceFuTitle,String downloadTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,Date serviceStartDate,Date serviceEndDate,String serviceIntro,String[] areaids,int countryid,String[] serviceCostInclude,String serviceCostTypeID,String uploadDataUrl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cs="";
				for(String i:areaids) {
					cs+=i+",".substring(0,areaids.length-1);
				}
				String baohao="";
				for(String val:serviceCostInclude) {
					baohao+=val+",".substring(0,serviceCostInclude.length-1);
				}
			
				service.setStid(stid);
				service.setResourceid(resourceID);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setServicecity(cs);
			
				service.setServicestartdate(serviceStartDate);
				service.setServiceenddate(serviceEndDate);
				service.setServicecostinclude(baohao);
				service.setDownloadtitle(downloadTitle);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
				service.setCountry(countryid);
				service.setUploaddataurl(uploadDataUrl);
				merchantBiz.addService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	

	@PostMapping("updateServiceswzx")
	public String  updateServiceswzx(HttpSession session,Model model,int serviceID,int stid,int resourceID,String serviceTitle,String hospitalName,String serviceFuTitle,String downloadTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,Date serviceStartDate,Date serviceEndDate,String serviceIntro,String[] areaids,int countryid,String[] serviceCostInclude,String serviceCostTypeID,String uploadDataUrl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cs="";
				for(String i:areaids) {
					cs+=i+",".substring(0,areaids.length-1);
				}
				String baohao="";
				for(String val:serviceCostInclude) {
					baohao+=val+",".substring(0,serviceCostInclude.length-1);
				}
				service.setServiceid(serviceID);
				service.setStid(stid);
				service.setResourceid(resourceID);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setServicecity(cs);
			
				service.setServicestartdate(serviceStartDate);
				service.setServiceenddate(serviceEndDate);
				service.setServicecostinclude(baohao);
				service.setDownloadtitle(downloadTitle);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
				service.setCountry(countryid);
				service.setUploaddataurl(uploadDataUrl);
				merchantBiz.modifyService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	
	
	@PostMapping("addServicesxxzy")
	public String  addServicesxxzy(HttpSession session,Model model,int stid,int resourceID,String serviceTitle,String serviceFuTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,String schoolRegion,String schoolNameByCN,String majoyNameByCN,String schoolNameByROK,String majoyNameByROK,String serviceIntro,String serviceCostTypeID,String uploadDataUrl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
				service.setStid(stid);
				service.setResourceid(resourceID);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setSchoolregion(schoolRegion);
				service.setSchoolnamebycn(schoolNameByCN);
				service.setMajoynamebycn(majoyNameByCN);
				service.setSchoolnamebyrok(schoolNameByROK);
				service.setMajoynamebyrok(majoyNameByROK);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
				service.setUploaddataurl(uploadDataUrl);
				merchantBiz.addService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	@PostMapping("updateServicesxxzy")
	public String  updateServicesxxzy(HttpSession session,Model model,int serviceID,int stid,int resourceID,String serviceTitle,String serviceFuTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,String schoolRegion,String schoolNameByCN,String majoyNameByCN,String schoolNameByROK,String majoyNameByROK,String serviceIntro,String serviceCostTypeID,String uploadDataUrl) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				service.setServiceid(serviceID);
				service.setStid(stid);
				service.setResourceid(resourceID);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setSchoolregion(schoolRegion);
				service.setSchoolnamebycn(schoolNameByCN);
				service.setMajoynamebycn(majoyNameByCN);
				service.setSchoolnamebyrok(schoolNameByROK);
				service.setMajoynamebyrok(majoyNameByROK);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
				service.setUploaddataurl(uploadDataUrl);
				merchantBiz.modifyService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	@PostMapping("addServiceshyfy")
	public String  addServiceshyfy(HttpSession session,Model model,int stid,String serviceTitle,String [] typeid,String serviceFuTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,String serviceIntro,String[] serviceCostInclude,String serviceCostTypeID) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String zy="";
				for(String i:typeid) {
					zy+=i+",".substring(0,typeid.length-1);
				}
				String baohao="";
				for(String val:serviceCostInclude) {
					baohao+=val+",".substring(0,serviceCostInclude.length-1);
				}
			
				service.setStid(stid);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setServicecostinclude(baohao);
				service.setGoodatmajoy(zy);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
		
				merchantBiz.addService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	
	@PostMapping("updateServiceshyfy")
	public String  updateServiceshyfy(HttpSession session,Model model,int serviceID,int stid,String serviceTitle,String [] typeid,String serviceFuTitle,int servicePrice, MultipartFile serviceCoverImg,MultipartFile serviceImgUrlOne,MultipartFile serviceImgUrlTwo,MultipartFile serviceImgUrlThree,MultipartFile serviceImgUrlFour,String serviceIntro,String[] serviceCostInclude,String serviceCostTypeID) {
				Services service=new Services();
				try {
				String	fmturl=Upload.uploadImg(serviceCoverImg);
				String	xjturl1=Upload.uploadImg(serviceImgUrlOne);
				String	xjturl2=Upload.uploadImg(serviceImgUrlTwo);
				String	xjturl3=Upload.uploadImg(serviceImgUrlThree);
				String	xjturl4=Upload.uploadImg(serviceImgUrlFour);
				System.out.println(fmturl);
				service.setServicecoverimg(fmturl);
				service.setServiceimgurlone(xjturl1);
				service.setServiceimgurltwo(xjturl2);
				service.setServiceimgurlthree(xjturl3);
				service.setServiceimgurlfour(xjturl4);
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String zy="";
				for(String i:typeid) {
					zy+=i+",".substring(0,typeid.length-1);
				}
				String baohao="";
				for(String val:serviceCostInclude) {
					baohao+=val+",".substring(0,serviceCostInclude.length-1);
				}
				service.setServiceid(serviceID);
				service.setStid(stid);
				service.setServicetitle(serviceTitle);
				service.setServicefutitle(serviceFuTitle);
				service.setServicecostinclude(baohao);
				service.setGoodatmajoy(zy);
				service.setServiceprice(servicePrice);
				service.setServicecosttypeid(serviceCostTypeID);
				service.setServiceintro(serviceIntro);
		
				merchantBiz.modifyService(service);
				return "redirect:getServices?pageNum=1&pageSize=3";
	}
	
	@GetMapping("removeServices")
	public String  removeService(int serviceID) {
		merchantBiz.removeService(serviceID);
		return "redirect:/zg/c/getServices?pageNum=1&pageSize=3";
	}
	
	
	
}
