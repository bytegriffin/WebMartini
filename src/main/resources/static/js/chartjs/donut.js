$.get('chartjs/donut').done(function(result) {

	var ctx = document.getElementById("canvasDoughnut");
	var data = {
		labels : result.labels,
		datasets : [ {
			data : result.datasets[0].data,
			backgroundColor : [ "#455C73", "#9B59B6", "#BDC3C7", "#26B99A",	"#3498DB" ],
			hoverBackgroundColor : [ "#34495E", "#B370CF", "#CFD4D8",	"#36CAAB", "#49A9EA" ]
		} ]
	};

	var canvasDoughnut = new Chart(ctx, {
		type : 'doughnut',
		tooltipFillColor: "rgba(51, 51, 51, 0.55)",
		data : data,
		options : {
			title : {
				display : true,
				text : 'Chart.js Doughnut Chart'
			},
			tooltips : {
				mode : 'label',
				callbacks : {
				}
			},
			hover : {
				mode : 'dataset'
			},
		}	
	});

});