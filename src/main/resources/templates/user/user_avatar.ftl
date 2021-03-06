<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
<link rel="stylesheet" href="/css/fileinput.min.css" />
<style>
.kv-avatar .file-preview-frame,.kv-avatar .file-preview-frame:hover {
    margin: 0;
    padding: 0;
    border: none;
    box-shadow: none;
    text-align: center;
}
.kv-avatar .file-input {
    display: table-cell;
    max-width: 320px;
}
</style>
</head>
<body id="body" style="background:#F7F7F7;" >
<br />

<div class="x_panel">
	<div class="x_conten " >
				<div id="kv-avatar-success" class="alert alert-success alert-dismissible fade in" style="display:none" role="alert"></div>
				<div id="kv-avatar-errors" class="center-block" style="display:none" role="alert"></div>
				<form class="text-center" action="/user/upload/avatar" method="post" enctype="multipart/form-data">
						<input id="input-repl-1a" name="file" type="file" class="file-loading" accept="image/*">		
				</form>
	</div>
</div>

<script type="text/javascript" src="${request.contextPath}/js/layer.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/layer-plugin.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/fileinput.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/fileinput.zh.js"></script>
<script type="text/javascript">
$("#input-repl-1a").fileinput({
   language:"zh",
   overwriteInitial: true,
   uploadUrl: "${request.contextPath}/user/upload/avatar",
   uploadAsync:true,
   autoReplace: true,
   overwriteInitial: true,
   showUploadedThumbs: false,
   autoReplace:true,
   maxFileCount: 1,
   maxFileSize: 1024, //kb
   showBrowse: true,
   showRemove: true,
   showClose: false,
   browseOnZoneClick: true,
   removeClass: "btn btn-danger",
   uploadClass: 'btn btn-success',
   elErrorContainer: '#kv-avatar-errors',
   msgErrorClass: 'alert alert-block alert-danger',
   layoutTemplates: {footer: ''}, // disable thumbnail deletion
   allowedFileExtensions: ["jpg", "png", "gif", "jpeg", "bmp"]
}).on('fileuploaded', function(event, data, id, index) {
	 var resp = data.response;
		if(resp.status){
			alertSuccess();
			parent.$("#avatar").attr('src', ${request.contextPath}+'/'+resp.avatar+'?v='+Math.random());
			parent.parent.$("#avatar_samll").attr('src', ${request.contextPath}+'/'+resp.avatar+'?v='+Math.random());
		}else{
			alertError(resp.message);
		}
});


$('#body').niceScroll({ 
    cursorcolor: "#26B99A",//#26B99A 光标颜色 
    cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0 
    touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备 
    cursorwidth: "5px", //像素光标的宽度 
    cursorborder: "0", // 游标边框css定义 
    cursorborderradius: "5px",//以像素为光标边界半径 
    autohidemode: "cursor" //是否隐藏滚动条 
});
</script>
</body>
</html>