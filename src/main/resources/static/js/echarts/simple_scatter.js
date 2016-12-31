$.get('echarts/simplescatter').done(function(result) {
	var myechart = echarts.init(document.getElementById('simplescatter'));
	

				option = {
				title : {
					text : '男性女性身高体重分布',
					subtext : '抽样调查来自: Heinz  2003'
				},
				grid : {
					left : '3%',
					right : '7%',
					bottom : '3%',
					containLabel : true
				},
				tooltip : {
					trigger : 'axis',
					showDelay : 0,
					formatter : function(params) {
						if (params.value.length > 1) {
							return params.seriesName + ' :<br/>'
									+ params.value[0] + 'cm ' + params.value[1]
									+ 'kg ';
						} else {
							return params.seriesName + ' :<br/>' + params.name
									+ ' : ' + params.value + 'kg ';
						}
					},
					axisPointer : {
						show : true,
						type : 'cross',
						lineStyle : {
							type : 'dashed',
							width : 1
						}
					}
				},
				toolbox : {
					feature : {
						dataZoom : {},
						brush : {
							type : [ 'rect', 'polygon', 'clear' ]
						}
					}
				},
				brush : {},
				legend : {
					data : result.legend,
					left : 'center'
				},
				xAxis : [ {
					type : 'value',
					scale : true,
					axisLabel : {
						formatter : '{value} cm'
					},
					splitLine : {
						show : false
					}
				} ],
				yAxis : [ {
					type : 'value',
					scale : true,
					axisLabel : {
						formatter : '{value} kg'
					},
					splitLine : {
						show : false
					}
				} ],
				series : [ {
					name : '女性',
					type : 'scatter',
					data : result.series[0],
					markArea : {
						silent : true,
						itemStyle : {
							normal : {
								color : 'transparent',
								borderWidth : 1,
								borderType : 'dashed'
							}
						},
						data : [ [ {
							name : '女性分布区间',
							xAxis : 'min',
							yAxis : 'min'
						}, {
							xAxis : 'max',
							yAxis : 'max'
						} ] ]
					},
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						lineStyle : {
							normal : {
								type : 'solid'
							}
						},
						data : [ {
							type : 'average',
							name : '平均值'
						}, {
							xAxis : 160
						} ]
					}
				}, {
					name : '男性',
					type : 'scatter',
					data : result.series[1],
					markArea : {
						silent : true,
						itemStyle : {
							normal : {
								color : 'transparent',
								borderWidth : 1,
								borderType : 'dashed'
							}
						},
						data : [ [ {
							name : '男性分布区间',
							xAxis : 'min',
							yAxis : 'min'
						}, {
							xAxis : 'max',
							yAxis : 'max'
						} ] ]
					},
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						lineStyle : {
							normal : {
								type : 'solid'
							}
						},
						data : [ {
							type : 'average',
							name : '平均值'
						}, {
							xAxis : 170
						} ]
					}
				} ]
			};

	
	myechart.setOption(option);
});