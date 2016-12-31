var custom_skin = 'layui-layer-molv';//边框
var custom_shadeClose = true;//点击阴影也可以关闭窗口
var custom_area = ['50%', '50%'];
var custom_area_small = ['50%', '40%'];
var custom_area_big = ['50%', '60%'];
var custom_offset = "100px";
if ('ontouchstart' in document.documentElement){
	custom_area = ['90%', '90%'];
	custom_area_small = ['90%', '90%'];
	custom_area_big = ['90%', '90%'];
	custom_offset = "20px";
}

function alertSuccess(){
	layer.alert("操作成功",{
		  title:'操作提示',
			 icon: 1,
		  skin: custom_skin,//边框
			 shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
			 offset: custom_offset
	   });
}

function alertError(message){
	layer.alert("操作失败，"+message,{
		  title:'操作提示',
			 icon: 2,
		  skin: custom_skin,//边框
			 shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
			 offset: custom_offset
	});
}

