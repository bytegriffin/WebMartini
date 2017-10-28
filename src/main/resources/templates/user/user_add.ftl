<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
</head>
<body id="body" style="background:#F7F7F7;" >

<form id="form_new" class="form-horizontal form-label-left"  action="${request.contextPath}/user/add" method="post">
            <br />
            
								<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="loginName">登录名称 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="text" class="form-control" name="loginName" placeholder="由4-20个字符组成"   maxlength="20"/> 
                   <span class="help-block" id="loginNameMessage"></span>
                 </div>
            </div>
            
            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">设置密码 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="password" class="form-control" name="password" placeholder="由6-20个字符组成"  maxlength="20"/>
                   <span class="help-block" id="passwordMessage"></span>
                 </div>
            </div>
                      
							<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="repassword">确认密码 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="password" class="form-control" name="repassword" placeholder="两次输入的密码要保持一致"    maxlength="20"/>
                   <span class="help-block" id="repasswordMessage"></span>
                 </div>
            </div>

							<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="role">所属角色  &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <select id="role" name="role" class="form-control" multiple="multiple" data-placeholder="选择一个或多个角色">
                       <#list roles as cur>
							<option value="${cur.id}"> ${cur.name} </option>
						</#list>
				</select>
                 </div>
            </div>
            
            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="group">所属组织  &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <select id="group" name="group" class="form-control" multiple="multiple"　data-placeholder="选择一个或多个组织">
													   <#list groups as cur>
																	<option value="${cur.id}"> ${cur.name} </option>
										  	    			</#list>
												 </select>
                 </div>
            </div>

				 <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">邮 箱 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="text" class="form-control" name="email" placeholder="国内常用邮箱"  maxlength="20"/>
                   <span class="help-block" id="emailMessage"></span>
                 </div>
            </div>

								<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="phone">手机号码  &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                    <input type="text" class="form-control" name="phone" maxlength="20"/>
                   <span class="help-block" id="phoneMessage"></span>
                 </div>
            </div>
            
								<div class="ln_solid"></div>
								
								<div class="form-group">
								 　　　　<label class="control-label col-md-3 col-sm-3 col-xs-12" > </label>
                 <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                    <button id="resetbtn" type="reset" class="btn btn-default ">清 空</button>
           						<button id="submitbtn" type="submit" class="btn btn-success">保 存</button>
                 </div>
               </div>

      
       </form>
<script type="text/javascript" src="${request.contextPath}/js/form.js"></script>
<script type="text/javascript">
$("#role").select2({placeholder:"选择一个或多个角色"});
$("#group").select2({placeholder:"选择一个或多个组织"});
$('#resetbtn').click(function() {
  	$("#role > option").removeAttr("selected");
   $("#role").trigger("change");
   $("#group > option").removeAttr("selected");
   $("#group").trigger("change");   
});
$(document).ready(function() {
	 var form_id = "#form_new";
  $('#resetbtn').click(function() {
      $(form_id).data('bootstrapValidator').resetForm(true);
     });
  $(form_id).bootstrapValidator({
      submitHandler: function(validator, form, submitButton) {
		      $.post(form.attr('action'), form.serialize(), function(result) {
	        		if(result){
	        			 parent.dtTable.draw(false);//保持当前分页信息
	        			 $(form_id).data('bootstrapValidator').resetForm(false);
	        			 var index = parent.layer.getFrameIndex(window.name); 
	        			 parent.layer.close(index);
	                      		}																																																
			     }, 'json');
                },
      fields: {
         loginName: {
            container: '#loginNameMessage',
            trigger: 'blur',
            validators: {
               notEmpty: {message: '此处为必填项'},
               stringLength: {min: 4,max: 20,message: '字符串长度必须在4-20之间'},
               regexp: {regexp: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/,message: '只能填写数字、英文、中文和下划线'},
               remote: {url: '${request.contextPath}/user/validate?flag=loginName&t='+(new Date()).getTime() ,message: '此登陆名已被其他人使用' }
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
             　　　identical: { field: 'password',message: '两次输入的密码要保持一致'}
             　　　　　　}
			          },
         email: {
            container: '#emailMessage',
            trigger: 'blur',
            validators: {
                 notEmpty: {message: '此处为必填项'  },
                 emailAddress: { message: '无效的邮件地址'},
                 remote: {url: '${request.contextPath}/user/validate?flag=email&t='+(new Date()).getTime() ,message: '此邮箱已被其他人使用' }
                                    }
                         },
        phone: {
            container: '#phoneMessage',
            trigger: 'blur',
            validators: {
                //notEmpty: {message: '此处为必填项'  },
                regexp: {regexp: /^1[3|4|5|7|8][0-9]{9}$/ , message: '无效的电话号码'},
                remote: {url: '${request.contextPath}/user/validate?flag=phone&t='+(new Date()).getTime() ,message: '此手机号码已被其他人使用' }
                                      }
                          }
             }
   }).on('success.form.bv', function(e) {// 阻止默认事件提交
       e.preventDefault();
        });
});

</script>  
</body>
</html>