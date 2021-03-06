<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
<script type="text/javascript">
//加载ztree
var setting = {
	 check: {
			enable: true,
			chkboxType: { "Y": "p", "N": "p" }
	  },
  data:{
			 key:{ 
	      name:"name" 
	          }, 
	    simpleData :{ 
	       enable:true, 
	       idKey:"id", 
	       pIdKey:"parentId" 
	            } 
	   },
	async: {
			enable: true,
			url:"${request.contextPath}/role/group_tree/"+${role_id},
			autoParam: ["id"],
			dataType:"json",
			dataFilter: filter
	 }
};

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

$(document).ready(function(){
	 $.fn.zTree.init($("#treeDemo"), setting);

	 //全选
  $("#checkallbtn").click(function(){
	  var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	  treeObj.checkAllNodes(true);
	 });

	 //全取消
  $("#uncheckallbtn").click(function(){
	    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	    treeObj.checkAllNodes(false);
	 });

	 //保存
  $("#savebtn").click(function(){
		  var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		  var nodes = treeObj.getCheckedNodes(true);
		  var nodestr = "";
		  for(var i=0;i<nodes.length;i++){
			  nodestr += nodes[i].id + ","; //获取选中节点的值
		 	 }
		  $.ajax({ 
       type: "post", 
       url: "${request.contextPath}/role/group_ref", 
       data: {"group_ids" : nodestr,"role_id":${role_id}}, 
       success: function (data) { 
    	   	var index = parent.layer.getFrameIndex(window.name); 
		 				parent.layer.close(index);
                    }
            });
		  
	 });

});

</script>
</head>
<body id="body" style="background:#F7F7F7;" >
 <form class="form-horizontal form-label-left" method="post">
 <br />

  <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>组织树</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li class="pull-right"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>

							 					<div class="x_content">
							 					
							 							<div ><ul id="treeDemo" class="ztree"></ul></div>
							 						
							 					</div>
							 					</div>
					</div>
 
 		<div class="form-group col-md-12 col-sm-12 col-xs-12">
				     <button id="checkallbtn" type="button" class="btn btn-default ">全 选</button>
           <button id="uncheckallbtn" type="button" class="btn btn-default ">全不选</button>
           <button id="savebtn" type="button" class="btn btn-success">保 存</button>
      </div>

 </form>
<script type="text/javascript">
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