package com.yc.fresh.service;

import java.util.List;

import com.yc.fresh.entity.GoodsType;

public interface IGoodsTypeService {
	public List<GoodsType> finds();

	public int add(GoodsType gt);
}
