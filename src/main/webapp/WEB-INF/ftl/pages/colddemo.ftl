<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>codedemo</title>
    <link rel="stylesheet" href="../assets/bower_components/amazeui/dist/css/amazeui.min.css"/>
    <link rel="stylesheet" href="../assets/css/colddemo.css">
    <style>

        #deviceDetailInfo label{
            width: 100px;
        }

        .clicklabel{
            position: absolute;
            /*top: 95.3625px;
            left: 56.08px;*/
            background: #39A6AE;
            width: 100px;
            height: 50px;
            text-align: center;
            line-height: 50px;
        }
    </style>
</head>
<body style="background-color:#E6E6E6">
<div class="container">
    <div>
	<#--<input type="button" value="矫正坐标" id="record" style="position: absolute;">-->
        <img src="../pic/1469152710277" alt="" style="width:100%" data-picconfid="10" id="map">
    </div>
    <div class="container" style="overflow: hidden;display: none">

        <div class="am-g"  style="border-top:5px solid #CBCBCB;font-size:20px;">
            <div class="am-u-sm-12">
                <span style="" class="feature_title">冷站系统参数</span>
            </div>

            <div class="am-g">
                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>集水阀压力</span>
					<span>20</span>
					<span>Pa</span>
				</span>
                </div>

                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷冻水总供应水温度</span>
					<span>7</span>
					<span>&#8451;</span>
				</span>
                </div>


                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷站机房温度</span>
					<span>28</span>
					<span>&#8451;</span>
				</span>
                </div>


                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>分集水器压差</span>
					<span>30</span>
					<span>mH2O</span>
				</span>
                </div>


                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷冻水总回水温度</span>
					<span>12</span>
					<span>&#8451;</span>
				</span>
                </div>


                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷站机房湿度</span>
					<span>60</span>
					<span>%</span>
				</span>
                </div>

                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷冻水流量</span>
					<span>30</span>
					<span>m3/h</span>
				</span>
                </div>

                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷却水总供水温度</span>
					<span>37</span>
					<span>&#8451;</span>
				</span>
                </div>

                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>室外温度</span>
					<span>28</span>
					<span>&#8451;</span>
				</span>
                </div>

                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>旁通阀位</span>
					<span>5</span>
					<span>%</span>
				</span>
                </div>
                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>冷却水总回水温度</span>
					<span>32</span>
					<span>&#8451;</span>
				</span>
                </div>
                <div class="feature am-u-sm-4 am-u-md-4 am-u-lg-4">
				<span>
					<span>室外湿度</span>
					<span>60</span>
					<span>%</span>
				</span>
                </div>
            </div>

        </div>
        <!--
        <div class="am-u-sm-4">
            <span>
                <span>集水阀压力</span>
                <span>20</span>
                <span>Pa</span>
            </span>
        </div>
        <div class="am-u-sm-4">8</div>
        <div class="am-u-sm-4">8</div>
        -->
    </div>
