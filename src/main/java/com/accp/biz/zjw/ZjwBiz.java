package com.accp.biz.zjw;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accp.dao.zjw.IZjwDao;
import com.accp.pojo.Post;
import com.accp.pojo.Postcomment;

@Service
public class ZjwBiz {
	@Autowired
	private IZjwDao dao;

	public List<Post> query(Integer postid) {
		return dao.query(postid);
	}
	public List<Postcomment> queryhuifu(Integer postid){
		return dao.queryhuifu(postid);
		
	}
}
