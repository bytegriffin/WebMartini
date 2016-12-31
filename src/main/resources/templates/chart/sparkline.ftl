<#include "/layout/header.ftl">  
<#include "/layout/chart.ftl">  
<@header />
<@chart />
</head>
<body style="background: #F7F7F7;" class="animated fadeIn">
	<div class="page-title">
		<div class="title_left">
			<ol class="breadcrumb">
				<li><a href="javascript:void(0)">报表管理</a></li>
				<li class="active">Flot</li>
			</ol>
		</div>
	</div>

	<div class="clearfix"></div>

						<div class="col-md-6 col-sm-6 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Area Line Graph <small>Sessions</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li class="pull-right"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div id="dlc" class="x_content">
                    <div class="tiles">
                        <div class="col-md-4 tile">
                          <span>Total Sessions</span>
                          <h2>231,809</h2>
                          <span class="sparkline11 graph" style="height: 160px;">
                                          <canvas width="200" height="60" style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
                                      </span>
                        </div>
                        <div class="col-md-4 tile">
                          <span>Total Revenue</span>
                          <h2>$231,809</h2>
                          <span class="sparkline22 graph" style="height: 160px;">
                                          <canvas width="200" height="60" style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
                                      </span>
                        </div>
                        <div class="col-md-4 tile">
                          <span>Total Sessions</span>
                          <h2>231,809</h2>
                          <span class="sparkline33 graph" style="height: 160px;">
                              <canvas width="200" height="60" style="display: inline-block; vertical-align: top; width: 194px; height: 30px;"></canvas>
                           </span>
                        </div>
                      </div>
                  </div>
                </div>
              </div>
              

					<div class="col-md-6 col-sm-6 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Line Graph <small>Sessions</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li class="pull-right"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                     								
                  </div>
                </div>
              </div>
              
              <span id="mousespeed"><canvas style="display: inline-block; width: 60px; height: 16px; vertical-align: top;" width="60" height="16"></canvas></span>


<script type="text/javascript">
$(document).ready(function() {




				$(".sparkline11").sparkline(
						[ 2, 4, 3, 4, 5, 4, 5, 4, 3, 4, 6, 2, 4, 3, 4, 5, 4, 5,
								4, 3 ], {
							type : 'bar',
							height : '40',
							barWidth : 8,
							colorMap : {
								'7' : '#a1a1a1'
							},
							barSpacing : 2,
							barColor : '#26B99A'
						});

				$(".sparkline22").sparkline(
						[ 2, 4, 3, 4, 7, 5, 4, 3, 5, 6, 2, 4, 3, 4, 5, 4, 5, 4,
								3, 4, 6 ], {
							type : 'line',
							height : '40',
							width : '200',
							lineColor : '#26B99A',
							fillColor : '#ffffff',
							lineWidth : 3,
							spotColor : '#34495E',
							minSpotColor : '#34495E'
						});

				$(".sparkline33").sparkline(
						[ 2, 4, 3, 4, 5, 4 ], {
							type : 'pie',
							height : '40',
							width :'200',
							offset : "100",
							colorMap : {
								'7' : '#a1a1a1'
							},
						});

});
</script>
</body>
</html>