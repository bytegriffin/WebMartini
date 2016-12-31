
var dalchart = echarts.init(document.getElementById("dynamicarealine"));

var interval = 10 * 1000; // 每次刷新数据的时间间隔 10s
var max_col = 20;// 最多展示多少列数据，多余的前面会丢掉

var app = {};
var xvalue = [];
var yvalue = [];
var option = {
	 title : {
		text : '动态数据',
	 },
	 tooltip : {
			trigger : 'axis'
	 },
	 legend: {
	    data:['日志数据']  //必须要跟series的name一样，否则不显示!
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
	 grid: {//修改chart所占面积
	    left: '3%',
	    right: '4%',
	    bottom: '3%',
	    containLabel: true
	  },
	 xAxis : {
			type : 'category',
			boundaryGap : false,
			data : xvalue
		},
		yAxis : {
				boundaryGap : [ 0, '50%' ],
				type : 'value'
		},
   series: [ {
		    name:'日志数据',
		    type:'line', 
		    smooth : true,
					symbol : 'none',
					stack : 'a',
					areaStyle : {
						normal : {}
					},
		    data: yvalue
            }
        ]
};

var count = 0;
//每隔1秒就去拉一次数据
app.timeTicket = setInterval(function() {
	$.get('echarts/dynamicarealine').done(function(result) {
		xvalue.push(result.xAxis);
		yvalue.push(result.series);
		dalchart.setOption({
			xAxis : {
				data : xvalue
			},
			series : [{
				data : yvalue
			} ]
		});
		count ++;
	});
	if(count >= max_col){
		xvalue.shift();
		yvalue.shift();
		count --;
	}
}, interval);

if (option && typeof option === "object") {
	dalchart.setOption(option, true);
}