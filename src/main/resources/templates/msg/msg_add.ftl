<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
		 <link rel="stylesheet" href="/css/wangEditor.css" />
		 <link rel="stylesheet" href="/css/layer.css" />

		 <script type="text/javascript">
//加载ztree
var groupsetting = {
	 check: {
			enable: true,
			chkboxType: { "Y": "s", "N": "s" }
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
			url:"/group/tree",
			autoParam: ["id"],
			dataType:"json",
			dataFilter: filter
	 },
	 callback:{
		 onCheck:onGroupCheck
	 }
};

var rolesetting = {
		 check: {
				enable: true,
				chkboxType: { "Y": "", "N": "" }
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
				url:"/role/tree",
				autoParam: ["id"],
				dataType:"json",
				dataFilter: filter
		 },
		 callback:{
			 onCheck:onRoleCheck
		 }
};

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function onRoleCheck(){
	var roleTree = $.fn.zTree.getZTreeObj("roleTree").getCheckedNodes(true);
	$("#alluser").iCheck('uncheck');
	$("#receiver").removeClass("has-error");
	$("#msgreceiverMessage").hide();
	var treeObj = $.fn.zTree.getZTreeObj("groupTree");
  treeObj.checkAllNodes(false);
	var rolestr = "";
	for(var i=0;i<roleTree.length;i++){
		rolestr += roleTree[i].name + ",";
	}
	$("#msgreceiver").val(rolestr);
	autosize.update($('#msgreceiver'));
}

function onGroupCheck(){
	var groupTree = $.fn.zTree.getZTreeObj("groupTree").getCheckedNodes(true);
	var ischecked = $("#alluser").is(':checked');
	$("#alluser").iCheck('uncheck');
	$("#receiver").removeClass("has-error");
	$("#msgreceiverMessage").hide();
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	treeObj.checkAllNodes(false);
	var groupstr = "";
	for(var i=0;i<groupTree.length;i++){
		groupstr += groupTree[i].name + ",";
	}
	$("#msgreceiver").val(groupstr);
	autosize.update($('#msgreceiver'));
}

$(document).ready(function(){
	 $.fn.zTree.init($("#roleTree"), rolesetting);
	 $.fn.zTree.init($("#groupTree"), groupsetting);
	 
	//全选
	  $("#checkallrole").click(function(){
		  var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		  treeObj.checkAllNodes(true);
		  onRoleCheck();
		 });

		 //全取消
	  $("#uncheckallrole").click(function(){
		    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		    treeObj.checkAllNodes(false);
		    onRoleCheck();
		 });
		 
	    //全选
	  $("#checkallgroup").click(function(){
		  var treeObj = $.fn.zTree.getZTreeObj("groupTree");
		  treeObj.checkAllNodes(true);
		  onGroupCheck();
		 });

		  //全取消
	  $("#uncheckallgroup").click(function(){
		    var treeObj = $.fn.zTree.getZTreeObj("groupTree");
		    treeObj.checkAllNodes(false);
		    onGroupCheck();
		 });
});


