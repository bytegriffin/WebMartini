$.get('echarts/simpleradar').done(function(result) {
	var myechart = echarts.init(document.getElementById('simpleradar'));
	

	option = {
		title : {
			text : '基础雷达图'
		},
		tooltip : {},
		toolbox : {
			show : true,
			feature : {
					saveAsImage : {}
			}
		},
		legend : {
			data : result.legend
		},
		radar : {
			// shape: 'circle',
			indicator : result.indicator
		},
		series : [ {
			name : '预算 vs 开销（Budget vs spending）',
			type : 'radar',
			// areaStyle: {normal: {}},
			data : [ {
				value : result.series[0],
				name : result.legend[0]
			}, {
				value : result.series[1],
				name : result.legend[1]
			} ]
		} ]
	};
	
	myechart.setOption(option);
});