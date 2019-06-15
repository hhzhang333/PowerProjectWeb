-+<script>
    $(function () {
        $('${location}').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 10
                }
            },
            title: {
                text: '${title}'
            },
            plotOptions: {
                pie: {
                    innerSize: 50,
                    depth: 45,
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                    }
                }
            },
            series: [{
                name: '用电量',
                data: ${data}
            }]
        });
    });
</script>