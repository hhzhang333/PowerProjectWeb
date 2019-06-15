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
</div>

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
            <li class="f20 active">
                <a href="/admin/downloadReporter"><i class="fa fa-database fa-lg mr-10"></i>下载报表</a>
            </li>
        </ul>
    </div>
    <div class="admin-container-content fl">
        <div class="am-container mt-20">
            <div class="admin-container-content-bread f20">能源消耗比较</div>
            <div class="admin-container-content-main f9">
                <table class="am-table">
                    <thead>
                    <tr>
                        <th>项目</th>
                        <th>总建筑面积m²</th>
                        <th>本年用电kW.h</th>
                        <th>本年用水m³</th>
                        <th>本年用气m³</th>
                        <th>单位面积用电kW.h/m²</th>
                        <th>单位面积用水m³/㎡</th>
                        <th>单位面积用气m³/㎡</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if reporters??>
                        <#list reporters as reporter>
                        <tr>
                            <td>${reporter.blockName}</td>
                            <td>${reporter.area}</td>
                            <td>${((reporter.power)?number)?string("#.#")}</td>
                            <td>${reporter.water}</td>
                            <td>${reporter.piplinegas}</td>
                            <td>${(reporter.power?number / reporter.area)?string("#.#")}</td>
                            <td>${(reporter.water?number / reporter.area)?string("#.#")}</td>
                            <td>${(reporter.piplinegas?number / reporter.area)?string("#.#")}</td>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="am-container mt-20">
            <div class="am-u-sm-5">
                <div class="admin-container-content-bread f20">综合消耗比较</div>
                <div class="admin-container-content-main f9">
                    <table class="am-table">
                        <thead>
                        <tr>
                            <th>项目</th>
                            <th>综合能耗（吨标煤）</th>
                            <th>单位面积综合能耗(吨标煤/m²)</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if reporters??>
                            <#list reporters as reporter>
                            <tr>
                                <td>${reporter.blockName}</td>
                                <td>${reporter.powerUsed}</td>
                                <td>${((reporter.powerUsed?c)?number / reporter.area)?string("#.######")}</td>
                            </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="am-u-sm-1"></div>
            <div class="am-u-sm-6">
                <div class="admin-container-content-bread f20">用电分项</div>
                <div class="admin-container-content-main f9">
                    <table class="am-table">
                        <thead>
                        <tr>
                            <th>项目</th>
                            <th>照明插座用电kW.h</th>
                            <th>空调用电kW.h</th>
                            <th>动力用电kW.h</th>
                            <th>特殊用电kW.h</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if reporters??>
                            <#list reporters as reporter>
                            <tr>
                                <td>${reporter.blockName}</td>
                                <td>${((reporter.lightSocket)?number)?string("#.#")}</td>
                                <td>${((reporter.aircondition)?number)?string("#.#")}</td>
                                <td>${((reporter.dfPower)?number)?string("#.#")}</td>
                                <td>${((reporter.special)?number)?string("#.#")}</td>
                            </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
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
            }
        });
    });

    $(".am-table").DataTable();
</script>
</body>
</html>
