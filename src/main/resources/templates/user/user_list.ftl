<#include "/layout/header.ftl">  
<#include "/layout/table.ftl">  
<@header />
<@table />
	</head>
	<body style="background:#F7F7F7;" class="animated fadeIn" >
            <div class="page-title">
               <div class="title_left">
                  <ol class="breadcrumb">
					<li><a href="javascript:void(0)">系统管理</a></li>
					<li class="active">用户管理</li>
				  </ol>
              </div>
            </div>

         <div class="clearfix"></div>
			<div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>用户列表</h2>
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
																			<button id="delAll" type="button"  class="btn btn-danger">
																				<i class=" fa fa-trash-o  "></i>
																				批量删除
																			</button>
																</div>
												<div class="input-group col-md-5 " style="float:left;">
									   		<label class="input-group-btn">
	                  		<select id="selectRange" name="selectRange" class="form-control" style="width:110px" >
												      <option value="loginName" selected>登录名称</option> 
												      <option value="email">Email</option> 
												      <option value="phone">手机号码</option>
													 		</select>
													</label>

	                  <input type="text" id="searchKey" name="searchKey" placeholder="输入关键字 " class="form-control" autocomplete="off"/>
													<span class="input-group-btn" >
														<button id="search" type="button" class="btn btn-success" ><i class="fa fa-search"></i>搜 索</button>
													</span>
	               </div>
		               	<div class="input-group pull-right " style="float:left">
		                    <a id="refresh" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="刷新"><i class="fa fa-refresh green fa-spin"></i></a>
		                    <a id="upload" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="上传"><i class="fa fa-cloud-upload blue "></i></a>
		                    <a id="exp_excel" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="导出Excel"><i class="fa fa-file-excel-o purple"></i></a>
		                    <a id="exp_pdf" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="导出PDF"><i class="fa fa-file-pdf-o red"></i></a>
		                    <!-- a id="print" class="btn btn-default" tabindex="0" aria-controls="datatable-buttons" data-placement="top" data-toggle="tooltip" data-original-title="打印列表"><i class="fa fa-print"></i></a -->
	                   	</div>
                    </div>
	               </form>

											<table id="data-tables" class="table table-striped table-bordered table-hover dt-responsive " cellspacing="0" style="width:100%">
												<thead>
													<tr >
													 <th><input type="checkbox" id="check-all" class="icheck"/></th>
													 <th>id</th>
														<th>登陆名称</th>
														<th>Email</th>
														<th>手机号码</th>
														<th nowrap="nowrap">最近一次登陆时间</th>
														<th nowrap="nowrap">最近一次登陆IP地址</th>
														<th>账号状态</th>
														<th>操作</th>
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
  　 order: [[1, "desc"]], //默认排序
    ajax: { url:"/user/page", type:"post" },
    columns: [
				 { data: "id", className: "text-center",orderable:false,width:30,
			 	   render: function(data, type, full) {
			       return "<input type='checkbox' id='"+data+"' class='icheck' name='table_records'/>";
			   	      	}
            	   },
       { data: "id",visible:false },
       { data: "loginName" },
       { data: "email","orderable":false },
       { data: "phone","orderable":false },
       { data: "loginTime","orderable":false },
       { data: "loginIP","orderable":false },
       { data: "status","orderable":false,className: "text-center",
      			 render: function(data, type, full) {
      						if(data=='1'){
      							  return "<input data-size='mini' data-on-color='success' onchange=\"change_status('" + full.id+ "','1');\" data-off-color='warning'"
      										+" data-on-text='激活' data-off-text='锁定' type='checkbox' checked> ";
      						}else if(data=='2' || data == null){
      							  return "<input data-size='mini' data-on-color='success' onchange=\"change_status('" + full.id + "','2');\" data-off-color='warning'"
      							  		+" data-on-text='激活' data-off-text='锁定' type='checkbox'>";
      								 	}
       	                   	}
     				  },
       { data: "id","orderable":false,className: "text-center",
        	 render: function(data, type, full) {
        		  return	"<a class='btn btn-info btn-xs' title='修改'  onclick=\"edit('" + data + "');\" ><i class='fa fa-pencil '></i>修改</a>  "
	    								+"<a class='btn btn-danger btn-xs' title='删除'  onclick=\"del('" + data + "');\" ><i class='fa fa-trash-o'></i>删除</a>";
         	                    		}
       				    	}
        	        ],        
     dom: 't<"row"<"col-sm-3"i><"col-sm-4 input-s-sm"l><"col-sm-5"p>>',
     drawCallback: function(settings) {   //加载icheck/switch
        $('input.icheck').iCheck('uncheck');//分页时取消全选
   		  $('input.icheck').iCheck({
   			    checkboxClass: 'icheckbox_flat-green',
   			    radioClass: 'iradio_flat-green'
   		             });
   			$('#check-all').on('ifChecked', function(event){
   				  $('input[name="table_records"]').iCheck('check');
   			       });
   			$('#check-all').on('ifUnchecked', function(event){
   			  	$('input[name="table_records"]').iCheck('uncheck');
   		        	});
   			       //启动切换按钮
       $('[data-size="mini"]').bootstrapSwitch();
    		 }
 } );


