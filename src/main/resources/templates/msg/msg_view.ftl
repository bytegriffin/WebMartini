<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/animate.min.css" />
</head>
<body id="body" style="background:#F7F7F7;" class="animated fadeInRigth">
<div class="page-title">
              <div class="title_left">
                  <ol class="breadcrumb">
					  <li><a href="javascript:void(0)">系统管理</a></li>
					  <li class="active">站内信</li>
					</ol>
              </div>
            </div>

<div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2><a href="javascript:history.back()">&lt; 返回</a></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li class="pull-right"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
		<div class="x_content">
				<table class="table table-hover table-bordered">
                      <thead>
                        <tr>
                          <th>发送人 </th>
                          <th>发送时间 </th>
                          <th>保存时间 </th>
                          <th>状态</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td scope="row">${message.loginName}</td>
                          <td>${message.sendTime}</td>
                          <td>${message.saveTime}</td>
                          <td>${message.status}</td>
                        </tr>
                      </tbody>
                    </table>
                    
                    <table class="table table-hover table-bordered">
															<tbody>
                        <tr>
                          <th nowrap>消息标题  </th>
                          <td>${message.title} </td>
                        </tr>
                        <tr>
                          <th nowrap>消息内容 </th>
                          <td>${message.content}</td>
                        </tr>
                        <#if message.attachment1??><tr><th nowrap>消息附件1  </th><td> <a href="javascript:download1()"  class="green">下载</a> </td></tr></#if>
                        <#if message.attachment2??><tr><th nowrap>消息附件2  </th><td><a href="javascript:download2()" class="green">下载</a> </td></tr></#if>
                        <#if message.attachment3??><tr><th nowrap>消息附件3  </th><td><a href="javascript:download3()" class="green">下载</a>  </td></tr></#if>
                      </tbody>
                    </table>

							 					</div>
							 					</div>
					</div>

<script type="text/javascript" src="${request.contextPath}/js/form.js"></script>
<script type="text/javascript">
function download1(){
	 location.href="${request.contextPath}/message/download?url=${message.attachment1}";
}
function download2(){
	 location.href="${request.contextPath}/message/download?url=${message.attachment2}";
}
function download3(){
	 location.href="${request.contextPath}/message/download?url=${message.attachment3}";
}
</script>  
</body>
</html>