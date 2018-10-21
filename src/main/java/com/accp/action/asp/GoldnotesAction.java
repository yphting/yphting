package com.accp.action.asp;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.accp.pojo.Sharea;
import com.accp.pojo.User;
import com.accp.util.file.Upload;
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
	public String goldnotesQueryAll(Model model, HttpSession session, Integer p, Integer s) {
		if (p == null)
			p = 1;
		if (s == null)
			s = 6;
		Integer userId = ((User)session.getAttribute("USER")).getUserid();
		PageInfo<Goldnotes> pageInfo = biz.goldnotesQueryAll(p, s, userId);
		model.addAttribute("PAGE_INFO", pageInfo);
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
		Integer userId = 1;
		PageInfo<Integralrecord> pageInfo = biz.IntegralRecordQueryAll(p, s, userId);
		model.addAttribute("PAGE_INFO", pageInfo);
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

		putforward.setSubmittime(new Date());
		biz.addPutforWard(putforward);
		System.out.println("322");
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
        Date date=new Date();
        
        System.out.println("时间  "+date);
		goldnotes.setUserid(((User)session.getAttribute("USER")).getUserid());
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
		logistics.setUserid(((User)session.getAttribute("USER")).getUserid());
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
		evaluationService.setUserid(((User)session.getAttribute("USER")).getUserid());		
		PageInfo<Evaluationservice> pageInfo =biz.getListEvaluationService(p, s, evaluationService);
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
    	List<Banktype>list=biz.getListBankType();
    	model.addAttribute("List",list);
    	return "jinb-tixt";
    }
	/**
	 * 查询商品收藏表
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("getListBanke")
	public String getListBanke(Model model, HttpSession session) {
		return "grzx-favs";
	}
	@PostMapping("addLogistics")
	public String addLogistics(Model model, HttpSession session,Logistics logistics,MultipartFile[] imgFile) {
		try {
			List<String> imgUrls=new ArrayList<>(0);
			for (int i = 0; i < imgFile.length; i++) {
				imgUrls.add(Upload.uploadImg(imgFile[i]));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		logistics.setImg1("");
//		logistics.setImg2("");
//		logistics.setImg3("");
//		logistics.setImg4("");
//		logistics.setImg5("");
		logistics.setOrdertime(new Date());
		biz.addLogistics(logistics);
		User user=(User) session.getAttribute("User");
		if(user==null) {
			logistics.setUserid(1);
		}else {
			logistics.setUserid(user.getUserid());
		}
		return "redirect:/zsp/c/getListLogistics";
	}
	@GetMapping("getLogistics")
	public String getLogistics(Model model, HttpSession session,Integer id) {
	    User user=(User) session.getAttribute("user");
	    Integer userId=null;
	    if(user==null) {
	    	userId=1;
	    }else {
	    	userId=user.getUserid();
	    }
	    if(id==null) {
	    	id=1;
	    }
		Logistics logistics=biz.getLogistics(userId,id);
		model.addAttribute("logistics",logistics);
		return "wl-xianq";
	}
	@GetMapping("getLogisticsByPrice")
	public String getLogisticsByPrice(Model model, HttpSession session,Integer id) {
	    User user=(User) session.getAttribute("user");
	    Integer userId=null;
	    if(user==null) {
	    	userId=1;
	    }else {
	    	userId=user.getUserid();
	    }
	    if(id==null) {
	    	id=1;
	    }
		Logistics logistics=biz.getLogistics(userId,id);
		model.addAttribute("logistics",logistics);
		return "wu_zhif";
	}
	@GetMapping("getShAreaById")
	public String getShAreaById(Model model, HttpSession session) {
		List<Sharea>Sharea=biz.getShAreaById(0);
		model.addAttribute("ShArea",Sharea);
		return "wu-fwl";
	}
	@GetMapping("getShAreaBy")
	@ResponseBody
	public List<Sharea> getShAreaBy(Model model, HttpSession session,Integer id) {		
		return biz.getShAreaById(id);
	}
}
