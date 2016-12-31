
$.get('echarts/stackline').done(function (result) {
	var myechart = echarts.init(document.getElementById('stackline'));
	option = {
			title : {
				text : '折线图堆叠'
			},
			tooltip : {
				trigger : 'axis'
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
				type : 'value'
			},
			series : [ {
				name : result.legend[0],
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}, {
				name : result.legend[1],
				type : 'line',
				stack : '总量',
				data : [ 220, 182, 191, 234, 290, 330, 310 ]
			}, {
				name : result.legend[2],
				type : 'line',
				stack : '总量',
				data : [ 150, 232, 201, 154, 190, 330, 410 ]
			}, {
				name : result.legend[3],
				type : 'line',
				stack : '总量',
				data : [ 320, 332, 301, 334, 390, 330, 320 ]
			}, {
				name : result.legend[4],
				type : 'line',
				stack : '总量',
				data : [ 820, 932, 901, 934, 1290, 1330, 1320 ]
			} ]
	   };
	 myechart.setOption(option);
});
