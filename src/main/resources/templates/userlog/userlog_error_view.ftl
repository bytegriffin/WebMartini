<#include "/layout/header.ftl">  
<#include "/layout/form.ftl">  
<@header />
<@form />
</head>
<body id="body" style="background:#F7F7F7;" class="animated fadeInRigth">
<div class="page-title">
              <div class="title_left">
                  <ol class="breadcrumb">
												  <li><a href="javascript:void(0)">系统管理</a></li>
												  <li class="active">系统异常</li>
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
                          <th>操作时间 </th>
                          <th>操作用户 </th>
                          <th>操作链接 </th>
                          <th>操作类型 </th>
                          <th>操作内容 </th>
                          <th>HTTP状态码 </th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td scope="row">${userLog.operTime}</td>
                          <td>${userLog.userName}</td>
                          <td>${userLog.url}</td>
                          <td>${userLog.operType}</td>
                          <td>${userLog.operContent}</td>
                          <td>${userLog.httpStatus}</td>
                        </tr>
                      </tbody>
                    </table>
                    
                    <table class="table table-hover table-bordered">
															<tbody>
                        <tr>
                          <th nowrap>操作方法类名  </th>
                          <td>${userLog.controller} </td>
                        </tr>
                        <tr>
                          <th nowrap>异常信息</th>
                          <td>${userLog.exception}</td>
                        </tr>
                      </tbody>
                    </table>
							 						
							 					</div>
							 					</div>
					</div>

<script type="text/javascript" src="/js/form.js"></script>
<script type="text/javascript">

</script>  
</body>
</html>