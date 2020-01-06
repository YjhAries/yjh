package com.yc.fresh.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.fresh.entity.CartInfo;
import com.yc.fresh.entity.MemberInfo;
import com.yc.fresh.service.ICardInfoService;

@RestController
@RequestMapping("/cart")
public class CartInfoController {
	@Autowired
	private ICardInfoService cartInfoService;
	
	@RequestMapping("/info")
	public List<CartInfo> getInfo(HttpSession session) {
		Object obj = session.getAttribute("currenrLoginUser");
		if (obj == null) {
			return Collections.emptyList();
		}
		MemberInfo mf = (MemberInfo) obj;
		return cartInfoService.findByMno(mf.getMno());
	}
	
	@RequestMapping("/update")
	public int update(String cno, int num) {
		return cartInfoService.update(cno, num);
	}
	
	@RequestMapping("/finds")
	public List<CartInfo> finds(HttpSession session) {
		Object obj = session.getAttribute("currenrLoginUser");
		if (obj == null) {
			return Collections.emptyList();
		}
		MemberInfo mf = (MemberInfo) obj;
		return cartInfoService.finds(mf.getMno());
	}
	
	@RequestMapping("/add")
	public String add(HttpSession session, CartInfo cf) {
		Object obj = session.getAttribute("currenrLoginUser");
		if (obj == null) {
			return "0";
		}
		MemberInfo mf = (MemberInfo) obj;
		cf.setMno(mf.getMno());
		return cartInfoService.add(cf);
	}
}
