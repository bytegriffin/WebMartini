<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
<link rel="stylesheet" href="/css/layer.css" />		
<link rel="stylesheet" href="/css/bootstrap-datepicker3.min.css" />	
</head>
<body id="body" style="background:#F7F7F7;" >

        <div class="right_col" role="main">
          <div class="page-title">
               <div class="title_left col-md-12 col-sm-12 col-xs-12">
                  <ol class="breadcrumb">
												  <li class="active">个人设置</li>
												</ol>
              </div>
            </div>

            <div class="clearfix"></div>

              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                  <ul class="nav navbar-right panel_toolbox">
                      <li class="pull-right"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                    </ul>
                    <div class="ui-ribbon-wrapper ">
                     			<div class="ui-ribbon ">
                              											个人详情
                     			</div>
                 			</div>
 
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <div class="col-md-3 col-sm-3 col-xs-12 profile_left">
                      <div class="profile_img">
                        <div id="crop-avatar">
                          <#if user.avatar??>
									<img id="avatar" src="/${user.avatar}" class="btn img-responsive avatar-view" >
							<#else >
									<img id="avatar" src="/images/avatar.png" class="btn img-responsive avatar-view" >
							</#if>
                        </div>
                      </div>
                      <h3>${user.loginName}</h3>

                      <ul class="list-unstyled user_data">
                        <li><i class="fa fa-envelope"></i> 邮箱 :  ${user.email}</li>
                        		 <#if user.roleNamesStr??>
				                     			<li><i class="fa fa-graduation-cap user-profile-icon"></i> 角色 :  ${user.roleNamesStr}</li>
				                  <#elseif user.groupNamesStr??>
				                     			<li><i class="fa fa-users user-profile-icon"></i> 组织 :  ${user.groupNamesStr}</li>
				                   </#if>
                      </ul>

                      <a class="btn btn-success" href="/user/message"><i class="fa fa-envelope-o m-right-xs"></i> 站内信 
										 					 <#if msgcount?? && msgcount gt 0>
										 							<span class="badge">${msgcount}</span>
										 					</#if>
									 					</a>
                      <br />

                      <ul class="list-unstyled user_data"><li></li>
                        <li>

                       
                          <p>个人基本信息完整度</p>
                          <div class="progress ">
											             <div id="progress-div" data-percent="${progress_count}%" class="inline middle no-margin progress progress-striped active">
												         	    <div id="progress-bar-div" class="progress-bar progress-bar-success" style="width:${progress_count}%">${progress_count}%</div>
												           </div>
                          </div>
                        </li>
                      </ul>


                    </div>
                    <div class="col-md-9 col-sm-9 col-xs-12">

                      <div class="profile_title">
                        <div class="col-md-6">
                          <h4>最近七天访问情况</h4>
                        </div>
                      </div>

                      <div class="x_panel">
                  <div class="x_content" >
                    <canvas id="barChart"></canvas>
                  </div>
                </div>

                      <div class="" role="tabpanel" data-example-id="togglable-tabs">
                        <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                          <li role="presentation" class="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">个人近况</a>
                          </li>
                          <li role="presentation" class=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">基本信息</a>
                          </li>
                          <li role="presentation" class=""><a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">修改密码</a>
                          </li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                          <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">

										 <ul class="list-unstyled timeline">
                  <#list ullist as ul>
                    <li>
                      <div class="block">
                        <div class="tags">
                          <a href="" class="tag">
                            <span>${ul.operType}</span>
                          </a>
                        </div>
                        <div class="block_content">
                          <div class="byline">
                            <span>${ul.operTime}</span>
                          </div>
                          <p class="excerpt">${ul.operContent}
                          </p>
                        </div>
                      </div>
                    </li>
                   </#list>
               </ul>

                          </div>
                          <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">

                           <form id="form_edit_profile" class="form-horizontal form-label-left" action="/user/edit/profile" method="post">    
														            <div class="form-group">
														                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">真实姓名 <span class="required">*</span>
														                 </label>
														                 <div class="col-md-6 col-sm-6 col-xs-12">
														                   <input type="text" class="form-control" id="username" name="name" placeholder="由6-20个字符组成"  maxlength="20" value="${user.name}"/>
														                   <span class="help-block" id="nameMessage"></span>
														                 </div>
														            </div>

																					<div class="form-group">
														                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="repassword">用户性别 &nbsp;
														                 </label>
														                 <div class="col-md-6 col-sm-6 col-xs-12">
															              <div class="btn-group" data-toggle="buttons">
															                <input type="hidden" id="sexval" name="sexval" value="${user.sex}">
															                <label id="sex_0" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
												                              	<input id="radio1" type="radio" name="sex" value="0" data-parsley-multiple="sex" <#if user.sex?? &&user.sex=='0' > checked="checked" </#if> /> &nbsp; 女性 &nbsp;
												                            </label>
												                            <label id="sex_1" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
												                              	<input id="radio2" type="radio" name="sex" value="1" data-parsley-multiple="sex" <#if user.sex?? &&user.sex=='1' > checked="checked" </#if> /> &nbsp; 男性 &nbsp;
												                            </label>
												                            <label id="sex_null" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
												                              	<input id="radio3" type="radio" name="sex" value="" data-parsley-multiple="sex" <#if user.sex??> <#else> checked="checked" </#if> /> &nbsp; 保密 &nbsp;
												                            </label>
											                          </div>
														                   <span class="help-block" id="repasswordMessage"></span>
														                 </div>
														            </div>

														            <div class="xdisplay_inputx form-group has-feedback">
														                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="repassword">出生日期 &nbsp;
														                 </label>

														                 <div class="col-md-6 col-sm-6 col-xs-12" >
														                   <input type="text" class="date-picker form-control " id="birthday" name="birthday" placeholder="选择一个日期 "   data-provide="datepicker"  maxlength="20" value="${user.birthday!""}"/>
														                   <span class="fa fa-calendar-o form-control-feedback right" aria-hidden="true"></span>
														                   <span class="help-block" id="birthdayMessage"></span>
														                 </div>
														            </div>

																						<div class="form-group">
														                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">手机号码 <span class="required">*</span>
														                 </label>
														                 <div class="col-md-6 col-sm-6 col-xs-12">
														                    <input type="text" class="form-control" name="phone" id="phone" maxlength="20" value="${user.phone!""}"/>
														                   <span class="help-block" id="phoneMessage"></span>
														                 </div>
														            </div>   

														            <div class="form-group">
														                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="repassword">身份证号 &nbsp;
														                 </label>
														                 <div class="col-md-6 col-sm-6 col-xs-12">
														                   <input type="text" class="form-control" id="idNumber" name="idNumber" maxlength="20" value="${user.idNumber!""}"/>
														                   <span class="help-block" id="idNumberMessage"></span>
														                 </div>
														            </div>

														            <div class="ln_solid"></div>
			
																					<div class="form-group">
																					 　　　　<label class="control-label col-md-3 col-sm-3 col-xs-12" > </label>
													                 <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
													                    <button id="resetbtn_1" type="reset" class="btn btn-default ">重 置</button>
													           						<button id="submitbtn_1" type="submit" class="btn btn-success">保 存</button>
													                 </div>
													               </div>
											            </form>

                          </div>
                          <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                            <form id="form_edit_password" class="form-horizontal form-label-left" action="/user/edit/password" method="post">

                          		<div class="form-group">
											                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="currentpassword">输入当前密码 <span class="required">*</span>
											                 </label>
											                 <div class="col-md-6 col-sm-6 col-xs-12">
											                   <input type="password" class="form-control" name="currentpassword" placeholder="由6-20个字符组成"  maxlength="20" />
											                   <span class="help-block" id="currentpasswordMessage"></span>
											                 </div>
											            </div>
											            
                          		<div class="form-group">
											                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">设置新密码 <span class="required">*</span>
											                 </label>
											                 <div class="col-md-6 col-sm-6 col-xs-12">
											                   <input type="password" class="form-control" name="password" placeholder="由6-20个字符组成"  maxlength="20"/>
											                   <span class="help-block" id="passwordMessage"></span>
											                 </div>
											            </div>
							                      
																	   <div class="form-group">
											                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="repassword">确认新密码 <span class="required">*</span>
											                 </label>
											                 <div class="col-md-6 col-sm-6 col-xs-12">
											                   <input type="password" class="form-control" name="repassword" placeholder="两次输入的密码要保持一致"    maxlength="20"/>
											                   <span class="help-block" id="repasswordMessage"></span>
											                 </div>
											            </div>
											            
											            
											          <div class="ln_solid"></div>
								
																					<div class="form-group">
																					 　　　　<label class="control-label col-md-3 col-sm-3 col-xs-12" > </label>
													                 <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
													                    <button id="resetbtn_2" type="reset" class="btn btn-default ">清 空</button>
													           						<button id="submitbtn_2" type="submit" class="btn btn-success">保 存</button>
													                 </div>
													               </div>
                          		</form>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

