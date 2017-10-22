<#macro error>
		<link rel="stylesheet" href="${request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${request.contextPath}/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${request.contextPath}/css/nprogress.css" />
		<link rel="stylesheet" href="${request.contextPath}/css/custom.css" />
<!--======================================CSSjs　分割线===================================================== -->
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
		<script type="text/javascript" src="${request.contextPath}/js/nprogress.js"></script>
		<script type="text/javascript" src="${request.contextPath}/js/custom.js"></script>
</#macro>
