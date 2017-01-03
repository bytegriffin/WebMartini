$.get('echarts/nestpie').done(function(result) {
	var myechart = echarts.init(document.getElementById('nestpie'));

			option = {
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b}: {c} ({d}%)"
				},
				toolbox : {
					show : true,
					feature : {
							saveAsImage : {}
					}
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : result.legend
				},
				series : [ {
					name : '访问来源',
					type : 'pie',
					selectedMode : 'single',
					radius : [ 0, '30%' ],

					label : {
						normal : {
							position : 'inner'
						}
					},
					labelLine : {
						normal : {
							show : false
						}
					},
					data : result.series
				}, {
					name : '访问来源',
					type : 'pie',
					radius : [ '40%', '55%' ],

					data : result.subSeries
				} ]
			};

	myechart.setOption(option);
});