<script type="text/javascript" src="/js/layer.js"></script>
<script type="text/javascript" src="/js/layer-plugin.js"></script>
<script type="text/javascript" src="/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="/js/form.js"></script>
<script type="text/javascript" src="/js/chartjs/Chart.min.js"></script>
<script type="text/javascript">

$.get('/user/chart/pv').done(function(result) {
	var ctx = document.getElementById("barChart");
	ctx.height = 80;
	var lineChart = new Chart(ctx, {
		type : 'line',
		data : {
			labels : result.labels,
			datasets : [ {
				label : result.datasets[0].label,
				backgroundColor : "rgba(38, 185, 154, 0.31)",
				borderColor : "rgba(38, 185, 154, 0.7)",
				pointBorderColor : "rgba(38, 185, 154, 0.7)",
				pointBackgroundColor : "rgba(38, 185, 154, 0.7)",
				pointHoverBackgroundColor : "#fff",
				pointHoverBorderColor : "rgba(220,220,220,1)",
				pointBorderWidth : 1,
				data : result.datasets[0].data
			}]
		},
	});

});

function addprogressbar(){  
	var progress_count = 0;
	if ( $("#name").val() != "" ){ 
			progress_count = progress_count + 20;
	}
	if ( $("input[name='sex'][checked]").val() != "" && $("input[name='sex'][checked]").val() != undefined){ 
			progress_count = progress_count + 20;
	}
	if ( $("#birthday").val() != "" ){ 
			progress_count = progress_count + 20;
	}
	if ( $("#phone").val() != "" ){ 
			progress_count = progress_count + 20;
	}
	if ( $("#idNumber").val() != "" ){ 
			progress_count = progress_count + 20;
	}
	$("#progress-div").attr("data-percent",progress_count+"%");
	$("#progress-bar-div").width(progress_count+"%");
	$("#progress-bar-div").html(progress_count+"%");
	return progress_count;
}

