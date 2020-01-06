// JavaScript Document

//获取元素
var  $nickname = $("#nickname");
//输出到控制台
//用户名验证
//失去焦点事件
$("#nickname").blur (function(){
	//获取用户输入的用户名
	var nickname = $("#nickname").val();
	//判断用户名的格式
	if(nickname == '' ){
		//给当前对象的下一个标签设值
		$(this).next().html("用户不能为空");
		//未设值前该标签是隐藏的  此时就必须显示来
		$(this).next().show();
	}
	//正则表达式
	//创建一个规则
	var reg = /^[\u4400-\u9fa5\w]{2,15}$/;
	//判断
	/*
	*/
	if( !reg.test(nickname)){
		//给当前对象的下一个标签设值
		$(this).next().html("用户名必须是2-15位的汉字、英文或数字");
		//未设值前该标签是隐藏的  此时就必须显示来
		$(this).next().show();
	}
});

//聚焦事件
$("#nickname").focus(function(){
	//隐藏
	$(this).next().hide();
})


//密码验证	
//失焦事件
$("#pwd").blur(function(){
	check_pwd();	
});

//聚焦事件
$("#pwd").focus(function(){
	$(this).next().hide();
});
	
function check_pwd(){
	//获取密码
	var pwd = $("#pwd").val();
	//规则
	var reg = /^[\w@!#$%^&*~]{6,15}$/;
	//判断
	if(!reg.test(pwd)){
		$("#pwd").next().html("密码不符合规范");
		$("#pwd").next().show();
	}
}

//确认密码验证
//失焦事件
$("#cpwd").blur(function(){
	check_cpwd();	
});

//聚焦事件
$("#cpwd").focus(function(){
	$(this).next().hide();
});
	
function check_cpwd(){
	//获取原密码
	var pwd = $("#pwd").val();
	//获取再次输入的密码
	var cpwd = $("#cpwd").val();
	
	//判断
	if(cpwd == ''){
		$("#cpwd").next().html("确认密码不能为空");
		$("#cpwd").next().show();
	}
	if(cpwd != pwd){
		$("#cpwd").next().html("两次输入的密码不一致");
		$("#cpwd").next().show();
	}
}

//手机号码验证
//失焦事件
$("#tel").blur(function(){
	check_tel();	
});

//聚焦事件
$("#tel").focus(function(){
	$("#tel").next().hide();
});
	
function check_tel(){
	//获取电话号码
	var tel = $("#tel").val();
	//规则
	var reg = /^[1][3,4,5,7,8,9][0-9]{9}$/;
	
	if( !reg.test(tel)){
		$("#tel").next().html("手机号码格式错误");
		$("#tel").next().show();
	}
}

$("#email").blur(function(){
	var email = $("#email").val();
	//规则
	var reg = /^[\da-zA-Z]+@[\da-z]+\.[a-z.]{2,}$/;
	
	if( !reg.test(email)){
		$("#email").next().next().html("邮箱格式错误");
		$("#email").next().next().show();
	}
});

//聚焦事件
$("#email").focus(function(){
	$("#email").next().next().hide();
});


//获取验证码
function sendCode(){
	$("#nickname").blur();
	$("#email").blur();
	
	if ($("#nickname").next().css("display") != "none") {
		return;
	}
	
	if ($("#email").next().next().css("display") != "none") {
		return;
	}
	
	var email = $.trim($("#email").val());
	var nickName = $.trim($("#nickname").val());
	
	$("#getCode").attr("disabled", "true"); // 不允许再点击，避免用户重复提交
	$("#email").attr("readonly", "true");
	
	//请求方式  地址  参数  回调函数
	$.post("member/code",{email:email, nickName:nickName},function(data){
		data = parseInt($.trim(data));
		
		if (data > 0) { // 说明验证码发送成功，那么要开始倒计时
			// 倒计时
			var time = 180;
			var timetask = setInterval(function(){
				if (time > 0) {
					time --;
					$("#getCode").val(time + "S");
				} else {
					$("#getCode").removeAttr("disabled").val("重新获取");
					$("#email").removeAttr("readonly");
					clearInterval(timetask);
				}
				
			}, 1000);
			
		} else { // 发送失败
			$("#getCode").removeAttr("disabled").val("重新获取");
			$("#email").removeAttr("readonly");
			$("getCode").next().text("验证码发送失败，请重试...").show();
		}
	})
	
}
	

//协议
$("#allow").click(function(){
	//判断checkbox是否被选中
	if(!$(this).is(":checked")){
		$(this).next().next().html("您必须同意公司的协议");
		$(this).next().next().show();
		//给注册按钮设置禁用属性
		$("#reg").attr("disabled","disabled");
	}else{
		$(this).next().next().html("");
		$(this).next().next().hide();
		//移除注册按钮的禁用属性
		$("#reg").removeAttr("disabled");
	}
})
	
	
//注册
function checkRegister(){
	$("#myform input").each(function(){
		$(this).blur();
	})
	
	var spans = $(".error_tip");
	
	for (var i = 0, len = spans.length; i < len; i ++) {
		if ($(spans[i]).css("display") != "none") { // 说明这个文本框中的值有问题
			return;
		}
	}
	
	// 如果所有的校验都通过，则提交表单
	$.post("member/reg", $("#myform").serialize(), function(data){
		data = parseInt($.trim(data));
		
		if (data == -2) {
			$("#errmsg").text("验证码已过期，请重新获取...").show();
		} else if (data == -3) {
			$("#errmsg").text("验证码错误...").show();
		} else if (data == -1) {
			$("#errmsg").text("您输入的信息不完整，请完善后重试...").show();
		} else if (data > 0) {
			$("#myform")[0].reset(); // 重置表单
			$("#errmsg").css("color", "green").text("注册成功，请先登录..").show();
			setTimeout(function(){
				location.href="login.html";
			}, 2000);
		} else {
			$("#errmsg").text("注册失败，请稍后重试...").show();
		}
	}, "text");
}
	
	
	