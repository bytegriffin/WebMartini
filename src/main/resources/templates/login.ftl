<#include "/layout/header.ftl">  
<@header />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/nprogress.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/animate.min.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/icheck/green.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/layer.css" />
<link rel="stylesheet" type="text/css" href="${request.contextPath}/css/custom.css" />
</head>

<body class="login">

<div class="login_wrapper">
   <div id="login_form" class="animate fadeInLeft">
      <section class="login_content">
         <form id="form1" role="form" action="login" method="post" class="login-form form-horizontal form-label-left">
            <h1>WebMartini管理系统</h1>

            <div id="username_div" class="form-group col-md-12 col-sm-12 col-xs-12">
                 <input	type="text" id="username" name="username" placeholder="请输入登陆名/邮箱"	class="form-control " id="username" value="${username!''}" autofocus="autofocus" required>
                 <span class="fa fa-user form-control-feedback right" aria-hidden="true"></span>	
            </div>

            <div id="password_div" class="form-group col-md-12 col-sm-12 col-xs-12">
									  <input	type="password" id="password" name="password" placeholder="请输入密码"	class="form-control" id="password" value="${password!''}" required>
									  <span class="fa fa-lock form-control-feedback right" aria-hidden="true"></span>	
								</div>

								<div class="form-group col-md-12 col-sm-12 col-xs-12">
									  <input	type="text" id="verifycode" name="verifycode" placeholder="请输入图片验证码" 	 class="form-control" data-min="4" data-max="4" maxlength="4" size="4" style="width:70%;float:left" required>
									  <img id="verifyCodeImage" style="cursor:pointer" title="刷新验证码" alt="验证码"   onclick="reloadVerifyCode();return false;" src="login/getVerifyCodeImage?flag=login" />
								</div>
								<br />

								<div class="form-group col-md-12 col-sm-12 col-xs-12">
									  <div class="col-sm-5 "><input	type="checkbox" class="icheck" id="rememberMe" name="rememberMe">
									   <a id="freelogin" href="#">十天内免登录</a></div>
									  <div class="col-sm-4 pull-right"><span class="pull-right"><a id="forget_password" href="javascript:void(0)">忘记密码?</a></span></div>
								</div>

								<div id="validate_div" class="form-group col-md-12 col-sm-12  col-xs-12">
										<span class="help-block" id="validateMessage">${message!''}</span>
								</div>

            <div class="form-group col-md-12 col-sm-12  col-xs-12 text-center"> 
               <button id="loginbtn" type="submit" class="btn btn-default">登录</button>
            </div>

								<div class="clearfix"></div>

            <div class="separator">
                <div>
                  <p>©2016 All Rights Reserved.</p>
                </div>
            </div>

            </form>
          </section>
        </div>
        
    <div id="forget_form" class="animate fadeInLeft " >
          <section class="login_content">
            <form id="form2" role="form" action="login?type=forget" method="post" class="login-form">
              <h1>WebMartini后台管理系统</h1>

	            <div style="height:40px">
									  <a id="back" class="pull-left" href="javascript:void(0)"> &lt; 返回  </a>
									</div>

									<div class="form-group col-md-12 col-sm-12 col-xs-12">
										 <input	type="email" name="email" placeholder="请输入邮箱"	 class=" form-control" id="email" value="${email!''}" autofocus="autofocus" required>
										 <span class="fa fa-envelope form-control-feedback right" aria-hidden="true"></span>	
									</div>

								 <div class="form-group col-md-12 col-sm-12 col-xs-12">
									  <input	type="text" id="forgetverifycode" name="forgetverifycode" placeholder="请输入图片验证码" 	 class="form-control" data-min="4" data-max="4" maxlength="4" size="4" style="width:70%;float:left" required>
									  <img id="forgetVerifyCodeImage" style="cursor:pointer" title="刷新验证码" alt="验证码"   onclick="reloadForgetVerifyCode();return false;" src="login/getVerifyCodeImage?flag=forget" />
								 </div>
								 
							<div id="forget_validate_div" class="form-group col-md-12 col-sm-12  col-xs-12">
										<span class="help-block" id="forgetValidateMessage">${forget_message!''}</span>
								</div>
								
								 <div class="col-md-12 col-sm-12  col-xs-12 text-center"> 
               <button id="sendbtn" type="submit" class="btn btn-default">发送</button>
             </div>

              <div class="clearfix"></div>

              <div class="separator">
                <div>
                  <p>©2016 All Rights Reserved.</p>
                </div>
              </div>
            </form>
          </section>
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
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
<script src="${request.contextPath}/js/nprogress.js"></script>
<script src="${request.contextPath}/js/jquery.backstretch.min.js"></script>
<script src="${request.contextPath}/js/icheck.min.js"></script>
<script src="${request.contextPath}/js/jquery-html5Validate.js"></script>
<script src="${request.contextPath}/js/layer.js"></script>
<script src="${request.contextPath}/js/jquery.placeholder.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	  NProgress.configure({ ease: 'ease', speed: 1000, showSpinner: false });
	  NProgress.start();
	  $('input').placeholder();
	  //$(".container").css({ opacity: .8 });  //设置透明度
	  /* $.backstretch(["${request.contextPath}/images/login-1.jpg",
	                 "${request.contextPath}/images/login-2.jpg",
	                 "${request.contextPath}/images/login-3.jpg",
	                 "${request.contextPath}/images/login-4.jpg",
	                 "${request.contextPath}/images/login-5.jpg",
	                 "${request.contextPath}/images/login-6.jpg"],{duration:4000}); //元素背景，切换现实 */
	  $("#forget_form").hide();
	  $("#login_form").show();
	  reloadVerifyCode();
	  reloadForgetVerifyCode();
	  NProgress.done();
});

