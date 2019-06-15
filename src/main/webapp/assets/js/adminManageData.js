


$(document).ready(function () {
    var dt_startTime = 0,dt_endTime = 0;
    var isMultiParams = false;

    setTimeout(function () {
        $("#timeFilter").insertBefore( $(".am-g.am-datatable-hd").find('.am-u-sm-6:nth-child(1)')).show()
    },500)
    $("#isTimeFiltered").on('click',function () {
        console.log($(this).is(':checked'))
        isMultiParams = $(this).is(':checked');
        if(isMultiParams){
            $('#timeFilter>input[type=text]').show();
        }else{
            //$('#timeFilter>input[type=text]').hide();
            //$('.dtFilter').remove();
        }
    })
    table = $("#my-table").DataTable({
        "processing": true,
        "serverSide": true,
        // "ajax": "getDevmessByPages?pageNum=1&pageSize=100",
        "ajax": "dtServerProccess",
        columnDefs:[{
            "targets": 3,
            render:function (data, type, full, meta) {
                //console.log(data, type, full, meta)
                return moment(data).format("YYYY-MM-DD  h:mm:ss");

            }
        }],
        "fnServerParams": (function () {
            /*
            var includeUsuallyIgnored = $("#include-checkbox").is(":checked");
            aoData.push({name: "includeUsuallyIgnored", value: includeUsuallyIgnored});
            */
            // aoData.push({name:'test',value:"haha"})
            //if(aoData['search']['value'])
            var count = 0;
            return function (aoData) {
                if(isMultiParams && count!= 0){
                    aoData['search']['customvalue'] = JSON.stringify({
                        type:'2',
                        firstParam:dt_startTime,
                        secondParam:dt_endTime
                    });
                }else{
                    aoData['search']['customvalue'] = JSON.stringify({
                        type:'1',
                        param:aoData['search']['value']
                    });
                }

                console.log(aoData['search'])
                count++;
            }
        })()
    })

    table.on('draw',function () {
        console.log("table redrawn")
    })
    table.on('dblclick','tr',function (e) {
        var $tr = $(this);
        var $id = $tr.find('td:nth-child(1)');
        var $name = $tr.find('td:nth-child(2)');
        var $value = $tr.find('td:nth-child(3)');
        var $time = $tr.find('td:nth-child(4)');
        var $quality = $tr.find('td:nth-child(5)');
        console.log({
            id:$id.html(),
            name:$name.html(),
            vlaue:$value.html(),
            time:$time.html(),
            quality:$quality.html(),
        });

        //<input type="text" value="2015-02-15 21:05" id="datetimepicker" class="am-form-field">

        var layer_content = '<div class="layer-popup">' +
            '<div><label for="popup_id">id</label><input type="text" id="popup_id" value="'+$id.html()+'" disabled></div> ' +
            '<div><label for="popup_name">name</label><input type="text" id="popup_name" value="'+$name.html()+'" disabled></div> ' +
            '<div><label for="popup_value">value</label><input type="text" id="popup_value" value="'+$value.html()+'"></div> ' +
            '<div><label for="time">time</label><input type="text" id="popup_time" value="'+$time.html()+'"></div> ' +
            // '<div><label for="time">time</label><input type="text" id="time" value="'+$time.html()+'"></div> ' +
            '<div><label for="popup_quality">quality</label><input type="text" id="popup_quality" value="'+$quality.html()+'"></div> ' +
            '<div><input type="button" value="确定" id="popup_btn_submit" class="am-fr mb-10 mt-10"></div>'+
            '</div>'
        var modal1 = layer.open({
            type: 1,
            // area: ['350px', '380px'],
            shadeClose: true, //点击遮罩关闭
            content: layer_content,
            success:function () {
                $('#popup_time').datetimepicker({
                    format: 'yyyy-mm-dd hh:ii:ss'
                });
                $('#popup_btn_submit').on('click',function () {
                    console.log($('#popup_id').val(),$('#popup_name').val(),$('#popup_time').val(),$('#popup_quality').val())
                    $.ajax({
                        type:'GET',
                        url:'modifyDevmessItemById',
                        data:{
                            id:$('#popup_id').val(),
                            name:$('#popup_name').val(),
                            value:$('#popup_value').val(),
                            time:$('#popup_time').val(),
                            quality:$('#popup_quality').val()
                        },
                        success:function (d) {
                            if(d == 'success'){
                                layer.close(modal1)
                                alert("修改成功")
                                //layer.alert('修改成功');
                                window.location.reload()
                            }else{
                                 alert("修改失败")
                                //layer.msg('修改失败');
                            }
                        }
                    })
                })
            }
        });
    })



    $(".dtFilter")
        .datetimepicker({
            format: 'yyyy-mm-dd hh:ii'
        })
        .on('changeDate',(function () {
            var count = 0;
            return function (ev) {
                //moment(ev.date.valueOf()).format("YYYY-MM-DD  h:mm:ss")
                console.log(ev.target.id);
                console.log(ev.date.valueOf())
                if(ev.target.id === "dtStartTime")
                    dt_startTime = moment(ev.date.valueOf()).format("YYYY-MM-DD  hh:mm:ss")+".000";
                if(ev.target.id == "dtEndTime")
                    dt_endTime = moment(ev.date.valueOf()).format("YYYY-MM-DD  hh:mm:ss")+".000";
                count++;
                //$(this).remove()
                if(count%2 == 0 && isMultiParams){
                    //console.log("should redraw table")


                    table.draw();
                    $("#isTimeFiltered").trigger("click")



                }

            }
        })());

    $("#addDevmessItem").on('click',function () {
        var layer_content = '<div class="layer-popup">' +
            '<div><label for="popup_devmess_name">name</label><input type="text" id="popup_devmess_name" value=""></div> ' +
            '<div><label for="popup_devmess_value">value</label><input type="text" id="popup_devmess_value" value=""></div> ' +
            '<div><label for="popup_devmess_time">time</label><input type="text" id="popup_devmess_time" value=""></div> ' +
            '<div><label for="popup_devmess_quality">quality</label><input type="text" id="popup_devmess_quality" value=""></div> ' +
            '<div><input type="button" value="确定" id="popup_btn_submit_devmess" class="am-fr mb-10 mt-10"></div>'+
            '</div>'
        var modal1 = layer.open({
            type: 1,
            // area: ['350px', '380px'],
            shadeClose: true, //点击遮罩关闭
            content: layer_content,
            success:function () {
                $('#popup_devmess_time')
                    .datetimepicker({
                        format: 'yyyy-mm-dd hh:ii:ss'
                    })
                    .on('changeDate',function (e) {
                        console.log(e.date.valueOf());
                    });
                $('#popup_btn_submit_devmess').on('click',function () {

                    console.log($('#popup_devmess_name').val(),$('#popup_devmess_value').val(),$('#popup_devmess_time').val(),$('#popup_devmess_quality').val())
                    
                    $.ajax({
                        type:'GET',
                        url:'../devmess/addDevmessItem',
                        data:{
                            name:$('#popup_devmess_name').val(),
                            value:$('#popup_devmess_value').val(),
                            time:$('#popup_devmess_time').val(),
                            quality:$('#popup_devmess_quality').val(),
                        },
                        success:function (d) {
                            if(d == 'success'){
                                layer.close(modal1)
                                alert("增加成功")
                                //layer.alert('修改成功');
                                window.location.reload()
                            }else{
                                alert("增加失败")
                                //layer.msg('修改失败');
                            }
                        }
                    })
                    
                })
            }
        });
    })

    $('#csvUpload').on('click',function () {
        function progressHandlingFunction(e){
            if(e.lengthComputable){
                //$('progress').attr({value:e.loaded,max:e.total});
                console.log({value:e.loaded,max:e.total})
            }
        }
        function completeHandler(e) {
            // body...
            // console.log(e);
            console.log(e);
            //e.hash
            if(e == 'success')
                alert("批量导入成功");
            else
                alert("批量导入失败");
            // window.location.reload();
        }


        var formData = new FormData($('#csvForm')[0]);
        $.ajax({
            url: 'csvUpload',  //Server script to process data
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
    })
});