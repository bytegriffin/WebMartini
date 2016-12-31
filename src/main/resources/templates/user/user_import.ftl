<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
<link rel="stylesheet" href="/css/fileinput.min.css" />

</head>
<body id="body" style="background:#F7F7F7;" >
<br />

<div class="x_panel">
	<div class="x_conten" >
				<h4>请先下载<a href="/template/user.xlsx" class="green">模板</a>，再进行上传。</h4>
				<div id="kv-avatar-errors" class="center-block" style="display:none" role="alert"></div>
				<form class="text-center" action="/user/import/excel" method="post" enctype="multipart/form-data">
						<input id="input-repl-1a" name="file" type="file" class="file-loading" accept=".xls,.xlsx">		
				</form>
	</div>
</div>

<script type="text/javascript" src="/js/layer.js"></script>
<script type="text/javascript" src="/js/layer-plugin.js"></script>
<script type="text/javascript" src="/js/fileinput.min.js"></script>
<script type="text/javascript" src="/js/fileinput.zh.js"></script>
<script type="text/javascript">
$("#input-repl-1a").fileinput({
	 language:"zh",
   uploadUrl: "/user/import/excel",
   uploadAsync:true,
   autoReplace: true,
   overwriteInitial: true,
   showUploadedThumbs: false,
   autoReplace:true,
   maxFileCount: 1,
   maxFileSize: 10240, //kb
   showBrowse: true,
   showRemove: true,
   showClose: false,
   browseOnZoneClick: true,
   removeClass: "btn btn-danger",
   uploadClass: 'btn btn-success',
   elErrorContainer: '#kv-avatar-errors',
   msgErrorClass: 'alert alert-block alert-danger',
   layoutTemplates: {footer: ''}, // disable thumbnail deletion
   allowedFileExtensions: ["xls", "xlsx"]
}).on('fileuploaded', function(event, data, id, index) {
	 var resp = data.response;
		if(resp.status){
				parent.dtTable.draw(false);//保持当前分页信息
				alertSuccess();
		}else{
			alertError(resp.message);
		}
});

$('#body').niceScroll({ 
    cursorcolor: "#26B99A",//#CC0071 光标颜色 
    cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0 
    touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备 
    cursorwidth: "5px", //像素光标的宽度 
    cursorborder: "0", //     游标边框css定义 
    cursorborderradius: "5px",//以像素为光标边界半径 
    autohidemode: "cursor" //是否隐藏滚动条 
});

</script>
</body>
</html>