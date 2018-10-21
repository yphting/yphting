var userID=1;
var text=$('');
if(userID==null){
	text=$('<a href="szy-login.html">登录</a><span class="ht_line"></span><a href="szy-zuce.html">注册</a>');
}else{
	$.getJSON("/c/szy/user/queryHeaderUser",{"userID":userID},function(data){
		text=$('<a href="grzx-index.html">'+data.username+'</a> <span class="ht_line"></span><a href="/c/szy/user/close">退出</a><span class="ht_line"></span><a href="xx-xtxx.html"><img src="Public/images/e_i.png" alt=""></a>');
	})
}
$(".top_right").html("");
$(".top_right").append(text);