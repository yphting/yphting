package com.accp.action.sxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.biz.sxy.ForumBiz;
import com.accp.pojo.Forummanagement;
import com.accp.vo.sxy.PostVo;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/c/sxy")
public class ForumAction {
		@Autowired
		private ForumBiz biz;
		
		
		@GetMapping("column")
		public String showColumn(@RequestParam(required=true)Integer pid,@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="2")Integer size,@RequestParam(required=false)String title,@RequestParam(required=false)Integer forumId,@RequestParam(required=true)Integer orderId,@RequestParam(required=false)Integer essence,Model model) {

			//查询左侧栏板块
			List<Forummanagement> list = biz.queryBlock();
			model.addAttribute("BLIST", list);
			if(pid==null)
				pid=list.get(0).getFmid();
			//查询版块对应栏目
			List<Forummanagement> smalllist = biz.queryCount(pid);
			//分页并按条件查询帖子列表
			PageInfo<PostVo> pageInfo = biz.queryPostList(pid,page, size, pid,title,forumId,orderId,essence);
			
			//显示版块名称
			Forummanagement forumma = biz.queryTitleName(pid);
			model.addAttribute("CLIST", smalllist);
			model.addAttribute("FORUM", forumma);
			model.addAttribute("PAGE_INFO", pageInfo);
			return "lt-forum";
		}
		
		@GetMapping("toAddForum")
		public String toAddForum(Integer pid,@RequestParam(required=false)Integer fmid,Model model) {
			//显示版块下拉框
			List<Forummanagement> list = biz.queryBlock();
			model.addAttribute("BLIST", list);
			model.addAttribute("pid", pid);
			model.addAttribute("fmid", fmid);
			return "lt-addForum.html";
		}
		
		//显示栏目下拉框
		@GetMapping("showColumn")
		@ResponseBody
		public List<Forummanagement> showColumn(Integer pid){
			List<Forummanagement> list = biz.queryCount(pid);
			return list;
		}
		
}
