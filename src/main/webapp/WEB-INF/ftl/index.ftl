<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理系统</title>
    <link rel="stylesheet" href="assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="assets/css/chartCommon.css"/>
</head>
<body id="start_image" style="background: url('/assets/image/start.png'); background-size: cover;">
<div class="am-g start_shadow" style="padding-bottom: 20px">
    <div id="logo" class="col-xl-3 col-lg-3 col-sm-6 col-12 row" style="float: left">
        <img src="/assets/image/plogo.png" style="margin-left: 80px; margin-top: 20px; width: 100px; height: 75px; float: left">
        <div id="pre-title" style="margin: 21px 0 0 21px; float: left"><span style="font-size: 41px">国家能源集团</span></div>
    </div>
    <div id="pro-name" class="col-xl-6 col-lg-6 col-sm-6 col-12" style="margin: 21px 0 0 118px; float: left">
        <span style="font-size: 41px">CIGS-BIPV智控中心能耗展示云平台系统</span>
    </div>
    <div id="blank" class="col-xl-3 col-lg-3"></div>
</div>
<div class="am-g start_shadow" style="position: fixed; bottom: 0;">
    <div class="header_pos" style="margin-left: 100px" data-am-modal="{target: '#login-modal', closeViaDimmer: 0, width: 400, height: 225}">
        <img src="assets/image/normal.png" style="height: 95px">
        <div style="margin-left: 25px;">登录</div>
    </div>
    <#--<div class="header_pos" data-am-modal="{target: '#login-modal', closeViaDimmer: 0, width: 400, height: 225}">-->
        <#--<img src="assets/image/tech.png" style="height: 95px">-->
        <#--<div style="margin-left: 18px">技术用户</div>-->
    <#--</div>-->
    <#--<div class="header_pos" data-am-modal="{target: '#login-modal', closeViaDimmer: 0, width: 400, height: 225}">-->
        <#--<img src="assets/image/admin.png" style="height: 95px">-->
        <#--<div style="margin-left: 18px">管理用户</div>-->
    <#--</div>-->
    <div class="start_time start_time_sub">
        <div id="week">星期一</div>
        <div id="date">2016-1-1</div>

    </div>
    <div class="start_time" id="time">09:56</div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="login-modal">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">登录
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <div class="am-form am-form-horizontal">
                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label">用户名</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="username" placeholder="输入用户名">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label">密码</label>
                    <div class="am-u-sm-9">
                        <input type="password" id="password" placeholder="输入密码">
                    </div>
                </div>

                <div class="am-form-group">
                    <div class="am-u-sm-6">
                        <button class="am-btn am-btn-default" id="loginButton">登录</button>
                    </div>
                    <div class="am-u-sm-6">
                        <button class="am-btn am-btn-default" data-am-modal="{target: '#register-modal', closeViaDimmer: 0, width: 400, height: 258}">注册</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="register-modal">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">注册
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <div class="am-form am-form-horizontal">
                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label">用户名</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="reusername" placeholder="输入用户名">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label">密码</label>
                    <div class="am-u-sm-9">
                        <input type="password" id="repassword" placeholder="输入密码">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-3 am-form-label">邮箱</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="email" placeholder="email">
                    </div>
                </div>

                <div class="am-form-group">
                    <div class="am-u-sm-10 am-u-sm-offset-1">
                        <button class="am-btn am-btn-default" id="registerButton">注册</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="assets/bower_components/jquery/dist/jquery.min.js"></script>
<script src="assets/bower_components/amazeui/dist/js/amazeui.min.js"></script>
<script src="assets/bower_components/moment/moment.js"></script>
<script>
    $("#loginButton").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        $("#username").val("");
        $("#password").val("");
        if (username == '') {
            alert("用户名不能为空");
        }
        if (password == "") {
            alert("密码不能为空");
        }
        $.post("login", {username: username, password: password}, function(data) {
            if (data == "normal") {
                location.href = "showGraph";
            }
            if (data == "tech") {
                location.href="admin/index?type=tech";
            }
            if (data == "admin") {
                location.href="admin/index?type=admin";
            }
            if (data == "overview") {
                location.href="start";
            }
//            if (data == "overview") {
//                location.href="admin/powerCompare";
//            }
            if( data == "error"){
                alert("登录失败");
//                alert("登录失败");
            }
        })
    });

    $("#registerButton").click(function () {
        var username = $("#reusername").val();
        var password = $("#repassword").val();
        var email = $("#email").val();
        if (username == '') {
            alert("用户名不能为空");
        }
        if (password == "") {
            alert("密码不能为空");
        }
        if (email == "") {
            alert("邮箱不能为空");
        }
        $.post("register", {username: username, password: password, email: email}, function(data) {
            if(data) {
                location.href="index"
            } else {
                alert("注册失败，用户名已经存在或游戏已经注册");
            }
        })
    });

    (function(){
        var t = document.getElementById('time');
        var w = document.getElementById('week');
        var d = document.getElementById('date');
        var c = {
            'Sunday':'星期天',
            'Monday':'星期一',
            'Tuesday':'星期二',
            'Wednesday':'星期三',
            'Thursday':'星期四',
            'Friday':'星期五',
            'Saturday':'星期六'
        };
        setInterval(function(){
            t.innerHTML = moment().format('HH:mm');
            w.innerHTML = c[moment().format('dddd')];
            d.innerHTML = moment().format('YYYY-M-D');
        },1000);
    })();
</script>
</body>
</html>