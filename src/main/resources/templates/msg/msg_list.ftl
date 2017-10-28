<#include "/layout/header.ftl">  
<#include "/layout/table.ftl">  
<@header />
<@table />
</head>
<body style="background:#F7F7F7;" class="animated fadeIn">
            <div class="page-title">
               <div class="title_left">
                  <ol class="breadcrumb">
												  <li><a href="javascript:void(0)">系统管理</a></li>
												  <li class="active">站内信</li>
												</ol>
              </div>
            </div>

           <div class="clearfix"></div>

									<div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">


							 					<div class="x_content">
							 					
							 					<ul class="nav nav-tabs bar_tabs" role="tablist">
                    			<li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">站内信列表</a></li>
                        <li id="qunfa" role="presentation" class=""><a href="#tab_content2" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">群发站内信</a></li>
                   </ul>

									<div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">

													<div class="row">
                      <div class="col-md-3 col-sm-12 col-xs-12">
                    <ul class="nav nav-tabs tabs-left">
                        <li id="allmsg" class="active"><a href="#home" data-toggle="tab"><i class="glyphicon glyphicon-envelope"></i> &nbsp;全部消息</a></li>
                        <li id="draftsmsg" ><a href="#profile" data-toggle="tab"><i class="glyphicon glyphicon-briefcase"></i> &nbsp;草稿箱</a></li>
                        <li id="sendmsg" ><a href="#messages" data-toggle="tab"><i class="glyphicon glyphicon-send"></i> &nbsp;已发送</a></li>
                      </ul>

                      </div>
                      <!-- /MAIL LIST -->
                      <!-- CONTENT MAIL -->
                      <div class="mail_list_column col-md-9 col-sm-12 col-xs-12">
              
                      <form id="search_form" role="form" class=" form-horizontal form-label-left ">
							<div id="optbutton" class="btn-group ">
                              <button id="delAll" class="btn btn-sm btn-danger" type="button" data-placement="top" data-toggle="tooltip" data-original-title="彻底删除"><i class="fa fa-trash-o"></i>彻底删除</button>
                              <button class="btn btn-sm btn-default" onclick="window.print();" type="button" data-placement="top" data-toggle="tooltip" data-original-title="打印"><i class="fa fa-print"></i> 打印</button>
                            </div>
	                    </form>	                    

                     <table id="data-tables" class="table table-striped  table-hover dt-responsive " cellspacing="0" style="width:100%">
							<thead>
								<tr >
								 <th><input type="checkbox" id="check-all" class="icheck"/></th>
								 <th>id</th>
								 <th>&nbsp;</th>
									<th>发送人</th>
									<th>标题</th>
									<th>发送时间</th>
									<th>保存时间</th>
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
	    </div>
  </div>
<script type="text/javascript">
$("#qunfa").click(function(){
	location.href="${request.contextPath}/message/add";
} );

$.fn.dataTable.ext.errMode = 'throw';
var dtTable = $('#data-tables').DataTable({
		 responsive: true,//自适应宽度，适合手机
    language:{url:"${request.contextPath}/js/datatables_zh.json"},
    processing: false,
    serverSide: true,
    autoWidth: true,
    //pagingType: "full_numbers",
    lengthMenu:[10,30,50],
    searching: false,
  　 order: [[1, "desc"]], //默认排序
    ajax: { url:"${request.contextPath}/message/page", type:"post" },
    columns: [
				 { data: "id", className: "text-center",orderable:false,width:30,
			 	   render: function(data, type, full) {
			       return "<input type='checkbox' id='"+data+"' class='icheck' name='table_records'/>";
			   	      	}
            	   },
       { data: "id","orderable":false,visible:false },
       { data: "id","orderable":false ,width:10, 
		    	   render: function(data, type, full) {
		    		    var prefix = "";
		    		    if(full.attachment1 != null || full.attachment2 != null || full.attachment3 != null){
		    		    	 prefix = '<i class="fa fa-paperclip"></i>';
		    		               }
				       return prefix;
				   	      	}
       				},
       	{ data: "loginName","orderable":false },
       { data: "title","orderable":false },
       { data: "sendTime","orderable":false },
       { data: "saveTime","orderable":false },
       { data: "id","orderable":false,className: "text-center",
      	 render: function(data, type, full) {
      		  var str =	"<a href='javascript:void(0)' class='btn btn-primary btn-xs' title='查看详情'  onclick=\"view('" + data + "');\" ><i class='fa fa-pencil'></i>查看详情</a>";
      		 	if(full.status == "0"){//草稿箱
      		 		  return	str + " <a href='javascript:void(0)' class='btn btn-success btn-xs' title='直接发送'  onclick=\"drafts_publish('" + data + "');\" ><i class='fa fa-paper-plane'></i>直接发送</a>  ";
      		  }else if(full.status == "1"){//已经发送
      		 			return str;
      		 				 }
       	                }
     			  }
        	  ],        
     dom: 'tip',
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
    		 }
 } );

function view(id){
	location.href="${request.contextPath}/message/view/"+id;
}

// 以下为页面操作，以上为datatables，之所以放在下面是要引用dtTable对象

$("#allmsg").click(function(){
	dtTable.ajax.url('${request.contextPath}/message/page?&t='+(new Date()).getTime()).load();
});

$("#draftsmsg").click(function(){
	dtTable.ajax.url('${request.contextPath}/message/page?status=0&t='+(new Date()).getTime()).load();
});

$("#sendmsg").click(function(){
	dtTable.ajax.url('${request.contextPath}/message/page?status=1&t='+(new Date()).getTime()).load();
});


function drafts_publish(id){
 $.ajax({
    type:"get",
    url:"${request.contextPath}/message/draftspublish/"+id,
    success:function(msg){
 	    if(msg){
					alertSuccess();
	      dtTable.draw(false);//保持当前分页信息
 		 	   }
          }
  }); 
}

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
		     url:'${request.contextPath}/message/delete/'+rowIds,
		     success:function(msg){
	     	   if(msg){
		     			 dtTable.draw(false);//保持当前分页信息
		     			 layer.close(index);
	     		 			 }
	                   }
	            });
			}, function(index){
			   layer.close(index);
		});
});


//导出excel
$("#exp_excel").click(function(){
	var selectRange = $("#selectRange option:selected").val();
	var searchValue = $.trim($("#searchKey").val());
	location.href = "${request.contextPath}/user/export/excel?selectRange="+selectRange+"&searchValue="+searchValue;
	alertSuccess();
});

//导出pdf
$("#exp_pdf").click(function(){
	var selectRange = $("#selectRange option:selected").val();
	var searchValue = $.trim($("#searchKey").val());
	location.href = "${request.contextPath}/user/export/pdf?selectRange="+selectRange+"&searchValue="+searchValue;
	alertSuccess();
});


//打印
$("#print").click(function(){
	var selectRange = $("#selectRange option:selected").val();
	var searchValue = $.trim($("#searchKey").val());
	$.ajax({
		   type:"get",
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   url:"${request.contextPath}/user/print?selectRange="+selectRange+"&searchValue="+searchValue,
		   success:function(msg){
				  if(msg){
					  $("#print_table").printThis({
						   header: msg,
						   importCSS: true,
						   pageTitle: "用户列表",
						   loadCSS: "${request.contextPath}/css/bootstrap.min.css", 
						   importStyle: true  
					  		});
				      }
		         }
		   });
});

</script>
</body>