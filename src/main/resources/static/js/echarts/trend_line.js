
$.get('echarts/trendline').done(function (result) {
	var myechart = echarts.init(document.getElementById('trendline'));
	option = {
		title : {
			text : result.title
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
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : result.xAxis
		},
		yAxis : {
			type : 'value',
			axisLabel : {
				formatter : '{value} °C'
			}
		},
		series : [ {
			name : result.legend[0],
			type : 'line',
			data : result.series[0],
			markPoint : {
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
		}, {
			name : result.legend[1],
			type : 'line',
			data : result.series[1],
			markPoint : {
				data : [ {
					name : '周最低',
					value : -2,
					xAxis : 1,
					yAxis : -1.5
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

