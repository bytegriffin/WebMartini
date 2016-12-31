$.get('echarts/categorybar').done(function(result) {
			var myechart = echarts.init(document.getElementById('categorybar'));
			option = {
				title : {
					text : '某地区蒸发量和降水量',
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : result.legend
				},
				toolbox : {
					show : true,
					feature : {
							magicType : {
								type : [ 'line', 'bar']
							},
							saveAsImage : {}
					}
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : result.xAxis
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [
						{
							name : '蒸发量',
							type : 'bar',
							data : result.series[0],
							markPoint : {//可以让程序自动判断最大值和最小值，并标注
								data : [ {
									type : 'max',
									name : '最大值'
								}, {
									type : 'min',
									name : '最小值'
								} ]
							},
							markLine : {
								data : [ {
									type : 'average',
									name : '平均值'
								} ]
							}
						},
						{
							name : '降水量',
							type : 'bar',
							data : result.series[1],
							markPoint : {//也可以人工设定
								data : [ {
									name : '年最高',
									value : 182.2,
									xAxis : 7,
									yAxis : 183
								}, {
									name : '年最低',
									value : 2.3,
									xAxis : 11,
									yAxis : 3
								} ]
							},
							markLine : {
								data : [ {
									type : 'average',
									name : '平均值'
								} ]
							}
						} ]
			};
			myechart.setOption(option);
		});