package com.yc.fresh.mapper;

import java.util.List;
import java.util.Map;

import com.yc.fresh.entity.GoodsInfo;

public interface GoodsInfoMapper {
	/**
	 * 添加商品信息
	 * @param gf
	 * @return
	 */
	public int add(GoodsInfo gf);
	
	/**
	 * 查每种类型的前4种商品
	 * @return
	 */
	public List<GoodsInfo> finds();

	/**
	 * 根据商品编号，查询商品信息
	 * @param gno
	 * @return
	 */
	public GoodsInfo findByGno(Integer gno);

	/**
	 * 根据类型编号分页查询的方法
	 * @param map
	 * @return
	 */
	public List<GoodsInfo> findByPage(Map<String, Object> map);

	/**
	 * 获取总记录数的方法
	 * @param tno
	 * @return
	 */
	public Object getTotal(Integer tno);
}
