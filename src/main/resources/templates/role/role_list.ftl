<#include "/layout/header.ftl">  
<#include "/layout/table.ftl">  
<@header />
<@table />
<script type="text/javascript">
	//加载ztree
	var setting = {
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
				url:"/role/tree",
				autoParam: ["id"],
				dataType:"json",
				dataFilter: filter
			},
			callback: {
                onClick: onClick
            }
	};
	
	function onClick(e, treeId, treeNode) {
		dtTable.ajax.url('/role/page?code='+treeNode.code).load();
	}

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}

	$(document).ready(function(){
		 $.fn.zTree.init($("#treeDemo"), setting);
	});

</script>
</head>
<body style="background:#F7F7F7;" class="animated fadeIn">
 <div class="page-title">
               <div class="title_left">
                  <ol class="breadcrumb">
												  <li><a href="javascript:void(0)">系统管理</a></li>
												  <li class="active">角色管理</li>
												</ol>
              </div>
            </div>
           <div class="clearfix"></div>

     <div class="col-md-4 col-sm-4 col-xs-12">
          <div class="x_panel">
                  <div class="x_title">
                    <h2>角色树</h2>
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

     	<div class="col-md-8 col-sm-8 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>角色列表</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li class="pull-right"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>

							 					<div class="x_content">

							<form id="search_form" role="form" class="row form-horizontal form-label-left">
				
	               <div class=" btn-group col-md-12 col-sm-12 col-xs-12">
	              				 <div class="btn-group">
						                 <button id="add" type="button" class="btn btn-success" >
																				<i class=" fa fa-plus-circle  "></i>
																				新增
																			</button>
																</div>
												<div class="input-group col-md-5 " style="float:left;">

	                  <input type="text" id="searchKey" name="searchKey" placeholder="输入角色名称 " class="form-control" autocomplete="off"/>
													<span class="input-group-btn" >
														<button id="search" type="button" class="btn btn-success" ><i class="fa fa-search"></i>搜 索</button>
													</span>
	               </div>
		               	<div class="input-group pull-right " style="float:left">
		                    <a id="refresh" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="刷新"><i class="fa fa-refresh green fa-spin"></i></a>
		                    <a id="upload" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="上传"><i class="fa fa-cloud-upload blue "></i></a>
		                    <a id="exp_excel" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="导出Excel"><i class="fa fa-file-excel-o purple"></i></a>
		                    <a id="exp_pdf" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="导出PDF"><i class="fa fa-file-pdf-o red"></i></a>
		                    <!--a id="refresh" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="打印列表"><i class="fa fa-print"></i></a-->
	                   	</div>
                    </div>
	               </form>

													<table id="data-tables" class="table table-striped table-bordered table-hover dt-responsive " cellspacing="0" style="width:100%">
														<thead>
															<tr >
															 <th nowrap="nowrap">序列号</th>
															 <th>id</th>
																<th nowrap="nowrap">角色名称</th>
																<th nowrap="nowrap">角色描述</th>
																<th nowrap="nowrap">权限设置</th>
																<th nowrap="nowrap">操作</th>
															</tr>
														</thead>
														<tbody></tbody>
														<!-- tbody是必须的 -->
													</table>
	 						
							</div>
				</div>			
  </div>

<script type="text/javascript">
$.fn.dataTable.ext.errMode = 'throw';
var dtTable = $('#data-tables').DataTable({
 		  responsive: true,//自适应宽度，适合手机
     language:{url:"/js/datatables_zh.json"},
     processing: false,
     serverSide: true,
     autoWidth: true,
     //pagingType: "full_numbers",
     lengthMenu:[10,30,50],
     searching: false,
   　 order: [[1, "asc"]], //默认排序
     ajax: { url:"/role/page", type:"post" },
     columns: [
				  { "data": "num" ,"orderable":false,width:70,className: "text-center"},
	       { data: "id",visible:false },
        { data: "name",width:150 },
        { data: "description","orderable":false,width:200,
        	render: function(data, type, full) {
        				if(data != '' && data != null){
        					  return	"<div style='word-break:break-all; word-wrap:break-word;'>" + data + "</div>";
        	  		}else{
        						return "";			
        							}
   					       }
        			},
        { data: "id","orderable":false,className: "text-center",width:230,
        	 render: function(data, type, full) {
        		 return	"<a class='blue' title='授权菜单'  onclick=\"authMenu('" + data + "');\" style='cursor: pointer;'>授权菜单</a> | "
								      +"<a id='del' title='授权用户'  class='blue' onclick=\"authUser('" + data + "');\" style='cursor: pointer;'>授权用户</a> | " 
								      +"<a id='del' title='授权组织'  class='blue' onclick=\"authGroup('" + data + "');\" style='cursor: pointer;'>授权组织</a>";
      					       }
       				  },
        { data: "id","orderable":false,className: "text-center",width:150,
         	 render: function(data, type, full) {
         		 return	"<a class='btn btn-info btn-xs' title='修改'  onclick=\"edit('" + data + "');\" ><i class='fa fa-pencil '></i>修改</a>  "
		    								+"<a class='btn btn-danger btn-xs' title='删除'  onclick=\"del('" + data + "');\" ><i class='fa fa-trash-o'></i>删除</a>";
          	                    		}
        				    	}
         	        ],        
      dom: 't<"row"<"col-sm-3"i><"col-sm-4 input-s-sm"l><"col-sm-5"p>>'
 } );

