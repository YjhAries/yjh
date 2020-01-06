package com.yc.fresh.mapper;

import java.util.List;

import com.yc.fresh.entity.GoodsType;

public interface GoodsTypeMapper {
	public List<GoodsType> finds();

	public int add(GoodsType gt);
}
