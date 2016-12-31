$.get('echarts/simplegauge').done(function(result) {
	var myechart = echarts.init(document.getElementById('simplegauge'));

	option = {
		tooltip : {
			formatter : "{a} <br/>{b} : {c}%"
		},
		toolbox : {
			feature : {
				saveAsImage : {}
			}
		},
		series : [ {
			name : '业务指标',
			type : 'gauge',
			detail : {
				backgroundColor : 'rgba(30,144,255,0.8)',
				borderWidth : 1,
				borderColor : '#fff',
				shadowColor : '#fff', //默认透明
				shadowBlur : 5,
				offsetCenter : [ 0, '50%' ], // x, y，单位px
				textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					fontWeight : 'bolder',
					color : '#fff'
				}
			},
			data : [ {value: result.value, name: result.name } ]
		} ]
	};


	myechart.setOption(option);
});