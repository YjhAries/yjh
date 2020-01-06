// JavaScript Document
//商品数量的添加
function add(obj, cno){	
	//获取购物车中点击的商品数量
	var num = $(obj).prev().val();
	num++;
	
	$.post("../cart/update", {cno:cno, num:1}, function(data) {
		data = parseInt($.trim(data));
		if (data > 0) {
			//数量写入标签中
			$(obj).prev().val(num);	
			//获取单价
			var price = $(obj).parent().parent().prev().html();
			//获取小计
			var total = num*price*1.00;
			//获取小计标签
			var $subtotal = $(obj).parent().parent().next();
			//小计价钱写入标签中
			$subtotal.text(total.toFixed(2));
			productCount();
			$("#goods_nums").text(  parseInt($("#goods_nums").text()) + 1 );
		}
	},"text");
}

//保留两位小数的方位
function returnFloat(value){
	//Math js中的静态的数学对象  round四舍五入
	 var value=Math.round(value*100)/100;
	//转换成字符串并以.分割成数组
	 var xsd=value.toString().split(".");
	//判断数组长度
	 if(xsd.length==1){ //整数
	 	value=value.toString()+".00";//拼接
		return value;
	 }
	 if(xsd.length>1){ //带小数
	 	if(xsd[1].length<2){
	 		value=value.toString()+"0";
	 	}
		 return value;
	}
}

//删除商品
function delGoods(obj, cno){
	//温馨提示
	var result = confirm("您确定要删除购物车中当前商品吗？");
	if (!result) {
		return;
	}
	
	$.post("../cart/del", {cno:cno}, function(data){
		data = parseInt($.trim(data));
		if (data > 0) {
			var num = $(obj).parent().parent().find("input[type='text']").val();
			$("#show_count").text(  parseInt($("#show_count").text()) - 1 );
			$("#goods_nums").text( parseInt($("#goods_nums").text()) - num );
			
			//找到对应的UL
			$ul = $(obj).parent().parent();
			//判断
			$ul.remove();
			productCount();
		}
	}, "text");
}

//全选和全不选
$("#all").click(function(){
	//获取全选是否被选中
	//prop 获取标签的固有属性  attr 自动义的属性
	var flag = $(this).prop("checked");
	//判断    
	$(".cart_list_td ul .col01 input").prop("checked",flag);
});

//商品数量的减法
function lost(obj, cno){
	//获取购物车中点击的商品数量
	var num = $(obj).next().val();
	//判断此商品的数量是否大于1
	if( num <= 1){
		return; 
	}
	num--;
	
	$.post("../cart/update", {cno:cno, num:-1}, function(data) {
		data = parseInt($.trim(data));
		if (data > 0) {
			//数量写入标签中
			$(obj).next().val(num);	
			//获取单价
			var price = $(obj).parent().parent().prev().html();
			//获取小计
			var total = num*price*1.00;
			//获取小计标签
			var $subtotal = $(obj).parent().parent().next();
			//小计价钱写入标签中
			total = returnFloat(total);
			$subtotal.text(total);
			
			productCount();
			$("#goods_nums").text(  parseInt($("#goods_nums").text()) - 1 );
		}
	},"text");
}



$(function(){
	$.post("../member/check", null, function(data) {
	 	var str = "";
	 	if(data.mno) {
			str += '<div class="login_btn fl"><a href="#">欢迎您 &nbsp;['+data.nickName+']</a><span> | </span>';
			str += '<a href="javascript:menberLogin()">注销</a><span> | </span><a href="register.html">注册</a></div>';
		} else {
			str += '<div class="login_btn fl">';
			str += '<a href="javascript:menberLogin()">登录</a><span> | </span><a href="register.html">注册</a></div>';
		}
		str += '<div class="user_link fl"><span> | </span><a href="#">用户中心</a><span> | </span>';
		str += '<a href="front/cart.html">我的购物车</a><span> | </span><a href="#">我的订单</a></div> ';
		$("#head_info").append($(str));
	}, "json");
	
	
	$.get("../cart/finds", null, function(data){
		$("#show_count").text(data.length);
		
		var str = "";
		$.each(data, function(index, item) {
			str += '<ul><li class="col01"><input type="checkbox" checked></li><li class="col02"><img src="../../'+item.pics.split(";")[0]+'"></li>';
			str += '<li class="col03">'+item.gname+'<br><em>'+item.weight+'/'+item.unit+'</em></li><li class="col04">'+item.weight+'</li>';
			str += '<li class="col05">'+item.price+'</li><li class="col06"><div class="num_add">';
			str += '<a href="javascript:void(0)" onclick="lost(this,\''+item.cno+'\')" class="minus fl">-</a><input type="text" value="'+item.num+'" class="num_show fl">';
    		str += '<a href="javascript:void(0)" onclick="add(this,\''+item.cno+'\')" class="add fl">+</a></div></li>';
    		str += '<li class="col07">&yen;'+(item.num * item.price).toFixed(2)+'</li>';
        	str += '<li class="col08"><a href="javascript:;" onclick="delGoods(this, \''+item.cno+'\')">删除</a></li></ul>';
		});
		$("#cart_list").append($(str));
		
		productCount(); // 计算价格和数量
		
		
		//给这些多选按钮绑定事件
		var checkbox=$("cart_list input[type='checkbox']");
		var len=checkboxs.length;
		for(var i=0;i<len;i++){
			//循环给每一个绑定
			checkboxs[i].onclick=function(){
				productCount();//重新计算总价信息
				
				//判断有没有选择的，如果有则全取消，否则全部选中
				for(var j=0;j<len;j++){
					if(!checkboxs[j].checked){
						//只要有一个没选择，则全选就不能选中
						$("#all").prop("checked",true);
						return;
					}
				}
				$("#all").prop("checked",true);
			}
		}
	}, "json");
})

//计算商品总额的方法
function productCount(){
	var total=0;  //总价
	var price;//每一行的市场价
	var number;//每一行的数量
	var numbers=0;//总数量
	var myul=$(".cart_list_td ul");

	for(var i=0;i<myul.length;i++){//循环每一行
		if ($(myul[i]).find("input[type='checkbox']").prop("checked") ){
			price=$(".cart_list_td  ul:eq("+i+")").find(".col05").html();
			number=$(".cart_list_td  ul:eq("+i+")").find(".col06 input").val();
			total+=price*number;
			numbers+= number*1.0;
		}
	}
	$("#totalPrices").text(total.toFixed(2));
	$("#totalNumbers").text(numbers);
	$("#total_goods_count").text(numbers);
}

//去结算，首先需要添加一个订单信息，然后可能要添加多条订单详细信息，还需要删除购物车信息-》 首先必须获取用户选中的商品
function gotoPay(){
	var strs=[];
	$("#cart_list input[type='checkbox']:checked").each(function(){
		strs[strs.length]=$(this).val();
	})
	
	if(strs.length<=0){//说明没有选中任何商品}
		return;
	}
	
	$.post("../order/order",{
		cons:strs.join(";")
	},function(data){
		data=parseInt($.trim(data));
		if(data>0){
			location.href="order.html";
		}else{
			alert("下单失败，请稍后重试！")
		}
	},"text")
}

