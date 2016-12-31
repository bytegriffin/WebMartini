$.get('chartjs/pie').done(function(result) {

	// Pie chart
	var ctx = document.getElementById("pieChart");
	var data = {
		datasets : [ {
			data : result.datasets[0].data,
			backgroundColor : [ "#455C73", "#9B59B6", "#BDC3C7", "#26B99A", "#3498DB" ],
			label : result.datasets[0].label
		} ],
		labels : result.labels
	};

	var pieChart = new Chart(ctx, {
		data : data,
		type : 'pie',
		options : {
			title : {
				display : true,
				text : 'Pie Chart'
			},
		}
	});

});

