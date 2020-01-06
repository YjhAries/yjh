package com.yc.fresh.service;

import java.util.List;

import com.yc.fresh.entity.CartInfo;

public interface ICardInfoService {
	public List<CartInfo> findByMno(int mno);

	public String add(CartInfo cf);

	public int update(String cno, int num);

	public List<CartInfo> finds(int mno);
}
