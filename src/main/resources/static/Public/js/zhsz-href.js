$.getJSON("/c/szy/user/queryAUser",function(data){
	if(data.merchanttype==0){
		$(".account_ul_a li:eq(3)").remove();
	}
})

