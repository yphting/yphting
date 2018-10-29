package com.accp.action.asp;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.accp.biz.zsp.GoldnotesBiz;
import com.accp.pojo.Banktype;
import com.accp.pojo.Evaluationservice;
import com.accp.pojo.Goldnotes;
import com.accp.pojo.Integralrecord;
import com.accp.pojo.Logistics;
import com.accp.pojo.Putforward;
import com.accp.pojo.Services;
import com.accp.pojo.Sharea;
import com.accp.pojo.User;
import com.accp.util.WlliusUtil;
import com.accp.util.file.Upload;
import com.accp.vo.zsp.EvaluationserviceToservicesVo;
import com.accp.vo.zsp.UserToServicesVo;
import com.accp.vo.zsp.userVo;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/zsp/c")
public class GoldnotesAction {
	@Autowired
	private GoldnotesBiz biz;

	/**
	 * 查询金币流向记录表 （goldnotesQueryAll）
	 * 
	 * @param model
	 * @param session
	 * @param p
	 * @param s
	 * @return
	 */
	@GetMapping("goldnotesQueryAll")
	public String goldnotesQueryAll(Model model, HttpSession session, Integer p, Integer s,Integer acquisitionMode) {
		if (p == null)
			p = 1;
		if (s == null)
			s = 6;
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		PageInfo<Goldnotes> pageInfo = biz.goldnotesQueryAll(p, s, userId,acquisitionMode);
		User users = biz.getUser(userId);
		if(users.getUsermoney()==null) {
			users.setUsermoney((float) 0);
		}
		model.addAttribute("PAGE_INFO", pageInfo);
		model.addAttribute("USER", users);
		return "grzx-moneys";
	}

	/**
	 * 查询积分流向记录表（QueryAll）
	 * 
	 * @param model
	 * @param session
	 * @param p
	 * @param s
	 * @return
	 */
	@GetMapping("QueryAll")
	public String IntegralRecordQueryAll(Model model, HttpSession session, Integer p, Integer s) {
		if (p == null)p = 1;
		if (s == null)s = 6;
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		User users=biz.getUser(userId);
		PageInfo<Integralrecord> pageInfo = biz.IntegralRecordQueryAll(p, s, userId);
		model.addAttribute("PAGE_INFO", pageInfo);
		model.addAttribute("USER",users);
		return "grzx-points";
	}

	/**
	 * 添加金币提现记录流向表
	 * 
	 * @param model
	 * @param session
	 * @param putforward
	 * @return
	 */
	@PostMapping("addPutforWard")
	public String addGoldnotes(Model model, HttpSession session, Putforward putforward) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		putforward.setSubmittime(new Date());
		putforward.setUserid(userId);
		biz.addPutforWard(putforward);
		return "redirect:/zsp/c/goldnotesQueryAll";
	}
/**
 * 添加金币充值记录流向表
 * @param model
 * @param session
 * @param goldnotes
 * @return
 */
	@PostMapping("addGoldnotes")
	public String addGoldnotes(Model model, HttpSession session, Goldnotes goldnotes) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		goldnotes.setUserid(userId);
		goldnotes.setRecorddate(new Date());
		biz.addGoldnotes(goldnotes);
		return "redirect:/zsp/c/goldnotesQueryAll";
	}

