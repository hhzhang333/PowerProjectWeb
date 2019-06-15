$(function(){
	var projectName = $('#projectName').val();
	var prefix = projectName + '.';
	var conf = {
		'normal':'普通用户',
		'tech':'技术员',
		'admin':'管理员',
		'overview':'全局管理员',

		'普通用户':'normal',
		'技术员':'tech',
		'管理员':'admin',
		'全局管理员':'overview'
	}

	function parsePassword(oPwd) {
		//var dotIndex = oPwd.indexOf('.');
		//return oPwd.slice(dotIndex+1);
		return oPwd.replace(prefix,'');
	}
	
	$.ajax({
		url:'getAllUsers',
		success:function (d) {
			var d = JSON.parse(d);
			var _html = '';
			for(var i = 0; i < d.length; i++){
				console.log(parsePassword(d[i]["password"]))
				var htmlTemplate = '<tr><td>'+d[i]["id"]+'</td><td>'+d[i]["username"]+'</td><td>'+parsePassword(d[i]["password"])+'</td><td>'+conf[d[i]["authority"]]+'</td><td>' +
					'<button type="button" class="am-btn am-btn-sm am-radius am-btn-danger">删除</button> ' +
					'<button type="button" class="am-btn am-btn-sm am-radius am-btn-edit">编辑</button>' +
					'<button type="button" class="am-btn am-btn-sm am-radius am-btn-primary ">提交</button> </tr>';
				_html = _html + htmlTemplate;
			}
			$('table>tbody').html(_html)
		}
	})


	$('table').on('click','.am-btn-edit',(function(e){
		var isEdited = 0;
		var pwd,permission;
		return function(e){
			if(isEdited == 0){

				var $tr = $(this).parentsUntil('tbody','tr');
				var $pwd = $tr.find('td:nth-child(3)')
				var $permission = $tr.find('td:nth-child(4)')

				pwd = $pwd.html();
				permission = $permission.html();

				var pwdHtml = '<input type="text" value="'+pwd+'"/>';
				$pwd.html(pwdHtml)
				var permissonHtml =
				'<select data-am-selected>'
					+ (permission == '普通用户' ? '<option value="normal" selected>普通用户</option>' : '<option value="1">普通用户</option>')
					+ (permission == '技术员' ? '<option value="tech" selected>技术员</option>' : '<option value="2">技术员</option>&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;')
					+ (permission == '管理员' ? '<option value="admin" selected>管理员</option>' : '<option value="3">管理员</option>')
					+ (permission == '全局管理员' ? '<option value="overview" selected>全局管理员</option>' : '<option value="4">全局管理员</option>')
				+'</select>';
				$permission.html(permissonHtml)

			}else{
				var $tr = $(this).parentsUntil('tbody','tr');
				var $pwd = $tr.find('td:nth-child(3)')
				var $permission = $tr.find('td:nth-child(4)')
				$pwd.html($tr.find('input').val())
				$permission.html($tr.find('option:selected').html())

			}
			isEdited = (isEdited + 1) % 2;
		}
	})())


	$('table').on('click','.am-btn-primary',function(e){
		var $tr = $(this).parentsUntil('tbody','tr');
		var $id = $tr.find('td:nth-child(1)')
		var $username = $tr.find('td:nth-child(2)')
		var $pwd = $tr.find('td:nth-child(3)')
		var $permission = $tr.find('td:nth-child(4)')
		var isEdited = $tr.find('input').length != 0;
		if(!isEdited){
			// console.log($id.html())
			// console.log($username.html())
			// console.log($pwd.html())
			// console.log($permission.html())

			$.ajax({
				url:'modifyUserById',
				method:'POST',
				data:{
					userinfo:JSON.stringify({id:$id.html(),username:$username.html(),password:prefix+$pwd.html(),authority:conf[$permission.html()]})
				},
				success:function(d){
					console.log(d)
					alert(d);
					window.location.reload();
				}
			})
		}else{
			alert('先取消编辑状态')
		}
	});


	$('table').on('click','.am-btn-danger',function (e) {
		var $tr = $(this).parentsUntil('tbody','tr');
		var $id = $tr.find('td:nth-child(1)')
		$.ajax({
			url:'deleteUser',
			type:'GET',
			data:{
				userId:$id.html()
			},
			success:function (d) {
				if(d == 'success'){
					alert('删除成功');
					window.location.reload();
				}else{
					alert("删除失败");
				}
			}
		})
	})

	
})