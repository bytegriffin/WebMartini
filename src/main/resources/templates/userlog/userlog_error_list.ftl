<#include "/layout/header.ftl">  
<#include "/layout/table.ftl">  
<@header />
<@table />
<link rel="stylesheet" href="/css/daterangepicker.css" />	
</head>
<body style="background:#F7F7F7;" class="animated fadeIn">
<div class="page-title">
              <div class="title_left">
                  <ol class="breadcrumb">
												  <li><a href="javascript:void(0)">系统管理</a></li>
												  <li class="active">系统异常</li>
												</ol>
              </div>
            </div>
           <div class="clearfix"></div>
           
                <div class="x_panel">
							 					<div class="x_content">
							 					
							 					 <ul class="nav nav-tabs bar_tabs" role="tablist">
									          <li role="presentation" class=""><a href="#tab_content1" id="log-tab" role="tab" data-toggle="tab" aria-expanded="true">用户日志</a></li>
									          <li role="presentation" class="active"><a href="#tab_content2" role="tab" id="error-tab" data-toggle="tab" aria-expanded="false">系统异常</a></li>
									      </ul>
									     <div class="tab-content">

											<div id="tab_content2" class="tab-pane fade active in" role="tabpanel" class="tab-pane fade active in" >
					
							 						<form id="search_form" role="form" class="row form-horizontal form-label-left">
															<div class=" btn-group col-md-12 col-sm-12 col-xs-12">

	               <div class="input-group col-md-4 col-xs-12" style="float:left;">
	      		                <div class="input-prepend input-group">
                              <span class="add-on input-group-addon">操作时间</span>
                              <input type="text"  name="operTime" id="operTime" class="form-control" placeholder="选择操作时间范围"   />
                           </div>
                   </div>

												<div class="input-group col-md-4 col-xs-12" style="float:left;">
		                  <input type="text" id="searchKey" name="searchKey" placeholder="输入操作用户"   class="form-control" autocomplete="off"/>
														<span class="input-group-btn" >
																<button id="search" type="button" class="btn btn-success" ><i class="fa fa-search"></i>搜 索</button>
														</span>
	               </div>
		               	<div class="input-group pull-right " style="float:left">
		                    <a id="refresh" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="刷新"><i class="fa fa-refresh green fa-spin"></i></a>
		                    <a id="exp_excel" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="导出Excel"><i class="fa fa-file-excel-o purple"></i></a>
		                    <a id="exp_pdf" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="导出PDF"><i class="fa fa-file-pdf-o red"></i></a>
		                    <!--a id="refresh" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="打印列表"><i class="fa fa-print"></i></a-->
	                   	</div>
                    </div>
	            			 </form>
													<table id="data-tables" class="table table-striped table-bordered table-hover dt-responsive " cellspacing="0" style="width:100%">
														<thead>
															<tr >
															  <th>id</th>
																<th nowrap="nowrap">操作时间</th>
																<th nowrap="nowrap">操作用户</th>
																<th nowrap="nowrap">操作链接</th>
																<th nowrap="nowrap">异常信息</th>
																<th nowrap="nowrap">操作</th>
															</tr>
														</thead>
														<tbody></tbody>
														<!-- tbody是必须的 -->
													</table>
							</div>
				</div>
  </div>
</div>

<script type="text/javascript" src="/js/moment.min.js"></script>
<script type="text/javascript" src="/js/moment.zh-cn.js"></script>
<script type="text/javascript" src="/js/daterangepicker.js"></script>
<script type="text/javascript">
//切换tab
$("#log-tab").click(function(){
  location.href="/userlog/list";
});

$('#operTime').daterangepicker({
    timePicker: true,
    timePicker24Hour: true,
    timePickerSeconds: true,
    locale: {
      format: 'YYYY-MM-DD HH:mm:ss'
            }
});
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
   　  order: [[1, "desc"]], //默认排序
     ajax: { url:"/userlog/error/page", type:"post" },
     columns: [
	      	 { data: "id",visible:false },
       		 { data: "operTime" ,width:100},
       		 { data: "userName" ,"orderable":false,width:100},
       		 { data: "url" ,"orderable":false,width:150},
    	  	 { data: "exception","orderable":false},
             { data: "id","orderable":false,className: "text-center",
	        	 render: function(data, type, full) {
	        		  return "<a class='btn btn-primary btn-xs' title='查看详情'  onclick=\"view('" + data + "');\" ><i class='fa fa-pencil '></i>查看详情</a>  ";
	         	              }
	       				   }
	         	        ],
	 dom: 't<"row"<"col-sm-3"i><"col-sm-4 input-s-sm"l><"col-sm-5"p>>'
 });

setInterval("reload()" , ${interval_error});
function reload(){
	var operTime = $.trim($("#operTime").val());
	var searchValue = $.trim($("#searchKey").val());
	dtTable.ajax.url('/userlog/error/search?searchValue='+searchValue+'&operTime='+operTime).load();
}

function view(id){
	location.href="/userlog/error/view/"+id;
}

//搜索
$("#search").click(function(){
	  var operTime = $.trim($("#operTime").val());
		var searchValue = $.trim($("#searchKey").val());
		dtTable.ajax.url('/userlog/error/search?searchValue='+searchValue+'&operTime='+operTime).load();
});


//搜索建议
$('#searchKey').typeahead({
	ajax: {
     url: '/userlog/error/suggest?t='+(new Date()).getTime(),
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
   display: "userName", // 默认的对象属性名称为 name 属性
   val: "id",    // 默认的标识属性名称为 id 属性
   items: 10 // 最多显示项目数量
});

//刷新列表
$("#refresh").click(function(){
	dtTable.ajax.url('/userlog/error/page?t='+(new Date()).getTime()).load();
});


//导出excel
$("#exp_excel").click(function(){
	var searchValue = $.trim($("#searchKey").val());
	location.href = "/userlog/error/export/excel?searchValue="+searchValue;
	alertSuccess();
});

//导出pdf
$("#exp_pdf").click(function(){
	var searchValue = $.trim($("#searchKey").val());
	location.href = "/userlog/error/export/pdf?searchValue="+searchValue;
	alertSuccess();
});
</script>
</body>
</html>