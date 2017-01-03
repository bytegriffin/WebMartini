$.get('echarts/mixlinebar').done(function(result) {
	var myechart = echarts.init(document.getElementById('mixlinebar'));
option = {
	tooltip : {
		trigger : 'axis'
	},
	toolbox : {
		feature : {
			magicType : {
				show : true,
				type : [ 'line', 'bar' ]
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	legend : {
		data : result.legend
	},
	xAxis : [ {
		type : 'category',
		data : result.xAxis
	} ],
	yAxis : [ {
		type : 'value',
		name : '水量',
		min : 0,
		max : 250,
		interval : 50,
		axisLabel : {
			formatter : '{value} ml'
		}
	}, {
		type : 'value',
		name : '温度',
		min : 0,
		max : 25,
		interval : 5,
		axisLabel : {
			formatter : '{value} °C'
		}
	} ],
	series : [
			{
				name : '蒸发量',
				type : 'bar',
				data : result.series[0]
			},
			{
				name : '降水量',
				type : 'bar',
				data : result.series[1]
			},
			{
				name : '平均温度',
				type : 'line',
				yAxisIndex : 1,
				data : result.series[2]
			} ]
	};
myechart.setOption(option);
});