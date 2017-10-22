<#include "/layout/header.ftl">  
<#include "/layout/menu.ftl">  
<#include "/layout/footer.ftl">  

<@header />
<link rel="stylesheet" href="${request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${request.contextPath}/css/font-awesome.min.css">
<link rel="stylesheet" href="${request.contextPath}/css/custom.css" />
<link rel="stylesheet" href="${request.contextPath}/css/nprogress.css" />
<link rel="stylesheet" href="${request.contextPath}/css/animate.min.css" />
</head>

<body id="body" class="nav-md"  oncontextmenu="return false" >
    <div class="container body">
      <div class="main_container">

<@menu data=menus/>

		<div class="top_nav">
          <div class="nav_menu">
            <nav class="" role="navigation">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="mail_list_column">
                  <a href="javascript:;" class="user-profile dropdown-toggle " data-toggle="dropdown" aria-expanded="false">
                    <#if cur.avatar??> 
						<img src="${request.contextPath}/${cur.avatar}" id="avatar_samll" alt="头像"  />
					<#else>
						<img src="${request.contextPath}/images/avatar.png" id="avatar_samll" alt="头像"  />
					</#if>
                    ${user.loginName}
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="javascript:void(0);" onclick="loadprofile();">&nbsp;个人设置</a></li>
                    <li><a href="logout"><i class="fa fa-sign-out pull-right"></i>&nbsp;登出</a></li>
                  </ul>
                </li>

               <li role="presentation" class="dropdown mail_list_column">

		                  <a id="msgcount" href="javascript:void(0);" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false"></a>

                  <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">

                   <#list UserMessageList as cur>
	                    <li>
	                      <a onclick="viewmsg(${cur.id});" >
	                        <span class="image">
	                         		<#if cur.avatar??> 
											<img src="${request.contextPath}/${cur.avatar}"  alt="头像"  />
									<#else>
											<img src="${request.contextPath}/images/avatar.png" alt="头像"  />
									</#if>
	                        </span>
	                        <span>
	                          <span>${cur.loginName}</span>
	                          <span class="time">${cur.sendTime}</span>
	                        </span>
	                        <span class="message">
	                          ${cur.title} <br />
	                          ${cur.content}
	                        </span>
	                      </a>
	                    </li>
                    </#list>
                    <li>
                      <div class="text-center">
                          <#if UserMessageList?? && UserMessageList?size gt 0><a onclick="viewallmsg();"><strong>查看所有站内信</strong><i class="fa fa-angle-right"></i> </a>
                          <#else><a ><strong>没有任何消息</strong> </a></#if>
                      </div>
                    </li>
                  </ul>
                </li>
                
                <li role="presentation" class="dropdown mail_list_column">
                  <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                    <i class="fa fa-bell-o animated swing"></i>
                    <span class="badge bg-green">3</span>
                  </a>
                  <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                 								
                    <li>
                      <a>
                        <span class="image"></span>
                        <span>
                          <span><div id="state" name="state">d</div> 	John Smith</span>
                          <span class="time">3 mins ago</span>
                        </span>
                        <span class="message">
                          Film festivals used to be do-or-die moments for movie makers. They were where...
                        </span>
                      </a>
                    </li>
                    <li>
                      <div class="text-center">
                        <a>
                          <strong>See All Alerts</strong>
                          <i class="fa fa-angle-right"></i>
                        </a>
                      </div>
                    </li>
                  </ul>
                </li>

              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

		<div class="right_col" role="main">
			<iframe name="mainFrame" id="mainFrame" src="${request.contextPath}/user/dashboard" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" style="padding: 0px; width: 100%; height: 1000px;" class="nav-md footer_fixed"></iframe>
		</div>

<@footer /> 

	</div>
</div>

<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='${request.contextPath}/js/jquery.min.js'>"	+ "<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${request.contextPath}/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript" src="${request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/custom.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/nprogress.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/jquery.nicescroll.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/form.js"></script>

<script type="text/javascript">

NProgress.configure({ ease: 'ease', speed: 1000, showSpinner: false });
NProgress.start();

var firstmenu = $($("li[id^=menuid_]")[0]);
firstmenu.attr("class","active");
var fmid = "";
var mid = "";
NProgress.done(); 

loadcount();

function loadcount(){
	$.ajax({
	    type:"get",
	    url:"${request.contextPath}/user/message/count?v="+Math.random(),
	    cache:false,
	    success:function(msg){
	 	     if(msg > 0){
	 	    	   $("#msgcount").empty();
	 		      $("#msgcount").append("<i class='fa fa-envelope-o animated flash'></i> <span class='badge bg-blue'>"+msg+"</span>");
	 		 	  }else{
	 		 				$("#msgcount").empty();
	 		 		   $("#msgcount").append("<i class='fa fa-envelope-o'></i>");
	 		 	     }
	          }
      }); 
}

function loadframe(id,fid,url){
	  NProgress.start();
		firstmenu.removeClass();
		if(id != mid){
			$("#menuid_"+mid).removeClass();
			mid = id;
		}
		if(fid != fmid){
			$("#menuid_"+fmid).removeClass();
			fmid = fid;
		}
		if(fid != 0){
			$("#menuid_"+fid).attr("class","active");
		}
		$("#menuid_"+id).attr("class","current-page");
		$("#mainFrame").attr("src",url);
		$("#mainFrame").attr("class","animated fadeIn");
		NProgress.done();
}

function loadprofile(){
	  NProgress.start();
		$("#mainFrame").attr("src","${request.contextPath}/user/profile");
		$("#mainFrame").attr("class","animated fadeIn");
		NProgress.done();
}

function viewallmsg(){
	  NProgress.start();
		$("#mainFrame").attr("src","${request.contextPath}/user/message");
		$("#mainFrame").attr("class","animated fadeIn");
		NProgress.done();
}

function viewmsg(id){
	  NProgress.start();
		$("#mainFrame").attr("src","${request.contextPath}/user/message/view/"+id);
		$("#mainFrame").attr("class","animated fadeIn");
		NProgress.done();
}

$('#mainFrame').niceScroll({ 
    cursorcolor: "#7F7F7F",//#CC0071 光标颜色 
    cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0 
    touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备 
    cursorwidth: "5px", //像素光标的宽度 
    cursorborder: "0", // 游标边框css定义 
    cursorborderradius: "5px",//以像素为光标边界半径 
    autohidemode: "cursor" //是否隐藏滚动条 
});

</script>
</body>
</html>