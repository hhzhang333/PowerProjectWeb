$(function () {
    var auth = $("#authority").val();
    if(auth == "tech"){
        $("ul>li:nth-child(1)").hide();
    }else{
        $("ul>li:gt(1)").hide();
        $("ul>li:nth-child(5)").show();
    }
})

$('#logout').on('click',function () {
    window.location.href = '../index';
})