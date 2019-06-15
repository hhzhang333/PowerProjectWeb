<script>
    $(function () {
        var xary = ${xary};
        var dataTmp = ${data};
        $('${location}').highcharts({
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: '${title}'
            },
            xAxis: {
                categories: xary
            },
            yAxis: {
                title: {
                    text: '${subTitle} (kW.h)'
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
            series: [{
                name: '${subTitle}',
                type: 'column',
                data: dataTmp,
                tooltip: {
                    valueSuffix: ' kW.h'
                }
            }]
        });
    });
</script>