package com.yc.fresh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.GoodsInfo;
import com.yc.fresh.mapper.GoodsInfoMapper;
import com.yc.fresh.mapper.GoodsTypeMapper;
import com.yc.fresh.service.IGoodsInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService {
	@Autowired
	private GoodsInfoMapper goodsInfoMapper;
	
	@Autowired
	private GoodsTypeMapper goodsTypeMapper;
	
	@Override
	public int add(GoodsInfo gf) {
		if (StringUtil.checkNull(gf.getGname(), gf.getPics(), gf.getIntro())) {
			return -1;
		}
		return goodsInfoMapper.add(gf);
	}

	/**
	 * 查询所有类型以及每种类型的前4条数据
	 * @return
	 */
	public Map<String, Object> finds() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", goodsInfoMapper.finds());
		map.put("types", goodsTypeMapper.finds());
		return map;
	}

	@Override
	public GoodsInfo findByGno(Integer gno) {
		return goodsInfoMapper.findByGno(gno);
	}

	@Override
	public Map<String, Object> findByFirst(Integer page, Integer rows, Integer tno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goods", findByPage(page, rows, tno));
		map.put("total", goodsInfoMapper.getTotal(tno));
		return map;
	}

	@Override
	public List<GoodsInfo> findByPage(Integer page, Integer rows, Integer tno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tno", tno);
		map.put("page", (page - 1) * rows);
		map.put("rows", rows);
		return goodsInfoMapper.findByPage(map);
	}
}