</script>

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
                    			<li id="msglist" role="presentation" class=""><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">站内信列表</a></li>
                        <li role="presentation" class="active"><a href="#tab_content2" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">群发站内信</a></li>
                   </ul>

						<div role="tabpanel" class="tab-pane fade active in" id="tab_content2" aria-labelledby="home-tab">
								 <form id="form_new" class="form-horizontal form-label-left"  action="/message/add" method="post" enctype="multipart/form-data">
											  <div class="accordion col-md-3 col-sm-12 col-xs-12" id="accordion" role="tablist" aria-multiselectable="true">
											      <span class="input-control"><h4>&nbsp;收信人类型</h4></span>
                      <div class="panel">
                        <a class="panel-heading" role="tab" id="headingOne" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                          <h4 class="panel-title">按用户划分</h4>
                        </a>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                          <div class="panel-body">
                            <input id="alluser" type="checkbox" name="alluser" class="icheck" checked="checked" /> 
				        			       	  <label for="alluser">全体用户</label> 
                          </div>
                        </div>
                      </div>
                      <div class="panel">
                        <a class="panel-heading collapsed" role="tab" id="headingTwo" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                          <h4 class="panel-title">按角色划分</h4>
                        </a>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                          <div class="panel-body">
                          <button id="checkallrole" type="button" class="btn btn-default ">全 选</button>
           										<button id="uncheckallrole" type="button" class="btn btn-default ">全不选</button>
                            <p><ul id="roleTree" class="ztree" /></p>
                            </div>
                        </div>
                      </div>
                      <div class="panel">
                        <a class="panel-heading collapsed" role="tab" id="headingThree" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                          <h4 class="panel-title">按组织划分</h4>
                        </a>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                          <div class="panel-body">
                          <button id="checkallgroup" type="button" class="btn btn-default ">全 选</button>
           										<button id="uncheckallgroup" type="button" class="btn btn-default ">全不选</button>
                            <p><ul id="groupTree" class="ztree" /></p>
                          </div>
                        </div>
                      </div>
                    </div>
											 
											 <div class="mail_list_column col-md-9 col-sm-12 col-xs-12">

											    <div id="receiver" class="form-group">
															  <label class="control-label">收信人 &nbsp;</label>
																 <div id="input_msgtitle" style="display:block;">		
								                   <!--  input type="text" class="tags form-control" name="msgreceiver" id="msgreceiver" placeholder="请选择收信人类型"    value="全体用户"  readOnly="readOnly"/ --> 
								                   <textarea name="msgreceiver" id="msgreceiver" class="tags form-control" style="width:100%;" rows="1" readOnly="readOnly">全体用户</textarea>
								             </div>
					                  <span class="help-block" id="msgreceiverMessage"></span>
							          </div>
		
													 <div class="form-group">
													      <label class="control-label"> 消息标题 &nbsp;</label>
					                   <input type="text" class="form-control" name="msgtitle" placeholder="请输入消息标题"   maxlength="100"  /> 
					                   <span class="help-block" id="msgtitleMessage"></span>
							          </div>
		
		           				<div id="divmsgcontent" class="form-group" >
		           							<label class="control-label"> 消息内容 &nbsp;</label>
		           							<textarea class="text" id="msgcontent" name="msgcontent" style="width:100%; min-height:150px;" maxlength="8000" ></textarea>
					                 <span class="help-block" id="msgcontentMessage"></span>
							         </div>
							         
							         <div class="form-group">
		           							<label class="control-label"> 附件1 &nbsp;</label>
																<input id="attachment1" name="attachment1" class="" type="file" />
					                 <span class="help-block" id="attachment1Message"></span>
					                 <label class="control-label"> 附件2 &nbsp;</label>
							   							 <input id="attachment2" name="attachment2" class="" type="file" />
					                 <span class="help-block" id="attachment2Message"></span>
					                 <label class="control-label"> 附件3 &nbsp;</label>
					                 <input id="attachment3" name="attachment3" class="" type="file" />
					                 <span class="help-block" id="attachment3Message"></span>
							         </div>

										    <div class="ln_solid"></div>

										    	<div class="form-group">
													 　　　　<label class="control-label col-md-3 col-sm-3 col-xs-12" > </label>
					                 <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
					                     <input type="hidden" name="msgid" id="msgid"/>
					                 		 <input type="hidden" name="roleids" id="roleids"/>
					                 		 <input type="hidden" name="groupids" id="groupids"/>
					                     <input type="hidden" name="submittype" id="submittype"/>
					                     <button id="resetbtn" type="reset" class="btn btn-default ">重 置</button>
					           						 <button id="savebtn" type="submit" class="btn btn-info"><i class="fa fa-download"></i>保存到草稿箱</button>
					           						 <button id="sendbtn" type="submit" class="btn btn-success"><i class="fa fa-paper-plane"></i>直接发送</button>
					                 </div> 
			               </div>
               	</div>
               </form>
											 </div>

											</div>
								</div>
      </div>

<script type="text/javascript" src="/js/autosize.min.js"></script>
<script type="text/javascript" src="/js/wangEditor.js"></script>
<script type="text/javascript" src="/js/bootstrap-filestyle.min.js"></script>
<script type="text/javascript" src="/js/layer.js"></script>
<script type="text/javascript" src="/js/layer-plugin.js"></script>
<script type="text/javascript">

//启动autosize
autosize($('#msgreceiver'));
//启动filestyle
$('#attachment1').filestyle({
	buttonBefore : true,
	badge: false,
	iconName : 'glyphicon glyphicon-plus',
	buttonText : '增加',
	placeholder : '选择要上传的文件',
	buttonName : 'btn-primary'
});
$('#attachment2').filestyle({
	buttonBefore : true,
	badge: false,
	iconName : 'glyphicon glyphicon-plus',
	buttonText : '增加',
	placeholder : '选择要上传的文件',
	buttonName : 'btn-primary'
});
$('#attachment3').filestyle({
	buttonBefore : true,
	badge: false,
	iconName : 'glyphicon glyphicon-plus',
	buttonText : '增加',
	placeholder : '选择要上传的文件',
	buttonName : 'btn-primary'
});

//切换tab
$("#msglist").click(function(){
	location.href="/message/list";
} );
//启动icheck
$('#alluser').iCheck({
	checkboxClass: 'icheckbox_flat-green',
	radioClass: 'iradio_flat-green'
});

$('#alluser').on('ifChecked', function(event){
	$('#msgreceiver').val("全体用户");
	autosize.update($('#msgreceiver'));
	$("#receiver").removeClass("has-error");
	$("#msgreceiverMessage").hide();
	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	treeObj.checkAllNodes(false);
	var treeObj = $.fn.zTree.getZTreeObj("groupTree");
	treeObj.checkAllNodes(false);
});

$('#alluser').on('ifUnchecked', function(event){
	$('#msgreceiver').val("");
});