// 以下为页面操作，以上为datatables，之所以放在下面是要引用dtTable对象

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
	      url:'/user/delete/'+id,
	      success:function(msg){
      		if(msg){
      			dtTable.draw(false);//保持当前分页信息
      			layer.close(index);
      		}else{
      			alertError("不能删除当前登录账户");
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
		  title: '编辑用户',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset,
		  content: ['/user/edit/'+id, 'no']
		}); 
}

//激活/锁定 用户
function change_status(id,value){
		if(value == '1'){
			value = '2';
		}else if(value=='' || value == '2'){
			value = '1';
		}
	 $.ajax({
	    type:"get",
	    url:'/user/status?id='+id+"&status="+value+"&t="+(new Date()).getTime()
      });
}

//新增
$("#add").click(function(){
	 layer.open({
		  type: 2,
		  title: '新增用户',
		  content: ['/user/add', 'no'],
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset
		}); 
});

//批量删除
$("#delAll").click(function(){
	 if($("input[name='table_records']").is(':checked')==false){
			　alertError("请至少选择一项");
			 return false;
	    }
	 var id_array = new Array();  
	 $('input[name="table_records"]:checked').each(function(){
	     id_array.push($(this).attr('id'));//向数组中添加元素  
	  });  
	 var rowIds=id_array.join(',');
	 layer.confirm('您确定要删除这些记录么？', {
			  title: '操作提示',
			  skin: custom_skin,//边框
				 shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
				 offset: custom_offset,
				 btn: ['是的','放弃']
		}, function(index){
			$.ajax({
		     type:"get",
		     url:'/user/delete/'+rowIds,
		     success:function(msg){
	     	   if(msg){
		     			 dtTable.draw(false);//保持当前分页信息
		     			 layer.close(index);
	     		  }else{
	     		    alertError("不能删除当前登录账户");
	      					}
	                  }
	            });
			}, function(index){
			   layer.close(index);
		});
});

//搜索
$("#search").click(function(){
		var selectRange = $("#selectRange option:selected").val();
		var searchValue = $.trim($("#searchKey").val());
		dtTable.ajax.url('/user/search?selectRange='+selectRange+"&searchValue="+searchValue).load();
});	

//搜索建议
$('#searchKey').typeahead({
	  ajax: {
	     url: '/user/suggest?t='+(new Date()).getTime(),
	     timeout: 300, // 延时
	     method: 'post',//中文参数必须要用POST
	     triggerLength: 1, // 输入几个字符之后，开始请求
	     preDispatch: function (query) {
	        var para = { selectRange: $("#selectRange option:selected").val(), searchValue : $.trim($("#searchKey").val()) };
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
	dtTable.ajax.url('/user/page?t='+(new Date()).getTime()).load();
});

//导出excel
$("#exp_excel").click(function(){
	var selectRange = $("#selectRange option:selected").val();
	var searchValue = $.trim($("#searchKey").val());
	location.href = "/user/export/excel?selectRange="+selectRange+"&searchValue="+searchValue;
	alertSuccess();
});

//上传excel
$("#upload").click(function(){
	 layer.open({
		  type: 2,
		  title: '上传用户数据',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset,
		  content: ['/user/import/excel', 'no']
		}); 
});

//导出pdf
$("#exp_pdf").click(function(){
	var selectRange = $("#selectRange option:selected").val();
	var searchValue = $.trim($("#searchKey").val());
	location.href = "/user/export/pdf?selectRange="+selectRange+"&searchValue="+searchValue;
	alertSuccess();
});


//打印
$("#print").click(function(){
	var selectRange = $("#selectRange option:selected").val();
	var searchValue = $.trim($("#searchKey").val());
	$.ajax({
		   type:"get",
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   url:"user/print?selectRange="+selectRange+"&searchValue="+searchValue,
		   success:function(msg){
				  if(msg){
					  $("#print_table").printThis({
						   header: msg,
						   importCSS: true,
						   pageTitle: "用户列表",
						   loadCSS: "/css/bootstrap.min.css", 
						   importStyle: true  
					  		});
				      }
		         }
		   });
});

</script>
</body>