$.get('chartjs/bar').done(function(result) {
	
	var ctx = document.getElementById("barChart");
	var mybarChart = new Chart(ctx, {
		type : 'bar',
		data : {
			labels : result.labels,
			datasets : [ {
				label : result.datasets[0].label,
				backgroundColor : "#26B99A",
				data : result.datasets[0].data
			}, {
				label : result.datasets[1].label,
				backgroundColor : "#03586A",
				data : result.datasets[1].data
			} ]
		},

		options : {
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			}
		}
	});

});