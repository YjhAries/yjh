package com.yc.fresh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.fresh.entity.CartInfo;
import com.yc.fresh.mapper.CartInfoMapper;
import com.yc.fresh.service.ICardInfoService;
import com.yc.fresh.util.StringUtil;

@Service
public class CartInfoServiceImpl implements ICardInfoService {
	@Autowired
	private CartInfoMapper cartInfoMapper;
	
	@Override
	public List<CartInfo> findByMno(int mno) {
		return cartInfoMapper.findByMno(mno);
	}

	@Override
	public int update(String cno, int num) { // 添加数量
		if (StringUtil.checkNull(cno)) {
			return -1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cno", cno);
		map.put("num", num);
		return cartInfoMapper.update(map);
	}
	
	@Override
	public List<CartInfo> finds(int mno) {
		return cartInfoMapper.finds(mno);
	}

	@Override
	public String add(CartInfo cf) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		cf.setCno(uuid); // 生成主键列的值
		if (cartInfoMapper.add(cf) > 0) {
			return uuid;
		}
		return "";
	}
}