//上传头像
$("#avatar").click(function(){
	 layer.open({
		  type: 2,
		  title: '上传头像',
		  skin: custom_skin,//边框
		  shadeClose: custom_shadeClose,//点击阴影也可以关闭窗口
		  area: custom_area,
		  offset: custom_offset,
		  content: ['/user/avatar', 'no']
		}); 
});

$("#avatar").mouseenter(function(){
	 layer.tips('点击可上传头像', '#avatar', {
		  tips: [1, '#26B99A'], //还可配置颜色
		  time: 60000
		});
});
$("#avatar").mouseleave(function(){
	 layer.closeAll("tips");
});

$.fn.datepicker.defaults.autoclose = true;
$.fn.datepicker.defaults.clearBtn = true;
$.fn.datepicker.defaults.language = "zh-CN";
$.fn.datepicker.defaults.todayHighlight = true;
$('#birthday').datepicker();

$(document).ready(function() {
  var sex = $('#sexval').val();
  if(sex == ''){
    $("#sex_null").addClass("active");
    $('input:radio[name=sex]')[2].checked = true; 
  }else if(sex == 1){
    $("#sex_1").addClass("active");
    $('input:radio[name=sex]')[1].checked = true;
  }else if(sex == 0){
    $("#sex_0").addClass("active");
    $('input:radio[name=sex]')[0].checked = true;
  }

  $('#resetbtn_1').click(function() {
      $("#form_edit_profile").data('bootstrapValidator').resetForm(true);
     });
  $("#form_edit_profile").bootstrapValidator({
      submitHandler: function(validator, form, submitButton) {
		      $.post(form.attr('action'), form.serialize(), function(result) {
	        		if(result){
	        			 alertSuccess();
	        			 addprogressbar();
	        			 $("#form_edit_profile").data('bootstrapValidator').resetForm(false);
	                      		}																																																
			     }, 'json');
                },
      fields: {
    	     name: {
             container: '#nameMessage',
             trigger: 'blur',
             validators: {
                 notEmpty: {message: '此处为必填项'},
                 stringLength: {min: 2,max: 6,message: '字符串长度必须在2-6之间'},
                 regexp: {regexp: /^[\u4e00-\u9fa5]+$/ ,message: '只能填写中文'}
			                  }
			          },
        phone: {
            container: '#phoneMessage',
            trigger: 'blur',
            validators: {
                //notEmpty: {message: '此处为必填项'  },
                regexp: {regexp: /^1[3|4|5|7|8][0-9]{9}$/ , message: '无效的电话号码'},
                remote: {url: '/user/validate?id=${user.id}&flag=phone&t='+(new Date()).getTime() ,message: '此手机号码已被其他人使用' }
                                      }
                      },
        idNumber: {
            container: '#idNumberMessage',
            trigger: 'blur',
            validators: {
                regexp: {regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/ , message: '格式不正确'}
		                        }
                    }
             }
   }).on('success.form.bv', function(e) {// 阻止默认事件提交
       e.preventDefault();
        });

  $('#resetbtn_2').click(function() {
      $("#form_edit_password").data('bootstrapValidator').resetForm(true);
     });
  $("#form_edit_password").bootstrapValidator({
      submitHandler: function(validator, form, submitButton) {
		      $.post(form.attr('action'), form.serialize(), function(result) {
	        		if(result){
	        			 alertSuccess();
	        			 $("#form_edit_password").data('bootstrapValidator').resetForm(false);
	                      		}																																			
			     }, 'json');
                },
      fields: {
    	  　　currentpassword: {
             container: '#currentpasswordMessage',
             trigger: 'blur',
             validators: {
                 notEmpty: {message: '此处为必填项'},
                 stringLength: {min: 6,max: 20,message: '字符串长度必须在6-20之间'},
                 regexp: {regexp: /^[\w.]{6,20}$/ , message: '只能填写数字、英文和特殊符号'},
                 remote: {url: '/user/validate/password' ,message: '当前用户密码输入错误' }
			                  }
			          },
			   password: {
           container: '#passwordMessage',
           trigger: 'blur',
           validators: {
               notEmpty: {message: '此处为必填项'},
               stringLength: {min: 6,max: 20,message: '字符串长度必须在6-20之间'},
               regexp: {regexp: /^[\w.]{6,20}$/ , message: '只能填写数字、英文和特殊符号'}
 			                 }
		     	 	 },
			  	repassword: {
           container: '#repasswordMessage',
           trigger: 'blur',
           validators: {
          　　　notEmpty: {message: '此处为必填项'},
          　　　identical: { field: 'password',message: '两次输入的新密码要保持一致'}
            　　　}
	 			     },
             }
   }).on('success.form.bv', function(e) {// 阻止默认事件提交
       e.preventDefault();
        });

});
</script>  
</body>
</html>