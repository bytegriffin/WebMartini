
$.get('chartjs/polarArea').done(function(result) {

	// PolarArea chart
	var ctx = document.getElementById("polarArea");
	var data = {
		datasets : [ {
			data : result.datasets[0].data,
			backgroundColor : [ "#455C73", "#9B59B6", "#BDC3C7", "#26B99A","#3498DB" ],
			label : result.datasets[0].label
		} ],
		labels : result.labels
	};

	var polarArea = new Chart(ctx, {
		data : data,
		type : 'polarArea',
		options : {
			title : {
				display : true,
				text : 'Polar Area Chart'
			},
			scale : {
				ticks : {
					beginAtZero : true
				}
			}
		}
	});

});

