<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
</head>
<body id="body" style="background:#F7F7F7;" >
<form id="form_edit" class="form-horizontal form-label-left" action="/role/edit" method="post">
<input type="hidden" id="id" name="id" value="${role.id}">
<br />

						<div class="form-group table ">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="loginName">角色名称 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="text" class="form-control" name="name" placeholder="由1-20个字符组成"   maxlength="20" value="${role.name}"/> 
                   <span class="help-block" id="nameMessage"></span>
                 </div>
            </div>

            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="parentId">上级角色  &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                  <select id="parentId" name="parentId" class="form-control" >
                   <option value="0" id="0" >无</option>
											<#list roleList as cur>
															<option value="${cur['id']}" <#if (role.parentId==cur.id)> selected="selected"  </#if> > ${cur['name']} </option>
								  	    		 </#list>
										 </select>
                 </div>
                 <span class="help-block"></span>
            </div>

            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">角色描述 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <textarea class="form-control" name="description" rows="3" maxlength="100">${role['description']}</textarea>
                 </div>
                 <span class="help-block"></span>
            </div>


					<div class="ln_solid"></div>
								
								<div class="form-group">
								 　　　　<label class="control-label col-md-3 col-sm-3 col-xs-12" > </label>
                 <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                    <button id="resetbtn" type="reset" class="btn btn-default ">重 置</button>
           						<button id="submitbtn" type="submit" class="btn btn-success">保 存</button>
                 </div>
               </div>


</form>
<script type="text/javascript" src="/js/form.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	 var form_id = "#form_edit";
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
                 remote: {url: '/role/validate?id=${role.id}&flag=name&t='+(new Date()).getTime() , message: '此角色名称已被使用' }
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