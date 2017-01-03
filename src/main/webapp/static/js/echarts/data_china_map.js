
$.get('echarts/datachinamap').done(function(result) {
			var myechart = echarts.init(document.getElementById('datachinamap'));			



	option = {
		title : {
			text : 'iphone销量',
			left : 'center'
		},
		tooltip : {
			trigger : 'item'
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			data : [ 'iphone' ]
		},
		visualMap : {
			min : 0,
			max : 2500,
			left : 'left',
			top : 'bottom',
			text : [ '高', '低' ], // 文本，默认为数值文本
			calculable : true
		},
		toolbox : {
			show : true,
			orient : 'vertical',
			left : 'right',
			top : 'center',
			feature : {
				restore : {},
				saveAsImage : {}
			}
		},
		series : [ {
			name : 'iphone',
			type : 'map',
			mapType : 'china',
			label : {
				normal : {
					show : true
				},
				emphasis : {
					show : true
				}
			},
			data : result.data
		} ]
	};
			
			myechart.setOption(option);
});