</div>
<script src="../assets/bower_components/jquery/dist/jquery.min.js"></script>
<script src="../assets/bower_components/amazeui/dist/js/amazeui.min.js"></script>
<script src="../assets/bower_components/MessengerJS/messenger.js"></script>
<script src="../assets/bower_components/layer/layer.js"></script>
<script>

    var pos;
    // Rackspace Hosting
    var imgWidth = 1402,imgHeight = 635.75;
    //var dimgWidth = $('img').width(),dimgHeight = $('img').height();
    var dimgWidth ,dimgHeight;
    var deltax = 100/2, deltay = 50/2;
    $.ajax({
        url:'../admin/getPicConfItemByName',
        data:{
            picConfName:'LNTYY-NTKT'//'暖通空调'
        },
        success:function (d) {
            d = JSON.parse(d);
            //<img src="/pic/1469152710277" alt="" style="width:100%" data-picconfid="10" id="map">
            $("img")
                    .on('load',function () {
                        console.log('img loaded')
                        dimgWidth = $('img').width(),dimgHeight = $('img').height()
                        console.log('w:',dimgWidth,'h:',dimgHeight)
                        getPicConf();
                    })
                    .attr('src',d['url'])
                    .attr('data-picconfid',d['id']);


        }
    })


    //var pos = [];
    //非矫正模式
    /*
    var pos = [
        {
            "x": 445/imgWidth*dimgWidth,
            "y": 29/imgHeight*dimgHeight,
            "name":"1号冷却塔",
            "cb":function(){
                console.log("1号冷却塔")
                alert("1号冷却塔")
            }
        },
        {
            "x": 653/imgWidth*dimgWidth,
            "y": 60/imgHeight*dimgHeight,
            "name":"2号冷却塔",
            "cb":function(){
                console.log("2号冷却塔")
                alert("2号冷却塔")
            }
        },
        {
            "x": 880/imgWidth*dimgWidth,
            "y": 109/imgHeight*dimgHeight,
            "name":"3号冷却塔",
            "cb":function(){
                console.log("3号冷却塔")
                alert("3号冷却塔")
            }
        },
        {
            "x": 1135/imgWidth*dimgWidth,
            "y": 149/imgHeight*dimgHeight,
            "name":"4号冷却塔",
            "cb":function(){
                console.log("4号冷却塔")
                alert("4号冷却塔")
            }
        }
    ];
    */

    function drawLabel(x,y,title) {
        var _labelhtml = '<span style="top:'+(y-deltay)+'px;left: '+(x-deltax)+'px" class="clicklabel">'+title+'</span>';
        $(_labelhtml).insertAfter('#map')
    }

    function getPicConf() {
        $.ajax({
            url:'../admin/getPicConfItem',
            data:{
                picConfId:$('img').data('picconfid')
            },
            success:function (d) {
                //console.dir(JSON.parse(d))
                d = JSON.parse(d)
                var conf = JSON.parse(d['conf']);
                window.pos = conf.map(function (e,i) {
                    drawLabel(e['xPercent']*dimgWidth,e['yPercent']*dimgHeight,decodeURI(e['posName']))
                    return{
                        x:e['xPercent']*dimgWidth,
                        y:e['yPercent']*dimgHeight,
                        name:decodeURI(e['posName']),
                        deviceId:e['deviceId']
                    }
                })


                console.table(window.pos)
                //console.log(decodeURI(conf))

            }
        })
    }
    var isRecord = false;
    $('#record').on('click',function(e){
        isRecord = !isRecord;
        if(isRecord)
            console.log('矫正坐标开始');
        else
            console.log(JSON.stringify(pos,null,4));
        e.stopPropagation();
    })
    $(document).on('click','img,span',function(e){
        //console.log(e);
        //console.log('x',e.clientX,'y',e.clientY)
        //pos.push()
        var that = this;
        if(isRecord){
            console.log('push','x',e.clientX,'y',e.clientY)
            pos.push({'x':e.clientX,'y':e.clientY})

        }else{
            var clientX = e.clientX;
            var clientY = e.clientY;

            var target = window.pos.filter(function(e,i){
                // console.dir(e);
                // console.dir(i);
                console.log(clientX,clientY)
                if(e.x-deltax<= clientX && e.x+deltax>= clientX && e.y-deltay<= clientY && e.y+deltay>= clientY ){
                    //e.cb.apply(null);
                    console.log(e)
                    //var deviceId = e['deviceId'];
                    $.ajax({
                        url:'../devmess/getDeviceInfo',
                        data:{
                            deviceId:e['deviceId']
                        },
                        success:function (d) {
                            d = JSON.parse(d);
                            var deviceInfo = {};
                            var _contentHtml = '<div id="deviceDetailInfo">';

                            for(var key in d){
                                deviceInfo[decodeURI(key)] =d[key] ;
                                _contentHtml = _contentHtml + '<div><label for="'+decodeURI(key)+'">'+decodeURI(key)+':</label><span id="'+decodeURI(key)+'">'+d[key]+'</span></div>';
                            }
                            _contentHtml = _contentHtml + '</div>';

                            console.dir(deviceInfo);
                            //layer.tips(e['deviceId']+"详细信息", '#map');
                            layer.open({
                                type: 1,
                                title:e['deviceId']+"详细信息",
                                offset:[e.y+'px',e.x+'px'],
                                content:_contentHtml
                            })
                        }
                    })
                    //return true;
                }
            });



        }

        e.stopPropagation();
        e.preventDefault();
        return false;
    })



</script>
</body>
</html>