<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据管理</title>
    <link rel="stylesheet" href="/assets/css/base.css" />
    <link rel="stylesheet" href="/assets/css/subPage-common.css"/>
    <style>
        .none{display: none;}
    </style>
</head>
<body>
<header class='subPage-header fn-clear'>
    <a href="javascript:void(0)" class="logo fn-left">
        <img src="/assets/image/subPage/logo.png" alt="南京维蒙得节能环保技术有限公司" title="南京维蒙得节能环保技术有限公司" width="370" height="50"/>
    </a>
    <h1>后台管理系统</h1>
    <h1 class="exit" style="float: right;margin-right: 20px;font-weight: normal;font-size: 24px;">登出</h1>
</header>
<section class="subPage-main fn-clear">
    <div class="subPage-main-left">
        <ul class="subPage-main-itemList">
            <#if authority == 'admin'>
            <!-- 管理员部分 -->
            <li class="check active"><a href="javascript:void(0)" data-title='能耗监测' data-content='/powerMonitor'>能耗监测</a></li>
            <li class='compare'><a href="javascript:void(0)" data-title='能耗比较' data-content='/admin/powerCompare'>能耗比较</a></li>
            <li class="power"><a href="javascript:void(0)" data-title='权限管理' data-content='/admin/manageUsers'>权限管理</a></li>
            <li class="data"><a href="javascript:void(0)" data-title='数据管理' data-content='/admin/manageData'>数据管理</a></li>
            <#elseif authority == 'tech'>
            <!-- 技术人员部分 -->
            <li class="data2 active"><a href="javascript:void(0)" data-title='数据管理' data-content='/admin/manageData'>数据管理</a></li>
            <li class="pic"><a href="javascript:void(0)" data-title='底图配置' data-content='/admin/picConfig'>底图配置</a></li>
            <li class="formula"><a href="javascript:void(0)" data-title='公式配置' data-content='/admin/formulaConfig'>公式配置</a></li>
            <li class="reporter"><a href="javascript:void(0)" data-title='下载报表' data-content='/admin/downloadReporter'>下载报表</a></li>
            </#if>
        </ul>
    </div>
    <div class="subPage-main-right clearfix">
        <div class="subPage-showBox">
            <h2 id='content-title'>数据管理</h2>
            <div id='content'>
                <iframe src="<#if authority == 'admin'>/powerMonitor<#elseif authority == 'tech'>/admin/manageData</#if>" frameborder="0" style="width:100%;height:700px;overflow-y: scroll;" id='content_iframe'></iframe>

            </div>
        </div>
    </div>
</section>
</body>
<script src="/assets/js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="/assets/js/subPage-base.js" type="text/javascript" charset="utf-8"></script>
</html>
