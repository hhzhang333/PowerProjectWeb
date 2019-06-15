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
    <link rel="stylesheet" href="../assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="../assets/node_modules/amazeui-datatables/dist/amazeui.datatables.css">
    <link rel="stylesheet" href="../assets/bower_components/datetimepicker/css/amazeui.datetimepicker.css">
    <link rel="stylesheet" href="../assets/bower_components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/common.css">
    <link rel="stylesheet" href="../assets/css/admin.css">
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
                <a href="manageUsers"><i class="fa fa-bars fa-lg mr-10"></i>权限管理</a>
            </li>
            <li class="f20 active">
                <a href="manageData"><i class="fa fa-database fa-lg mr-10"></i>数据管理</a>
            </li>

            <li class="f20">
                <a href="picConfig"><i class="fa fa-database fa-lg mr-10"></i>底图配置</a>
            </li>

            <li class="f20">
                <a href="formulaConfig"><i class="fa fa-database fa-lg mr-10"></i>公式配置</a>
            </li>
            <li class="f20">
                <a href="powerMonitor"><i class="fa fa-database fa-lg mr-10"></i>能源监控</a>
            </li>
            <li class="f20">
                <a href="/admin/downloadReporter"><i class="fa fa-database fa-lg mr-10"></i>下载报表</a>
            </li>
        </ul>
    </div>
    <div class="admin-container-content fl">
        <div class="am-container mt-20">
            <div class="admin-container-content-bread f20">数据管理</div>
            <div style="margin-bottom: 10px;">
                <input type="button" class="am-btn-primary" value="增加单条数据" id="addDevmessItem" style="display: inline-block;">
                <form enctype="multipart/form-data" id="csvForm" style="display: inline-block;">
                    <input type="file" class="" value="批量增加数据" id="addDevmessItems" style="display: inline-block;" name="file">

                </form>
                <input type="button" class="am-btn-primary" id="csvUpload" value="上传" style="display: inline-block">
            </div>
            <div class="admin-container-content-main">

                <table id="my-table" class="am-table am-table-striped am-table-bordered am-table-compact">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>name</th>
                            <th>value</th>
                            <th>time</th>
                            <th>quality</th>
                            <#--<th>operation</th>-->
                        </tr>
                    </thead>

                </table>

                <div id="timeFilter" class="ml-20" style="display: none;">
                    <input type="checkbox" id="isTimeFiltered">是否按时间筛选
                    <input type="text" value="2016-07-19 08:15" id="dtStartTime" class="dtFilter">
                    <input type="text" value="2016-07-21 09:25" id="dtEndTime" class="dtFilter">
                </div>
                <!--div class="layer-popup">
                    <div><label for="dataid">id</label><input type="text" id="dataid" value="1"></div>
                    <div><label for="name">name</label><input type="text" id="name" value="KJF-A3.08.CT"></div>
                    <div><label for="value">value</label><input type="text" id="value" value="94"></div>
                    <div><label for="time">time</label><input type="text" id="time" value="Tue Jul 19 20:14:40 CST 2016"></div>
                    <div><label for="quality">quality</label><input type="text" id="quality" value="0"></div>
                </div-->
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
<script src="../assets/bower_components/jquery/dist/jquery.min.js"></script>
<!--<![endif]-->
<script src="../assets/bower_components/layer/layer.js"></script>
<script src="../assets/bower_components/moment/moment.js"></script>
<script src="../assets/bower_components/amazeui/dist/js/amazeui.min.js"></script>
<script src="../assets/bower_components/datetimepicker/js/amazeui.datetimepicker.js"></script>
<script src="../assets/node_modules/amazeui-datatables/dist/amazeui.datatables.js"></script>
<script src="../assets/js/auth.js"></script>
<script src="../assets/js/adminManageData.js"></script>
</body>
</html>
