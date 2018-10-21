package com.accp.action.zjw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.accp.biz.zjw.ZjwBiz;
import com.accp.pojo.Post;
import com.accp.pojo.Postcomment;

@Controller
@RequestMapping("/c/zjw")
public class ZjwAction {
	
@Autowired
private ZjwBiz biz;

	@GetMapping("/login")
	public String query( Integer userID,Model model){
		List<Post> rec=biz.query(userID);
		model.addAttribute("rec", rec);
		return "zjw-fatie";
	}
	public String queryhuitie(Integer userID,Model model) {
		List<Postcomment> rec=biz.queryhuifu(userID);
		System.out.println(rec);
		model.addAttribute("rec", rec);
		return "zjw-huitie";
	}
}
