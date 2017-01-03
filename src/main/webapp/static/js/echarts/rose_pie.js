$.get('echarts/rosepie').done(function(result) {
	var myechart = echarts.init(document.getElementById('rosepie'));

			option = {
				title : {
					text : '南丁格尔玫瑰图',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					left : 'left',
					data : result.legend
				},
				toolbox : {
					show : true,
					feature : {
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				series : [ {
					name : '面积模式',
					type : 'pie',
					radius : [ 30, 110 ],
					center : [ '55%', '50%' ],
					roseType : 'radis',
					data : result.series
				} ]
			};

	
	myechart.setOption(option);
});