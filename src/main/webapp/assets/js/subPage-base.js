//TODO hover效果
$('.subPage-main-itemList>li').click(function () {
	var $btn = $(this);
	var title = $btn.find('a').data('title');
	var content_src = $btn.find('a').data('content');
	$('#content-title').html(title);
	$('#content_iframe').attr('src',content_src);
	console.log(content_src);
	if(!$(this).hasClass('active')){
		$(this).addClass('active').siblings().removeClass('active')
	}
})

$('.exit').click(function () {
	window.location.href = '/start';
})