$("#freelogin").mouseenter(function(){
	 layer.tips('为了您的信息安全，请不要在网吧或公用电脑上使用此功能！', '#freelogin', {
		  tips: [1, '#26B99A'], //还可配置颜色
		  time: 60000
		});
});

$("#freelogin").mouseleave(function(){
	 layer.closeAll("tips");
});

function reloadVerifyCode(){
	$("#verifyCodeImage").attr('src', 'login/getVerifyCodeImage?flag=login&v='+Math.random());
 }  
 
function reloadForgetVerifyCode(){
	$("#forgetVerifyCodeImage").attr('src', 'login/getVerifyCodeImage?flag=forget&v='+Math.random());
 }  
 
$('input.icheck').iCheck({
	  checkboxClass: 'icheckbox_flat-green',
	  radioClass: 'iradio_flat-green'
});

$('#forget_password').click(function(){
	 $("#login_form").hide();
   $("#forget_form").show();
});

$('#back').click(function(){
/* 	 $("#forget_form").hide();
  $("#login_form").show(); */
  location.href="login";
});

$('#username').keydown(function(){
	 $("#validateMessage").html("");
});

$('#password').keydown(function(){
	 $("#validateMessage").html("");
});

$("#form1").html5Validate(function() {
    this.submit();
    	}, {
		 validate: function() {
			 var verifycodeflag = false;
			 $.ajax({
				 url : "login/validate/verifycode?flag=login",
				 type : "post",
				 async : false,
				 data : {verifycode : $("#verifycode").val() },
				 dataType : "json",
				 success : function(result) {
						 if (!result.valid) {
							   $("#verifycode").testRemind("图片验证码错误");
							   verifycodeflag = false;
					   }else{
						    verifycodeflag = true;	
					    	}
				    },
				 error : function() {
					 $("#verifycode").testRemind("网络原因，图片验证码没有验证成功，您可以稍后重试！");
				   }
			 });
				if(!verifycodeflag){
					 $("#verifycode").testRemind("图片验证码错误");
					 return false;
				  }
				return true;
		}
});

var text = $("#validateMessage").text();
if ('' != text) {
	$("#validate_div").addClass("has-error");
}


$("#form2").html5Validate(function() {
    this.submit();
    	}, {
		 validate: function() {
			 var forgetverifycodeflag = false;
			 $.ajax({
				 url : "login/validate/verifycode?flag=forget",
				 type : "post",
				 async : false,
				 data : {forgetverifycode : $("#forgetverifycode").val() },
				 dataType : "json",
				 success : function(result) {
						 if (!result.valid) {
							   $("#forgetverifycode").testRemind("图片验证码错误");
							   forgetverifycodeflag = false;
					   }else{
						   forgetverifycodeflag = true;	
					    	}
				    },
				 error : function() {
					 $("#forgetverifycode").testRemind("网络原因，图片验证码没有验证成功，您可以稍后重试！");
				   }
			 });
				if(!forgetverifycodeflag){
					 $("#forgetverifycode").testRemind("图片验证码错误");
					 return false;
				  }
				return true;
		}
});

$('#email').keydown(function(){
	 $("#forgetValidateMessage").html("");
});

$(document).ready(function(){
	var forgettext = $("#forgetValidateMessage").text();
	if ('' != forgettext) {
		$("#login_form").hide();
	  $("#forget_form").show();
		if(forgettext.indexOf("成功") != -1){
			$("#forget_validate_div").addClass("has-success");
		}else{
			$("#forget_validate_div").addClass("has-error");
		}
	}
});

</script>

</body>

</html>