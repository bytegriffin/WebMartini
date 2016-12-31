<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
</head>
<body id="body" style="background:#F7F7F7;" >
<form id="form_new" class="form-horizontal form-label-left" action="/group/add" method="post">
 <br />

								<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="loginName">组织名称 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="text" class="form-control" name="name" placeholder="由1-20个字符组成"   maxlength="20"/> 
                   <span class="help-block" id="nameMessage"></span>
                 </div>
            </div>

						<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="parentId">上级组织  &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <select id="parentId" name="parentId" class="form-control" >
		                   <option value="0" id="0" >无</option>
								  <#list groups as cur>
									<option value="${cur.id}"> ${cur.name} </option>
								</#list>
							</select>
                 </div>
                 <span class="help-block" id="parentIdMessage"></span>
            </div>

				<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">组织描述 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <textarea class="form-control" name="description" rows="3" maxlength="100"></textarea>
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
<script type="text/javascript" src="/js/form.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	 var form_id = "#form_new";
  $('#resetbtn').click(function() {
      $(form_id).data('bootstrapValidator').resetForm(true);
     });
  $(form_id).bootstrapValidator({
      submitHandler: function(validator, form, submitButton) {
		      $.post(form.attr('action'), form.serialize(), function(result) {
	        		if(result){
	        		   parent.$.fn.zTree.getZTreeObj("treeDemo").reAsyncChildNodes(null, "refresh");
	        		   parent.dtTable.draw(false);//保持当前分页信息
	        			 $(form_id).data('bootstrapValidator').resetForm(false);
	        			 var index = parent.layer.getFrameIndex(window.name); 
	        			 parent.layer.close(index);
	                      		}																																																
			     }, 'json');
                },
      fields: {
         name: {
             container: '#nameMessage',
             trigger: 'blur',
             validators: {
                 notEmpty: {message: '此处为必填项'},
                 regexp: {regexp: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/,message: '只能填写数字、英文、中文和下划线'},
                 remote: {url: '/group/validate?flag=name&t='+(new Date()).getTime() , message: '此组织名称已被使用' }
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