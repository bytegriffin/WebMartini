$.get('echarts/negativebar').done(function(result) {
	var myechart = echarts.init(document.getElementById('negativebar'));
	option = {
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		toolbox : {
			feature : {
				saveAsImage : {
					show : true
				}
			}
		},
		legend : {
			data : result.legend
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : [ {
			type : 'value'
		} ],
		yAxis : [ {
			type : 'category',
			axisTick : {
				show : false
			},
			data : result.yAxis,
		} ],
		series : [ {
			name : result.legend[0],
			type : 'bar',
			label : {
				normal : {
					show : true,
					position : 'inside'
				}
			},
			data : result.series[0]
		}, {
			name : result.legend[1],
			type : 'bar',
			stack : '总量',
			label : {
				normal : {
					show : true
				}
			},
			data : result.series[1],
		}, {
			name : result.legend[2],
			type : 'bar',
			stack : '总量',
			label : {
				normal : {
					show : true,
					position : 'left'
				}
			},
			data : result.series[2]
		} ]
	};

	myechart.setOption(option);
});