$.get('echarts/simplepie').done(function(result) {
	var myechart = echarts.init(document.getElementById('simplepie'));

 option = {
		title : {
			text : '某站点用户访问来源',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		toolbox : {
			show : true,
			feature : {
					saveAsImage : {}
			}
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			data : result.legend
		},
		series : [ {
			name : '访问来源',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
					value : result.series[0],
					name : result.legend[0]
				}, {
					value : result.series[1],
					name : result.legend[1]
				}, {
					value : result.series[2],
					name : result.legend[2]
				}, {
					value : result.series[3],
					name : result.legend[3]
				}, {
					value : result.series[4],
					name : result.legend[4]
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			}
		} ]
  };

	myechart.setOption(option);
});