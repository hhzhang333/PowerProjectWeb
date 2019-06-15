<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <title>数据管理</title>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/assets/css/base.css" />
    <link rel="stylesheet" href="/assets/css/detail-common.css"/>
</head>
<body>
<!--TODO 页头导航-->
<header class="detailHeader fn-clear">
    <#--<a href="javascript:void(0)" class="logo fn-left">-->
        <#--<img src="/assets/image/subPage/logo.png" alt="" width="370" height="50">-->
    <#--</a>-->
    <div id="logo" style="float: left">
        <img src="/assets/image/plogo.png" style="margin-left: 22px; margin-top: -9px; width: 100px; height: 75px; float: left">
        <div id="pre-title" style="margin: 1px 0 0 21px; float: left;"><span style="font-size: 41px; color: white;">国家能源集团</span></div>
    </div>
    <ul class="detailHeader-nav">
        <li class="nav_menu" data-code='index'><a href="javascript:void(0)">系统首页</a></li>
        <li class="nav_menu" data-code='reporter'><a href="javascript:void(0)">报表查询</a></li>
        <li class="nav_menu" data-code='warn'><a href="javascript:void(0)">报警管理</a></li>
        <li class="nav_menu" data-code='x'><a href="javascript:void(0)">辅助功能</a></li>
        <li class='admin' style="position: relative;"><a href="javascript:void(0)">Admin<span class="caret"></span></a><a href="javascript:void(0)" class='exit'>登出</a></li>
    </ul>
    <div class="detailHeader-dailyMsg fn-right">
        <div class="msgImg"><img src="/assets/image/detail/weather.png"/></div>
        <div class="msgCaption">
            多云<br />1&#8451;-7&#8451;
        </div>
        <div class="msgTime">
            <div class="time">
                09:56
            </div>
            <div class="ymd" style="font-size: 18px;">
                <h3>星期一<br /><small>2016-1-1</small></h3>
            </div>
        </div>
    </div>
</header>
<section class="detail-main fn-clear">
    <div class="detail-main-left">
        <ul class="itemList">
            <li class='saveEng'>
                <div class="title">
                    <span>节能管理</span>
                </div>
                <div class="content">
                    <span class="active" data-title='能耗计量' data-content='pages/powerManagement' data-code='NHJL'><i></i>能耗计量</span>
                </div>
            </li>
            <li class='dev'>
                <div class="title">
                    <span>设备管理</span>
                </div>
                <div class="content">
                    <ul>
                        <li class='warm' data-title='暖通空调' data-content='pages/cold' data-code='NTKT'><i></i>暖通空调</li>
                        <li class='drain' data-title='给水排水' data-content='x' data-code='GSPS'><i></i>给水排水</li>
                        <li class="light" data-title='公共照明' data-content='x' data-code='GGZM'><i></i>公共照明</li>
                        <li class="ele" data-title='电梯运行' data-content='x' data-code='DTYX'><i></i>电梯运行</li>
                        <li class="var" data-title='变配电' data-content='x' data-code='BPD'><i></i>变配电</li>
                        <li class="gas" data-title='供燃气' data-content='x' data-code='GRQ'><i></i>供燃气</li>
                    </ul>
                </div>
            </li>
            <li class="safe">
                <div class="title">
                    <span>安防管理</span>
                </div>
                <div class="content">
                    <ul>
                        <li class='video' data-title='视频监控' data-content='x' data-code='SPJK'><i></i>视频监控</li>
                        <li class='door' data-title='门禁管理' data-content='x' data-code='MJGL'><i></i>门禁管理</li>
                        <li class='patrol' data-title='电子巡更' data-content='x' data-code='DZXL'><i></i>电子巡更</li>
                    </ul>
                </div>
            </li>
            <li class='fire'>
                <div class="title">
                    <span>消防管理</span>
                </div>
                <div class="content">
                    <span data-title='消防系统' data-content='x' data-code='XFXT'><i></i>消防系统</span>
                </div>
            </li>
        </ul>
    </div>
    <div class="detail-main-right" style="width:100%;margin-left: 246px;">
        <div class="summary">
            <h1 id='sub_title'>能耗计量</h1>
            <div id='sub_content'>
                <p><span>图表展示</span></p>
                <p style="display:none"><span>图表展示</span></p>
            </div>
        </div>
        <div class="showImg" style="width:79%">
            <iframe src="pages/powerManagement" frameborder="0" style="height:100%;width:100%" id='content_iframe'></iframe>
        </div>

    </div>
    <iframe src="warnManagement" frameborder="0" style="height:900px;width:100%;display:none;" id='warn_iframe'></iframe>
    <iframe src="admin/downloadReporter" frameborder="0" style="height:900px;width:100%;display:none;" id='reporter_iframe'></iframe>
</section>
<script src='/assets/js/jquery-1.8.3.min.js'></script>
<script src="/assets/bower_components/moment/moment.js"></script>
<script src='/assets/js/base.js'></script>
</body>
</html>