/**
 * 查询物流记录
 * @param model
 * @param session
 * @param p
 * @param s
 * @param logistics
 * @return
 */
	@GetMapping("getListLogistics")
	public String getListLogistics(Model model, HttpSession session,Integer p, Integer s,Logistics logistics) {
		if (p == null)p = 1;
		if (s == null)s = 6;
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		logistics.setUserid(userId);
		
		PageInfo<Logistics> pageInfo=biz.getListLogistics(p, s, logistics);
		model.addAttribute("PAGE_INFO", pageInfo);
		return "grzx-logistics";
	}
	/**
	 * 查询商品服务评价记录表
	 * @param model
	 * @param session
	 * @param p
	 * @param s
	 * @param evaluationService
	 * @return
	 */
	@GetMapping("EvaluationService")
	public String getListEvaluationService(Model model, HttpSession session,Integer p, Integer s,Evaluationservice evaluationService) {
		if (p == null)p = 1;
		if (s == null)s = 6;
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		evaluationService.setUserid(userId);
		PageInfo<EvaluationserviceToservicesVo> pageInfo =biz.getListEvaluationService(p, s, evaluationService);
		model.addAttribute("PAGE_INFO", pageInfo);
		return "grzx-comments";
	}
	/**
	 * 查询银行类别表
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("getListBankType")
	public String getListBankType(Model model, HttpSession session) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		User user = biz.getUser(userId);
		if(user.getUsermoney()==null) {
			user.setUsermoney((float) 0);
		}
		List<Banktype> list = biz.getListBankType();
		model.addAttribute("List", list);
		model.addAttribute("USER",user);
		return "jinb-tixt";
	}

	/**
	 * 添加物流记录
	 * @param model
	 * @param session
	 * @param logistics
	 * @param imgFile
	 * @return
	 */
	@PostMapping("addLogistics")
	public String addLogistics(Model model, HttpSession session,Logistics logistics,MultipartFile[] imgFile,
			String areaid11,String areaid22,String areaid33,String areaid4,
			String shareaid11,String shareaid22,String shareaid33,String shareaid4) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		logistics.setUserid(userId);
		
		try {
			List<String> imgUrls=new ArrayList<>(0);
			for (int i = 0; i < imgFile.length; i++) {
				imgUrls.add(Upload.uploadFile(imgFile[i]));
			}
			int a=1;
			for (String url : imgUrls) {
				switch (a) {
				case 1:
					logistics.setImg1(url);
					break;
				case 2:
					logistics.setImg2(url);
					break;
				case 3:					
					logistics.setImg3(url);
					break;
				case 4:					
					logistics.setImg4(url);
					break;
				case 5:					
					logistics.setImg5(url);
					break;
				default:
					break;
				}
				a++;
			}
			
		} catch (Exception e) {
			// TOD O Auto-generated catch block
			e.printStackTrace();
		} 
        logistics.setCollectgoodsaddr(shareaid11+shareaid22+shareaid33+shareaid4);
        logistics.setUseraddr(areaid11+areaid22+areaid33+areaid4);;
		logistics.setOrdertime(new Date());
		logistics.setAuditstatus(1);
		String orderid=WlliusUtil.Getnum();
		logistics.setOrderid(orderid);
		biz.addLogistics(logistics);
		return "redirect:/zsp/c/getListLogistics";
	}
	/**
	 * 查询物流记录详情
	 * @param model
	 * @param session
	 * @param id
	 * @return
	 */
	@GetMapping("getLogistics")
	public String getLogistics(Model model, HttpSession session,Integer id,Integer auditstatus) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
	    if(id==null) {
	    	id=1;
	    }
		Logistics logistics=biz.getLogistics(userId,id);
		model.addAttribute("logistics",logistics);
		if(auditstatus==1) {
			return "wl-xianq";//待支付
		}else if(auditstatus==2){
			return "wl-shz";//审核中
		}else if(auditstatus==3) {
			return "wl-sh";//审核通过 
		}else  if(auditstatus==4)  {
			return "wl-EMS";//已发国际EMS
		}else{
			return "wl-wcsh";//完成收货
		}
	}
	/**
	 * 查询物流支付页面
	 * @param model
	 * @param session
	 * @param id
	 * @return
	 */
	@GetMapping("getLogisticsByPrice")
	public String getLogisticsByPrice(Model model, HttpSession session,Integer id) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
	    if(id==null) {
	    	id=1;
	    }
	    User users=biz.getUser(userId);
	    if(users.getUsermoney()==null) {
	    	users.setUsermoney((float) 0);
	    }
		Logistics logistics = biz.getLogistics(userId, id);
		model.addAttribute("Logistics",logistics);
		model.addAttribute("USER",users);
		if(users.getUsermoney()==null) {
			return "wu_zhif";
		}else {
			if(users.getUsermoney()<logistics.getPrice()) {
				return "wu_zhif";
			}else {
				return "wl-zf";
			}
		}
		
	}
	/**
	 * 查询地址
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("getShAreaById")
	public String getShAreaById(Model model, HttpSession session) {   
		List<Sharea>Sharea=biz.getShAreaById(0);
		model.addAttribute("ShArea",Sharea);
		return "wu-fwl";
	}
	/**
	 * 查询地址省市
	 * @param model
	 * @param session
	 * @param id
	 * @return
	 */
	@GetMapping("getShAreaBy")
	@ResponseBody
	public List<Sharea> getShAreaBy(Model model, HttpSession session,Integer id) {		
		return biz.getShAreaById(id);
	}
	/**
	 * 金币充值
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("getLogisticsById")
    public String getLogisticsById(Model model, HttpSession session) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		User user = biz.getUser(userId);
		if(user.getUsermoney()==null) {
			user.setUsermoney((float) 0.0);
		}
		model.addAttribute("USER",user);
    	return "jinb-index";
    }
	/**
	 * 查询收藏的服务及商家
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("getMerchantCollectionById")
	public String getMerchantCollectionById(Model model, HttpSession session,Integer p, Integer s) {
		if (p == null)p = 1;
		if (s == null)s = 6;
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		PageInfo<userVo>pageInfo= biz.getMerchantCollectionById(p, s, userId);
		List<UserToServicesVo>list=biz.getUserToServicesVo();
		model.addAttribute("LIST",list);
		model.addAttribute("PAGE_INFO",pageInfo);
		return "sc-sj";
	}
	/**
	 * 查询收藏的服务
	 * @param model
	 * @param session
	 * @return 返回商家收藏页面 sc-sj。html
	 */
	@GetMapping("getServicesByUserId")
    public String getServicesByUserId(Model model, HttpSession session,Integer p, Integer s) {
		if (p == null)p = 1;
		if (s == null)s = 6;
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		PageInfo<Services>pageInfo= biz.getServicesByUserId(p, s, userId);
		List<UserToServicesVo>list=biz.getUserToServicesVo();
		model.addAttribute("LIST",list);
		model.addAttribute("PAGE_INFO",pageInfo);
		return "grzx-favs";
    }
	/**
	 * 物流支付状态
	 * @param model
	 * @param session
	 * @param logisticsid
	 * @param userprice
	 * @return
	 */
	@GetMapping("updlogistics")
	@ResponseBody
	public Map<String, String> updlogistics(Model model, HttpSession session,Integer logisticsid,Float userprice) {		
				Map<String, String> message=new HashMap<String,String>();
				try {
					Integer userId=((User)session.getAttribute("USER")).getUserid();
					biz.updUser(userprice,userId,logisticsid);
					message.put("code", "200");
					message.put("msg", "ok");
				} catch (Exception ex) {
					message.put("code", "500");
					message.put("msg", ex.getMessage());
				}
				return message;
	}
	@PostMapping("updateLogistics")
	public String updateLogistics(Model model, HttpSession session,Logistics logistics) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		logistics.setUserid(userId);
		logistics.setNumbertime1(new Date());
		logistics.setAuditstatus(4);
		biz.updatedLogistics(logistics);
		return "redirect:/zsp/c/getListLogistics";
	}
	@GetMapping("xiugai")
	public String xiugai(Model model, HttpSession session,Integer id) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
		Logistics logistics = biz.getLogistics(userId, id);
		
		List<Sharea>Sharea=biz.getShAreaById(0);
		model.addAttribute("l",logistics);
		model.addAttribute("ShArea",Sharea);
		return "diz";
	}
	@PostMapping("updat")
	public String updat(Model model, HttpSession session,Logistics logistics,
			String shareaid11,String shareaid22,String shareaid33,String shareaid4) {
		Integer userId=((User)session.getAttribute("USER")).getUserid();
        logistics.setCollectgoodsaddr(shareaid11+shareaid22+shareaid33+shareaid4);
        System.out.println("    rere         "+shareaid33);
        logistics.setUserid(userId);
		biz.updatedLogistics(logistics);
		return "redirect:/zsp/c/getListLogistics";
	}
}
