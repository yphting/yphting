function serviceCollection(sid){
	jQuery.getJSON("api/serviceCollection",{"sid":sid},function(result){
		alert(result.msg);
		location.reload();
	});
}