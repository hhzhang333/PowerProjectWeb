$(document).ready(function () {
    console.log( "admin pic config");

    $('#picUpload').on("click", function () {
        //console.log("pic upload");
        //上传进度函数
        function progressHandlingFunction(e){
            if(e.lengthComputable){
                //$('progress').attr({value:e.loaded,max:e.total});
                console.log({value:e.loaded,max:e.total})
            }
        }
        function completeHandler(e) {
            // body...
            // console.log(e);
            $('img').attr('src',e);
            //e.hash
        }


        var formData = new FormData($('#picForm')[0]);
        $.ajax({
            url: 'picUpload',  //Server script to process data
            type: 'POST',
            xhr: function() {  // Custom XMLHttpRequest
                var myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){ // Check if upload property exists
                    myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
                }
                return myXhr;
            },
            //Ajax events
            //beforeSend: beforeSendHandler,
            success: completeHandler,
            //error: errorHandler,
            // Form data
            data: formData,
            //Options to tell jQuery not to process data or worry about content-type.
            cache: false,
            contentType: false,
            processData: false
        });

    });


    var confPicPos = $("#confPic").offset();
    confPicPos["width"] = $("#confPic").width();
    confPicPos["height"] = $("#confPic").height();

    var Pos = (function () {
        var posArray = [];



        return {
            addPos:addPos,
            getLength:getLength,
            getPos:getPos,
            dumpPos:dumpPos,
            resetPos:resetPos,
        }

        function addPos(posItem) {
            posArray.push(posItem)
        }
        function getLength() {
            return posArray.length;
        }
        function getPos() {
            return posArray;
        }
        function dumpPos() {
            console.table(posArray);
        }
        function resetPos() {
            posArray = [];
        }



    })();
    // console.dir(Pos)



    $("#startConf").on('click',function () {
        //bind img event
        $('#confPic').on('click',function (e) {
            //console.log(e.offsetX,e.offsetY)
            //console.log(e.clientX-confPicPos.left,e.clientY-confPicPos.top)
            //console.log(e.offsetX/confPicPos.width,e.offsetY/confPicPos.height)
            var xPercent = (e.offsetX/confPicPos.width).toFixed(2);
            var yPercent = (e.offsetY/confPicPos.height).toFixed(2);
            var confLayerId = layer.open({
                content: '<div class="popup_PicConf"><div><label for="xPercent">xPercent:</label><input type="text" value="'+xPercent+'" id="xPercent"></div><div><label for="yPercent">yPercent:</label><input type="text" value="'+yPercent+'" id="yPercent"></div><div><label for="posName">位置名:</label><input type="text" id="posName"></div><div><label for="deviceId">设备ID:</label><input type="text" id="deviceId"></div></div>'
                ,btn: ['确定', '取消']
                ,yes: function(index, layero){
                    //按钮【按钮一】的回调
                    //console.log('确定')

                    //console.log({
                    //    xPercent:$('#xPercent').val(),
                    //    yPercent:$('#yPercent').val(),
                    //    posName:$('#posName').val(),
                    //})
                    Pos.addPos({
                        xPercent:$('#xPercent').val(),
                        yPercent:$('#yPercent').val(),
                        posName:encodeURI($('#posName').val()),
                        deviceId:$('#deviceId').val()
                    })
                    //Pos.dumpPos()
                    layer.close(confLayerId)

                },btn2: function(index, layero){
                    //按钮【按钮二】的回调
                    layer.close(confLayerId)
                    //console.log("取消")
                }
                ,cancel: function(){
                    //右上角关闭回调
                    //console.log("cancel")
                }
            });
        })
    })

    $("#stopConf").on('click',function () {
        //unbind img event
        $('#confPic').off('click');
        //layer tips
        Pos.dumpPos()


        //var projectPrefix = $('#projectPrefix').val();
        //var projectName = $('#projectName').val();
        //projectName = projectPrefix+'-'+projectName;
        var level3name = $('#level3name').val();

        var posArr = Pos.getPos();
        var picUrl = $('#confPic').attr('src');

        $.ajax({
            url:'addPicConfItem',
            data:{
                level3name:level3name,
                url:picUrl,
                conf:JSON.stringify(posArr)
            },
            success:function (d) {
                console.log(d);
                if(d == "success"){
                    alert("配置成功")
                }else{
                    alert("配置失败")
                }
            }
        })


    })

    $("#resetConf").on('click',function () {
        //empty array
        console.log("empty array");
        Pos.resetPos();
    })
});

