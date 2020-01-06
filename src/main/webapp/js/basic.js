$(function(){
	$.get("member/check", null, function(data) {
		var str = "";
		if (data.mno) { // 说明登录了
			str += '<div class="login_info fl"><a href="#">欢迎您&nbsp;['+data.nickName+']</a><span> | </span>';
			str += '<a href="member/loginout">注销</a><span> | </span><a href="register.html">注册</a>';
			
			getCartInfo(); // 查询当前登录用户的购物车信息
		} else {
			str += ' <div class="login_btn fl"><a href="login.html">登录</a><span> | </span>';
			str += ' <a href="register.html">注册</a>';
		}
		str += '</div><div class="user_link fl"><span> | </span><a href="#">用户中心</a><span> | </span>';
		str += ' <a href="front/cart.html">我的购物车</a><span> | </span><a href="front/order.html">我的订单</a></div>';
		$("#login_info").append($(str));
	}, "json");
})

var cart_infos = "";

function getCartInfo() {
	$.get("cart/info", null, function(data) {
		cart_infos = data;
		
		$("#show_count").text(data.length);
	}," json");
}