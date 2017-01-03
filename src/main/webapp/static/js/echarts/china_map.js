
$.get('echarts/chinamap').done(function(result) {
			var myechart = echarts.init(document.getElementById('chinamap'));			

	option = {
			tooltip : {
				trigger : 'item',
				formatter : '{b}'
			},
			series : [ {
				name : '中国',
				type : 'map',
				mapType : 'china',
				selectedMode : 'multiple',
				label : {
					normal : {
						show : true
					},
					emphasis : {
						show : true
					}
				},
				data : [ {
					name : '广东',
					selected : true
				} ]
		} ]
	};
			
			myechart.setOption(option);
});