//删除
function del(id){
  layer.confirm('您确定要删除这条记录么？', {
		   offset: custom_offset,
		   title: '操作提示',
			  skin: custom_skin,//边框
			  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
			  btn: ['是的','放弃']
		}, function(index){
			$.ajax({
	      type:"get",
	      url:'/role/delete/'+id,
	      success:function(msg){
      			if(msg=="true"){
	      			 $.fn.zTree.getZTreeObj("treeDemo").reAsyncChildNodes(null, "refresh");
	      			 dtTable.draw(false);//保持当前分页信息
	      			 layer.close(index);
							}else{
			      	layer.close(index);
			      	alertError("请先删除其子角色");
	    	      		  }
                   }
            });
		}, function(index){
		   layer.close(index);
	});
}

//修改
function edit(id){
	layer.open({
		  type: 2,
		  title: '编辑角色',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area_small,
		  offset: custom_offset,
		  content: ['/role/edit/'+id, 'no']
		}); 
}

//授权菜单
function authMenu(id){
	layer.open({
		  type: 2,
		  title: '授权菜单',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset,
		  content: ['/role/menu/'+id, 'no']
		}); 
}

//授权用户
function authUser(id){
	layer.open({
		  type: 2,
		  title: '授权用户',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area_small,
		  offset: custom_offset,
		  content: ['/role/user/'+id, 'no']
		}); 
}

//授权组织
function authGroup(id){
	layer.open({
		  type: 2,
		  title: '授权组织',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset,
		  content: ['/role/group/'+id, 'no']
		}); 
}


//新增
$("#add").click(function(){
	 layer.open({
		  type: 2,
		  title: '新增角色',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area_small,
		  offset: custom_offset,
		  content: ['/role/add', 'no']
		}); 
});

//搜索
$("#search").click(function(){
		var searchValue = $.trim($("#searchKey").val());
		dtTable.ajax.url('/role/search?searchValue='+searchValue).load();
});	

//搜索建议
$('#searchKey').typeahead({
	ajax: {
     url: '/role/suggest?t='+(new Date()).getTime(),
     timeout: 300, // 延时
     method: 'post',//中文参数必须要用POST
     triggerLength: 1, // 输入几个字符之后，开始请求
     preDispatch: function (query) {
         var para = { searchValue : $.trim($("#searchKey").val()) };
         para.query = query;
         return para;
                },
      preProcess: function (result) {
         return result;
                 }
          },
   menu:'<ul class="dropdown-menu" style="width:'+$("#searchKey").css("width")+'"></ul>',//下拉菜单
   display: "name", // 默认的对象属性名称为 name 属性
   val: "id",    // 默认的标识属性名称为 id 属性
   items: 10 // 最多显示项目数量
});

//刷新列表
$("#refresh").click(function(){
	dtTable.ajax.url('/role/page?t='+(new Date()).getTime()).load();
});

//上传excel
$("#upload").click(function(){
	 layer.open({
		  type: 2,
		  title: '上传角色数据',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset,
		  content: ['/role/import/excel', 'no']
		}); 
});

//导出excel
$("#exp_excel").click(function(){
	var searchValue = $.trim($("#searchKey").val());
	location.href = "/role/export/excel?searchValue="+searchValue;
	alertSuccess();
});

//导出pdf
$("#exp_pdf").click(function(){
	var searchValue = $.trim($("#searchKey").val());
	location.href = "/role/export/pdf?searchValue="+searchValue;
	alertSuccess();
});
</script>
</body>
</html>