package com.accp.action.sxy;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.biz.sxy.ForumBiz;
import com.accp.pojo.Forummanagement;
import com.accp.pojo.Post;
import com.accp.pojo.User;
import com.accp.vo.sxy.PostVo;
import com.accp.vo.sxy.PostcommentVo;
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
		
		/**
		 * 查询热门帖子
		 * @param pid
		 * @param page
		 * @param size
		 * @param title
		 * @param model
		 * @return
		 */
		@GetMapping("hotPost")
		public String shwoHotPost(@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="2")Integer size,@RequestParam(required=false)String title,Model model) {
			//查询左侧栏板块
			List<Forummanagement> list = biz.queryBlock();
			model.addAttribute("BLIST", list);
			//分页并按条件查询帖子列表
			PageInfo<PostVo> pageInfo = biz.queryHotPost(page, size, title);
			model.addAttribute("PAGE_INFO", pageInfo);
			return "lt-hotforum";
		}
		
		/**
		 * 跳转到发帖页面
		 * @param pid
		 * @param fmid
		 * @param model
		 * @param session
		 * @return
		 */
		@GetMapping("toAddForum")
		public String toAddForum(Integer pid,@RequestParam(required=false)Integer fmid,Model model,HttpSession session) {
			User user = (User)session.getAttribute("USER");
			if(user==null) {
				return "szy-login.html";
			}else {
			//显示版块下拉框
			List<Forummanagement> list = biz.queryBlock();
			model.addAttribute("BLIST", list);
			model.addAttribute("pid", pid);
			model.addAttribute("fmid", fmid);
				return "lt-addForum.html";
			}
		}
		
		/**
		 * 显示栏目下拉框
		 * @param pid
		 * @return
		 */
		@GetMapping("showColumn")
		@ResponseBody
		public List<Forummanagement> showColumn(Integer pid){
			List<Forummanagement> list = biz.queryCount(pid);
			return list;
		}
		
		/**
		 * 发布帖子
		 * @param post
		 * @return
		 */
		@PostMapping("savePost")
		@ResponseBody
		public int savePost(@RequestBody Post post,HttpSession session) {
			Integer userId = ((User)session.getAttribute("USER")).getUserid();
			post.setUserid(userId);
			int result = biz.savePost(post);
			return result;
		}
		
		/**
		 * 查询帖子详情
		 * @param postId
		 * @param model
		 * @return
		 */
		@GetMapping("postDetail")
		public String queryPostDetail(@RequestParam(defaultValue="1")Integer page,@RequestParam(defaultValue="2")Integer size,Integer postId,Integer fmid,Integer pfmid,String fmname,Model model) {
			//帖子详情
			PostVo pv = biz.queryPostDetail(postId);
			//详情内评论列表
			PageInfo<PostcommentVo> pageInfo = biz.queryComment(page, size, postId);
			//详情右侧最新话题
			List<PostVo> newList = biz.queryNewPost(fmid);
			//帖子右侧热门话题
			PageInfo<PostVo> hotPost = biz.queryHotPost(1, 5, null);
			//帖子对应版块名
			Forummanagement forum = biz.queryTitleName(pfmid);
			model.addAttribute("HOT", hotPost);
			model.addAttribute("FORUM", forum);
			model.addAttribute("FMNAME", fmname);
			model.addAttribute("NEWLIST", newList);
			model.addAttribute("POSTVO", pv);
			model.addAttribute("PAGE_INFO", pageInfo);
			return "lt-postDetail";
		}
		
		/**
		 * 显示我的韩汀论坛中我的发帖列表
		 * @param page
		 * @param model
		 * @param session
		 * @return
		 */
		@GetMapping("myPost")
		public String toMyForum(@RequestParam(defaultValue="1")Integer page,@RequestParam(required=false)String title,Model model,HttpSession session) {
			User user = (User)session.getAttribute("USER");
			if(user==null) {
				return "szy-login";
			}else {
				List<Forummanagement> list = biz.queryBlock();
				model.addAttribute("BLIST", list);
				PageInfo<PostVo> myPostList = biz.queryMyForum(page, 3, user.getUserid(),title);
				model.addAttribute("MYPOST_PAGE", myPostList);
				return "lt-myforum";
			}
		}
}
