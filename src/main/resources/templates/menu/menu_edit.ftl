<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
</head>
<body id="body" style="background:#F7F7F7;" >
<form id="form_edit" class="form-horizontal form-label-left"  action="${request.contextPath}/menu/edit" method="post">
<input type="hidden" id="id" name="id" value="${menu.id}">
<br />

								<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">菜单名称 <span class="required">*</span>
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <div class="input-group">
                       <input type="text" class="form-control" name="name" placeholder="由1-20个字符组成"   maxlength="20" value="${menu['name']}"/> 
				                 <span class="input-group-btn">
										            <label >菜单图标</label> <button id="icon" name="icon" class="btn btn-default" role="iconpicker" data-iconset="fontawesome" <% if(isNotEmpty(menu.icon)) {%> data-icon="${menu['icon']}" <%}%> ></button>
										        </span> 
										    </div>
                   <span class="help-block" id="nameMessage"></span>
                 </div>
            </div>

            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="url">菜单链接 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="text" class="form-control" name="url" value="${menu.url}" 　placeholder="相对路径"  maxlength="1024"/>
                   <span class="help-block" id="urlMessage"></span>
                 </div>
            </div>

           	<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="parentId">上级菜单 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <select id="parentId" name="parentId" class="form-control" >
                      <option value="0" id="0" >无</option>
							<#list menuList as cur>
										<option value="${cur.id}" id="${cur.levels}" <#if (cur.id == menu.parentId)> selected="selected"  </#if> > ${cur.name}</option>
	  	    				</#list> 
					 </select>
					 <span class="help-block"></span>
                 </div>
            </div>
            
            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="levels">本菜单层级 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                    <span id="levels-text" class="form-control">
		                 		<#if (menu.levels == 1) >顶级菜单</#if>
		                 		<#if (menu.levels == 2) >二级菜单</#if>
		                 		<#if (menu.levels == 3) >三级菜单</#if>
		                 		<#if (menu.levels == 4) >四级菜单</#if>
		                 		<#if (menu.levels == 5) >五级菜单</#if>
		                 		<#if (menu.levels == 6) >六级菜单</#if>
		                 		<#if (menu.levels == 7) >七级菜单</#if>
		                 		<#if (menu.levels == 8) >八级菜单</#if>
		                 		<#if (menu.levels == 9) >九级菜单</#if>
		                 		<#if (menu.levels == 10) >十级菜单</#if>
						 </span>
			        <input type="hidden" id="levels" name="levels" value="${menu.levels}"/> 
			        		<span class="help-block"></span>
                 </div>
            </div>
 
								<div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="status">菜单状态 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input id="status" name="status" data-on-color='success' data-off-color='warning' data-on-text='启用'  data-off-text='禁用'  type='checkbox' <#if (menu.status == 1) > checked </#if>　> 
                   <span class="help-block" ></span>
                 </div>
            </div>

            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="priority">菜单权重 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <input type="number" class="form-control" id="priority" name="priority" placeholder="必须为正整数"   maxlength="20" value="${menu.priority}"/>
                   <span class="help-block" id="priorityMessage"></span>
                 </div>
            </div>
            
            <div class="form-group">
                 <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">菜单描述 &nbsp;
                 </label>
                 <div class="col-md-6 col-sm-6 col-xs-12">
                   <textarea class="form-control" name="description" rows="3" maxlength="100">${menu.description}</textarea>
                   <span class="help-block"></span>
                 </div>
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

<script type="text/javascript" src="${request.contextPath}/js/form.js"></script>
<script type="text/javascript">
//启动切换按钮
$('#status').bootstrapSwitch();
//启动图标选择
$('#icon').iconpicker({ 
    align: 'center', // Only in div tag
    cols: 4,
    footer: true,
    header: true,
    icon: "${menu['icon']}",//默认
    iconset: 'fontawesome',   
    labelHeader: '{0} of {1} pages',
    labelFooter: '{0} - {1} of {2} icons',
    placement: 'bottom', // Only in button tag
    rows: 4,
    search: true,
    searchText: 'Search',
    selectedClass: 'btn-success',
    unselectedClass: ''
});

$('#parentId').change(function(){
   var levels = $("#parentId option:selected").attr('id');//id属性存放的是levels
   if(levels == 0){value = '顶级菜单'}
   if(levels == 1){value = '二级菜单'}
   if(levels == 2){value = '三级菜单'}
   if(levels == 3){value = '四级菜单'}
   if(levels == 4){value = '五级菜单'}
   if(levels == 5){value = '六级菜单'}
   if(levels == 6){value = '七级菜单'}
   if(levels == 7){value = '八级菜单'}
   if(levels == 8){value = '九级菜单'}
   if(levels == 9){value = '十级菜单'}
		$("#levels-text").text(value);
		$("#levels").val(parseInt(levels)+1);
});

$(document).ready(function() {
	 var form_id = "#form_edit";
  $('#resetbtn').click(function() {
      $(form_id).data('bootstrapValidator').resetForm(true);
      $("#msg").hide();
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
         name: {
             container: '#nameMessage',
             trigger: 'blur',
             validators: {
                 notEmpty: {message: '此处为必填项'},
                 regexp: {regexp: /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/,message: '只能填写数字、英文、中文和下划线'},
                 remote: {url: '${request.contextPath}/menu/validate?id=${menu.id}&flag=name&t='+(new Date()).getTime() , message: '此菜单名称已被使用' }
			                  }
			          },
			    url: {
             container: '#urlMessage',
             trigger: 'blur',
             validators: {
                 //notEmpty: {message: '此处为必填项'},
                 remote: {url: '${request.contextPath}/menu/validate?id=${menu.id}&flag=url&t='+(new Date()).getTime() , message: '此菜单链接已被使用' }
			                  }
			          },
			  priority: {
          container: '#priorityMessage',
          trigger: 'blur',
          validators: {
        	  		regexp: {regexp: /^[0-9]*[1-9][0-9]*$/,message: '只能填写正整数'}
              //remote: {url: '${request.contextPath}/menu/validate?id=${menu.id}&flag=priority&parentId='+$("#parentId option:selected").val()+'&t='+(new Date()).getTime() , message: '此菜单权重已被其它同层级的菜单所使用' }
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