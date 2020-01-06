package com.yc.fresh.service;

import java.util.List;
import java.util.Map;

import com.yc.fresh.entity.GoodsInfo;

public interface IGoodsInfoService {
	/**
	 * 添加商品信息
	 * @param gf
	 * @return
	 */
	public int add(GoodsInfo gf);
	
	/**
	 * 查询所有类型以及每种类型的前4条数据
	 * @return
	 */
	public Map<String, Object> finds();

	public GoodsInfo findByGno(Integer gno);

	public Map<String, Object> findByFirst(Integer page, Integer rows, Integer tno);

	public List<GoodsInfo> findByPage(Integer page, Integer rows, Integer tno);
}
