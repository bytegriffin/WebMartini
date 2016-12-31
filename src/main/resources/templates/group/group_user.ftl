<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />

</head>
<body id="body" style="background:#F7F7F7;" >
 <br />
 <form id="form_new" class="form-horizontal form-label-left" method="post">

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

           	                   <#list list as ru>
           	                   		
									<div class="col-md-3 col-sm-3 col-xs-12">
				           	               <div class="form-group">
				        			       	    <input id="userids_${ru.id}" type="checkbox" name="userids" class="icheck" value="${ru.id}" <#if ru.checked> checked </#if> > 
				        			       	    <label for="userids_${ru.id}">${ru.loginName}</label> 
				        			       </div>
										</div>

 								</#list> 
							</div>
					</div>		 						

         </div>

	  <div class="form-group col-md-12 col-sm-12 col-xs-12">
			<button id="checkallbtn" type="button" class="btn btn-default ">全 选</button>
		    <button id="uncheckallbtn" type="button" class="btn btn-default ">全不选</button>
			<button id="savebtn" type="button" class="btn btn-success">保 存</button>
      </div>
</form>
<script type="text/javascript" src="/js/form.js"></script>
<script type="text/javascript">
$('input.icheck').iCheck({
  checkboxClass: 'icheckbox_flat-green',
  radioClass: 'iradio_flat-green'
});
$('#checkallbtn').on('click', function(event){
  $('input.icheck').iCheck('check');
});
$('#uncheckallbtn').on('click', function(event){
  $('input.icheck').iCheck('uncheck');
});

 //保存
$("#savebtn").click(function(){
	 var chk_value =[]; 
  	$('input[name="userids"]:checked').each(function(){ 
  			chk_value.push($(this).val()); 
  		});
	  $.ajax({ 
		   type: "post", 
		   url: "/group/user_ref", 
		   data: {"user_ids" : chk_value,"group_id":${group_id}}, 
		   success: function (data) { 
					 var index = parent.layer.getFrameIndex(window.name); 
			 		 parent.layer.close(index);
                }
        });
});

</script>
</body>
</html>