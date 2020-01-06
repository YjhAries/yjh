package com.yc.fresh.mapper;

import java.util.List;
import java.util.Map;

import com.yc.fresh.entity.CartInfo;

public interface CartInfoMapper {
	/**
	 * 根据会员编号，查询会员购物车信息
	 * @param mno
	 * @return
	 */
	public List<CartInfo> findByMno(int mno);

	/**
	 * 数量每次加1
	 * @param cf
	 * @return
	 */
	public int update(Map<String, Object> map);

	/**
	 * 根据会员编号，查询购物车详细
	 * @param mno
	 * @return
	 */
	public List<CartInfo> finds(int mno);
	
	/**
	 * 添加购物车
	 * @param cf
	 * @return
	 */
	public int add(CartInfo cf);
}
