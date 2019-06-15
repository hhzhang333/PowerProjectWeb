(function(){
	//var t = document.getElementById('time');
	//var w = document.getElementById('week');
	//var d = document.getElementById('date');
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
		//t.innerHTML = moment().format('hh:mm');
		//w.innerHTML = c[moment().format('dddd')];
		//d.innerHTML = moment().format('YYYY-M-D');
		$('div.time').html(moment().format('HH:mm'));
		$('div.ymd').html('<h3>'+c[moment().format('dddd')]+'<br><small>'+moment().format('YYYY-M-D')+'</small></h3>');
	},20000);
})();

$('.detail-main-left').delegate('.content li,.content span','click',function(){

	var $item = $(this);
	var title = $item.data('title');
	var content_src = $item.data('content');
	var code = $item.data('code');


	if(code != 'NHJL' && code != 'NTKT'){
		return false;
	}
	$('.active').removeClass('active');
	$(this).addClass('active');

	if(content_src != 'x')
		$('#content_iframe').attr('src',content_src);
	/*
	switch(code){
		case 'XFXT':
		case 'GSPS':
		case 'GGZM':
		case 'DTYX':
		case 'GPD':
		case 'GRQ':
		case 'SPJK':
		case 'MJGL':
		case 'DZXG':break;
		case 'XFXT':
			//NHJL
			$('#sub_content').html('<p><span>图表展示</span></p><p><span>图表展示</span></p>');
		break;
		case 'NTKT':
			$('#sub_content').html('<p><span>物业1-1T</span></p><p><span>物业1-2T</span></p><p><span>物业2-3T</span></p><p><span>物业2-4T</span></p><p><span>10KV高压变配电</span></p>');
		break;
	}
	console.log(content_src);
	*/
	if(code == 'NHJL'){
		$('#sub_content').html('<p><span>图表展示</span></p><p><span>图表展示</span></p>');
		$('#sub_title').html(title);
	}else{
		$('#sub_content').html('<p><span>物业1-1T</span></p><p><span>物业1-2T</span></p><p><span>物业2-3T</span></p><p><span>物业2-4T</span></p><p><span>10KV高压变配电</span></p>');
		$('#sub_title').html(title);
	}
});

$('.nav_menu').click(function(){
	var $item = $(this);
	var code = $item.data('code');
	if(code == 'index'){
		$('.detail-main-left').show();
		$('.detail-main-right').show();
		$('#warn_iframe').hide();
		$('#reporter_iframe').hide();
	}
	if(code == 'warn'){
		$('.detail-main-left').hide();
		$('.detail-main-right').hide();
		$('#warn_iframe').show();
		$('#reporter_iframe').hide();
	}
	if(code == 'reporter'){
		$('.detail-main-left').hide();
		$('.detail-main-right').hide();
		$('#warn_iframe').hide();
		$('#reporter_iframe').show();
	}
})



$('.admin>a:first-child').click(function () {
	if($(this).siblings('.exit').css('display')==='none'){
		$(this).siblings('.exit').css('display','block');
	}
	else{
		$(this).siblings('.exit').css('display','none');
	}
});

$('.exit').click(function () {
	window.location.href = '/start';
});