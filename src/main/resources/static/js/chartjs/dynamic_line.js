var interval = 1 * 1000; // 每次刷新数据的时间间隔 10s
var max_col = 20;// 最多展示多少列数据，多余的前面会丢掉
var app = {};
var xvalue = [];
var yvalue1 = [];
var yvalue2 = [];


var ctx = document.getElementById("dynamicLineChart");
var config = {
	type : 'line',
	data : {
		labels : xvalue,
		datasets : [ {
			label : "数据1",
			fill: false, // 是否填充
			// borderDash: [5, 5], 虚线
			backgroundColor : "rgba(38, 185, 154, 0.31)",
			borderColor : "rgba(38, 185, 154, 0.7)",
			pointBorderColor : "rgba(38, 185, 154, 0.7)",
			pointBackgroundColor : "rgba(38, 185, 154, 0.7)",
			pointHoverBackgroundColor : "#fff",
			pointHoverBorderColor : "rgba(220,220,220,1)",
			pointBorderWidth : 1,
			data : yvalue1
		}, {
			label : "数据2",
			backgroundColor : "rgba(3, 88, 106, 0.3)",
			borderColor : "rgba(3, 88, 106, 0.70)",
			pointBorderColor : "rgba(3, 88, 106, 0.70)",
			pointBackgroundColor : "rgba(3, 88, 106, 0.70)",
			pointHoverBackgroundColor : "#fff",
			pointHoverBorderColor : "rgba(151,187,205,1)",
			pointBorderWidth : 1,
			data : yvalue2
		} ]
	},
	options : {
		title : {
			display : true,
			text : 'Chart.js Line Chart'
		},
		tooltips : {
			mode : 'label',
			callbacks : {
			}
		},
		hover : {
			mode : 'dataset'
		},
		scales : {
			xAxes : [ {
				display : true,
				scaleLabel : {
					show : true,
					labelString : 'Month'
				}
			} ],
			yAxes : [ {
				display : true,
				scaleLabel : {
					show : true,
					labelString : 'Value'
				},
/*				ticks : {
					suggestedMin : -10,
					suggestedMax : 250,
				}*/
			} ]
		}
     }
}
var myline = new Chart(ctx,config);

var count = 0;
//每隔1秒就去拉一次数据
app.timeTicket = setInterval(function() {
	$.get('chartjs/dynamicline').done(function(result) {
		xvalue.push(result.labels);
		yvalue1.push(result.data1);
		yvalue2.push(result.data2);
		
		myline.update();
		count ++;
	});
	if(count >= max_col){
		xvalue.shift();
		yvalue1.shift();
		yvalue2.shift();
		count --;
	}
}, interval);
