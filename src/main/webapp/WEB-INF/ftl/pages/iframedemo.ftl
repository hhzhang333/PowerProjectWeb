<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>iframedemo</title>
</head>
<body style="background-color:#ccc;">
	<h1>hello iframe demo</h1>
	<p id="tips"></p>
	<input type="button" value="sendParentMsg" id="sendParentMsg">
	<script src="../bower_components/jquery/dist/jquery.min.js"></script>

	<script src="../bower_components/MessengerJS/messenger.js"></script>
	<script>
		(function(){
			var messenger = new Messenger('sub-frame', 'power');
			messenger.addTarget(window.parent, 'parent');
			// iframe中 - 监听消息
			// 回调函数按照监听的顺序执行
			messenger.listen(function(msg){
			    //alert("sub-frame 收到消息: " + msg);
			    $('#tips').text(msg)
			});

			window.messenger = messenger;
		})()

		$('#sendParentMsg').on('click',function(e){
			//alert(1)
			messenger.targets['parent'].send("message from sub-frame: test" );
			//alert(2)
		})

		/*
		$(window).on('click',function(e){
			console.log(e);
		})
		*/
		
	</script>
</body>
</html>