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
    <div class="admin-container-sidebar fl" style="display: none">
        <ul class="list-item">
            <li class="f20">
                <a href="manageUsers"><i class="fa fa-bars fa-lg mr-10"></i>权限管理</a>
            </li>
            <li class="f20">
                <a href="manageData"><i class="fa fa-database fa-lg mr-10"></i>数据管理</a>
            </li>

            <li class="f20 active">
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
            <div class="admin-container-content-bread f20">底图配置</div>
            <div class="admin-container-content-main">
                <form enctype="multipart/form-data" id="picForm">
                    <input type="file" name="file">
                    <#--<input type="submit" value="upload"/>-->
                </form>

                <input type="button" class="am-btn-primary" id="picUpload" value="上传图片">

                <#--projectPrefix + projectName 组成数据库中 projectName-->
                <#--<input type="text" id="projectPrefix" value="" placeholder="请输入地域">-->
                <#--<input type="text" id="projectName" value="" placeholder="请输入项目名称">-->
                <input type="text" id="level3name" value="" placeholder="三级目录名称">

                <input type="button" class="am-btn-primary" id="startConf" value="配置">
                <input type="button" class="am-btn-primary" id="stopConf" value="完成">
                <input type="button" class="am-btn-primary" id="resetConf" value="重置">

                <img src="../assets/images/cold.jpg" alt="" id="confPic"">
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
<script src="../assets/bower_components/amazeui/dist/js/amazeui.min.js"></script>
<script src="../assets/js/auth.js"></script>
<script src="../assets/js/adminPicConfig.js"></script>
</body>
</html>
