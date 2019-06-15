<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>powerMonitor</title>
    <link rel="stylesheet" href="assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="assets/css/chartCommon.css"/>
</head>
<body style="background-color:whitesmoke;">

<div class="am-g chart-container">
    <div class="am-u-sm-12" style="">
        <div class="am-container" style="max-width: 100%; margin-bottom: 10px">
            <button class="am-btn am-btn-primary">XX市</button>
        </div>
        <div class="am-container" id="block-number" style="max-width: 100%; border: dashed gainsboro;"></div>
    </div>
</div>


<div class="am-g chart-container">
    <div class="am-u-sm-6">
        <div class="am-container" id="block-pie" style=" border: dashed gainsboro;">
        </div>
    </div>
    <div class="am-u-sm-6" style=" border: dashed gainsboro;">
        <div class="am-u-sm-6">
            <div class="am-container chart-title" id="chart-total-ele">
                <div>已监控建筑总数</div>
            </div>
            <div class="am-container chart-container-sub">
                <div class="am-u-sm-6">
                    <span style="font-size: 60px; margin-left: 30px">8</span><span style="font-size: 30px">栋</span>
                </div>
                <div class="am-u-sm-6">
                    <div style="margin: 15px">
                        <img src="assets/image/house.jpg" style="width: 105px">
                    </div>
                </div>
            </div>
            <div class="am-container chart-title chart-container" id="chart-divide-ele">
                <div>已监控总面积</div>
            </div>
            <div class="am-container chart-container-sub">
                <div class="am-u-sm-6">
                    <span style="font-size: 60px">21.66</span><span style="font-size: 30px">万㎡</span>
                </div>
                <div class="am-u-sm-6">
                    <div style="margin: 15px">
                        <img src="assets/image/square.png" style="width: 105px">
                    </div>
                </div>
            </div>
        </div>
        <div class="am-u-sm-6">
            <div class="am-container chart-title" id="chart-total-dt">
                <div>环境参数</div>
            </div>
            <div class="am-container chart-container-sub">
                <div class="am-u-sm-6">
                    <div style="margin: 15px">
                        <img src="assets/image/sun.png" style="width: 105px">
                    </div>
                </div>
                <div class="am-u-sm-6">
                    <span id="datetime">2016-11-24</span>
                    <br/>
                    XX市区
                    <br/>
                    1℃~7℃
                    <br/>
                    多云
                </div>
            </div>
            <div class="am-container chart-container-sub">
                <div class="am-u-sm-6">
                    <div style="margin: 15px">
                        <img src="assets/image/tem.jpg" style="width: 42px">
                    </div>
                    室外温度：5℃
                </div>
                <div class="am-u-sm-6">
                    <div style="margin: 15px">
                        <img src="assets/image/rain.png" style="width: 224px;margin-left: -58px">
                    </div>
                    室外湿度：70%
                </div>
            </div>
        </div>
    </div>
</div>


<div class="footer"></div>
<!--<p id="tips"></p>-->
<!--<input type="button" value="sendParentMsg" id="sendParentMsg">-->

<script src="assets/bower_components/MessengerJS/messenger.js"></script>
<script src="assets/bower_components/jquery/dist/jquery.min.js"></script>
<script src="assets/bower_components/highcharts/highcharts.js"></script>
<script src="assets/bower_components/moment/moment.js"></script>
<script>
    $(function () {
        // Create the chart
        $('#block-number').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: 'XX市各行政区监测建筑数量'
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: '建筑统计/栋'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y}'
                    }
                }
            },
            series: [{
                name: '数量',
                colorByPoint: true,
                data: [{
                    name: '广陵区',
                    y: 3
                }, {
                    name: '邗江区',
                    y: 0
                }, {
                    name: '宝应县',
                    y: 0
                }, {
                    name: '高邮市',
                    y: 0
                }, {
                    name: '江都区',
                    y: 0
                }, {
                    name: '仪征市',
                    y: 0
                }]
            }]
        });
    });

    $(function () {
        // Create the chart
        $('#block-pie').highcharts({
            chart: {
                type: 'pie'
            },
            title: {
                text: 'XX市各建筑数量占比'
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        format: '{point.name}: {point.y}栋'
                    }
                }
            },
            series: [{
                name: '数量',
                colorByPoint: true,
                data: [{
                    name: '办公建筑',
                    y: 4
                }, {
                    name: '体育建筑',
                    y: 3
                }, {
                    name: '宾馆建筑',
                    y: 1
                }]
            }]
        });
    });

    $(function () {
        $("#datetime").html(moment().format('YYYY-M-D'));
    })
</script>
</body>
</html>