$.get('chartjs/radar').done(function(result) {

	var ctx = document.getElementById("canvasRadar");
	var data = {
		labels : result.labels,
		datasets : [ {
			label : result.datasets[0].label,
			backgroundColor : "rgba(3, 88, 106, 0.2)",
			borderColor : "rgba(3, 88, 106, 0.80)",
			pointBorderColor : "rgba(3, 88, 106, 0.80)",
			pointBackgroundColor : "rgba(3, 88, 106, 0.80)",
			pointHoverBackgroundColor : "#fff",
			pointHoverBorderColor : "rgba(220,220,220,1)",
			data : result.datasets[0].data
		}, {
			label : result.datasets[1].label,
			backgroundColor : "rgba(38, 185, 154, 0.2)",
			borderColor : "rgba(38, 185, 154, 0.85)",
			pointColor : "rgba(38, 185, 154, 0.85)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rgba(151,187,205,1)",
			data : result.datasets[1].data
		} ]
	};

	var canvasRadar = new Chart(ctx, {
		type : 'radar',
		data : data,
	});

});