(function($){
	/*
	$('.sidebar-list span').delegate('.sidebar-list',"click",function(){
		console.log(this);
	});

	$('.sidebar-list').on('click',function(e){
		console.log(e.target)
	})
	*/
	
	var setSubmenu = (function(){

		var $sidebar_submenu = $('#sidebar-submenu');


		function setSubTitle(subtitle){
			$sidebar_submenu.find('.sidebar-submenu-title>span').html(subtitle);

		}
		function setSubContent(){
			console.error('unfinished')
		}

		return {
			setSubTitle:setSubTitle,
			setSubContent:setSubContent
		}
	})();

	var config = (function(){
		var map = {
			'消防系统':{},

			'视频监控':{},
			'防盗报警':{},
			'消防系统':{},
			'电子巡更':{},


			'暖通空调':{},
			'给水排水':{},
			'变配电':{},
			'公共照明':{},
			'夜景照明':{},
			'电梯运行':{},

			'客流统计':{},
			'停车管理':{},
			'信息发布':{},
			'背景音乐':{},


			'背景音乐':{},


		}
	})();

	//初始化messagerjs
	(function(){
		var messenger = new Messenger('parent', 'power');
		messenger.addTarget(document.getElementById('sub-frame').contentWindow, 'sub-frame');
		messenger.listen(function (msg) {
	        console.log(msg);
	    });
		window.messenger = messenger;
	})()
	$('.sidebar-list').delegate('span','click',(function(){
		var old_active = $('.sidebar-list span.active');
		return function(e){
			//console.log($(this).text())
			
			old_active.removeClass('active');
			$(this).addClass('active');
			old_active = $(this);


			//设置子标题
			setSubmenu.setSubTitle($(this).text())
			switch($(this).text()){
				case '暖通空调':
					console.log('暖通空调')
					$("#sub-frame").attr('src',"pages/cold");
				break;
			}

			//设置submenu内容部分

			//触发iframe变化
			messenger.targets['sub-frame'].send($(this).text())
		}
	})())
	
	//messenger.targets['sub-frame'].send("test")
	$(document).on('click','iframe',function(e){
		console.log(e);
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
	$('#logout').on('click',function () {
		window.location.href = '../index';
	})
})(jQuery);