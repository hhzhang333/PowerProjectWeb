<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>报警管理</title>
    <link rel="stylesheet" href="assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="assets/css/chartCommon.css"/>
</head>
<body>

<div class="am-g" style="width: 95%">
    <div class="am-u-sm-6">
        <div class="am-g">
            <div class="am-u-sm-3 warn-font">报警概况</div>
            <div class="am-u-sm-9 line-position">
                <div class="line-style"></div>
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-6">
                <img src="assets/image/level2.png" class="img-position">
            </div>
            <div class="am-u-sm-6">
                <img src="assets/image/level1.png" class="img-position">
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-6">
                <div class="font-position">7</div>
            </div>
            <div class="am-u-sm-6">
                <div class="font-position">3</div>
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-6">
                <div class="label-position">火灾报警</div>
            </div>
            <div class="am-u-sm-6">
                <div class="label-position">故障报警</div>
            </div>
        </div>
    </div>
    <div class="am-u-sm-6">
        <div class="am-g">
            <div class="am-u-sm-3 warn-font">报警记录</div>
            <div class="am-u-sm-9 line-position">
                <div class="line-style"></div>
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-10" id="warnRecord"></div>
            <div class="am-u-sm-2">
                <button class="am-btn am-btn-primary am-radius data-cho-p">日</button>
                <button class="am-btn am-btn-default am-radius data-cho-p">月</button>
                <button class="am-btn am-btn-default am-radius data-cho-p">年</button>
            </div>
        </div>
    </div>
</div>

<div class="am-g" style="width: 95%">
    <div class="am-g">
        <div class="am-u-sm-2 warn-font">
            实时报警
        </div>
        <div class="am-u-sm-10 line-position">
            <div class="line-style"></div>
        </div>
    </div>

    <table class="am-table am-table-bordered am-table-radius am-table-striped am-table-hover">
        <thead>
        <tr class="am-primary">
            <th>报警日期</th>
            <th>报警时间</th>
            <th>位置</th>
            <th>设备</th>
            <th>报警描述</th>
            <th>报警等级</th>
            <th>报警处理</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        <tr>
            <td>2016-7-6</td>
            <td>13:34:32</td>
            <td>楼A</td>
            <td>电梯</td>
            <td>按键失效</td>
            <td>3</td>
            <td>已处理</td>
        </tr>
        </tbody>
    </table>
    <div class="am-cf">
        <div class="am-fr">
            <ul class="am-pagination">
                <li class="am-disabled"><a href="#">«</a></li>
                <li class="am-active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">»</a></li>
            </ul>
        </div>
    </div>
</div>

<!--<p id="tips"></p>-->
<!--<input type="button" value="sendParentMsg" id="sendParentMsg">-->
<script src="assets/bower_components/jquery/dist/jquery.min.js"></script>
<script src="assets/bower_components/MessengerJS/messenger.js"></script>
<script src="assets/bower_components/highcharts/highcharts.js"></script>
<script>
    $(function () {
        $('#warnRecord').highcharts({
            chart: {
                type: 'line'
            },
            title: {
                text: '报警记录'
            },
            xAxis: {
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12','14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24']
            },
            yAxis: {
                title: {
                    text: '报警次数(次)'
                }
            },
            tooltip: {
                enabled: false,
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'次';
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
            series: [{
                name: '报警次',
                data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6,3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
            }]
        });
    });
//    (function(){
//        var messenger = new Messenger('sub-frame', 'power');
//        messenger.addTarget(window.parent, 'parent');
//        // iframe中 - 监听消息
//        // 回调函数按照监听的顺序执行
//        messenger.listen(function(msg){
//            //alert("sub-frame 收到消息: " + msg);
//            $('#tips').text(msg)
//        });
//
//        window.messenger = messenger;
//    })()
//
//    $('#sendParentMsg').on('click',function(e){
//        //alert(1)
//        messenger.targets['parent'].send("message from sub-frame: test" );
//        //alert(2)
//    })
</script>
</body>
</html>