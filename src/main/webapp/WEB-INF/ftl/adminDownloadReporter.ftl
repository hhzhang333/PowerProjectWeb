<!doctype html>
<html class="no-js fixed-layout">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>管理员</title>
    <meta name="description" content="首页">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="/assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="/assets/node_modules/amazeui-datatables/dist/amazeui.datatables.css">
    <link rel="stylesheet" href="/assets/bower_components/datetimepicker/css/amazeui.datetimepicker.css">
    <link rel="stylesheet" href="/assets/bower_components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/css/common.css">
    <link rel="stylesheet" href="/assets/css/admin.css">
</head>
<body style="overflow:hidden;">
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehapspy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
<div class="admin-navbar" style="display: none;">
    <h1 class="title f20 ml-10">后台管理系统 </h1>
    <div id="logout">登出</div>
</div>
<input type="hidden" value="${authority}" id="authority">
<div class="admin-container clearfix">
    <div class="admin-container-sidebar fl" style="display: none;">
        <ul class="list-item">
            <li class="f20">
                <a href="/admin/manageUsers"><i class="fa fa-bars fa-lg mr-10"></i>权限管理</a>
            </li>
            <li class="f20">
                <a href="/admin/manageData"><i class="fa fa-database fa-lg mr-10"></i>数据管理</a>
            </li>

            <li class="f20">
                <a href="/admin/picConfig"><i class="fa fa-database fa-lg mr-10"></i>底图配置</a>
            </li>

            <li class="f20">
                <a href="/admin/formulaConfig"><i class="fa fa-database fa-lg mr-10"></i>公式配置</a>
            </li>
            <li class="f20">
                <a href="powerMonitor"><i class="fa fa-database fa-lg mr-10"></i>能源监控</a>
            </li>
            <li class="f20 active">
                <a href="/admin/downloadReporter"><i class="fa fa-database fa-lg mr-10"></i>下载报表</a>
            </li>
        </ul>
    </div>
    <div class="admin-container-content fl">
        <div class="am-container mt-20">
            <div class="admin-container-content-bread f20">报表下载</div>
            <div class="admin-container-content-main">
                <div style="margin: 20px">
                    <div class="am-u-sm-4">
                        <button class="am-btn am-btn-default download" place="day">当天报表下载</button>
                    </div>
                    <div class="am-u-sm-4">
                        <button class="am-btn am-btn-default download" place="month">当月报表下载</button>
                    </div>
                    <div class="am-u-sm-4">
                        <button class="am-btn am-btn-default download" place="year">本年报表下载</button>
                    </div>
                </div>
                <div style="padding-top: 70px">自定义下载</div>
                <div class="am-g" style="margin: 20px">
                    <div class="am-u-sm-4">
                        <label class="am-u-sm-3 am-form-label">开始</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="startTime" placeholder="2016-7-8 14:4:23">
                        </div>
                    </div>
                    <div class="am-u-sm-1">
                        至
                    </div>
                    <div class="am-u-sm-4">
                        <label class="am-u-sm-3 am-form-label">截止</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="endTime" placeholder="2016-7-9 17:34:34">
                        </div>
                    </div>
                    <div class="am-u-sm-3">
                        <button class="am-btn am-btn-default" id="targetTime">报表下载</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>

<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="/assets/bower_components/jquery/dist/jquery.min.js"></script>
<!--<![endif]-->
<script src="/assets/bower_components/layer/layer.js"></script>
<script src="/assets/bower_components/moment/moment.js"></script>
<script src="/assets/bower_components/amazeui/dist/js/amazeui.min.js"></script>
<script src="/assets/bower_components/datetimepicker/js/amazeui.datetimepicker.js"></script>
<script src="/assets/node_modules/amazeui-datatables/dist/amazeui.datatables.js"></script>
<script src="/assets/js/auth.js"></script>
<script src="/assets/js/adminFormulaConfig.js"></script>
<script>
    $(".download").click(function () {
        var showType = $(this).attr("place");
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
            "showType": showType
        };
        var time = {
            "start": start,
            "end": start
        };
        $.ajax({
            type: 'post',
            url: 'download',
            contentType: "application/json",
            data: JSON.stringify(time),
            success: function(data, status) {
                location.href="../pic/" + data;
            }
        });
    });

    $("#targetTime").click(function () {
        var startTime = moment($("#startTime").val());
        var endTime = moment($("#endTime").val());
        if (startTime.year() == null) {
            alert("输入的时间格式错误");
        }
        var start = {
            "year": startTime.year(),
            "month": startTime.month() + 1,
            "day": startTime.date(),
            "hour": startTime.hour(),
            "minute": startTime.minute(),
            "second": startTime.second(),
            "showType": 'hour'
        };
        var end = {
            "year": endTime.year(),
            "month": endTime.month() + 1,
            "day": endTime.date(),
            "hour": endTime.hour(),
            "minute": endTime.minute(),
            "second": endTime.second(),
            "showType": 'hour'
        };
        var time = {
            "start": start,
            "end": end
        };
        $.ajax({
            type: 'post',
            url: 'download',
            contentType: "application/json",
            data: JSON.stringify(time),
            success: function(data, status) {
                location.href="../pic/" + data;
            }
        });
    })
</script>
</body>
</html>
