<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>powerManagement</title>
    <link rel="stylesheet" href="../assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="../assets/css/chartCommon.css"/>
</head>
<body style="background-color:#ccc;">
<div class="am-g chart-container">
    <div class="am-u-sm-6">
        <div class="am-container chart-title" id="chart-total-ele">
            <div>建筑总用电趋势</div>
        </div>
        <div class="am-container chart-container-sub">
            <div class="data-choose">
                <button class="am-btn am-btn-primary am-radius data-cho-p" place="#line-chart-total-ele" time="day" graph="line" graphType="total" title="建筑总用电趋势">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-ele" time="month" graph="line" graphType="total" title="建筑总用电趋势">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-ele" time="year" graph="line" graphType="total" title="建筑总用电趋势">年</button>
            </div>
            <div id="line-chart-total-ele" class="chart-size">

            </div>
        </div>
    </div>
    <div class="am-u-sm-6">
        <div class="am-container chart-title" id="chart-divide-ele">
            <div>建筑用电分项</div>
        </div>
        <div class="am-container chart-container-sub">
            <div class="data-choose">
                <button class="am-btn am-btn-primary am-radius data-cho-p" place="#line-chart-divide-ele" time="day" graph="pie" graphType="total" title="建筑用电分项">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-divide-ele" time="month" graph="pie" graphType="total" title="建筑用电分项">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-divide-ele" time="year" graph="pie" graphType="total" title="建筑用电分项">年</button>
            </div>
            <div id="line-chart-divide-ele" class="chart-size">

            </div>
        </div>
    </div>
</div>

<div class="am-g chart-container">
    <div class="am-u-sm-6">
        <div class="am-container chart-title" id="chart-total-kt">
            <div>空调系统用电趋势</div>
        </div>
        <div class="am-container chart-container-sub">
            <div class="data-choose">
                <button class="am-btn am-btn-primary am-radius data-cho-p" place="#line-chart-total-kt" time="day" graph="line" graphType="ac" title="空调系统用电趋势">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-kt" time="month" graph="line" graphType="ac" title="空调系统用电趋势">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-kt" time="year" graph="line" graphType="ac" title="空调系统用电趋势">年</button>
            </div>
            <div id="line-chart-total-kt" class="chart-size">

            </div>
        </div>
    </div>
    <div class="am-u-sm-6">
        <div class="am-container chart-title" id="chart-divide-kt">
            <div>空调系统用电分项</div>
        </div>
        <div class="am-container chart-container-sub">
            <div class="data-choose">
                <button class="am-btn am-btn-primary am-radius data-cho-p" place="#line-chart-divide-kt" time="day" graph="pie" graphType="ac" title="空调系统用电分项">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-divide-kt" time="month" graph="pie" graphType="ac" title="空调系统用电分项">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-divide-kt" time="year" graph="pie" graphType="ac" title="空调系统用电分项">年</button>
            </div>
            <div id="line-chart-divide-kt" class="chart-size">

            </div>
        </div>
    </div>
</div>

<div class="am-g chart-container">
    <div class="am-u-sm-6">
        <div class="am-container chart-title" id="chart-total-zm">
            <div>照明系统用电趋势</div>
        </div>
        <div class="am-container chart-container-sub">
            <div class="data-choose">
                <button class="am-btn am-btn-primary am-radius data-cho-p" place="#line-chart-total-zm" time="day" graph="line" graphType="light" title="照明系统用电趋势">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-zm" time="month" graph="line" graphType="light" title="照明系统用电趋势">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-zm" time="year" graph="line" graphType="light" title="照明系统用电趋势">年</button>
            </div>
            <div id="line-chart-total-zm" class="chart-size">

            </div>
        </div>
    </div>
    <div class="am-u-sm-6">
        <div class="am-container chart-title" id="chart-total-dt">
            <div>电梯用电趋势</div>
        </div>
        <div class="am-container chart-container-sub">
            <div class="data-choose">
                <button class="am-btn am-btn-primary am-radius data-cho-p" place="#line-chart-total-td" time="day" graph="line" graphType="elevator" title="电梯用电趋势">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-td" time="month" graph="line" graphType="elevator" title="电梯用电趋势">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p" place="#line-chart-total-td" time="year" graph="line" graphType="elevator" title="电梯用电趋势">年</button>
            </div>
            <div id="line-chart-total-td" class="chart-size">

            </div>
        </div>
    </div>
</div>

<div class="footer"></div>
<!--<p id="tips"></p>-->
<!--<input type="button" value="sendParentMsg" id="sendParentMsg">-->

<script src="../assets/bower_components/MessengerJS/messenger.js"></script>
<script src="../assets/bower_components/jquery/dist/jquery.min.js"></script>
<script src="../assets/bower_components/highcharts/highcharts.js"></script>
<script src="../assets/bower_components/highcharts/highcharts-3d.js"></script>

<script>

    function showGraph(title, subTitle, showType, graph, graphType, loadId) {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth();
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        var start = {
            "year": year,
            "month": month + 1,
            "day": day,
            "hour": hour,
            "minute": minute,
            "second": second,
            "title": title,
            "subTitle": subTitle,
            "showType": showType,
            "location": loadId
        };
        var time = {
            "start": start,
            "end": start
        };
        var graphUrl = "showGraph/" + graph + "/" + graphType;
        $.ajax({
            type: 'post',
            url: graphUrl,
            contentType: "application/json",
            data: JSON.stringify(time),
            success: function(data, status) {
                $(loadId).html(data);
            }
        });
    }

    showGraph("建筑总用电趋势", "用电量", "day", "line", "total", "#line-chart-total-ele");
    showGraph("空调系统用电趋势", "用电量", "day", "line", "ac", "#line-chart-total-kt");
    showGraph("照明系统用电趋势", "用电量", "day", "line", "light", "#line-chart-total-zm");
    showGraph("电梯用电趋势", "用电量", "day", "line", "elevator", "#line-chart-total-td");
    showGraph("建筑用电分项", "用电量", "day", "pie", "total", "#line-chart-divide-ele");
    showGraph("空调系统用电分项", "用电量", "day", "pie", "ac", "#line-chart-divide-kt");

    $(".data-cho-p").click(function () {
        var loadId = $(this).attr("place");
        var showType = $(this).attr("time");
        var graphType = $(this).attr("graphType");
        var title = $(this).attr("title");
        var graph = $(this).attr("graph");
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth();
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        var start = {
            "year": year,
            "month": month + 1,
            "day": day,
            "hour": hour,
            "minute": minute,
            "second": second,
            "title": title,
            "subTitle": "用电量",
            "showType": showType,
            "location": loadId
        };
        var time = {
            "start": start,
            "end": start
        };
        var graphUrl = "showGraph/" + graph + "/" + graphType;
        $.ajax({
            type: 'post',
            url: graphUrl,
            contentType: "application/json",
            data: JSON.stringify(time),
            success: function(data, status) {
                $(loadId).html(data);
            }
        });
    })
</script>
</body>
</html>