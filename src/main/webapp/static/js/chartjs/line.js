$.get('chartjs/line').done(function(result) {

	var ctx = document.getElementById("lineChart");
	var lineChart = new Chart(ctx, {
		type : 'line',
		data : {
			labels : result.labels,
			datasets : [ {
				label : result.datasets[0].label,
				backgroundColor : "rgba(38, 185, 154, 0.31)",
				borderColor : "rgba(38, 185, 154, 0.7)",
				pointBorderColor : "rgba(38, 185, 154, 0.7)",
				pointBackgroundColor : "rgba(38, 185, 154, 0.7)",
				pointHoverBackgroundColor : "#fff",
				pointHoverBorderColor : "rgba(220,220,220,1)",
				pointBorderWidth : 1,
				data : result.datasets[0].data
			}, {
				label : result.datasets[1].label,
				backgroundColor : "rgba(3, 88, 106, 0.3)",
				borderColor : "rgba(3, 88, 106, 0.70)",
				pointBorderColor : "rgba(3, 88, 106, 0.70)",
				pointBackgroundColor : "rgba(3, 88, 106, 0.70)",
				pointHoverBackgroundColor : "#fff",
				pointHoverBorderColor : "rgba(151,187,205,1)",
				pointBorderWidth : 1,
				data : result.datasets[1].data
			} ]
		},
	});

});