$(document).ready(function() {
	//加载wangEditor
	var textarea = document.getElementById('msgcontent');// 获取元素
	wangEditor.config.printLog = false;
	var editor = new wangEditor(textarea);// 生成编辑器
	editor.config.emotionsShow = 'value';
	// 仅仅想移除某几个菜单，例如想移除『插入代码』和『全屏』菜单：
    // 其中的 wangEditor.config.menus 可获取默认情况下的菜单配置
  editor.config.menus = $.map(wangEditor.config.menus, function(item, key) {
    if (item === 'emotion') {
        return null;
            }
    if (item === 'location') {
        return null;
           }
    return item;
    });
	editor.create();
//	editor.$txt.html("<p id='p'>请输入消息内容...</p>");

$(".wangEditor-txt").bind("keydown", function(e) { 
		$("#savebtn").attr("disabled",false);
		$("#sendbtn").attr("disabled",false);
		$("#divmsgcontent").removeClass("has-error");
		$("#msgcontentMessage").text("");
});

var form_id = "#form_new";
$('#resetbtn').click(function() {
	   $('#alluser').iCheck("check");
	   $("#savebtn").attr("disabled",false);
		  $("#sendbtn").attr("disabled",false);
			$("#divmsgcontent").removeClass("has-error");
			$("#msgcontentMessage").text("");
			editor.$txt.html("");
    $(form_id).data('bootstrapValidator').resetForm(true);
 });
$(form_id).bootstrapValidator({
     submitHandler: function(validator, form, submitButton) {
    	 var text = editor.$txt.text();
 	    if(text == '' || text == null){
 	    			$("#savebtn").attr("disabled",true);
 						$("#sendbtn").attr("disabled",true);
 	    			$("#divmsgcontent").addClass("has-error");
 	    			$("#msgcontentMessage").append("此处为必填项");
 	    			return;
 	 			}
 	     var rolenodes = $.fn.zTree.getZTreeObj("roleTree").getCheckedNodes(true);
 	  		 var roleids = "";
 	  		 for(var i=0;i<rolenodes.length;i++){
 	  					roleids += rolenodes[i].id + ","; //获取选中节点的值
 	  		 	 	}
 	  		 $("#roleids").val(roleids);
 	  		 var groupnodes = $.fn.zTree.getZTreeObj("groupTree").getCheckedNodes(true);
 	  		 var groupids = "";
 	  		 for(var i=0;i<groupnodes.length;i++){
 	  					groupids += groupnodes[i].id + ","; //获取选中节点的值
 	  		 	 	}
 	  		 $("#groupids").val(groupids);
 	  		 var url = "";
 	  			if(submitButton.prop("id")=='savebtn'){//保存操作
		 	    		$("#submittype").val("save");
		 	    		$("#savebtn").attr("disabled",true);
		 	    		url="/message/save";
		 	    		$.ajax({
			 	        type: 'POST',
			 	        url: url,
			 	      	 cache: false,
			 	     		 processData: false,
			 	   		  contentType: false,
			 	  			  data: new FormData($('#form_new')[0]),
			 	        success:function(result) {
                 var result = jQuery.parseJSON(result);
			 	    	      if(result.status){
			 	    	     			 $("#msgid").val(result.msgid);
			 	    	    	  		 alertSuccess();
													 $("#savebtn").reset();
			 	    	    	     $(form_id).data('bootstrapValidator').resetForm(false); 
			 	    	      }else{
			 	    	    	 			alertError("操作失败");
			 	    	                        }  
			 	    	                 },  
			 	              });  
 	    		   }
 	     if(submitButton.prop("id")=='sendbtn'){//发送操作
	 	    		 $("#submittype").val("send");
	 	    		 $("#sendbtn").attr("disabled",true);
	 	    		 url="/message/publish?msgid="+$("#msgid").val();
	 	    		 $.ajax({
		 	       type: 'POST',
		 	       url: url,
		 	      	cache: false,
		 	     		processData: false,
		 	   		 contentType: false,
		 	  			 data: new FormData($('#form_new')[0]),
		 	       success:function(result) {  
		 	          var result = jQuery.parseJSON(result);
		 	    	      if(result.status){
		 	    	    	  		//alertSuccess();
		 	    	    	     //$(form_id).data('bootstrapValidator').resetForm(false);
		 	    	    	     location.href="/message/list";
		 	    	      }else{  
		 	    	    	 			alertError("操作失败"); 
		 	    	                        }  
		 	    	                 },  
		 	               }); 
 	  			  }	     
              },
     fields: {
    	 			msgreceiver:{
    		 					container: '#msgreceiverMessage',
             trigger: 'blur',
             validators: {
                notEmpty: {message: '此处为必填项'}
  			                     }
    					 },
    			  msgtitle: {
           container: '#msgtitleMessage',
           trigger: 'blur',
           validators: {
              notEmpty: {message: '此处为必填项'}
			                     }
			           },
			    attachment1: {
			       validators: {
			    	  	   container: '#attachment1Message',
				         trigger: 'blur',
			          file: {
                 maxSize: 10*1024*1024,
                 message: '附件文件的大小不能超过10M.'
			                             }
			                      }
			            }
            }
  }).on('success.form.bv', function(e) {// 阻止默认事件提交
      e.preventDefault();
       });
});

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


</script>
</body>